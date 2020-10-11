package com.mushroom.midnight.common.world.layer;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;

public enum VoronoiZoomLayer implements IAreaTransformer1 {
    INSTANCE;

    @Override
    public int apply(IExtendedNoiseRandom<?> context, IArea area, int x, int z) {
        int i = x - 2;
        int j = z - 2;
        int k = i >> 2;
        int l = j >> 2;
        int i1 = k << 2;
        int j1 = l << 2;
        context.setPosition(i1, j1);
        double d0 = ((double) context.random(1024) / 1024.0D - 0.5D) * 3.6D;
        double d1 = ((double) context.random(1024) / 1024.0D - 0.5D) * 3.6D;
        context.setPosition(i1 + 4, j1);
        double d2 = ((double) context.random(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
        double d3 = ((double) context.random(1024) / 1024.0D - 0.5D) * 3.6D;
        context.setPosition(i1, j1 + 4);
        double d4 = ((double) context.random(1024) / 1024.0D - 0.5D) * 3.6D;
        double d5 = ((double) context.random(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
        context.setPosition(i1 + 4, j1 + 4);
        double d6 = ((double) context.random(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
        double d7 = ((double) context.random(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
        int k1 = i & 3;
        int l1 = j & 3;
        double d8 = ((double) l1 - d1) * ((double) l1 - d1) + ((double) k1 - d0) * ((double) k1 - d0);
        double d9 = ((double) l1 - d3) * ((double) l1 - d3) + ((double) k1 - d2) * ((double) k1 - d2);
        double d10 = ((double) l1 - d5) * ((double) l1 - d5) + ((double) k1 - d4) * ((double) k1 - d4);
        double d11 = ((double) l1 - d7) * ((double) l1 - d7) + ((double) k1 - d6) * ((double) k1 - d6);
        if (d8 < d9 && d8 < d10 && d8 < d11) {
            return area.getValue(this.getOffsetX(i1), this.getOffsetZ(j1));
        } else if (d9 < d8 && d9 < d10 && d9 < d11) {
            return area.getValue(this.getOffsetX(i1 + 4), this.getOffsetZ(j1)) & 255;
        } else {
            return d10 < d8 && d10 < d9 && d10 < d11 ? area.getValue(this.getOffsetX(i1), this.getOffsetZ(j1 + 4)) : area.getValue(this.getOffsetX(i1 + 4), this.getOffsetZ(j1 + 4)) & 255;
        }
    }

    @Override
    public int getOffsetX(int x) {
        return x >> 2;
    }

    @Override
    public int getOffsetZ(int z) {
        return z >> 2;
    }
}