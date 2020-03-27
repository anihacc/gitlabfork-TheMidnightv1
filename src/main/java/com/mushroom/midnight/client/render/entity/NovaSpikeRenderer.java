package com.mushroom.midnight.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.NovaSpikeModel;
import com.mushroom.midnight.common.entity.projectile.NovaSpikeEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NovaSpikeRenderer extends EntityRenderer<NovaSpikeEntity> {
    private static final ResourceLocation NOVA_SPIKE_TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/nova_spike.png");
    private final NovaSpikeModel<NovaSpikeEntity> model = new NovaSpikeModel<>();

    public NovaSpikeRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public void render(NovaSpikeEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entity.prevRotationYaw, entity.rotationYaw) - 90.0F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entity.prevRotationPitch, entity.rotationPitch) - 90.0F));
        this.getEntityTexture(entity);


        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.model.getRenderType(NOVA_SPIKE_TEXTURE));
        int i = getPackedOverlay(entity, 0.0F);
        this.model.render(matrixStackIn, ivertexbuilder, packedLightIn, i, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStackIn.pop();

        super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public static int getPackedOverlay(NovaSpikeEntity livingEntityIn, float uIn) {
        return OverlayTexture.getPackedUV(OverlayTexture.getU(uIn), OverlayTexture.getV(false));
    }

    @Override
    public ResourceLocation getEntityTexture(NovaSpikeEntity entity) {
        return NOVA_SPIKE_TEXTURE;
    }
}
