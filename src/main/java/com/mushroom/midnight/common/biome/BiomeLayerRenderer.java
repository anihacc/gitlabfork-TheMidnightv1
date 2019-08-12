package com.mushroom.midnight.common.biome;

import com.google.common.collect.ImmutableMap;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightSurfaceBiomes;
import net.minecraft.world.biome.Biome;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class BiomeLayerRenderer {
    private static final ImmutableMap<Biome, Pattern> BIOME_PATTERNS = ImmutableMap.<Biome, Pattern>builder()
            .put(MidnightSurfaceBiomes.BLACK_RIDGE, Pattern.flat(new Color(0x303030)))
            .put(MidnightSurfaceBiomes.VIGILANT_FOREST, Pattern.flat(new Color(0x695F8C)))
            .put(MidnightSurfaceBiomes.DECEITFUL_BOG, Pattern.flat(new Color(0x5C4947)))
            .put(MidnightSurfaceBiomes.FUNGI_FOREST, Pattern.flat(new Color(0x3D9574)))
            .put(MidnightSurfaceBiomes.OBSCURED_PEAKS, Pattern.checker(new Color(0x303030)))
            .put(MidnightSurfaceBiomes.WARPED_FIELDS, Pattern.flat(new Color(0x565639)))
            .put(MidnightSurfaceBiomes.CRYSTAL_SPIRES, Pattern.flat(new Color(0xFF9BFE)))
            .put(MidnightSurfaceBiomes.NIGHT_PLAINS, Pattern.flat(new Color(0x9FC0B6)))
            .put(MidnightSurfaceBiomes.OBSCURED_PLATEAU, Pattern.flat(Color.BLACK))
            .put(MidnightSurfaceBiomes.PHANTASMAL_VALLEY, Pattern.flat(new Color(0x56D3CF)))
            .put(MidnightSurfaceBiomes.RUNEBUSH_GROVE, Pattern.checker(new Color(0x2E586E)))
            .put(MidnightSurfaceBiomes.HILLY_VIGILANT_FOREST, Pattern.checker(new Color(0x695F8C)))
            .put(MidnightSurfaceBiomes.HILLY_FUNGI_FOREST, Pattern.checker(new Color(0x3D9574)))
            .build();

    public static void renderDebug() {
        try {
            Midnight.LOGGER.info("Rendering debug biome map");

            BiomeLayers<Biome> layers = BiomeLayerType.SURFACE.make(0);

            BufferedImage image = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
            renderLayer(layers.noise, image);

            ImageIO.write(image, "png", new File("biome_debug.png"));

            System.exit(1);
        } catch (IOException e) {
            Midnight.LOGGER.error("Failed to output debug biome render", e);
        }
    }

    public static void renderLayer(BiomeLayer<Biome> layer, BufferedImage image) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Biome biome = layer.sample(x, y);
                Pattern pattern = BIOME_PATTERNS.get(biome);
                int color = pattern != null ? pattern.get(x, y) : 0xFFFFFF;
                image.setRGB(x, y, color);
            }
        }
    }

    public interface Pattern {
        int get(int x, int y);

        static Pattern flat(Color color) {
            int rgb = color.getRGB();
            return (x, y) -> rgb;
        }

        static Pattern checker(Color color) {
            int primary = color.getRGB();
            int secondary = color.darker().getRGB();
            return (x, y) -> (x / 2 + y / 2) % 2 == 0 ? primary : secondary;
        }
    }
}
