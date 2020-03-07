package com.mushroom.midnight.client.render.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mushroom.midnight.common.tile.MidnightChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MidnightChestItemRenderer extends ItemStackTileEntityRenderer {
    private final MidnightChestTileEntity chest = new MidnightChestTileEntity();

    public MidnightChestItemRenderer(Block block) {
        chest.setChestModel(block);
    }

    @Override
    public void render(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        super.render(itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        TileEntityRendererDispatcher.instance.renderNullable(this.chest, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}
