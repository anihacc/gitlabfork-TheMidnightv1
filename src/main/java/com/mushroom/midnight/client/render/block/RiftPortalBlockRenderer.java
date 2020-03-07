package com.mushroom.midnight.client.render.block;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.RiftPortalBlockModel;
import com.mushroom.midnight.common.tile.RiftPortalTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class RiftPortalBlockRenderer extends TileEntityRenderer<RiftPortalTileEntity> {
    private static final Minecraft CLIENT = Minecraft.getInstance();
    private static final ResourceLocation[] MASKS = new ResourceLocation[] {
            new ResourceLocation(Midnight.MODID, "textures/effects/rift_portal_mask_1.png"),
            new ResourceLocation(Midnight.MODID, "textures/effects/rift_portal_mask_2.png")
    };

    private static final RiftPortalBlockModel BLOCK_MODEL = new RiftPortalBlockModel();

    public RiftPortalBlockRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(RiftPortalTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (tileEntityIn == null) return;

        float closeAnimation = tileEntityIn.getCloseAnimation(partialTicks);
        if (closeAnimation >= 1.0F) return;

        BlockPos pos = tileEntityIn.getPos();
        long seed = MathHelper.getCoordinateRandom(pos.getX(), pos.getY(), pos.getZ());
        long textureSeed = seed ^ 8211203336981069197L;
        long rotationSeed = seed ^ 526247544445692899L;

        CLIENT.getTextureManager().bindTexture(MASKS[(int) (textureSeed & 1)]);

        matrixStackIn.push();
        matrixStackIn.rotate(Vector3f.YP.rotation((rotationSeed & 3) * 90.0F));

        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.polygonOffset(-1.0F, -10.0F);
        RenderSystem.enablePolygonOffset();

        RenderSystem.alphaFunc(GL11.GL_GREATER, closeAnimation);

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        BLOCK_MODEL.render(matrixStackIn, bufferIn.getBuffer(RenderType.entityTranslucent(MASKS[(int) (textureSeed & 1)])), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.alphaFunc(GL11.GL_GREATER, 0.1F);

        RenderSystem.polygonOffset(0.0F, 0.0F);
        RenderSystem.disablePolygonOffset();

        matrixStackIn.pop();
    }
}
