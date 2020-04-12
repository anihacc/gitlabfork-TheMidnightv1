package com.mushroom.midnight;

import com.google.common.reflect.Reflection;
import com.mushroom.midnight.client.ClientProxy;
import com.mushroom.midnight.client.model.MidnightModelRegistry;
import com.mushroom.midnight.common.ServerProxy;
import com.mushroom.midnight.common.capability.*;
import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.data.loot.MidnightBlockLootProvider;
import com.mushroom.midnight.common.data.recipe.*;
import com.mushroom.midnight.common.data.tag.MidnightBlockTagsProvider;
import com.mushroom.midnight.common.data.tag.MidnightFluidTagsProvider;
import com.mushroom.midnight.common.data.tag.MidnightItemTagsProvider;
import com.mushroom.midnight.common.loot.InBiomeLootCondition;
import com.mushroom.midnight.common.loot.InBlockLootCondition;
import com.mushroom.midnight.common.loot.IsChildLootCondition;
import com.mushroom.midnight.common.network.AnimationMessage;
import com.mushroom.midnight.common.network.CaptureEntityMessage;
import com.mushroom.midnight.common.network.ItemActivationMessage;
import com.mushroom.midnight.common.network.RockshroomBrokenMessage;
import com.mushroom.midnight.common.registry.*;
import com.mushroom.midnight.common.util.EntityUtil;
import com.mushroom.midnight.common.util.IProxy;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Midnight.MODID)
@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class Midnight {
    public static final String MODID = "midnight";
    public static final String NETWORK_PROTOCOL = "2";

    public static final Logger LOGGER = LogManager.getLogger(Midnight.class);
    public static final IProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MODID, "net"))
            .networkProtocolVersion(() -> NETWORK_PROTOCOL)
            .clientAcceptedVersions(NETWORK_PROTOCOL::equals)
            .serverAcceptedVersions(NETWORK_PROTOCOL::equals)
            .simpleChannel();

    // TODO not sure why there're 2 strings param here (they can be replaced with normal entityClassification and directly set their number max in the mob spawner)
    public static final EntityClassification MIDNIGHT_MOB = EntityClassification.create("midnight_mob", "midnight_mob", 10, false, false);
    public static final EntityClassification MIDNIGHT_AMBIENT = EntityClassification.create("midnight_ambient", "midnight_ambient", 30, true, false);

    @CapabilityInject(RiftTraveller.class)
    public static final Capability<RiftTraveller> RIFT_TRAVELLER_CAP = RegUtil.injected();

    @CapabilityInject(RifterCapturable.class)
    public static final Capability<RifterCapturable> RIFTER_CAPTURABLE_CAP = RegUtil.injected();

    @CapabilityInject(AnimationCapability.class)
    public static final Capability<AnimationCapability> ANIMATION_CAP = RegUtil.injected();

    public Midnight() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, MidnightConfig.CLIENT_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MidnightConfig.GENERAL_SPEC);

        setupMessages();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientProxy::setup));

        bus.addListener(this::setup);
        bus.addListener(this::registerModels);
        bus.addListener(this::gatherData);
    }

    private void setup(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(RiftTraveller.class, new RiftTravellerStorage<>(), RiftTraveller::new);
        CapabilityManager.INSTANCE.register(RifterCapturable.class, new NullStorage<>(), RifterCapturable::new);
        CapabilityManager.INSTANCE.register(AnimationCapability.class, new NullStorage<>(), AnimationCapability::new);

        Reflection.initialize(MidnightCriterion.class, MidnightItemGroups.class, MidnightGameRules.class);

        EntityUtil.register();

        LootConditionManager.registerCondition(new InBiomeLootCondition.Serializer());
        LootConditionManager.registerCondition(new InBlockLootCondition.Serializer());
        LootConditionManager.registerCondition(new IsChildLootCondition.Serializer());

        setupWorldGen();
    }

    private void setupMessages() {
        CHANNEL.messageBuilder(CaptureEntityMessage.class, 0)
                .encoder(CaptureEntityMessage::serialize).decoder(CaptureEntityMessage::deserialize)
                .consumer(CaptureEntityMessage::handle)
                .add();

        CHANNEL.messageBuilder(AnimationMessage.class, 1)
                .encoder(AnimationMessage::serialize).decoder(AnimationMessage::deserialize)
                .consumer(AnimationMessage::handle)
                .add();

        CHANNEL.messageBuilder(RockshroomBrokenMessage.class, 2)
                .encoder(RockshroomBrokenMessage::serialize).decoder(RockshroomBrokenMessage::deserialize)
                .consumer(RockshroomBrokenMessage::handle)
                .add();

        CHANNEL.messageBuilder(ItemActivationMessage.class, 3)
                .encoder(ItemActivationMessage::serialize).decoder(ItemActivationMessage::deserialize)
                .consumer(ItemActivationMessage::handle)
                .add();
    }

    private static void setupWorldGen()
    {
        MidnightSurfaceBiomes.onInit();
        MidnightCavernousBiomes.onInit();
        initStructures();
    }

    /**
     * This method uses a for loop to add the Entrance Rift to all biomes in the Overworld and The Midnight.
     * It does this by grabbing every registered biome and checking the Biome Dictionay to make sure that
     * - the biome is from Minecraft, The Midnight, or Biomes o' Plenty
     * - the biome is not from the Nether, The End, the Void, Oceans, Rivers, and Mushroom biomes
     */
    public static void initStructures()
    {
        for(Biome biome : ForgeRegistries.BIOMES.getValues())
        {
            if (!BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)
                    && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.END)
                    && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.VOID)
                    && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN)
                    && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.RIVER)
                    && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.MUSHROOM)
                    && (    biome.getRegistryName().getNamespace().equals("minecraft"))
                    || (biome.getRegistryName().getNamespace().equals("midnight"))
                    || (biome.getRegistryName().getNamespace().equals("biomesoplenty"))
            )
            {
                biome.addStructure(MidnightStructures.ENTRANCE_RIFT.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
                biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, MidnightStructures.ENTRANCE_RIFT.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
            }
        }
    }

    private void registerModels(ModelRegistryEvent event) {
        MidnightModelRegistry.registerModels(event);
    }

    private void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new MidnightBlockTagsProvider(generator));
            generator.addProvider(new MidnightFluidTagsProvider(generator));
            generator.addProvider(new MidnightItemTagsProvider(generator));

            generator.addProvider(new MidnightDecorativeRecipes(generator));
            generator.addProvider(new MidnightFabricatedRecipes(generator));
            generator.addProvider(new MidnightFoodRecipes(generator));
            generator.addProvider(new MidnightMaterialRecipes(generator));
            generator.addProvider(new MidnightPlantRecipes(generator));
            generator.addProvider(new MidnightStoneRecipes(generator));
            generator.addProvider(new MidnightWoodenRecipes(generator));

            generator.addProvider(new MidnightBlockLootProvider(generator));
        }
    }
}
