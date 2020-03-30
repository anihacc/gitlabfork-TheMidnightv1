package com.mushroom.midnight.client.render.block;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockRenderLayer {

    public static void renderBlock() {
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DARK_WATER, RenderType.getTranslucent());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.ARCHAIC_GLASS, RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.ARCHAIC_GLASS_PANE, RenderType.getTranslucent());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.GRASS, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.GRASS_BLOCK, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.TALL_GRASS, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.ROUXE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.BLOOMCRYSTAL, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.GLOB_FUNGUS, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DECEITFUL_MOSS, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.NIGHTSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DOUBLE_NIGHTSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.NIGHTSHROOM_SHELF, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.NIGHTSHROOM_ROOTS, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.NIGHTSHROOM_FLOWERING_ROOTS, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.VIRIDSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DOUBLE_VIRIDSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.VIRIDSHROOM_SHELF, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.VIRIDSHROOM_ROOTS, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.VIRIDSHROOM_FLOWERING_ROOTS, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.BOGSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DOUBLE_BOGSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.BOGSHROOM_SHELF, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEWSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DOUBLE_DEWSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEWSHROOM_SHELF, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEWSHROOM_ROOTS, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEWSHROOM_FLOWERING_ROOTS, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.MISTSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DOUBLE_MISTSHROOM, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.LUMEN_BUD, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DOUBLE_LUMEN_BUD, RenderType.getCutout());


        RenderTypeLookup.setRenderLayer(MidnightBlocks.BOGWEED, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.GHOST_PLANT, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.FINGERED_GRASS, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.TENDRILWEED, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.RUNEBUSH, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DRAGON_NEST, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.VIOLEAF, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.CRYSTAL_FLOWER, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.CRYSTALOTUS, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.CRYSTALOTUS, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.DARK_WILLOW_SAPLING, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.SHADOWROOT_SAPLING, RenderType.getCutoutMipped());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.DARK_WILLOW_LEAVES, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.HANGING_DARK_WILLOW_LEAVES, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.SHADOWROOT_LEAVES, RenderType.getCutoutMipped());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.BOGSHROOM_DOOR, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DARK_WILLOW_DOOR, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEAD_WOOD_DOOR, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEWSHROOM_DOOR, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.NIGHTSHROOM_DOOR, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.SHADOWROOT_DOOR, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.VIRIDSHROOM_DOOR, RenderType.getCutoutMipped());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.BOGSHROOM_LADDER, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DARK_WILLOW_LADDER, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEAD_WOOD_LADDER, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEWSHROOM_LADDER, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.NIGHTSHROOM_LADDER, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.SHADOWROOT_LADDER, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.VIRIDSHROOM_LADDER, RenderType.getCutoutMipped());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.BOGSHROOM_TRAPDOOR, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DARK_WILLOW_TRAPDOOR, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEAD_WOOD_TRAPDOOR, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEWSHROOM_TRAPDOOR, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.NIGHTSHROOM_TRAPDOOR, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.SHADOWROOT_TRAPDOOR, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.VIRIDSHROOM_TRAPDOOR, RenderType.getCutoutMipped());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_BLUE_PLANT_BLOCK, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_GREEN_PLANT_BLOCK, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_PURPLE_PLANT_BLOCK, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_RED_PLANT_BLOCK, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.GLOWING_MALIGNANT_BLUE_PLANT_BLOCK, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.GLOWING_MALIGNANT_GREEN_PLANT_BLOCK, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.GLOWING_MALIGNANT_PURPLE_PLANT_BLOCK, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.GLOWING_MALIGNANT_RED_PLANT_BLOCK, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_RED_PLANT_SURFACE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_BLUE_PLANT_SURFACE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_GREEN_PLANT_SURFACE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_PURPLE_PLANT_SURFACE, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_FOXGLOVE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_HEMLOCK, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_HYACINTH, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_IVY, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_LARKSPUR, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_LILY, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_MANDRAKE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_MOONSEED, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_NETTLE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_RAGWEED, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_SNAKEROOT, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_SPINDLE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_TAILFLOWER, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_THISTLE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_WALLFLOWER, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_WOLFSBANE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_BANEBERRY, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_BLOODROOT, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_NIGHTSHADE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_WISTERIA, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DECEITFUL_ALGAE, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.BLADESHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.UNSTABLE_BUSH, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.UNSTABLE_BUSH_BLUE_BLOOMED, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.UNSTABLE_BUSH_GREEN_BLOOMED, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.UNSTABLE_BUSH_LIME_BLOOMED, RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.BOGSHROOM_SPORCH, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.BOGSHROOM_WALL_SPORCH, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEWSHROOM_SPORCH, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEWSHROOM_WALL_SPORCH, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.NIGHTSHROOM_SPORCH, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.NIGHTSHROOM_WALL_SPORCH, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.VIRIDSHROOM_SPORCH, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.VIRIDSHROOM_WALL_SPORCH, RenderType.getCutoutMipped());
    }
}
