package com.mushroom.midnight.common.registry;

import com.google.common.collect.Lists;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.block.*;
import com.mushroom.midnight.common.block.BlockMidnightChest.ChestModel;
import com.mushroom.midnight.common.item.ItemDeceitfulAlgae;
import com.mushroom.midnight.common.item.ItemMidnightSlab;
import com.mushroom.midnight.common.tile.base.TileEntityMidnightChest;
import com.mushroom.midnight.common.tile.base.TileEntityMidnightFurnace;
import com.mushroom.midnight.common.world.feature.LargeBogshroomFeature;
import com.mushroom.midnight.common.world.feature.DarkWillowTreeFeature;
import com.mushroom.midnight.common.world.feature.LargeBulbFungusFeature;
import com.mushroom.midnight.common.world.feature.LargeFungiFeature;
import com.mushroom.midnight.common.world.feature.ShadowrootTreeFeature;
import com.mushroom.midnight.common.block.BlockMidnightButton;
import com.mushroom.midnight.common.block.BlockMidnightPressurePlate;
import com.mushroom.midnight.common.block.BlockMidnightPressurePlateWeighted;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@GameRegistry.ObjectHolder(Midnight.MODID)
@Mod.EventBusSubscriber(modid = Midnight.MODID)
@SuppressWarnings("WeakerAccess")
public class ModBlocks {

    static List<Block> blocks = new ArrayList<>();

    public static final Block SHADOWROOT_LOG = Blocks.AIR;
    public static final Block SHADOWROOT_LEAVES = Blocks.AIR;
    public static final Block SHADOWROOT_PLANKS = Blocks.AIR;
    public static final Block DEAD_WOOD_LOG = Blocks.AIR;
    public static final Block DEAD_WOOD_PLANKS = Blocks.AIR;
    public static final Block DARK_WILLOW_LOG = Blocks.AIR;
    public static final Block DARK_WILLOW_LEAVES = Blocks.AIR;
    public static final Block DARK_WILLOW_PLANKS = Blocks.AIR;
    public static final Block NIGHTSTONE = Blocks.AIR;
    public static final Block NIGHTSTONE_BRICKS = Blocks.AIR;
    public static final Block CHISELED_NIGHTSTONE_BRICKS = Blocks.AIR;
    public static final Block TRENCHSTONE = Blocks.AIR;
    public static final Block TRENCHSTONE_BRICKS = Blocks.AIR;

    public static final Block DARK_PEARL_ORE = Blocks.AIR;
    public static final Block DARK_PEARL_BLOCK = Blocks.AIR;
    public static final Block TENEBRUM_ORE = Blocks.AIR;
    public static final Block TENEBRUM_BLOCK = Blocks.AIR;
    public static final Block NAGRILITE_ORE = Blocks.AIR;
    public static final Block NAGRILITE_BLOCK = Blocks.AIR;
    public static final Block EBONYS_ORE = Blocks.AIR;
    public static final Block EBONYS_BLOCK = Blocks.AIR;
    public static final Block ARCHAIC_ORE = Blocks.AIR;

    public static final Block SHADOWROOT_CRAFTING_TABLE = Blocks.AIR;
    public static final Block SHADOWROOT_CHEST = Blocks.AIR;
    public static final Block MIDNIGHT_FURNACE = Blocks.AIR;
    public static final Block MIDNIGHT_FURNACE_LIT = Blocks.AIR;

    public static final Block MIDNIGHT_COARSE_DIRT = Blocks.AIR;
    public static final Block MIDNIGHT_DIRT = Blocks.AIR;
    public static final Block MIDNIGHT_GRASS = Blocks.AIR;
    public static final Block MIDNIGHT_MYCELIUM = Blocks.AIR;

    public static final Block TALL_MIDNIGHT_GRASS = Blocks.AIR;
    public static final Block DOUBLE_MIDNIGHT_GRASS = Blocks.AIR;

    public static final Block NIGHTSHROOM = Blocks.AIR;
    public static final Block DOUBLE_NIGHTSHROOM = Blocks.AIR;
    public static final Block NIGHTSHROOM_SHELF = Blocks.AIR;

    public static final Block DEWSHROOM = Blocks.AIR;
    public static final Block DOUBLE_DEWSHROOM = Blocks.AIR;
    public static final Block DEWSHROOM_SHELF = Blocks.AIR;

    public static final Block DEWSHROOM_PLANKS = Blocks.AIR;

    public static final Block VIRIDSHROOM = Blocks.AIR;
    public static final Block DOUBLE_VIRIDSHROOM = Blocks.AIR;
    public static final Block VIRIDSHROOM_SHELF = Blocks.AIR;

    public static final Block VIRIDSHROOM_PLANKS = Blocks.AIR;

    public static final Block VIRIDSHROOM_STEM = Blocks.AIR;
    public static final Block VIRIDSHROOM_HAT = Blocks.AIR;

    public static final Block NIGHTSHROOM_STEM = Blocks.AIR;
    public static final Block NIGHTSHROOM_HAT = Blocks.AIR;

    public static final Block NIGHTSHROOM_PLANKS = Blocks.AIR;

    public static final Block DEWSHROOM_STEM = Blocks.AIR;
    public static final Block DEWSHROOM_HAT = Blocks.AIR;

    public static final Block BOGSHROOM = Blocks.AIR;
    public static final Block DOUBLE_BOGSHROOM = Blocks.AIR;
    public static final Block BOGSHROOM_SHELF = Blocks.AIR;
    public static final Block BOGSHROOM_STEM = Blocks.AIR;
    public static final Block BOGSHROOM_HAT = Blocks.AIR;

    public static final Block BULB_FUNGUS = Blocks.AIR;
    public static final Block BULB_FUNGUS_STEM = Blocks.AIR;
    public static final Block BULB_FUNGUS_HAT = Blocks.AIR;

    public static final Block ROCKSHROOM = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICKS = Blocks.AIR;

    public static final Block LUMEN_BUD = Blocks.AIR;
    public static final Block DOUBLE_LUMEN_BUD = Blocks.AIR;

    public static final Block BLADESHROOM = Blocks.AIR;
    public static final Block BOGWEED = Blocks.AIR;
    public static final Block GHOST_PLANT = Blocks.AIR;
    public static final Block FINGERED_GRASS = Blocks.AIR;
    public static final Block TENDRILWEED = Blocks.AIR;
    public static final Block RUNEBUSH = Blocks.AIR;
    public static final Block DRAGON_NEST = Blocks.AIR;
    public static final Block VIOLEAF = Blocks.AIR;

