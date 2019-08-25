package com.mushroom.midnight.client.model;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockEntityModel extends Model {
    private final RendererModel shape;

    public BlockEntityModel() {
        this.textureWidth = 16;
        this.textureHeight = 16;
        this.shape = new RendererModel(this, 0, 0);
        this.shape.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F);
    }

    public void render() {
        this.shape.render(0.0625F);
    }
}
