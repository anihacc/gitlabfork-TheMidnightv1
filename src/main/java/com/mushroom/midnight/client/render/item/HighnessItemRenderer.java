package com.mushroom.midnight.client.render.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mushroom.midnight.client.render.entity.NightStagRenderer;
import com.mushroom.midnight.common.entity.creature.NightStagEntity;
import com.mushroom.midnight.common.registry.MidnightEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HighnessItemRenderer extends ItemStackTileEntityRenderer {
    private NightStagEntity entity;

    @Override
    public void render(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (entity == null && Minecraft.getInstance().world != null) {
            entity = MidnightEntities.NIGHTSTAG.create(Minecraft.getInstance().world);
        }
        if (entity != null) {
            try {
                matrixStackIn.push();
                NightStagRenderer render = new NightStagRenderer(Minecraft.getInstance().getRenderManager());
                render.render(entity, 0F, 0F, matrixStackIn, bufferIn, combinedLightIn);
                matrixStackIn.pop();

                RenderHelper.disableStandardItemLighting();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.render(itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}
