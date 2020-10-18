package com.mushroom.midnight.client;

import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.client.Minecraft;

public class GammaModifier {
    public static double modifyGamma(double gamma) {
        if (MidnightUtil.isMidnightDimension(Minecraft.getInstance().world)) {
            return 0;
        }
        return gamma;
    }
}