    public static final Block CRYSTAL_FLOWER = Blocks.AIR;

    public static final Block SHADOWROOT_SAPLING = Blocks.AIR;
    public static final Block DARK_WILLOW_SAPLING = Blocks.AIR;

    public static final Block SHADOWROOT_DOOR = Blocks.AIR;
    public static final Block DEAD_WOOD_DOOR = Blocks.AIR;
    public static final Block DARK_WILLOW_DOOR = Blocks.AIR;

    public static final Block TENEBRUM_DOOR = Blocks.AIR;

    public static final Block NIGHTSHROOM_DOOR = Blocks.AIR;
    public static final Block DEWSHROOM_DOOR = Blocks.AIR;
    public static final Block VIRIDSHROOM_DOOR = Blocks.AIR;

    public static final Block SHADOWROOT_TRAPDOOR = Blocks.AIR;
    public static final Block DEAD_WOOD_TRAPDOOR = Blocks.AIR;
    public static final Block DARK_WILLOW_TRAPDOOR = Blocks.AIR;

    public static final Block TENEBRUM_TRAPDOOR = Blocks.AIR;

    public static final Block NIGHTSHROOM_TRAPDOOR = Blocks.AIR;
    public static final Block DEWSHROOM_TRAPDOOR = Blocks.AIR;
    public static final Block VIRIDSHROOM_TRAPDOOR = Blocks.AIR;

    public static final Block BLOOMCRYSTAL = Blocks.AIR;
    public static final Block BLOOMCRYSTAL_ROCK = Blocks.AIR;

    public static final Block ROUXE = Blocks.AIR;
    public static final Block ROUXE_ROCK = Blocks.AIR;

    public static final Block ARCHAIC_GLASS = Blocks.AIR;
    public static final Block ARCHAIC_GLASS_PANE = Blocks.AIR;

    public static final Block MIASMA_SURFACE = Blocks.AIR;
    public static final Block MIASMA = Blocks.AIR;
    public static final Block DARK_WATER = Blocks.AIR;

    public static final Block MUSHROOM_INSIDE = Blocks.AIR;

    public static final Block DECEITFUL_PEAT = Blocks.AIR;
    public static final Block DECEITFUL_MUD = Blocks.AIR;
    public static final Block DECEITFUL_ALGAE = Blocks.AIR;
    public static final Block DECEITFUL_MOSS = Blocks.AIR;

    public static final Block SHADOWROOT_SLAB = Blocks.AIR;
    public static final Block DEAD_WOOD_SLAB = Blocks.AIR;
    public static final Block DARK_WILLOW_SLAB = Blocks.AIR;
    public static final Block NIGHTSTONE_SLAB = Blocks.AIR;
    public static final Block NIGHTSTONE_BRICK_SLAB = Blocks.AIR;
    public static final Block TRENCHSTONE_SLAB = Blocks.AIR;
    public static final Block TRENCHSTONE_BRICK_SLAB = Blocks.AIR;
    public static final Block DEWSHROOM_SLAB = Blocks.AIR;
    public static final Block VIRIDSHROOM_SLAB = Blocks.AIR;
    public static final Block NIGHTSHROOM_SLAB = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICKS_SLAB = Blocks.AIR;

    public static final Block SHADOWROOT_DOUBLE_SLAB = Blocks.AIR;
    public static final Block DEAD_WOOD_DOUBLE_SLAB = Blocks.AIR;
    public static final Block DARK_WILLOW_DOUBLE_SLAB = Blocks.AIR;
    public static final Block NIGHTSTONE_DOUBLE_SLAB = Blocks.AIR;
    public static final Block NIGHTSTONE_BRICK_DOUBLE_SLAB = Blocks.AIR;
    public static final Block TRENCHSTONE_DOUBLE_SLAB = Blocks.AIR;
    public static final Block TRENCHSTONE_BRICK_DOUBLE_SLAB = Blocks.AIR;
    public static final Block DEWSHROOM_DOUBLE_SLAB = Blocks.AIR;
    public static final Block VIRIDSHROOM_DOUBLE_SLAB = Blocks.AIR;
    public static final Block NIGHTSHROOM_DOUBLE_SLAB = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICKS_DOUBLE_SLAB = Blocks.AIR;

    public static final Block SHADOWROOT_STAIRS = Blocks.AIR;
    public static final Block DEAD_WOOD_STAIRS = Blocks.AIR;
    public static final Block DARK_WILLOW_STAIRS = Blocks.AIR;
    public static final Block NIGHTSTONE_STAIRS = Blocks.AIR;
    public static final Block NIGHTSTONE_BRICK_STAIRS = Blocks.AIR;
    public static final Block TRENCHSTONE_STAIRS = Blocks.AIR;
    public static final Block TRENCHSTONE_BRICK_STAIRS = Blocks.AIR;
    public static final Block DEWSHROOM_STAIRS = Blocks.AIR;
    public static final Block VIRIDSHROOM_STAIRS = Blocks.AIR;
    public static final Block NIGHTSHROOM_STAIRS = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICKS_STAIRS = Blocks.AIR;

    public static final Block SHADOWROOT_FENCE = Blocks.AIR;
    public static final Block DEAD_WOOD_FENCE = Blocks.AIR;
    public static final Block DARK_WILLOW_FENCE = Blocks.AIR;
    public static final Block NIGHTSTONE_WALL = Blocks.AIR;
    public static final Block NIGHTSTONE_BRICK_WALL = Blocks.AIR;
    public static final Block TRENCHSTONE_WALL = Blocks.AIR;
    public static final Block TRENCHSTONE_BRICK_WALL = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICKS_WALL = Blocks.AIR;
    public static final Block DEWSHROOM_FENCE = Blocks.AIR;
    public static final Block VIRIDSHROOM_FENCE = Blocks.AIR;
    public static final Block NIGHTSHROOM_FENCE = Blocks.AIR;

    public static final Block SHADOWROOT_FENCE_GATE = Blocks.AIR;
    public static final Block DEAD_WOOD_FENCE_GATE = Blocks.AIR;
    public static final Block DARK_WILLOW_FENCE_GATE = Blocks.AIR;
    public static final Block DEWSHROOM_FENCE_GATE = Blocks.AIR;
    public static final Block VIRIDSHROOM_FENCE_GATE = Blocks.AIR;
    public static final Block NIGHTSHROOM_FENCE_GATE = Blocks.AIR;

    public static final Block SUAVIS = Blocks.AIR;

