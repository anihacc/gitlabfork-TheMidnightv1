package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.world.feature.AlgaeFeature;
import com.mushroom.midnight.common.world.feature.BladeshroomFeature;
import com.mushroom.midnight.common.world.feature.BogweedFlowersFeature;
import com.mushroom.midnight.common.world.feature.CrystalClusterFeature;
import com.mushroom.midnight.common.world.feature.CrystalFlowersFeature;
import com.mushroom.midnight.common.world.feature.DeadLogFeature;
import com.mushroom.midnight.common.world.feature.FingeredGrassFeature;
import com.mushroom.midnight.common.world.feature.FungiPatchFeature;
import com.mushroom.midnight.common.world.feature.GlobFungiFlowersFeature;
import com.mushroom.midnight.common.world.feature.HeapFeature;
import com.mushroom.midnight.common.world.feature.MidnightDoublePlantFeature;
import com.mushroom.midnight.common.world.feature.MidnightOreFeature;
import com.mushroom.midnight.common.world.feature.MossFeature;
import com.mushroom.midnight.common.world.feature.NightstoneBoulderFeature;
import com.mushroom.midnight.common.world.feature.ScatteredPlantFeature;
import com.mushroom.midnight.common.world.feature.SpikeFeature;
import com.mushroom.midnight.common.world.feature.StingerEggFeature;
import com.mushroom.midnight.common.world.feature.TrenchstoneBoulderFeature;
import com.mushroom.midnight.common.world.feature.UnstableBushFeature;
import com.mushroom.midnight.common.world.feature.ViridshroomCacheFeature;
import com.mushroom.midnight.common.world.feature.config.CrystalClusterConfig;
import com.mushroom.midnight.common.world.feature.config.MidnightOreConfig;
import com.mushroom.midnight.common.world.feature.config.UniformCompositionConfig;
import com.mushroom.midnight.common.world.feature.structure.WellStructure;
import com.mushroom.midnight.common.world.feature.tree.DarkWillowTreeFeature;
import com.mushroom.midnight.common.world.feature.tree.DeadTreeFeature;
import com.mushroom.midnight.common.world.feature.tree.LargeBogshroomFeature;
import com.mushroom.midnight.common.world.feature.tree.LargeDewshroomFeature;
import com.mushroom.midnight.common.world.feature.tree.LargeGlobFungusFeature;
import com.mushroom.midnight.common.world.feature.tree.LargeNightshroomFeature;
import com.mushroom.midnight.common.world.feature.tree.LargeViridshroomFeature;
import com.mushroom.midnight.common.world.feature.tree.MediumDewshroomFeature;
import com.mushroom.midnight.common.world.feature.tree.MediumNightshroomFeature;
import com.mushroom.midnight.common.world.feature.tree.MediumViridshroomFeature;
import com.mushroom.midnight.common.world.feature.tree.ShadowrootTreeFeature;
import com.mushroom.midnight.common.world.feature.tree.SmallBogshroomFeature;
import com.mushroom.midnight.common.world.feature.tree.SmallDewshroomFeature;
import com.mushroom.midnight.common.world.feature.tree.SmallNightshroomFeature;
import com.mushroom.midnight.common.world.feature.tree.SmallViridshroomFeature;
import com.mushroom.midnight.common.world.template.ShelfAttachProcessor;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DoublePlantConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

// TODO: Register from correct event when Forge is fixed
@ObjectHolder(Midnight.MODID)
public class MidnightFeatures {
    public static final AbstractTreeFeature<NoFeatureConfig> SHADOWROOT_TREE = new ShadowrootTreeFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature<NoFeatureConfig> DARK_WILLOW_TREE = new DarkWillowTreeFeature(NoFeatureConfig::deserialize);
    public static final Feature<NoFeatureConfig> DEAD_TREE = new DeadTreeFeature(NoFeatureConfig::deserialize, ShelfAttachProcessor.FOREST_SHELF_BLOCKS);
    public static final Feature<NoFeatureConfig> BOG_DEAD_TREE = new DeadTreeFeature(NoFeatureConfig::deserialize, ShelfAttachProcessor.SHELF_BLOCKS);
    public static final Feature<NoFeatureConfig> DEAD_LOG = new DeadLogFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature<NoFeatureConfig> LARGE_GLOB_FUNGUS = new LargeGlobFungusFeature(NoFeatureConfig::deserialize);

