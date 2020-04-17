package com.mushroom.midnight.common.config.ifc;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum EditAccess {
    ALWAYS {
        @Override
        @OnlyIn(Dist.CLIENT)
        public boolean canEdit(Minecraft minecraft) {
            return true;
        }
    },
    SERVER_HOST {
        @Override
        @OnlyIn(Dist.CLIENT)
        public boolean canEdit(Minecraft minecraft) {
            return minecraft.getConnection() != null;
        }
    };

    @OnlyIn(Dist.CLIENT)
    public abstract boolean canEdit(Minecraft minecraft);
}