    public static final Block SHADOWROOT_LADDER = Blocks.AIR;
    public static final Block DEAD_WOOD_LADDER = Blocks.AIR;
    public static final Block DARK_WILLOW_LADDER = Blocks.AIR;
    public static final Block DEWSHROOM_LADDER = Blocks.AIR;
    public static final Block VIRIDSHROOM_LADDER = Blocks.AIR;
    public static final Block NIGHTSHROOM_LADDER = Blocks.AIR;

    public static final Block STINGER_EGG = Blocks.AIR;
    public static final Block UNSTABLE_BUSH = Blocks.AIR;
    public static final Block UNSTABLE_BUSH_BLUE_BLOOMED = Blocks.AIR;
    public static final Block UNSTABLE_BUSH_GREEN_BLOOMED = Blocks.AIR;
    public static final Block UNSTABLE_BUSH_LIME_BLOOMED = Blocks.AIR;

    public static final Block BOGSHROOM_SPORCH = Blocks.AIR;
    public static final Block NIGHTSHROOM_SPORCH = Blocks.AIR;
    public static final Block DEWSHROOM_SPORCH = Blocks.AIR;
    public static final Block VIRIDSHROOM_SPORCH = Blocks.AIR;
    
    public static final Block CRYSTALOTUS = Blocks.AIR;
    
    public static final Block SHADOWROOT_BUTTON = Blocks.AIR;
    public static final Block DEAD_WOOD_BUTTON = Blocks.AIR;
    public static final Block DARK_WILLOW_BUTTON = Blocks.AIR;
    public static final Block DEWSHROOM_BUTTON = Blocks.AIR;
    public static final Block VIRIDSHROOM_BUTTON = Blocks.AIR;
    public static final Block NIGHTSHROOM_BUTTON = Blocks.AIR;
    public static final Block NIGHTSTONE_BUTTON = Blocks.AIR;
    public static final Block TRENCHSTONE_BUTTON = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICKS_BUTTON = Blocks.AIR;
    
    public static final Block SHADOWROOT_PRESSURE_PLATE = Blocks.AIR;
    public static final Block DEAD_WOOD_PRESSURE_PLATE = Blocks.AIR;
    public static final Block DARK_WILLOW_PRESSURE_PLATE = Blocks.AIR;
    public static final Block DEWSHROOM_PRESSURE_PLATE = Blocks.AIR;
    public static final Block VIRIDSHROOM_PRESSURE_PLATE = Blocks.AIR;
    public static final Block NIGHTSHROOM_PRESSURE_PLATE = Blocks.AIR;
    public static final Block NIGHTSTONE_PRESSURE_PLATE = Blocks.AIR;
    public static final Block TRENCHSTONE_PRESSURE_PLATE = Blocks.AIR;
    public static final Block ROCKSHROOM_BRICKS_PRESSURE_PLATE = Blocks.AIR;
    public static final Block NAGRILITE_PRESSURE_PLATE = Blocks.AIR;
    public static final Block TENEBRUM_PRESSURE_PLATE = Blocks.AIR;
    
    public static final Block MIDNIGHT_LEVER = Blocks.AIR;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        blocks.addAll(Lists.newArrayList(
                RegUtil.withName(new BlockMidnightGrass(), "midnight_grass"),
                RegUtil.withName(new BlockMidnightDirt(), "midnight_dirt"),
                RegUtil.withName(new BlockMidnightDirt(), "midnight_coarse_dirt"),
                RegUtil.withName(new BlockMidnightMycelium(), "midnight_mycelium"),
                RegUtil.withName(new BlockMidnightLog(), "shadowroot_log"),
                RegUtil.withName(new BlockMidnightLeaves(() -> SHADOWROOT_SAPLING), "shadowroot_leaves"),
                RegUtil.withName(new BlockMidnightLog(), "dead_wood_log"),
                RegUtil.withName(new BlockMidnightLog(), "dark_willow_log"),
                RegUtil.withName(new BlockMidnightLeaves(() -> DARK_WILLOW_SAPLING), "dark_willow_leaves"),
                RegUtil.withName(new BlockMidnightFungiStem(), "nightshroom_stem"),
                RegUtil.withName(new BlockMidnightFungiHat(() -> NIGHTSHROOM,() -> ModItems.NIGHTSHROOM_POWDER, MapColor.CYAN), "nightshroom_hat"),
                RegUtil.withName(new BlockMidnightFungiStem(), "dewshroom_stem"),
                RegUtil.withName(new BlockMidnightFungiHat(() -> DEWSHROOM,() -> ModItems.DEWSHROOM_POWDER, MapColor.PURPLE), "dewshroom_hat"),
                RegUtil.withName(new BlockMidnightFungiStem(), "viridshroom_stem"),
                RegUtil.withName(new BlockMidnightFungiHat(() -> VIRIDSHROOM,() -> ModItems.VIRIDSHROOM_POWDER, MapColor.EMERALD), "viridshroom_hat"),
                RegUtil.withName(new BlockBulbFungusStem(), "bulb_fungus_stem"),
                RegUtil.withName(new BlockBulbFungusHat(MapColor.MAGENTA), "bulb_fungus_hat"),
                RegUtil.withName(new BlockRockshroom(), "rockshroom"),
                RegUtil.withName(new BlockMidnightTallGrass(), "tall_midnight_grass"),
                RegUtil.withName(new BlockMidnightDoublePlant(PlantBehaviorType.BUSH, false), "double_midnight_grass"),
                RegUtil.withName(new BlockMidnightFungi(() -> DOUBLE_NIGHTSHROOM), "nightshroom"),
                RegUtil.withName(new BlockMidnightDoubleFungi(() -> new LargeFungiFeature(
                        ModBlocks.NIGHTSHROOM_STEM.getDefaultState(),
                        ModBlocks.NIGHTSHROOM_HAT.getDefaultState()
                )), "double_nightshroom"),
                RegUtil.withName(new BlockMidnightFungi(() -> DOUBLE_DEWSHROOM), "dewshroom"),
                RegUtil.withName(new BlockMidnightDoubleFungi(() -> new LargeFungiFeature(
                        ModBlocks.DEWSHROOM_STEM.getDefaultState(),
                        ModBlocks.DEWSHROOM_HAT.getDefaultState()
                )), "double_dewshroom"),
                RegUtil.withName(new BlockMidnightFungi(() -> DOUBLE_VIRIDSHROOM), "viridshroom"),
                RegUtil.withName(new BlockMidnightDoubleFungi(() -> new LargeFungiFeature(
                        ModBlocks.VIRIDSHROOM_STEM.getDefaultState(),
                        ModBlocks.VIRIDSHROOM_HAT.getDefaultState()
                )), "double_viridshroom"),
                RegUtil.withName(new BlockMidnightFungi(() -> DOUBLE_BOGSHROOM), "bogshroom"),
                RegUtil.withName(new BlockMidnightDoubleFungi(LargeBogshroomFeature::new), "double_bogshroom"),
                RegUtil.withName(new BlockMidnightFungiShelf(), "bogshroom_shelf"),
                RegUtil.withName(new BlockMidnightFungiStem(), "bogshroom_stem"),
                RegUtil.withName(new BlockMidnightFungiHat(() -> BOGSHROOM,() -> ModItems.BOGSHROOM_POWDER, MapColor.ADOBE), "bogshroom_hat"),
                RegUtil.withName(new BlockBulbFungus(LargeBulbFungusFeature::new), "bulb_fungus"),
                RegUtil.withName(new BlockMidnightFungiShelf(), "nightshroom_shelf"),
                RegUtil.withName(new BlockMidnightFungiShelf(), "dewshroom_shelf"),
                RegUtil.withName(new BlockMidnightFungiShelf(), "viridshroom_shelf"),
                RegUtil.withName(new BlockLumenBud(), "lumen_bud"),
                RegUtil.withName(new BlockMidnightDoublePlant(true), "double_lumen_bud"),
                RegUtil.withName(new BlockBladeshroom(), "bladeshroom"),
                RegUtil.withName(new BlockBogweed(), "bogweed"),
                RegUtil.withName(new BlockGhostPlant(), "ghost_plant"),
                RegUtil.withName(new BlockFingeredGrass(), "fingered_grass"),
                RegUtil.withName(new BlockTendrilweed(), "tendrilweed"),
                RegUtil.withName(new BlockMidnightPlant(false), "runebush"),
                RegUtil.withName(new BlockDragonNest(), "dragon_nest"),
                RegUtil.withName(new BlockVioleaf(), "violeaf"),
                RegUtil.withName(new BlockMidnightPlant(true), "crystal_flower"),
                RegUtil.withName(new BlockMidnightSapling(ShadowrootTreeFeature::new), "shadowroot_sapling"),
                RegUtil.withName(new BlockMidnightSapling(DarkWillowTreeFeature::new), "dark_willow_sapling"),
                RegUtil.withName(new BlockDarkWater(), "dark_water"),
                RegUtil.withName(new BlockMushroomInside(), "mushroom_inside"),
                RegUtil.withName(new BlockDeceitfulMud(), "deceitful_mud"),
                RegUtil.withName(new BlockMidnightDirt(), "deceitful_peat"),
                RegUtil.withName(new BlockDeceitfulAlgae(), "deceitful_algae"),
                RegUtil.withName(new BlockDeceitfulMoss(), "deceitful_moss"),
                RegUtil.withName(new BlockStingerEgg(), "stinger_egg"),
                RegUtil.withName(new BlockUnstableBush(), "unstable_bush"),
                RegUtil.withName(new BlockUnstableBushBloomed(() -> ModItems.UNSTABLE_FRUIT_BLUE), "unstable_bush_blue_bloomed"),
                RegUtil.withName(new BlockUnstableBushBloomed(() -> ModItems.UNSTABLE_FRUIT_GREEN), "unstable_bush_green_bloomed"),
                RegUtil.withName(new BlockUnstableBushBloomed(() -> ModItems.UNSTABLE_FRUIT_LIME), "unstable_bush_lime_bloomed"),
                RegUtil.withName(new BlockCrystalotus(), "crystalotus")
        ));