    public static final AbstractTreeFeature<NoFeatureConfig> LARGE_BOGSHROOM = new LargeBogshroomFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature<NoFeatureConfig> LARGE_NIGHTSHROOM = new LargeNightshroomFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature<NoFeatureConfig> LARGE_DEWSHROOM = new LargeDewshroomFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature<NoFeatureConfig> LARGE_VIRIDSHROOM = new LargeViridshroomFeature(NoFeatureConfig::deserialize);

    public static final AbstractTreeFeature<NoFeatureConfig> MEDIUM_NIGHTSHROOM = new MediumNightshroomFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature<NoFeatureConfig> MEDIUM_DEWSHROOM = new MediumDewshroomFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature<NoFeatureConfig> MEDIUM_VIRIDSHROOM = new MediumViridshroomFeature(NoFeatureConfig::deserialize);

    public static final AbstractTreeFeature<NoFeatureConfig> SMALL_BOGSHROOM = new SmallBogshroomFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature<NoFeatureConfig> SMALL_NIGHTSHROOM = new SmallNightshroomFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature<NoFeatureConfig> SMALL_DEWSHROOM = new SmallDewshroomFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature<NoFeatureConfig> SMALL_VIRIDSHROOM = new SmallViridshroomFeature(NoFeatureConfig::deserialize);

    public static final Feature<NoFeatureConfig> SUAVIS = new ScatteredPlantFeature(NoFeatureConfig::deserialize, MidnightBlocks.SUAVIS.getDefaultState());
    public static final Feature<NoFeatureConfig> DECEITFUL_ALGAE = new AlgaeFeature(NoFeatureConfig::deserialize);
    public static final Feature<NoFeatureConfig> DECEITFUL_MOSS = new MossFeature(NoFeatureConfig::deserialize);
    public static final Feature<NoFeatureConfig> BLADESHROOM = new BladeshroomFeature(NoFeatureConfig::deserialize);
    public static final Feature<NoFeatureConfig> FINGERED_GRASS = new FingeredGrassFeature(NoFeatureConfig::deserialize);

    public static final Feature<DoublePlantConfig> DOUBLE_PLANT = new MidnightDoublePlantFeature(DoublePlantConfig::deserialize);

    public static final Feature<NoFeatureConfig> NIGHTSHROOM_FLOWERS = new FungiPatchFeature(NoFeatureConfig::deserialize, MidnightBlocks.NIGHTSHROOM.getDefaultState(), MidnightBlocks.DOUBLE_NIGHTSHROOM.getDefaultState(), SMALL_NIGHTSHROOM);
    public static final Feature<NoFeatureConfig> DEWSHROOM_FLOWERS = new FungiPatchFeature(NoFeatureConfig::deserialize, MidnightBlocks.DEWSHROOM.getDefaultState(), MidnightBlocks.DOUBLE_DEWSHROOM.getDefaultState(), SMALL_DEWSHROOM);
    public static final Feature<NoFeatureConfig> VIRIDSHROOM_FLOWERS = new FungiPatchFeature(NoFeatureConfig::deserialize, MidnightBlocks.VIRIDSHROOM.getDefaultState(), MidnightBlocks.DOUBLE_VIRIDSHROOM.getDefaultState(), SMALL_VIRIDSHROOM);
    public static final Feature<NoFeatureConfig> BOGSHROOM_FLOWERS = new FungiPatchFeature(NoFeatureConfig::deserialize, MidnightBlocks.BOGSHROOM.getDefaultState(), MidnightBlocks.DOUBLE_BOGSHROOM.getDefaultState(), LARGE_BOGSHROOM);
    public static final Feature<NoFeatureConfig> MISTSHROOM_FLOWERS = new FungiPatchFeature(NoFeatureConfig::deserialize, MidnightBlocks.MISTSHROOM.getDefaultState(), MidnightBlocks.DOUBLE_MISTSHROOM.getDefaultState());
    public static final Feature<NoFeatureConfig> GLOB_FUNGI_FLOWERS = new GlobFungiFlowersFeature(NoFeatureConfig::deserialize);
    public static final Feature<NoFeatureConfig> BOGWEED_FLOWERS = new BogweedFlowersFeature(NoFeatureConfig::deserialize);
    public static final Feature<NoFeatureConfig> CRYSTAL_FLOWERS = new CrystalFlowersFeature(NoFeatureConfig::deserialize);
    public static final Feature<NoFeatureConfig> LUMEN_FLOWERS = new FungiPatchFeature(NoFeatureConfig::deserialize, MidnightBlocks.LUMEN_BUD.getDefaultState(), MidnightBlocks.DOUBLE_LUMEN_BUD.getDefaultState());

