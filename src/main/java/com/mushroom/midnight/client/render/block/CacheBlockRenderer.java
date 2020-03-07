package com.mushroom.midnight.client.render.block;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mushroom.midnight.common.tile.CacheTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class CacheBlockRenderer extends TileEntityRenderer<CacheTileEntity> {

    public CacheBlockRenderer(TileEntityRendererDispatcher p_i226008_1_) {
        super(p_i226008_1_);
        // could mimic block directly
    }

    @Override
    public void render(CacheTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

    }
}
