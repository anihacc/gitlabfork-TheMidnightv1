package com.mushroom.midnight.common.world.feature.structure;

import com.mushroom.midnight.common.registry.MidnightStructurePieces;
import com.mushroom.midnight.common.world.rift.EntranceRiftGenerator;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Random;

public class EntranceRiftPieces {
    private static final int MAX_RADIUS = 12;
    private static EntranceRiftGenerator riftGenerator;

    public static void addPieces(BlockPos blockPos, List<StructurePiece> pieces) {

        pieces.add(new Piece(blockPos));
    }

    public static class Piece extends StructurePiece {
        protected final int width;
        protected final int height;
        protected final int depth;

        public Piece(BlockPos pos) {
            super(MidnightStructurePieces.ENTRANCE_STRUCTURE, 0);
            this.width = pos.getX();
            this.height = pos.getY();
            this.depth = pos.getZ();
            this.boundingBox = new MutableBoundingBox(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 20, pos.getY() + 20, pos.getZ() + 20);
        }

        public Piece(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(MidnightStructurePieces.ENTRANCE_STRUCTURE, 0);
            this.width = compoundNBT.getInt("Width");
            this.height = compoundNBT.getInt("Height");
            this.depth = compoundNBT.getInt("Depth");
            this.boundingBox = new MutableBoundingBox(this.width, this.height, this.depth, this.width + 20, this.height + 20, this.depth + 20);
        }

        @Override
        protected void readAdditional(CompoundNBT compound) {
            compound.putInt("Width", this.width);
            compound.putInt("Height", this.height);
            compound.putInt("Depth", this.depth);
        }


        @Override
        public boolean func_225577_a_(IWorld p_225577_1_, ChunkGenerator<?> p_225577_2_, Random p_225577_3_, MutableBoundingBox boundingBox, ChunkPos p_225577_5_) {
            riftGenerator = new EntranceRiftGenerator(p_225577_1_);

            riftGenerator.generate(new BlockPos(this.boundingBox.minX + 10, this.boundingBox.minY, this.boundingBox.minZ + 10), p_225577_3_);

            return true;
        }
    }
}