        blocks.addAll(Lists.newArrayList(
                RegUtil.withName(new BlockNightstone(), "nightstone"),
                RegUtil.withName(new BlockBasic(Material.ROCK), "trenchstone")
                        .withHarvestLevel("pickaxe", 2)
                        .setHardness(5.0F)
                        .setResistance(200.0F),
                RegUtil.withName(new BlockBloomCrystal(), "bloomcrystal"),
                RegUtil.withName(new BlockBasic(Material.ROCK, MapColor.PINK), "bloomcrystal_rock")
                        .withSoundType(SoundType.GLASS)
                        .withHarvestLevel("pickaxe", 1)
                        .setLightLevel(1.0F)
                        .setHardness(4.0F),
                RegUtil.withName(new BlockCrystal(MapColor.RED), "rouxe"),
                RegUtil.withName(new BlockBasic(Material.ROCK, MapColor.RED), "rouxe_rock")
                        .withSoundType(SoundType.GLASS)
                        .withHarvestLevel("pickaxe", 1)
                        .withGlow()
                        .setLightLevel(0.2F)
                        .setHardness(4.0F),
                RegUtil.withName(new BlockMiasmaSurface(), "miasma_surface"),
                RegUtil.withName(new BlockMiasmaFluid(), "miasma"),
                RegUtil.withName(new BlockMidnightGem(() -> ModItems.GEODE, 0), "dark_pearl_ore"),
                RegUtil.withName(new BlockMidnightOre(2), "tenebrum_ore"),
                RegUtil.withName(new BlockMidnightOre(2), "nagrilite_ore"),
                RegUtil.withName(new BlockMidnightGem(() -> ModItems.EBONYS, 1), "ebonys_ore"),
                RegUtil.withName(new BlockMidnightGem(() -> ModItems.ARCHAIC_SHARD, 0), "archaic_ore")
        ));

