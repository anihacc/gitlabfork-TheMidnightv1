package com.mushroom.midnight.common.mixin;

import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public class MobEntityMixin {
    @Inject(at = @At("HEAD"), method = "updateEquipmentIfNeeded(Lnet/minecraft/entity/item/ItemEntity;)V", cancellable = true)
    private void updateEquipmentIfNeeded(ItemEntity itemEntity, CallbackInfo callback) {
        if (itemEntity.getItem().getItem() == MidnightItems.BOGSHROOM_SPORE_BOMB
                || itemEntity.getItem().getItem() == MidnightItems.DEWSHROOM_SPORE_BOMB
                || itemEntity.getItem().getItem() == MidnightItems.NIGHTSHROOM_SPORE_BOMB
                || itemEntity.getItem().getItem() == MidnightItems.VIRIDSHROOM_SPORE_BOMB) {
            callback.cancel();
        }
    }
}
