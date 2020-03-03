package com.mushroom.midnight.client.render.entity;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.SkulkModel;
import com.mushroom.midnight.common.entity.creature.SkulkEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class SkulkRenderer extends MobRenderer<SkulkEntity, SkulkModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/skulk.png");

    public SkulkRenderer(EntityRendererManager manager) {
        super(manager, new SkulkModel(), 0.4f);
    }


    protected RenderType func_230042_a_(SkulkEntity p_230042_1_, boolean p_230042_2_, boolean p_230042_3_) {
        ResourceLocation resourcelocation = this.getEntityTexture(p_230042_1_);
        if (p_230042_3_ || p_230042_1_.isStealth()) {
            return RenderType.entityTranslucent(resourcelocation);
        } else if (p_230042_2_) {
            return this.entityModel.getRenderType(resourcelocation);
        } else {
            return p_230042_1_.isGlowing() ? RenderType.outline(resourcelocation) : null;
        }
    }

    protected boolean isVisible(SkulkEntity livingEntityIn) {
        return livingEntityIn.isStealth() || !livingEntityIn.isInvisible();
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(SkulkEntity entity) {
        return TEXTURE;
    }
}