        blocks.addAll(Lists.newArrayList(
                RegUtil.withName(new BlockBasic(Material.ROCK), "nightstone_bricks")
                        .setHardness(1.5F)
                        .setResistance(10.0F),
                RegUtil.withName(new BlockBasic(Material.ROCK), "trenchstone_bricks")
                        .withHarvestLevel("pickaxe", 2)
                        .setHardness(5.0F)
                        .setResistance(200.0F),
                RegUtil.withName(new BlockBasic(Material.ROCK), "chiseled_nightstone_bricks")
                        .setHardness(1.5F)
                        .setResistance(10.0F),
                RegUtil.withName(new BlockBasic(Material.ROCK), "rockshroom_bricks")
                        .setHardness(1.5F)
                        .setResistance(10.0F),
                RegUtil.withName(new BlockBasic(Material.IRON), "dark_pearl_block")
                        .withSoundType(SoundType.METAL)
                        .setHardness(3.0F),
                RegUtil.withName(new BlockBasic(Material.IRON), "tenebrum_block")
                        .withSoundType(SoundType.METAL)
                        .setHardness(3.0F),
                RegUtil.withName(new BlockBasic(Material.IRON), "nagrilite_block")
                        .withSoundType(SoundType.METAL)
                        .setHardness(3.0F),
                RegUtil.withName(new BlockBasic(Material.IRON), "ebonys_block")
                        .withSoundType(SoundType.METAL)
                        .setHardness(3.0F),
                RegUtil.withName(new BlockMidnightGlass(), "archaic_glass"),
                RegUtil.withName(new BlockMidnightGlassPane(), "archaic_glass_pane"),
                RegUtil.withName(new BlockMidnightWoodPlank(), "shadowroot_planks"),
                RegUtil.withName(new BlockMidnightWoodPlank(), "dead_wood_planks"),
                RegUtil.withName(new BlockMidnightWoodPlank(), "dark_willow_planks"),
                RegUtil.withName(new BlockMidnightWoodPlank(), "nightshroom_planks"),
                RegUtil.withName(new BlockMidnightWoodPlank(), "dewshroom_planks"),
                RegUtil.withName(new BlockMidnightWoodPlank(), "viridshroom_planks"),
                RegUtil.withName(new BlockMidnightDoor(() -> ModItems.SHADOWROOT_DOOR), "shadowroot_door"),
                RegUtil.withName(new BlockMidnightDoor(() -> ModItems.DARK_WILLOW_DOOR), "dark_willow_door"),
                RegUtil.withName(new BlockMidnightDoor(() -> ModItems.DEAD_WOOD_DOOR), "dead_wood_door"),
                RegUtil.withName(new BlockMidnightDoor(true, () -> ModItems.TENEBRUM_DOOR), "tenebrum_door"),
                RegUtil.withName(new BlockMidnightDoor(() -> ModItems.NIGHTSHROOM_DOOR), "nightshroom_door"),
                RegUtil.withName(new BlockMidnightDoor(() -> ModItems.DEWSHROOM_DOOR), "dewshroom_door"),
                RegUtil.withName(new BlockMidnightDoor(() -> ModItems.VIRIDSHROOM_DOOR), "viridshroom_door"),
                RegUtil.withName(new BlockMidnightTrapDoor(), "shadowroot_trapdoor"),
                RegUtil.withName(new BlockMidnightTrapDoor(), "dark_willow_trapdoor"),
                RegUtil.withName(new BlockMidnightTrapDoor(), "dead_wood_trapdoor"),
                RegUtil.withName(new BlockMidnightTrapDoor(true), "tenebrum_trapdoor"),
                RegUtil.withName(new BlockMidnightTrapDoor(), "nightshroom_trapdoor"),
                RegUtil.withName(new BlockMidnightTrapDoor(), "dewshroom_trapdoor"),
                RegUtil.withName(new BlockMidnightTrapDoor(), "viridshroom_trapdoor"),
                RegUtil.withName(new BlockShadowrootCraftingTable(), "shadowroot_crafting_table"),
                RegUtil.withName(new BlockMidnightChest(ChestModel.SHADOWROOT), "shadowroot_chest"),
                RegUtil.withName(new BlockMidnightFurnace(false), "midnight_furnace"),
                RegUtil.withName(new BlockMidnightFurnace(true), "midnight_furnace_lit"),
                RegUtil.withName(new BlockMidnightSlab(() -> SHADOWROOT_PLANKS.getDefaultState()), "shadowroot_slab"),
                RegUtil.withName(new BlockMidnightSlab(() -> DEAD_WOOD_PLANKS.getDefaultState()), "dead_wood_slab"),
                RegUtil.withName(new BlockMidnightSlab(() -> DARK_WILLOW_PLANKS.getDefaultState()), "dark_willow_slab"),
                RegUtil.withName(new BlockMidnightSlab(() -> NIGHTSTONE.getDefaultState()), "nightstone_slab"),
                RegUtil.withName(new BlockMidnightSlab(() -> NIGHTSTONE_BRICKS.getDefaultState()), "nightstone_brick_slab"),
                RegUtil.withName(new BlockMidnightSlab(() -> TRENCHSTONE.getDefaultState()), "trenchstone_slab"),
                RegUtil.withName(new BlockMidnightSlab(() -> TRENCHSTONE_BRICKS.getDefaultState()), "trenchstone_brick_slab"),
                RegUtil.withName(new BlockMidnightSlab(() -> DEWSHROOM_PLANKS.getDefaultState()), "dewshroom_slab"),
                RegUtil.withName(new BlockMidnightSlab(() -> VIRIDSHROOM_PLANKS.getDefaultState()), "viridshroom_slab"),
                RegUtil.withName(new BlockMidnightSlab(() -> NIGHTSHROOM_PLANKS.getDefaultState()), "nightshroom_slab"),
                RegUtil.withName(new BlockMidnightSlab(() -> ROCKSHROOM_BRICKS.getDefaultState()), "rockshroom_bricks_slab"),
                RegUtil.withName(new BlockMidnightDoubleSlab(() -> SHADOWROOT_SLAB), "shadowroot_double_slab"),
                RegUtil.withName(new BlockMidnightDoubleSlab(() -> DEAD_WOOD_SLAB), "dead_wood_double_slab"),
                RegUtil.withName(new BlockMidnightDoubleSlab(() -> DARK_WILLOW_SLAB), "dark_willow_double_slab"),
                RegUtil.withName(new BlockMidnightDoubleSlab(() -> NIGHTSTONE_SLAB), "nightstone_double_slab"),
                RegUtil.withName(new BlockMidnightDoubleSlab(() -> NIGHTSTONE_BRICK_SLAB), "nightstone_brick_double_slab"),
                RegUtil.withName(new BlockMidnightDoubleSlab(() -> TRENCHSTONE_SLAB), "trenchstone_double_slab"),
                RegUtil.withName(new BlockMidnightDoubleSlab(() -> TRENCHSTONE_BRICK_SLAB), "trenchstone_brick_double_slab"),
                RegUtil.withName(new BlockMidnightDoubleSlab(() -> DEWSHROOM_SLAB), "dewshroom_double_slab"),
                RegUtil.withName(new BlockMidnightDoubleSlab(() -> VIRIDSHROOM_SLAB), "viridshroom_double_slab"),
                RegUtil.withName(new BlockMidnightDoubleSlab(() -> NIGHTSHROOM_SLAB), "nightshroom_double_slab"),
                RegUtil.withName(new BlockMidnightDoubleSlab(() -> ROCKSHROOM_BRICKS_SLAB), "rockshroom_bricks_double_slab"),
                RegUtil.withName(new BlockMidnightStairs(() -> SHADOWROOT_PLANKS.getDefaultState()), "shadowroot_stairs"),
                RegUtil.withName(new BlockMidnightStairs(() -> DEAD_WOOD_PLANKS.getDefaultState()), "dead_wood_stairs"),
                RegUtil.withName(new BlockMidnightStairs(() -> DARK_WILLOW_PLANKS.getDefaultState()), "dark_willow_stairs"),
                RegUtil.withName(new BlockMidnightStairs(() -> NIGHTSTONE.getDefaultState()), "nightstone_stairs"),
                RegUtil.withName(new BlockMidnightStairs(() -> NIGHTSTONE_BRICKS.getDefaultState()), "nightstone_brick_stairs"),
                RegUtil.withName(new BlockMidnightStairs(() -> TRENCHSTONE.getDefaultState()), "trenchstone_stairs"),
                RegUtil.withName(new BlockMidnightStairs(() -> TRENCHSTONE_BRICKS.getDefaultState()), "trenchstone_brick_stairs"),
                RegUtil.withName(new BlockMidnightStairs(() -> DEWSHROOM_PLANKS.getDefaultState()), "dewshroom_stairs"),
                RegUtil.withName(new BlockMidnightStairs(() -> VIRIDSHROOM_PLANKS.getDefaultState()), "viridshroom_stairs"),
                RegUtil.withName(new BlockMidnightStairs(() -> NIGHTSHROOM_PLANKS.getDefaultState()), "nightshroom_stairs"),
                RegUtil.withName(new BlockMidnightStairs(() -> ROCKSHROOM_BRICKS.getDefaultState()), "rockshroom_bricks_stairs"),
                RegUtil.withName(new BlockMidnightFence(() -> SHADOWROOT_PLANKS.getDefaultState()), "shadowroot_fence"),
                RegUtil.withName(new BlockMidnightFence(() -> DEAD_WOOD_PLANKS.getDefaultState()), "dead_wood_fence"),
                RegUtil.withName(new BlockMidnightFence(() -> DARK_WILLOW_PLANKS.getDefaultState()), "dark_willow_fence"),
                RegUtil.withName(new BlockMidnightWall(() -> NIGHTSTONE.getDefaultState()), "nightstone_wall"),
                RegUtil.withName(new BlockMidnightWall(() -> NIGHTSTONE_BRICKS.getDefaultState()), "nightstone_brick_wall"),
                RegUtil.withName(new BlockMidnightWall(() -> TRENCHSTONE.getDefaultState()), "trenchstone_wall"),
                RegUtil.withName(new BlockMidnightWall(() -> TRENCHSTONE_BRICKS.getDefaultState()), "trenchstone_brick_wall"),
                RegUtil.withName(new BlockMidnightWall(() -> ROCKSHROOM_BRICKS.getDefaultState()), "rockshroom_bricks_wall"),
                RegUtil.withName(new BlockMidnightFence(() -> DEWSHROOM_PLANKS.getDefaultState()), "dewshroom_fence"),
                RegUtil.withName(new BlockMidnightFence(() -> VIRIDSHROOM_PLANKS.getDefaultState()), "viridshroom_fence"),
                RegUtil.withName(new BlockMidnightFence(() -> NIGHTSHROOM_PLANKS.getDefaultState()), "nightshroom_fence"),
                RegUtil.withName(new BlockMidnightFenceGate(() -> SHADOWROOT_PLANKS.getDefaultState()), "shadowroot_fence_gate"),
                RegUtil.withName(new BlockMidnightFenceGate(() -> DEAD_WOOD_PLANKS.getDefaultState()), "dead_wood_fence_gate"),
                RegUtil.withName(new BlockMidnightFenceGate(() -> DARK_WILLOW_PLANKS.getDefaultState()), "dark_willow_fence_gate"),
                RegUtil.withName(new BlockMidnightFenceGate(() -> DEWSHROOM_PLANKS.getDefaultState()), "dewshroom_fence_gate"),
                RegUtil.withName(new BlockMidnightFenceGate(() -> VIRIDSHROOM_PLANKS.getDefaultState()), "viridshroom_fence_gate"),
                RegUtil.withName(new BlockMidnightFenceGate(() -> NIGHTSHROOM_PLANKS.getDefaultState()), "nightshroom_fence_gate"),
                RegUtil.withName(new BlockSuavis(), "suavis"),
                RegUtil.withName(new BlockMidnightLadder(), "shadowroot_ladder"),
                RegUtil.withName(new BlockMidnightLadder(), "dead_wood_ladder"),
                RegUtil.withName(new BlockMidnightLadder(), "dark_willow_ladder"),
                RegUtil.withName(new BlockMidnightLadder(), "dewshroom_ladder"),
                RegUtil.withName(new BlockMidnightLadder(), "viridshroom_ladder"),
                RegUtil.withName(new BlockMidnightLadder(), "nightshroom_ladder"),
                RegUtil.withName(new BlockSporch(BlockSporch.SporchType.BOGSHROOM), "bogshroom_sporch"),
                RegUtil.withName(new BlockSporch(BlockSporch.SporchType.NIGHTSHROOM), "nightshroom_sporch"),
                RegUtil.withName(new BlockSporch(BlockSporch.SporchType.DEWSHROOM), "dewshroom_sporch"),
                RegUtil.withName(new BlockSporch(BlockSporch.SporchType.VIRIDSHROOM), "viridshroom_sporch"),
                RegUtil.withName(new BlockMidnightButton(true, 0.75F), "shadowroot_button"),
                RegUtil.withName(new BlockMidnightButton(true, 0.75F), "dead_wood_button"),
                RegUtil.withName(new BlockMidnightButton(true, 0.75F), "dark_willow_button"),
                RegUtil.withName(new BlockMidnightButton(true, 0.75F), "dewshroom_button"),
                RegUtil.withName(new BlockMidnightButton(true, 0.75F), "viridshroom_button"),
                RegUtil.withName(new BlockMidnightButton(true, 0.75F), "nightshroom_button"),
                RegUtil.withName(new BlockMidnightButton(false, 1.0F), "nightstone_button"),
                RegUtil.withName(new BlockMidnightButton(false, 3.75F), "trenchstone_button"),
                RegUtil.withName(new BlockMidnightButton(false, 1.0F), "rockshroom_bricks_button"),
                RegUtil.withName(new BlockMidnightPressurePlate(true, 0.75F), "shadowroot_pressure_plate"),
                RegUtil.withName(new BlockMidnightPressurePlate(true, 0.75F), "dead_wood_pressure_plate"),
                RegUtil.withName(new BlockMidnightPressurePlate(true, 0.75F), "dark_willow_pressure_plate"),
                RegUtil.withName(new BlockMidnightPressurePlate(true, 0.75F), "dewshroom_pressure_plate"),
                RegUtil.withName(new BlockMidnightPressurePlate(true, 0.75F), "viridshroom_pressure_plate"),
                RegUtil.withName(new BlockMidnightPressurePlate(true, 0.75F), "nightshroom_pressure_plate"),
                RegUtil.withName(new BlockMidnightPressurePlate(false, 1.0F), "nightstone_pressure_plate"),
                RegUtil.withName(new BlockMidnightPressurePlate(false, 3.75F), "trenchstone_pressure_plate"),
                RegUtil.withName(new BlockMidnightPressurePlate(false, 1.0F), "rockshroom_bricks_pressure_plate"),
                RegUtil.withName(new BlockMidnightPressurePlateWeighted(Material.IRON, true, Material.IRON.getMaterialMapColor(), 2.0F), "nagrilite_pressure_plate"),
                RegUtil.withName(new BlockMidnightPressurePlateWeighted(Material.IRON, false, Material.IRON.getMaterialMapColor(), 2.0F), "tenebrum_pressure_plate"),
                RegUtil.withName(new BlockMidnightLever(), "midnight_lever")
        ));

