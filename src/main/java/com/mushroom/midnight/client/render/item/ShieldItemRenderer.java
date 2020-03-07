package com.mushroom.midnight.client.render.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.ShieldModel;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShieldItemRenderer extends ItemStackTileEntityRenderer {
    private final static ResourceLocation TEXTURE = new ResourceLocation("textures/block/purple_terracotta.png");
    private final ShieldModel shieldModel = new ShieldModel(); // TODO model rockshroom
    public static final Material LOCATION_SHIELD_BASE = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("block/purple_terracotta"));

    @Override
    public void render(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStackIn.push();
        matrixStackIn.scale(1.0F, -1.0F, -1.0F);
        Material material = LOCATION_SHIELD_BASE;
        IVertexBuilder ivertexbuilder = material.getSprite().wrapBuffer(ItemRenderer.getBuffer(bufferIn, this.shieldModel.getRenderType(material.getAtlasLocation()), false, itemStackIn.hasEffect()));
        this.shieldModel.func_228294_b_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);

        this.shieldModel.func_228293_a_().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);


        matrixStackIn.pop();
    }
}
