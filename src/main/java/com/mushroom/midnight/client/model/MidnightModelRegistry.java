package com.mushroom.midnight.client.model;

import com.mushroom.midnight.client.render.block.MidnightChestBlockRenderer;
import com.mushroom.midnight.client.render.entity.BladeshroomCapRenderer;
import com.mushroom.midnight.client.render.entity.BulbAnglerRenderer;
import com.mushroom.midnight.client.render.entity.CloudRenderer;
import com.mushroom.midnight.client.render.entity.CrystalBugRenderer;
import com.mushroom.midnight.client.render.entity.DeceitfulSnapperRenderer;
import com.mushroom.midnight.client.render.entity.HunterRenderer;
import com.mushroom.midnight.client.render.entity.NightStagRenderer;
import com.mushroom.midnight.client.render.entity.NovaRenderer;
import com.mushroom.midnight.client.render.entity.NovaSpikeRenderer;
import com.mushroom.midnight.client.render.entity.PenumbrianRenderer;
import com.mushroom.midnight.client.render.entity.RifterRenderer;
import com.mushroom.midnight.client.render.entity.ShadeSquirrelRenderer;
import com.mushroom.midnight.client.render.entity.SkulkRenderer;
import com.mushroom.midnight.client.render.entity.StingerRenderer;
import com.mushroom.midnight.client.render.entity.TreeHopperRenderer;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightEntities;
import com.mushroom.midnight.common.registry.MidnightTileEntities;
import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class MidnightModelRegistry {
    private static final Minecraft MC = Minecraft.getInstance();

    private static final int DEFAULT_GRASS_COLOR = 0xBF8ECC;
    private static final int DEFAULT_FOLIAGE_COLOR = 0x8F6DBC;

    public static void registerModels(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(MidnightEntities.RIFTER, RifterRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MidnightEntities.HUNTER, HunterRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MidnightEntities.BLADESHROOM_CAP, BladeshroomCapRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MidnightEntities.NOVA, NovaRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MidnightEntities.CRYSTAL_BUG, CrystalBugRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MidnightEntities.PENUMBRIAN, PenumbrianRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MidnightEntities.TREE_HOPPER, TreeHopperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MidnightEntities.STINGER, StingerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MidnightEntities.NIGHTSTAG, NightStagRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MidnightEntities.DECEITFUL_SNAPPER, DeceitfulSnapperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MidnightEntities.BULB_ANGLER, BulbAnglerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MidnightEntities.SKULK, SkulkRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MidnightEntities.SHADE_SQUIRREL, ShadeSquirrelRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MidnightEntities.THROWN_GEODE, manager -> new SpriteRenderer(MC.getRenderManager(), MC.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(MidnightEntities.SPORE_BOMB, manager -> new SpriteRenderer(MC.getRenderManager(), MC.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(MidnightEntities.CLOUD, CloudRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MidnightEntities.NOVA_SPIKE, NovaSpikeRenderer::new);

        ClientRegistry.bindTileEntityRenderer(MidnightTileEntities.MIDNIGHT_CHEST, MidnightChestBlockRenderer::new);
        //ClientRegistry.bindTileEntityRenderer(CacheTileEntity.class, new CacheBlockRenderer());
        //ClientRegistry.bindTileEntityRenderer(RiftPortalTileEntity.class, new RiftPortalBlockRenderer());

        BlockColors blockColors = MC.getBlockColors();
        ItemColors itemColors = MC.getItemColors();

        blockColors.register(MidnightModelRegistry::computeGrassColor, MidnightBlocks.GRASS_BLOCK);
        itemColors.register(MidnightModelRegistry::defaultGrassColor, MidnightBlocks.GRASS_BLOCK);

        blockColors.register(MidnightModelRegistry::computeFoliageColor, MidnightBlocks.SHADOWROOT_LEAVES);
        itemColors.register(MidnightModelRegistry::defaultFoliageColor, MidnightBlocks.SHADOWROOT_LEAVES);

        blockColors.register(MidnightModelRegistry::computeGrassColor, MidnightBlocks.GRASS, MidnightBlocks.TALL_GRASS);
        itemColors.register(MidnightModelRegistry::defaultGrassColor, MidnightBlocks.GRASS, MidnightBlocks.TALL_GRASS);
    }

    private static int computeGrassColor(BlockState state, ILightReader world, BlockPos pos, int tintIndex) {
        if (world == null || pos == null || !isMidnight()) {
            return DEFAULT_FOLIAGE_COLOR;
        }
        return BiomeColors.func_228358_a_(world, pos);
    }

    private static int defaultGrassColor(ItemStack stack, int tintIndex) {
        return DEFAULT_GRASS_COLOR;
    }

    private static int computeFoliageColor(BlockState state, ILightReader world, BlockPos pos, int tintIndex) {
        if (world == null || pos == null || !isMidnight()) {
            return DEFAULT_FOLIAGE_COLOR;
        }
        return BiomeColors.func_228361_b_(world, pos);
    }

    private static int defaultFoliageColor(ItemStack stack, int tintIndex) {
        return DEFAULT_FOLIAGE_COLOR;
    }

    private static boolean isMidnight() {
        return MidnightUtil.isMidnightDimension(MC.world);
    }
}