        blocks.forEach(event.getRegistry()::register);

        registerTile(TileEntityMidnightChest.class, "tile_shadowroot_chest");
        registerTile(TileEntityMidnightFurnace.class, "tile_midnight_furnace");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.registerAll(itemBlocks(
                SHADOWROOT_LOG, SHADOWROOT_LEAVES, SHADOWROOT_PLANKS,
                DEAD_WOOD_LOG, DEAD_WOOD_PLANKS,
                DARK_WILLOW_LOG, DARK_WILLOW_LEAVES, DARK_WILLOW_PLANKS,
                NIGHTSTONE, NIGHTSTONE_BRICKS, CHISELED_NIGHTSTONE_BRICKS,
                TRENCHSTONE, TRENCHSTONE_BRICKS,
                DARK_PEARL_ORE, DARK_PEARL_BLOCK,
                TENEBRUM_ORE, TENEBRUM_BLOCK,
                NAGRILITE_ORE, NAGRILITE_BLOCK,
                EBONYS_ORE, EBONYS_BLOCK,
                ARCHAIC_ORE,
                SHADOWROOT_CRAFTING_TABLE,
                SHADOWROOT_CHEST,
                MIDNIGHT_FURNACE,
                MIDNIGHT_COARSE_DIRT, MIDNIGHT_DIRT, MIDNIGHT_GRASS, MIDNIGHT_MYCELIUM,
                TALL_MIDNIGHT_GRASS, DOUBLE_MIDNIGHT_GRASS,
                NIGHTSHROOM, DOUBLE_NIGHTSHROOM, NIGHTSHROOM_SHELF,
                DEWSHROOM, DOUBLE_DEWSHROOM, DEWSHROOM_SHELF,
                VIRIDSHROOM, DOUBLE_VIRIDSHROOM, VIRIDSHROOM_SHELF,
                BOGSHROOM, DOUBLE_BOGSHROOM, BOGSHROOM_SHELF, BOGSHROOM_HAT, BOGSHROOM_STEM,
                NIGHTSHROOM_STEM, NIGHTSHROOM_HAT,
                DEWSHROOM_STEM, DEWSHROOM_HAT,
                VIRIDSHROOM_STEM, VIRIDSHROOM_HAT,
                BULB_FUNGUS, BULB_FUNGUS_STEM, BULB_FUNGUS_HAT,
                NIGHTSHROOM_PLANKS, DEWSHROOM_PLANKS, VIRIDSHROOM_PLANKS,
                ROCKSHROOM, ROCKSHROOM_BRICKS,
                LUMEN_BUD, DOUBLE_LUMEN_BUD,
                BOGWEED, GHOST_PLANT, FINGERED_GRASS, TENDRILWEED, RUNEBUSH, DRAGON_NEST,
                VIOLEAF, CRYSTAL_FLOWER,
                SHADOWROOT_SAPLING, DARK_WILLOW_SAPLING,
                SHADOWROOT_TRAPDOOR, DARK_WILLOW_TRAPDOOR, DEAD_WOOD_TRAPDOOR, TENEBRUM_TRAPDOOR,
                NIGHTSHROOM_TRAPDOOR, DEWSHROOM_TRAPDOOR, VIRIDSHROOM_TRAPDOOR,
                BLOOMCRYSTAL, BLOOMCRYSTAL_ROCK,
                ROUXE, ROUXE_ROCK,
                ARCHAIC_GLASS, ARCHAIC_GLASS_PANE,
                MIASMA_SURFACE, MIASMA,
                DARK_WATER,
                DECEITFUL_PEAT, DECEITFUL_MUD, DECEITFUL_MOSS,
                SHADOWROOT_STAIRS, DEAD_WOOD_STAIRS, DARK_WILLOW_STAIRS,
                NIGHTSTONE_STAIRS, NIGHTSTONE_BRICK_STAIRS,
                TRENCHSTONE_STAIRS, TRENCHSTONE_BRICK_STAIRS,
                DEWSHROOM_STAIRS, VIRIDSHROOM_STAIRS, NIGHTSHROOM_STAIRS, ROCKSHROOM_BRICKS_STAIRS,
                NIGHTSTONE_WALL, NIGHTSTONE_BRICK_WALL,
                TRENCHSTONE_WALL, TRENCHSTONE_BRICK_WALL, ROCKSHROOM_BRICKS_WALL,
                SHADOWROOT_FENCE, DEAD_WOOD_FENCE, DARK_WILLOW_FENCE,
                DEWSHROOM_FENCE, VIRIDSHROOM_FENCE, NIGHTSHROOM_FENCE,
                SHADOWROOT_FENCE_GATE, DEAD_WOOD_FENCE_GATE, DARK_WILLOW_FENCE_GATE,
                DEWSHROOM_FENCE_GATE, VIRIDSHROOM_FENCE_GATE, NIGHTSHROOM_FENCE_GATE,
                SUAVIS, SHADOWROOT_LADDER, DEAD_WOOD_LADDER, DARK_WILLOW_LADDER,
                DEWSHROOM_LADDER, VIRIDSHROOM_LADDER, NIGHTSHROOM_LADDER,
                BOGSHROOM_SPORCH, NIGHTSHROOM_SPORCH, DEWSHROOM_SPORCH, VIRIDSHROOM_SPORCH,
                STINGER_EGG, CRYSTALOTUS,
                SHADOWROOT_BUTTON, DEAD_WOOD_BUTTON, DARK_WILLOW_BUTTON, DEWSHROOM_BUTTON, VIRIDSHROOM_BUTTON, NIGHTSHROOM_BUTTON, NIGHTSTONE_BUTTON, TRENCHSTONE_BUTTON, ROCKSHROOM_BRICKS_BUTTON,
                SHADOWROOT_PRESSURE_PLATE, DEAD_WOOD_PRESSURE_PLATE, DARK_WILLOW_PRESSURE_PLATE, DEWSHROOM_PRESSURE_PLATE, VIRIDSHROOM_PRESSURE_PLATE, NIGHTSHROOM_PRESSURE_PLATE, NIGHTSTONE_PRESSURE_PLATE, TRENCHSTONE_PRESSURE_PLATE, ROCKSHROOM_BRICKS_PRESSURE_PLATE, NAGRILITE_PRESSURE_PLATE, TENEBRUM_PRESSURE_PLATE,
                MIDNIGHT_LEVER
        ));

