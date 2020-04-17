package com.mushroom.midnight.client.render.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mushroom.midnight.client.render.block.MidnightChestBlockRenderer;
import com.mushroom.midnight.common.block.MidnightChestBlock;
import com.mushroom.midnight.common.tile.MidnightChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MidnightChestItemRenderer extends ItemStackTileEntityRenderer {
    private final MidnightChestTileEntity chest = new MidnightChestTileEntity();
    private MidnightChestBlockRenderer renderer;

    public MidnightChestItemRenderer() {
        renderer = new MidnightChestBlockRenderer(null); // Using null here prevents data generator from crashing and it still works
    }

    @Override
    public void render(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Item item = itemStackIn.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem) item).getBlock();
            if (block instanceof MidnightChestBlock) {
                this.chest.setChestModel(block);

                renderer.render(this.chest, 0.0F, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
                //TileEntityRendererDispatcher.instance.renderNullable(this.chest, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
            }
        }
    }
}
