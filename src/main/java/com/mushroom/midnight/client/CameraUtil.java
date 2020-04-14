package com.mushroom.midnight.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Vector3f;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.EntityViewRenderEvent;

public class CameraUtil {
    public static void setupInverseCameraRotation(Matrix4f matrix, float partialTicks) {
        GameRenderer renderer = Minecraft.getInstance().gameRenderer;
        ActiveRenderInfo info = renderer.getActiveRenderInfo();

        EntityViewRenderEvent.CameraSetup cameraSetup = ForgeHooksClient.onCameraSetup(renderer, info, partialTicks);
        info.setAnglesInternal(cameraSetup.getYaw(), cameraSetup.getPitch());
        matrix.mul(Vector3f.ZP.rotationDegrees(cameraSetup.getRoll()));

        matrix.mul(Vector3f.XP.rotationDegrees(info.getPitch()));
        matrix.mul(Vector3f.YP.rotationDegrees(info.getYaw() + 180));
    }
}