        registry.register(itemBlock(DECEITFUL_ALGAE, ItemDeceitfulAlgae::new));

        registry.register(itemBlock(SHADOWROOT_SLAB, b -> new ItemMidnightSlab(b, SHADOWROOT_DOUBLE_SLAB)));
        registry.register(itemBlock(DEAD_WOOD_SLAB, b -> new ItemMidnightSlab(b, DEAD_WOOD_DOUBLE_SLAB)));
        registry.register(itemBlock(DARK_WILLOW_SLAB, b -> new ItemMidnightSlab(b, DARK_WILLOW_DOUBLE_SLAB)));
        registry.register(itemBlock(NIGHTSTONE_SLAB, b -> new ItemMidnightSlab(b, NIGHTSTONE_DOUBLE_SLAB)));
        registry.register(itemBlock(NIGHTSTONE_BRICK_SLAB, b -> new ItemMidnightSlab(b, NIGHTSTONE_BRICK_DOUBLE_SLAB)));
        registry.register(itemBlock(TRENCHSTONE_SLAB, b -> new ItemMidnightSlab(b, TRENCHSTONE_DOUBLE_SLAB)));
        registry.register(itemBlock(TRENCHSTONE_BRICK_SLAB, b -> new ItemMidnightSlab(b, TRENCHSTONE_BRICK_DOUBLE_SLAB)));
        registry.register(itemBlock(DEWSHROOM_SLAB, b -> new ItemMidnightSlab(b, DEWSHROOM_DOUBLE_SLAB)));
        registry.register(itemBlock(VIRIDSHROOM_SLAB, b -> new ItemMidnightSlab(b, VIRIDSHROOM_DOUBLE_SLAB)));
        registry.register(itemBlock(NIGHTSHROOM_SLAB, b -> new ItemMidnightSlab(b, NIGHTSHROOM_DOUBLE_SLAB)));
        registry.register(itemBlock(ROCKSHROOM_BRICKS_SLAB, b -> new ItemMidnightSlab(b, ROCKSHROOM_BRICKS_DOUBLE_SLAB)));