    public static final Feature<NoFeatureConfig> UNSTABLE_BUSH = new UnstableBushFeature(NoFeatureConfig::deserialize);

    public static final Feature<UniformCompositionConfig> HEAP = new HeapFeature(UniformCompositionConfig::deserialize);
    public static final Feature<UniformCompositionConfig> SPIKE = new SpikeFeature(UniformCompositionConfig::deserialize);
    public static final Feature<NoFeatureConfig> NIGHTSTONE_BOULDER = new NightstoneBoulderFeature(NoFeatureConfig::deserialize);
    public static final Feature<NoFeatureConfig> TRENCHSTONE_BOULDER = new TrenchstoneBoulderFeature(NoFeatureConfig::deserialize);
    public static final Feature<CrystalClusterConfig> CRYSTAL_CLUSTER = new CrystalClusterFeature(CrystalClusterConfig::deserialize, 3, 4);
    public static final Feature<CrystalClusterConfig> CRYSTAL_SPIRE = new CrystalClusterFeature(CrystalClusterConfig::deserialize, 4, 13);

    public static final Feature<MidnightOreConfig> ORE = new MidnightOreFeature(MidnightOreConfig::deserialize);
    public static final Feature<NoFeatureConfig> STINGER_EGG = new StingerEggFeature(NoFeatureConfig::deserialize);

    //structure
    public static final Feature<NoFeatureConfig> WELL = new WellStructure(NoFeatureConfig::deserialize);
    public static final Feature<NoFeatureConfig> DEAD_VIRIDSHROOM_AND_CACHE = new ViridshroomCacheFeature(NoFeatureConfig::deserialize);

    @SubscribeEvent
    public static void registerFeatures(IForgeRegistry<Feature<?>> event) {
        RegUtil.generic(event)
                .add("shadowroot_tree", SHADOWROOT_TREE)
                .add("dark_willow_tree", DARK_WILLOW_TREE)
                .add("dead_tree", DEAD_TREE)
                .add("bog_dead_tree", BOG_DEAD_TREE)
                .add("dead_log", DEAD_LOG)
                .add("large_bogshroom", LARGE_BOGSHROOM)
                .add("large_glob_fungus", LARGE_GLOB_FUNGUS)
                .add("large_nightshroom", LARGE_NIGHTSHROOM)
                .add("large_dewshroom", LARGE_DEWSHROOM)
                .add("large_viridshroom", LARGE_VIRIDSHROOM)
                .add("medium_nightshroom", MEDIUM_NIGHTSHROOM)
                .add("medium_dewshroom", MEDIUM_DEWSHROOM)
                .add("medium_viridshroom", MEDIUM_VIRIDSHROOM)
                .add("small_bogshroom", SMALL_BOGSHROOM)
                .add("small_nightshroom", SMALL_NIGHTSHROOM)
                .add("small_dewshroom", SMALL_DEWSHROOM)
                .add("small_viridshroom", SMALL_VIRIDSHROOM)
                .add("vidshoroom_cache", DEAD_VIRIDSHROOM_AND_CACHE)
                .add("suavis", SUAVIS)
                .add("deceitful_algae", DECEITFUL_ALGAE)
                .add("deceitful_moss", DECEITFUL_MOSS)
                .add("bladeshroom", BLADESHROOM)
                .add("fingered_grass", FINGERED_GRASS)
                .add("double_plant", DOUBLE_PLANT)
                .add("nightshroom_flowers", NIGHTSHROOM_FLOWERS)
                .add("dewshroom_flowers", DEWSHROOM_FLOWERS)
                .add("viridshroom_flowers", VIRIDSHROOM_FLOWERS)
                .add("bogshroom_flowers", BOGSHROOM_FLOWERS)
                .add("mistshroom_flowers", MISTSHROOM_FLOWERS)
                .add("bogweed_flowers", BOGWEED_FLOWERS)
                .add("crystal_flowers", CRYSTAL_FLOWERS)
                .add("glob_fungi_flowers", GLOB_FUNGI_FLOWERS)
                .add("lumen_flowers", LUMEN_FLOWERS)
                .add("unstable_bush", UNSTABLE_BUSH)
                .add("heap", HEAP)
                .add("spike", SPIKE)
                .add("nightstone_boulder", NIGHTSTONE_BOULDER)
                .add("trenchstone_boulder", TRENCHSTONE_BOULDER)
                .add("crystal_cluster", CRYSTAL_CLUSTER)
                .add("crystal_spire", CRYSTAL_SPIRE)
                .add("ore", ORE)
                .add("stinger_egg", STINGER_EGG)
                .add("well", WELL);
    }
}
