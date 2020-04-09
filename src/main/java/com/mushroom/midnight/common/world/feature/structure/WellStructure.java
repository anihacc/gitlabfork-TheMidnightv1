package com.mushroom.midnight.common.world.feature.structure;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.ScatteredStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.function.Function;

public class WellStructure extends ScatteredStructure<NoFeatureConfig> {
    public static final String NAME = "midnight:well";

    public WellStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> deserializer) {
        super(deserializer);
    }

    @Override
    protected int getSeedModifier() {
        return 499831872;
    }

    @Override
    public IStartFactory getStartFactory() {
        return Start::new;
    }

    @Override
    public String getStructureName() {
        return NAME;
    }

    @Override
    public int getSize() {
        return 2;
    }

    public static class Start extends StructureStart {

        public Start(Structure<?> structure, int chunkX, int chunkZ, Biome biome, MutableBoundingBox bounds, int reference, long seed) {
            super(structure, chunkX, chunkZ, biome, bounds, reference, seed);
        }

        @Override
        public void init(ChunkGenerator<?> generator, TemplateManager templateManager, int cx, int cz, Biome biome) {
            int x = cx * 16;
            int z = cz * 16;
            BlockPos blockpos = new BlockPos(x, 90, z);
            Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
            WellPieces.addWellPieces(templateManager, blockpos, rotation, this.components, rand);
            this.recalculateStructureSize();
        }
    }
}
