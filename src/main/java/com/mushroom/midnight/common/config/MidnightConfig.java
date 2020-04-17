package com.mushroom.midnight.common.config;

import com.google.common.collect.Lists;
import com.mushroom.midnight.common.config.ifc.*;
import com.mushroom.midnight.common.config.provider.ConfigProfile;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

import static com.mushroom.midnight.Midnight.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MidnightConfig {
    /*
     * RGSW: Configs
     *
     * Loader (common options according to how the mod operates within forge, FML and other mods)
     * NYI: When we have something for this category, we can add it under a common config
     *
     * Client (client rendering and sound options)
     * - Ambient Sounds Volume
     * - Sound Echo Volume
     * - Sound Echo Delay
     * - Hide Vignette Effect
     * - Show Ambient Spore Particles
     *
     * World Generator (server world generator options)
     * - Rift Structure Chance
     * - Rift Biome Blacklist
     * - Rift Biome Whitelist
     * - Well Structure Chance
     * - Guardtower Structure Chance
     * - Molten Crater Chance
     *
     * Server Logic (server logic options)
     * - Bladeshroom Damage Chance
     * - Can Respawn In Midnight
     * - Monster Spawn Rate
     * - Hunters Attack Tamed Skulks
     * - Hunters Attack Named Skulks
     * - Foreign Flowers From Bonemeal
     * - Allow Rift From Dark Pearl
     * - Rifts Are Open During Daytime
     *
     * **Lightning**
     * - Random Lightning Effects
     * - Allow Lightning Damage
     *
     * **Rifters**
     * - Rifter Spawn Rarity
     * - Max Rifters From Rift
     * - Allow Rifter Teleport
     * - Rifters Capture Tamed Animals
     * - Rifters Capture Named Animals
     * - Capturable Entities
     * - Not Capturable Entities
     *
     */

    public static final ConfigProfile PROFILE = new ConfigProfile();

    public static class CatWorldGen {
        public final ForgeConfigSpec.ConfigValue<Boolean> randomConfig;

        public CatWorldGen(ForgeConfigSpec.Builder builder) {
            builder.comment("World generator utilities.").push("worldgen");
            randomConfig = builder
                    .comment("This is a random config value in the category worldgen")
                    .define("random_config", true);
            PROFILE.add(randomConfig, true);
            builder.pop();
        }
    }

    public static class CatGeneral {
        public final ForgeConfigSpec.ConfigValue<Integer> bladeshroomDamageChance;
        public final ForgeConfigSpec.ConfigValue<Boolean> canRespawnInMidnight;
        public final ForgeConfigSpec.ConfigValue<Boolean> allowLightningDamage;
        public final ForgeConfigSpec.ConfigValue<Integer> rifterSpawnRarity;
        public final ForgeConfigSpec.ConfigValue<Integer> maxRifterByRift;
        public final ForgeConfigSpec.ConfigValue<Boolean> allowRifterTeleport;
        public final ForgeConfigSpec.ConfigValue<Integer> monsterSpawnRate;
        public final ForgeConfigSpec.ConfigValue<Boolean> rifterCaptureTamedAnimal;
        public final ForgeConfigSpec.ConfigValue<List<String>> capturableEntities;
        public final ForgeConfigSpec.ConfigValue<List<String>> notCapturableAnimals;
        public final ForgeConfigSpec.ConfigValue<Boolean> foreignFlowersFromBonemeal;

        public CatGeneral(ForgeConfigSpec.Builder builder) {
            builder.comment("All the options that can only be set on the server.").push("general");
            bladeshroomDamageChance = builder
                    .comment("The chance to take a small amount of damage when picking a bladeshroom cap as a percentage. If 0, this is disabled. Default= 5")
                    .translation(getTranslation("bladeshroom_damage_chance"))
                    .defineInRange("bladeshroom_damage_chance", 5, 0, 100);
            canRespawnInMidnight = builder
                    .comment("If true, players will respawn in Midnight after dying in the dimension. Default= false")
                    .translation(getTranslation("can_respawn_in_midnight"))
                    .define("can_respawn_in_midnight", false);
            allowLightningDamage = builder
                    .comment("Allows the lightning in Midnight to burn blocks and do damage to entities. Default= false")
                    .translation(getTranslation("allow_lightning_damage"))
                    .define("allow_lightning_damage", false);
            rifterSpawnRarity = builder
                    .comment("The rarity that rifts spawn rifters. Larger numbers increase rarity. If 0, rifters don't spawn. Default= 1000")
                    .translation(getTranslation("rifter_spawn_rarity"))
                    .defineInRange("rifter_spawn_rarity", 1000, 0, 10000);
            maxRifterByRift = builder
                    .comment("The maximum amount of rifters that spawn from a rift. Default= 1")
                    .translation(getTranslation("max_rifter_by_rift"))
                    .defineInRange("max_rifter_by_rift", 1, 1, 5);
            allowRifterTeleport = builder
                    .comment("Allows rifters to teleport to players when they aren't being looked at. Default= true")
                    .translation(getTranslation("allow_rifter_teleport"))
                    .define("allow_rifter_teleport", true);
            monsterSpawnRate = builder
                    .comment("Spawn rate of entities of the 'monster' type in midnight (eg. Rifter, Hunter). Default= 1")
                    .translation(getTranslation("monster_spawn_rate"))
                    .defineInRange("monster_spawn_rate", 1, 1, 10000);
            rifterCaptureTamedAnimal = builder
                    .comment("Allows rifters to capture tamed entities and drag them into rifts. Default= false")
                    .translation(getTranslation("rifter_capture_tamed_animal"))
                    .define("rifter_capture_tamed_animal", false);
            capturableEntities = builder
                    .comment("Mobs on this whitelist can be captured by rifters, players and animals are already captured by default. Format is mod id:name of creature, just use the modid to whitelist everything from it. Default= empty")
                    .translation(getTranslation("capturable_entities"))
                    .define("capturable_entities", new ArrayList<>());
            notCapturableAnimals = builder
                    .comment("Mobs on this blacklist cannot be captured by rifters. Format is mod id:name of creature, just use the mod id to blacklist everything from it. Default= iceandfire, midnight:nightstag")
                    .translation(getTranslation("not_capturable_animals"))
                    .define("not_capturable_animals", Lists.newArrayList("iceandfire", "midnight:nightstag"));
            foreignFlowersFromBonemeal = builder
                    .comment("Allows the modded flowers to appear with bonemeal in Midnight. Default= false")
                    .translation(getTranslation("foreign_flowers_from_bonemeal"))
                    .define("foreign_flowers_from_bonemeal", false);
            PROFILE.add(bladeshroomDamageChance);
            PROFILE.add(notCapturableAnimals);
            builder.pop();
        }
    }

    public static class CatClient {
        public final ForgeConfigSpec.ConfigValue<Boolean> hideVignetteEffect;
        public final ForgeConfigSpec.ConfigValue<Boolean> riftShaders;

        public CatClient(ForgeConfigSpec.Builder builder) {
            builder.comment("All the options that can be modified by players on server.").push("client");
            hideVignetteEffect = builder
                    .comment("Hides the vignette effect in the darker areas of Midnight. Default=false")
                    .translation(getTranslation("hide_vignette_effect"))
                    .define("hide_vignette_effect", false);
            riftShaders = builder
                    .comment("If true, rifts will be rendered with custom shaders for standard effects. If false, a simpler (motionless) texture will be rendered. Default=true")
                    .translation(getTranslation("rift_shaders"))
                    .define("rift_shaders", true);
            PROFILE.add(hideVignetteEffect);
            PROFILE.add(riftShaders);
            builder.pop();
        }
    }

    private static String getTranslation(String name) {
        return "config." + MODID + "." + name;
    }

    public static final ConfigInterface.Factory SERVER_IFC = provider -> {
        ConfigInterface ifc = new ConfigInterface(new TranslationTextComponent("config.midnight.title.server"), provider, true, EditAccess.SERVER_HOST);
        ifc.setting("config.midnight.random_config", new ToggleButtonControl(), "worldgen.random_config");
        return ifc;
    };

    public static final ConfigInterface.Factory CLIENT_IFC = provider -> {
        ConfigInterface ifc = new ConfigInterface(new TranslationTextComponent("config.midnight.title.client"), provider, true, EditAccess.ALWAYS);
        ifc.setting("config.midnight.hide_vignette_effect", new ToggleButtonControl(), "client.hide_vignette_effect");
        ifc.setting("config.midnight.rift_shaders", new ToggleButtonControl(), "client.rift_shaders");
        ifc.setting("config.midnight.bladeshroom_damage_chance", new IntSliderControl(0, 100, "config.midnight.format.number_percentage", "config.midnight.format.never", "config.midnight.format.always"), "general.bladeshroom_damage_chance");
        ifc.setting("config.midnight.not_capturable_animals", new StringListControl("config.midnight.not_capturable_animals"), "general.not_capturable_animals");
        return ifc;
    };

    public static final ConfigInterface.Factory MAIN_IFC = provider -> {
        ConfigInterface ifc = new ConfigInterface(new TranslationTextComponent("config.midnight.title"), provider, false, EditAccess.ALWAYS);
        ifc.category("config.midnight.category.client", CLIENT_IFC.makeInterface(provider));
        ifc.category("config.midnight.category.server", SERVER_IFC.makeInterface(provider));
        return ifc;
    };


    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final CatClient client;

    public static final ForgeConfigSpec SERVER_SPEC;
    public static final CatWorldGen worldgen;

    static {
        Pair<CatClient, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CatClient::new);
        CLIENT_SPEC = specPair.getRight();
        client = specPair.getLeft();

        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        worldgen = new CatWorldGen(builder);
        SERVER_SPEC = builder.build();
    }

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final CatGeneral general = new CatGeneral(BUILDER);

    public static final ForgeConfigSpec GENERAL_SPEC = BUILDER.build();

    /*@SubscribeEvent
    public static void configChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            // not yet gui in game for config
            //ConfigManager.sync(Midnight.MODID, Config.Type.INSTANCE);
        }
    }*/
}
