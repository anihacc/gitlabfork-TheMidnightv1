package com.mushroom.midnight.common.config;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mushroom.midnight.common.config.ifc.*;
import com.mushroom.midnight.common.config.provider.ConfigProfile;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.mushroom.midnight.Midnight.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MidnightConfig {
    private static final Logger LOGGER = LogManager.getLogger();

    /*
     * RGSW: Configs
     *
     * Compat (common options according to how the mod operates within forge, FML and other mods)
     * + Rift Biomes
     * + Rift Dimensions
     *
     * Client (client rendering and sound options)
     * - Ambient Sounds Volume
     * - Sound Echo Volume
     * - Hide Vignette Effect
     * - Show Ambient Spore Particles
     *
     * World Generator (server world generator options)
     * + Rift Structure Rarity
     * + Well Structure Rarity
     * + Guardtower Structure Rarity
     * + Molten Crater Rarity
     *
     * Server Logic (server logic options)
     * - Bladeshroom Damage Chance
     * - Can Respawn In Midnight
     * - Hunters Attack Tamed Skulks
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
     *
     */

    public static final ConfigProfile SERVER_PROFILE = new ConfigProfile();
    public static final ConfigProfile PROFILE = new ConfigProfile();

    public static class CatCompat {
        public final ForgeConfigSpec.ConfigValue<List<String>> riftBiomes;
        public final ForgeConfigSpec.ConfigValue<List<String>> riftDimensions;
        public final ForgeConfigSpec.ConfigValue<List<String>> capturableEntities;
        public final ForgeConfigSpec.ConfigValue<List<String>> notCapturableAnimals;

        public CatCompat(ForgeConfigSpec.Builder builder) {
            builder.comment("Common compatibility settings.").push("compat");
            riftBiomes = builder
                    .comment(
                            "Specifies several rules that define in which biomes rifts may generate. When an empty list is given rifts won't generate. This list is order dependent.",
                            "Specifying can be done with several rules which are case insensitive:",
                            " - Prefixing a rule with '+' will add all the items that match the given selector",
                            " - Prefixing a rule with '-' will remove all the items that match the given selector",
                            " - Prefixing a rule with '~' will remove all the items that do not match the given selector",
                            " - No prefix will imply a '+' prefix",
                            " - Prefixing a selector with '!' will invert the selector",
                            " - To match a biome, simply write the biome ID",
                            " - To match a biome type, prefix the type name with '#'",
                            " - 'modid:*' will match all biomes of a specific mod",
                            " - 'id' will match vanilla biomes with the specified id",
                            " - Only specifying '*' will match all biomes",
                            " - Empty strings and whitespaces are completely ignored",
                            "Default: '+ *', '- #VOID', '- #WATER', '- #BEACH', '- #MUSHROOM'"
                    )
                    .define("rift_biomes", Lists.newArrayList(
                            "+ *",
                            "- #VOID",
                            "- #WATER",
                            "- #BEACH",
                            "- #MUSHROOM"
                    ));

            riftDimensions = builder
                    .comment(
                            "Specifies several rules that define in which dimensions rifts may generate. When an empty list is given rifts won't generate. This list is order dependent.",
                            "Specifying can be done with several rules which are case insensitive:",
                            " - Prefixing a rule with '+' will add all the items that match the given selector",
                            " - Prefixing a rule with '-' will remove all the items that match the given selector",
                            " - Prefixing a rule with '~' will remove all the items that do not match the given selector",
                            " - No prefix will imply a '+' prefix",
                            " - Prefixing a selector with '!' will invert the selector",
                            " - To match a dimension, simply write the dimension ID (resource location, not a number!!!!!)",
                            " - 'modid:*' will match all dimensions of a specific mod",
                            " - 'id' will match vanilla dimensions with the specified id",
                            " - Only specifying '*' will match all dimensions",
                            " - Empty strings and whitespaces are completely ignored",
                            "Default: '+ minecraft:overworld', '+ midnight:midnight'"
                    )
                    .define("rift_dimensions", Lists.newArrayList(
                            "+ minecraft:overworld",
                            "+ midnight:midnight"
                    ));

            capturableEntities = builder
                    .comment("Mobs on this whitelist can be captured by rifters, players and animals are already captured by default. Format is mod id:name of creature, just use the modid to whitelist everything from it. Default is empty")
                    .define("capturable_entities", new ArrayList<>());
            notCapturableAnimals = builder
                    .comment("Mobs on this blacklist cannot be captured by rifters. Format is mod id:name of creature, just use the mod id to blacklist everything from it. Default: iceandfire, midnight:nightstag")
                    .define("not_capturable_animals", Lists.newArrayList("iceandfire", "midnight:nightstag"));

            builder.pop();

            PROFILE.add(riftBiomes);
            PROFILE.add(riftDimensions);
            PROFILE.add(capturableEntities);
            PROFILE.add(notCapturableAnimals);
        }
    }

    public static class CatWorldGen {
        public final ForgeConfigSpec.ConfigValue<Integer> riftStructureRarity;
        public final ForgeConfigSpec.ConfigValue<Integer> wellStructureRarity;
        public final ForgeConfigSpec.ConfigValue<Integer> guardtowerStructureRarity;
        public final ForgeConfigSpec.ConfigValue<Integer> moltenCraterStructureRarity;

        public CatWorldGen(ForgeConfigSpec.Builder builder) {
            builder.comment("Configurations of the world generator.").push("worldgen");
            riftStructureRarity = builder
                    .comment("The rarity of rift heap structures. Higher values increase rarity. When 0, rift structures are disabled. Default: 5")
                    .defineInRange("rift_structure_rarity", 5, 0, Integer.MAX_VALUE);
            wellStructureRarity = builder
                    .comment("The rarity of well structures in the midnight. Higher values increase rarity. When 0, well structures are disabled. Default: 1")
                    .defineInRange("well_structure_rarity", 1, 0, Integer.MAX_VALUE);
            guardtowerStructureRarity = builder
                    .comment("The rarity of shadowroot guardtower structures in the midnight. Higher values increase rarity. When 0, guardtower structures are disabled. Default: 1")
                    .defineInRange("guardtower_structure_rarity", 1, 0, Integer.MAX_VALUE);
            moltenCraterStructureRarity = builder
                    .comment("The rarity of molten crater structures in the midnight. Higher values increase rarity. When 0, crater structures are disabled. Default: 1")
                    .defineInRange("crater_structure_rarity", 1, 0, Integer.MAX_VALUE);
            builder.pop();

            PROFILE.add(riftStructureRarity, 5);
            PROFILE.add(wellStructureRarity, 1);
            PROFILE.add(guardtowerStructureRarity, 1);
            PROFILE.add(moltenCraterStructureRarity, 1);
            SERVER_PROFILE.add(riftStructureRarity, 5);
            SERVER_PROFILE.add(wellStructureRarity, 1);
            SERVER_PROFILE.add(guardtowerStructureRarity, 1);
            SERVER_PROFILE.add(moltenCraterStructureRarity, 1);
        }
    }

    public static class CatLogic {
        public final ForgeConfigSpec.ConfigValue<Integer> bladeshroomDamageChance;
        public final ForgeConfigSpec.ConfigValue<Boolean> canRespawnInMidnight;
        public final ForgeConfigSpec.ConfigValue<Boolean> huntersAttackTamedSkulks;
        public final ForgeConfigSpec.ConfigValue<Boolean> riftsFromDarkPearls;
        public final ForgeConfigSpec.ConfigValue<Boolean> riftsOpenAtDaytime;
        public final ForgeConfigSpec.ConfigValue<Boolean> allowLightningDamage;
        public final ForgeConfigSpec.ConfigValue<Boolean> randomLightnings;

        public final ForgeConfigSpec.ConfigValue<Integer> naturalRifterSpawnRarity;
        public final ForgeConfigSpec.ConfigValue<Integer> rifterSpawnRarity;
        public final ForgeConfigSpec.ConfigValue<Integer> maxRifterByRift;
        public final ForgeConfigSpec.ConfigValue<Boolean> allowRifterTeleport;
        public final ForgeConfigSpec.ConfigValue<Integer> rifterAmbienceChance;
        public final ForgeConfigSpec.ConfigValue<Boolean> rifterCaptureTamedAnimal;
        public final ForgeConfigSpec.ConfigValue<Boolean> rifterCaptureNamedAnimal;

        public CatLogic(ForgeConfigSpec.Builder builder) {
            builder.comment("All the options that can only be set on the server.").push("logic");
            bladeshroomDamageChance = builder
                    .comment("The chance in percents to take a small amount of damage when obtaining a bladeshroom cap. If 0, this is disabled. Default: 5")
                    .defineInRange("bladeshroom_damage_chance", 5, 0, 100);
            canRespawnInMidnight = builder
                    .comment("If true, players will respawn in Midnight after dying in the dimension. Default: false")
                    .define("can_respawn_in_midnight", false);
            huntersAttackTamedSkulks = builder
                    .comment("If true, hunters will attack tamed skulks. Default: true")
                    .define("hunters_attack_tamed_skulks", true);
            riftsFromDarkPearls = builder
                    .comment("If true, dark pearls can be used to make rifts. Default: true")
                    .define("rifts_from_dark_pearls", true);
            riftsOpenAtDaytime = builder
                    .comment("If true, rifts are open during the day. If false, rifts are only open at nighttime. Default: false")
                    .define("rifts_open_at_daytime", false);
            allowLightningDamage = builder
                    .comment("Allows the lightning in Midnight to burn blocks and do damage to entities. Default: false")
                    .define("allow_lightning_damage", false);
            randomLightnings = builder
                    .comment("Sets if random lightning effects appear in the midnight or not. Default: true")
                    .define("random_lightnings", true);

            naturalRifterSpawnRarity = builder
                    .comment("The percent chance (from 0 to 100) for a rifter to spawn naturally. If 0, no rifters spawn at all. Default: 75")
                    .defineInRange("natural_rifter_spawn_rarity", 75, 0, 100);
            rifterSpawnRarity = builder
                    .comment("The rarity that rifts spawn rifters. Larger numbers increase rarity. If 0, rifters don't spawn from rifts. Default: 1000")
                    .defineInRange("rifter_spawn_rarity", 1000, 0, Integer.MAX_VALUE);
            maxRifterByRift = builder
                    .comment("The maximum amount of rifters that spawn from a rift. Default: 2")
                    .defineInRange("max_rifter_by_rift", 2, 1, 10);
            allowRifterTeleport = builder
                    .comment("Allows rifters to teleport to players when they aren't being looked at. Default: true")
                    .define("allow_rifter_teleport", true);
            rifterAmbienceChance = builder
                    .comment("The percent chance (from 0 to 100) for a rifter to make an ambience sound at any time. Default: 75")
                    .defineInRange("rifter_ambience_chance", 75, 0, 100);
            rifterCaptureTamedAnimal = builder
                    .comment("Allows rifters to capture tamed entities and drag them into rifts. Default: false")
                    .define("rifter_capture_tamed_animal", false);
            rifterCaptureNamedAnimal = builder
                    .comment("Allows rifters to capture nametagged entities and drag them into rifts. Default: true")
                    .define("rifter_capture_named_animal", true);

            PROFILE.add(bladeshroomDamageChance);
            PROFILE.add(canRespawnInMidnight);
            PROFILE.add(huntersAttackTamedSkulks);

            PROFILE.add(riftsFromDarkPearls);
            PROFILE.add(riftsOpenAtDaytime);

            PROFILE.add(allowLightningDamage);
            PROFILE.add(randomLightnings);

            PROFILE.add(naturalRifterSpawnRarity);
            PROFILE.add(rifterSpawnRarity);
            PROFILE.add(maxRifterByRift);
            PROFILE.add(allowRifterTeleport);
            PROFILE.add(rifterAmbienceChance);
            PROFILE.add(rifterCaptureTamedAnimal);
            PROFILE.add(rifterCaptureNamedAnimal);
            builder.pop();
        }
    }

    public static class CatClient {
        public final ForgeConfigSpec.ConfigValue<Boolean> hideVignetteEffect;
        public final ForgeConfigSpec.ConfigValue<Boolean> allowBrightnessChange;
        public final ForgeConfigSpec.ConfigValue<Boolean> ambientSporeParticles;
        public final ForgeConfigSpec.ConfigValue<Boolean> experimentalCaptureRender;
        public final ForgeConfigSpec.ConfigValue<Double> ambientVolume;
        public final ForgeConfigSpec.ConfigValue<Double> echoVolume;
        public final ForgeConfigSpec.ConfigValue<Boolean> checkForRewrite;

        public CatClient(ForgeConfigSpec.Builder builder) {
            builder.comment("All the options that can be modified by players on server.").push("client");
            hideVignetteEffect = builder
                    .comment("Hides the vignette effect in the darker areas of Midnight. Default: false")
                    .define("hide_vignette_effect", false);
            allowBrightnessChange = builder
                    .comment("Allows brightness to be changed in Midnight. Default: false")
                    .define("allow_brightness_change", false);
            ambientSporeParticles = builder
                    .comment("If true, some ambient spore particles are spawned around the player. Default: true")
                    .define("ambient_spore_particle", true);
            experimentalCaptureRender = builder
                    .comment("If true, when an entity is captured by a rifter it will rotate to look like it is being dragged. Default: true")
                    .define("experimental_capture_render", true);

            ambientVolume = builder
                    .comment("The volume of ambient sounds (e.g. distant screams, environmental noises). Default: 1.0")
                    .defineInRange("ambient_volume", 1.0, 0, 1);
            echoVolume = builder
                    .comment("The volume of the echo effect playing in the Midnight. When 0, echoes are disabled. Default: 1.0")
                    .defineInRange("echo_volume", 1.0, 0, 1);

            checkForRewrite = builder
                    .comment("If true, The Midnight will check if version 0.6.0 or higher is available for release. The chat notification will be shown once when a world is loaded and again after each restart. Default: true")
                    .define("check_for_rewrite", true);

            PROFILE.add(hideVignetteEffect);
            PROFILE.add(allowBrightnessChange);
            PROFILE.add(ambientSporeParticles);
            PROFILE.add(experimentalCaptureRender);
            PROFILE.add(ambientVolume);
            PROFILE.add(echoVolume);
            PROFILE.add(checkForRewrite);
            builder.pop();
        }
    }

    public static final ConfigInterface.Factory SERVER_IFC = provider -> {
        ConfigInterface ifc = new ConfigInterface(new TranslationTextComponent("config.midnight.title.server_logic"), provider, true, EditAccess.SERVER_HOST);
        ifc.header("config.midnight.header.general");
        ifc.setting("config.midnight.bladeshroom_damage_chance", new IntSliderControl(0, 100, "config.midnight.format.number_percentage", "config.midnight.format.never", "config.midnight.format.always"), "logic.bladeshroom_damage_chance");
        ifc.setting("config.midnight.can_respawn_in_midnight", new ToggleButtonControl(), "logic.can_respawn_in_midnight");
        ifc.setting("config.midnight.hunters_attack_tamed_skulks", new ToggleButtonControl(), "logic.hunters_attack_tamed_skulks");
        ifc.header("config.midnight.header.rifts");
        ifc.setting("config.midnight.rifts_from_dark_pearls", new ToggleButtonControl(), "logic.rifts_from_dark_pearls");
        ifc.setting("config.midnight.rifts_open_at_daytime", new ToggleButtonControl(), "logic.rifts_open_at_daytime");
        ifc.header("config.midnight.header.lightning");
        ifc.setting("config.midnight.random_lightnings", new ToggleButtonControl(), "logic.random_lightnings");
        ifc.setting("config.midnight.allow_lightning_damage", new ToggleButtonControl(), "logic.allow_lightning_damage");
        ifc.header("config.midnight.header.rifters");
        ifc.setting("config.midnight.natural_rifter_spawn_rarity", new IntSliderControl(0, 100, "config.midnight.format.number_percentage", "config.midnight.format.never", "config.midnight.format.always"), "logic.natural_rifter_spawn_rarity");
        ifc.setting("config.midnight.rifter_spawn_rarity", new IntInputControl(0, Integer.MAX_VALUE), "logic.rifter_spawn_rarity");
        ifc.setting("config.midnight.max_rifters_by_rift", new IntSliderControl(1, 10), "logic.max_rifter_by_rift");
        ifc.setting("config.midnight.allow_rifter_teleport", new ToggleButtonControl(), "logic.allow_rifter_teleport");
        ifc.setting("config.midnight.rifter_ambience_chance", new IntSliderControl(0, 100, "config.midnight.format.number_percentage", "config.midnight.format.never", "config.midnight.format.always"), "logic.rifter_ambience_chance");
        ifc.setting("config.midnight.rifter_capture_tamed_animal", new ToggleButtonControl(), "logic.rifter_capture_tamed_animal");
        ifc.setting("config.midnight.rifter_capture_named_animal", new ToggleButtonControl(), "logic.rifter_capture_named_animal");
        return ifc;
    };

    public static final ConfigInterface.Factory COMPAT_IFC = provider -> {
        ConfigInterface ifc = new ConfigInterface(new TranslationTextComponent("config.midnight.title.compat"), provider, true, EditAccess.ALWAYS);
        ifc.setting("config.midnight.rift_biomes", new StringListControl("config.midnight.rift_biomes"), "compat.rift_biomes");
        ifc.setting("config.midnight.rift_dimensions", new StringListControl("config.midnight.rift_dimensions"), "compat.rift_dimensions");
        ifc.setting("config.midnight.capturable_entities", new StringListControl("config.midnight.capturable_entities"), "compat.capturable_entities");
        ifc.setting("config.midnight.not_capturable_animals", new StringListControl("config.midnight.not_capturable_animals"), "compat.not_capturable_animals");
        return ifc;
    };

    public static final ConfigInterface.Factory CLIENT_IFC = provider -> {
        ConfigInterface ifc = new ConfigInterface(new TranslationTextComponent("config.midnight.title.client"), provider, true, EditAccess.ALWAYS);
        ifc.header("config.midnight.header.graphical");
        ifc.setting("config.midnight.hide_vignette_effect", new ToggleButtonControl(), "client.hide_vignette_effect");
        ifc.setting("config.midnight.allow_brightness_change", new ToggleButtonControl(), "client.allow_brightness_change");
        ifc.setting("config.midnight.ambient_spore_particles", new ToggleButtonControl(), "client.ambient_spore_particle");
        ifc.setting("config.midnight.experimental_capture_render", new ToggleButtonControl(), "client.experimental_capture_render");
        ifc.header("config.midnight.header.sound");
        ifc.setting("config.midnight.ambient_volume", new DoublePercentSliderControl("config.midnight.format.number_percentage", "options.off", "config.midnight.format.number_percentage"), "client.ambient_volume");
        ifc.setting("config.midnight.echo_volume", new DoublePercentSliderControl("config.midnight.format.number_percentage", "options.off", "config.midnight.format.number_percentage"), "client.echo_volume");
        ifc.header("config.midnight.header.updates");
        ifc.setting("config.midnight.check_for_rewrite", new ToggleButtonControl(), "client.check_for_rewrite");
        return ifc;
    };

    public static final ConfigInterface.Factory MAIN_IFC = provider -> {
        ConfigInterface ifc = new ConfigInterface(new TranslationTextComponent("config.midnight.title"), provider, false, EditAccess.ALWAYS);
        ifc.category("config.midnight.category.client", CLIENT_IFC.makeInterface(provider));
        ifc.category("config.midnight.category.server", SERVER_IFC.makeInterface(provider));
        ifc.category("config.midnight.category.compat", COMPAT_IFC.makeInterface(provider));
        return ifc;
    };


    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final CatClient client;

    public static final ForgeConfigSpec SERVER_SPEC;
    public static final CatWorldGen worldgen;
    public static final CatLogic logic;

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final CatCompat compat;

    static {
        Pair<CatClient, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CatClient::new);
        CLIENT_SPEC = specPair.getRight();
        client = specPair.getLeft();

        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        worldgen = new CatWorldGen(builder);
        logic = new CatLogic(builder);
        SERVER_SPEC = builder.build();

        builder = new ForgeConfigSpec.Builder();
        compat = new CatCompat(builder);
        COMMON_SPEC = builder.build();
    }


    private static List<String> lastRiftBiomeSelector;
    private static List<DimensionType> lastDimensions;
    private static Set<Biome> riftBiomes = new HashSet<>();

    private static List<String> lastRiftDimSelector;
    private static Set<DimensionType> riftDims = new HashSet<>();

    public static Set<Biome> getRiftBiomes() {
        return riftBiomes;
    }

    public static Set<DimensionType> getRiftDims() {
        return riftDims;
    }

    @SubscribeEvent
    public static void tick(TickEvent event) {
        if (event.type == TickEvent.Type.SERVER && event.phase == TickEvent.Phase.END) {
            update();
        }
    }

    public static void update() {
        if (!compat.riftBiomes.get().equals(lastRiftBiomeSelector)) {
            lastRiftBiomeSelector = compat.riftBiomes.get();
            riftBiomes.clear();
            for (String rule : lastRiftBiomeSelector) {
                rule(rule, MidnightConfig::biomeSelector, ForgeRegistries.BIOMES).accept(riftBiomes);
            }
            LOGGER.debug("Rift biomes: " + riftBiomes);
        }
        Iterable<DimensionType> dimens = DimensionType.getAll();
        if (!compat.riftDimensions.get().equals(lastRiftDimSelector) || !Iterables.elementsEqual(dimens, lastDimensions)) {
            lastRiftDimSelector = compat.riftDimensions.get();
            lastDimensions = Lists.newArrayList(dimens);
            riftDims.clear();
            for (String rule : lastRiftDimSelector) {
                rule(rule, MidnightConfig::dimSelector, DimensionType.getAll()).accept(riftDims);
            }
            LOGGER.debug("Rift dimensions: " + riftDims);
        }
    }

    private static <T> Consumer<Set<T>> rule(String rule, Function<String, Predicate<T>> selectorParser, Iterable<T> allEntries) {
        rule = rule.replaceAll("(?U)\\s", "");
        if (rule.isEmpty()) return set -> {
        };

        char pfx = '+';
        int start = 0;
        if (rule.startsWith("+")) {
            start = 1;
        }
        if (rule.startsWith("-")) {
            pfx = '-';
            start = 1;
        }
        if (rule.startsWith("~")) {
            pfx = '~';
            start = 1;
        }

        Predicate<T> selector = selectorParser.apply(rule.substring(start));
        if (pfx == '+') {
            return set -> {
                for (T t : allEntries) {
                    if (selector.test(t)) {
                        set.add(t);
                    }
                }
            };
        } else if (pfx == '-') {
            return set -> {
                Set<T> remove = new HashSet<>();
                for (T t : set) {
                    if (selector.test(t)) {
                        remove.add(t);
                    }
                }
                for (T t : remove) {
                    set.remove(t);
                }
            };
        } else {
            return set -> {
                Set<T> remove = new HashSet<>();
                for (T t : set) {
                    if (!selector.test(t)) {
                        remove.add(t);
                    }
                }
                for (T biome : remove) {
                    set.remove(biome);
                }
            };
        }
    }

    private static Predicate<Biome> biomeSelector(String sel) {
        if (sel.startsWith("!")) {
            return biomeSelector(sel.substring(1)).negate();
        }
        if (sel.startsWith("#")) {
            return typeSelector(sel.substring(1));
        } else {
            Predicate<ResourceLocation> id = idSelector(sel);
            return biome -> id.test(biome.getRegistryName());
        }
    }

    private static Predicate<DimensionType> dimSelector(String sel) {
        if (sel.startsWith("!")) {
            return dimSelector(sel.substring(1)).negate();
        }
        Predicate<ResourceLocation> id = idSelector(sel);
        return dim -> id.test(dim.getRegistryName());
    }

    private static Predicate<Biome> typeSelector(String sel) {
        BiomeDictionary.Type type = BiomeDictionary.Type.getType(sel);
        return biome -> BiomeDictionary.hasType(biome, type);
    }

    private static Predicate<ResourceLocation> idSelector(String sel) {
        if (sel.equals("*")) {
            return id -> true;
        }
        int colon = sel.indexOf(':');
        if (colon == -1) {
            return id -> id != null && id.getNamespace().equals("minecraft") && id.getPath().equalsIgnoreCase(sel);
        }

        String ns = sel.substring(0, colon);
        String path = sel.substring(colon + 1);
        if (path.equals("*")) {
            return id -> id != null && id.getNamespace().equalsIgnoreCase(ns);
        }

        return id -> id != null && id.getNamespace().equalsIgnoreCase(ns) && id.getPath().equalsIgnoreCase(path);
    }
}
