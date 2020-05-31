package com.mushroom.midnight.common.effect;

import com.mushroom.midnight.common.util.MidnightDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class TormentedEffect extends Effect {
    private static final DamageSource TORMENTED_DAMAGE = new MidnightDamageSource("tormented").setMagicDamage();
    private double posX, posZ;

    public TormentedEffect() {
        super(EffectType.HARMFUL, 0x0);
    }

    @Override
    public void performEffect(LivingEntity entity, int amplifier) {
        if (!entity.world.isRemote) {
            if (entity.getPosX() != this.posX || entity.getPosZ() != this.posZ || entity.isSneaking() || entity.getMotion().y > 0) {
                this.posX = entity.getPosX();
                this.posZ = entity.getPosZ();
                entity.attackEntityFrom(TORMENTED_DAMAGE, 0.5f);
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}
