package com.mushroom.midnight.client.render;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightSurfaceBiomes;
import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public final class GrassColorModifier {
    private static final Minecraft CLIENT = Minecraft.getInstance();

    private static final int RANGE = 2;

    private static final int FINGERED_GRASS_COLOR = 0x004B7A;

    private static final int FINGERED_GRASS_RED = FINGERED_GRASS_COLOR >> 16 & 0xFF;
    private static final int FINGERED_GRASS_GREEN = FINGERED_GRASS_COLOR >> 8 & 0xFF;
    private static final int FINGERED_GRASS_BLUE = FINGERED_GRASS_COLOR & 0xFF;

    public static int modifyGrassColor(int color, BlockPos pos) {
        World world = CLIENT.world;

        if (!MidnightUtil.isMidnightDimension(world)) return color;

        Biome biome = world.getBiome(pos);
        if (biome == MidnightSurfaceBiomes.NIGHT_PLAINS) {
            return modifyGrassNightPlains(world, pos, color);
        }

        return color;
    }

    private static int modifyGrassNightPlains(World world, BlockPos pos, int color) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        int minDistanceSq = Integer.MAX_VALUE;

        for (BlockPos searchPos : BlockPos.getAllInBoxMutable(
                x - RANGE, y - RANGE, z - RANGE,
                x + RANGE, y + RANGE, z + RANGE
        )) {
            if (searchPos.equals(pos)) continue;
            if (world.getBlockState(searchPos).getBlock() == MidnightBlocks.FINGERED_GRASS) {
                int deltaX = searchPos.getX() - x;
                int deltaY = searchPos.getY() - y;
                int deltaZ = searchPos.getZ() - z;
                int distanceSq = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
                if (distanceSq < minDistanceSq) {
                    minDistanceSq = distanceSq;
                }
            }
        }

        if (minDistanceSq == Integer.MAX_VALUE) return color;

        double minDistance = Math.sqrt(minDistanceSq) / RANGE;

        if (minDistance >= 1.0) return color;
        if (minDistance <= 0.01) return FINGERED_GRASS_COLOR;

        minDistance = MathHelper.clamp(minDistance, 0.0, 1.0);

        int red = color >> 16 & 0xFF;
        int green = color >> 8 & 0xFF;
        int blue = color & 0xFF;

        int mixedRed = MathHelper.floor(red * minDistance + FINGERED_GRASS_RED * (1.0 - minDistance)) & 0xFF;
        int mixedGreen = MathHelper.floor(green * minDistance + FINGERED_GRASS_GREEN * (1.0 - minDistance)) & 0xFF;
        int mixedBlue = MathHelper.floor(blue * minDistance + FINGERED_GRASS_BLUE * (1.0 - minDistance)) & 0xFF;

        return mixedRed << 16 | mixedGreen << 8 | mixedBlue;
    }
}