        EntityEnderman.setCarriable(MIDNIGHT_GRASS, true);
        EntityEnderman.setCarriable(MIDNIGHT_DIRT, true);
        EntityEnderman.setCarriable(NIGHTSTONE, true);
        EntityEnderman.setCarriable(SUAVIS, true);
        EntityEnderman.setCarriable(TRENCHSTONE, true);
        EntityEnderman.setCarriable(ROCKSHROOM, true);
        EntityEnderman.setCarriable(DEWSHROOM, true);
        EntityEnderman.setCarriable(VIRIDSHROOM, true);
        EntityEnderman.setCarriable(NIGHTSHROOM, true);
        EntityEnderman.setCarriable(GHOST_PLANT, true);
    }

    public static void onInit() {
        OreDictionary.registerOre("logWood", SHADOWROOT_LOG);
        OreDictionary.registerOre("logWood", DARK_WILLOW_LOG);
        OreDictionary.registerOre("logWood", DEAD_WOOD_LOG);

        // TODO: Temporary solution. Trapdoor recipes get overridden by vanilla recipes if these are registered
        OreDictionary.registerOre("plankWoodMidnight", SHADOWROOT_PLANKS);
        OreDictionary.registerOre("plankWoodMidnight", DARK_WILLOW_PLANKS);
        OreDictionary.registerOre("plankWoodMidnight", DEAD_WOOD_PLANKS);
        OreDictionary.registerOre("plankWoodMidnight", NIGHTSHROOM_PLANKS);
        OreDictionary.registerOre("plankWoodMidnight", VIRIDSHROOM_PLANKS);
        OreDictionary.registerOre("plankWoodMidnight", DEWSHROOM_PLANKS);

        OreDictionary.registerOre("treeLeaves", SHADOWROOT_LEAVES);
        OreDictionary.registerOre("treeLeaves", DARK_WILLOW_LEAVES);

        OreDictionary.registerOre("oreEbonys", EBONYS_ORE);
        OreDictionary.registerOre("blockEbonys", EBONYS_BLOCK);

        OreDictionary.registerOre("oreArchaic", ARCHAIC_ORE);

        OreDictionary.registerOre("oreNagrilite", NAGRILITE_ORE);
        OreDictionary.registerOre("blockNagrilite", NAGRILITE_BLOCK);

        OreDictionary.registerOre("oreTenebrum", TENEBRUM_ORE);
        OreDictionary.registerOre("blockTenebrum", TENEBRUM_BLOCK);

        OreDictionary.registerOre("chest", SHADOWROOT_CHEST);
        OreDictionary.registerOre("chestWood", SHADOWROOT_CHEST);
    }

    private static Item[] itemBlocks(Block... blocks) {
        Item[] items = new Item[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            items[i] = itemBlock(blocks[i]);
        }
        return items;
    }

    private static Item itemBlock(Block block) {
        return itemBlock(block, ItemBlock::new);
    }

    private static Item itemBlock(Block block, Function<Block, ItemBlock> supplier) {
        ItemBlock item = supplier.apply(block);
        if (block.getRegistryName() == null) {
            throw new IllegalArgumentException("Cannot create ItemBlock for Block without registry name");
        }
        item.setRegistryName(block.getRegistryName());
        return item;
    }

    private static void registerTile(Class<? extends TileEntity> entityClass, String registryName) {
        GameRegistry.registerTileEntity(entityClass, new ResourceLocation(Midnight.MODID, registryName));
    }

    public static Collection<Block> getBlocks() {
        return Collections.unmodifiableCollection(blocks);
    }
}
