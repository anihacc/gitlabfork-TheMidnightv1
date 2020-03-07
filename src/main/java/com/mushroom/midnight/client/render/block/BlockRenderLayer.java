package com.mushroom.midnight.client.render.block;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockRenderLayer {

    public static void renderBlock() {
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DARK_WATER, RenderType.translucent());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.ARCHAIC_GLASS, RenderType.translucent());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.ARCHAIC_GLASS_PANE, RenderType.translucent());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.GRASS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.TALL_GRASS, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.ROUXE, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.BLOOMCRYSTAL, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.GLOB_FUNGUS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DECEITFUL_MOSS, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.NIGHTSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DOUBLE_NIGHTSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.NIGHTSHROOM_SHELF, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.NIGHTSHROOM_ROOTS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.NIGHTSHROOM_FLOWERING_ROOTS, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.VIRIDSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DOUBLE_VIRIDSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.VIRIDSHROOM_SHELF, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.VIRIDSHROOM_ROOTS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.VIRIDSHROOM_FLOWERING_ROOTS, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.BOGSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DOUBLE_BOGSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.BOGSHROOM_SHELF, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEWSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DOUBLE_DEWSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEWSHROOM_SHELF, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEWSHROOM_ROOTS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEWSHROOM_FLOWERING_ROOTS, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.MISTSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DOUBLE_MISTSHROOM, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.LUMEN_BUD, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DOUBLE_LUMEN_BUD, RenderType.cutout());


        RenderTypeLookup.setRenderLayer(MidnightBlocks.BOGWEED, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.GHOST_PLANT, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.FINGERED_GRASS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.TENDRILWEED, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.RUNEBUSH, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DRAGON_NEST, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.VIOLEAF, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.CRYSTAL_FLOWER, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.CRYSTALOTUS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.CRYSTALOTUS, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.DARK_WILLOW_SAPLING, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.SHADOWROOT_SAPLING, RenderType.cutoutMipped());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.DARK_WILLOW_LEAVES, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.HANGING_DARK_WILLOW_LEAVES, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.SHADOWROOT_LEAVES, RenderType.cutoutMipped());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.BOGSHROOM_DOOR, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DARK_WILLOW_DOOR, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEAD_WOOD_DOOR, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEWSHROOM_DOOR, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.NIGHTSHROOM_DOOR, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.SHADOWROOT_DOOR, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.VIRIDSHROOM_DOOR, RenderType.cutoutMipped());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.BOGSHROOM_LADDER, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DARK_WILLOW_LADDER, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEAD_WOOD_LADDER, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEWSHROOM_LADDER, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.NIGHTSHROOM_LADDER, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.SHADOWROOT_LADDER, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.VIRIDSHROOM_LADDER, RenderType.cutoutMipped());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.BOGSHROOM_TRAPDOOR, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DARK_WILLOW_TRAPDOOR, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEAD_WOOD_TRAPDOOR, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEWSHROOM_TRAPDOOR, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.NIGHTSHROOM_TRAPDOOR, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.SHADOWROOT_TRAPDOOR, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.VIRIDSHROOM_TRAPDOOR, RenderType.cutoutMipped());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_BLUE_PLANT_BLOCK, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_GREEN_PLANT_BLOCK, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_PURPLE_PLANT_BLOCK, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_RED_PLANT_BLOCK, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.GLOWING_MALIGNANT_BLUE_PLANT_BLOCK, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.GLOWING_MALIGNANT_GREEN_PLANT_BLOCK, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.GLOWING_MALIGNANT_PURPLE_PLANT_BLOCK, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.GLOWING_MALIGNANT_RED_PLANT_BLOCK, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_HEMLOCK, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_HYACINTH, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_IVY, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_LARKSPUR, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_LILY, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_MANDRAKE, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_MOONSEED, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_NETTLE, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_RAGWEED, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_SNAKEROOT, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_SPINDLE, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_TAILFLOWER, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_THISTLE, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_WALLFLOWER, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_WOLFSBANE, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_BANEBERRY, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_BLOODROOT, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_NIGHTSHADE, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.MALIGNANT_WISTERIA, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DECEITFUL_ALGAE, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.BLADESHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.UNSTABLE_BUSH, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.UNSTABLE_BUSH_BLUE_BLOOMED, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.UNSTABLE_BUSH_GREEN_BLOOMED, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.UNSTABLE_BUSH_LIME_BLOOMED, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(MidnightBlocks.BOGSHROOM_SPORCH, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.BOGSHROOM_WALL_SPORCH, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEWSHROOM_SPORCH, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.DEWSHROOM_WALL_SPORCH, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.NIGHTSHROOM_SPORCH, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.NIGHTSHROOM_WALL_SPORCH, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.VIRIDSHROOM_SPORCH, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(MidnightBlocks.VIRIDSHROOM_WALL_SPORCH, RenderType.cutoutMipped());
    }
}
