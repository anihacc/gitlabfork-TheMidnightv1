package com.mushroom.midnight.common.world.feature.structure;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightLootTables;
import com.mushroom.midnight.common.registry.MidnightStructurePieces;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Random;

public class ShadowrootGuardTowerPieces {
    private static final ResourceLocation LOCATION = new ResourceLocation(Midnight.MODID, "shadowroot_guardtower");

    public static void addTowerPieces(TemplateManager templateManager, BlockPos origin, Rotation rotation, List<StructurePiece> pieces) {
        pieces.add(new ShadowrootGuardTowerPieces.Piece(templateManager, LOCATION, origin, rotation));
    }

    public static class Piece extends TemplateStructurePiece {
        private final ResourceLocation templateLocation;
        private final Rotation rotation;

        public Piece(TemplateManager templateManager, ResourceLocation templateLocation, BlockPos position, Rotation rotation) {
            super(MidnightStructurePieces.SHADOWROOT_GUARDTOWER, 0);
            this.templateLocation = templateLocation;
            this.templatePosition = position;
            this.rotation = rotation;
            this.setup(templateManager);
        }

        public Piece(TemplateManager templateManager, CompoundNBT compound) {
            super(MidnightStructurePieces.SHADOWROOT_GUARDTOWER, compound);
            this.templateLocation = new ResourceLocation(compound.getString("Template"));
            this.rotation = Rotation.valueOf(compound.getString("Rot"));
            this.setup(templateManager);
        }

        private void setup(TemplateManager templateManager) {
            Template template = templateManager.getTemplateDefaulted(this.templateLocation);
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            this.setup(template, this.templatePosition, placementsettings);
        }

        @Override
        protected void readAdditional(CompoundNBT compound) {
            super.readAdditional(compound);
            compound.putString("Template", this.templateLocation.toString());
            compound.putString("Rot", this.rotation.name());
        }

        @Override
        protected void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand, MutableBoundingBox sbb) {
            if ("chest".equals(function)) {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                TileEntity tileentity = worldIn.getTileEntity(pos.down());
                if (tileentity instanceof ChestTileEntity) {
                    ((ChestTileEntity) tileentity).setLootTable(MidnightLootTables.LOOT_TABLE_SHADOWROOT_GUARDTOWER, rand.nextLong());
                }
            }
        }

        @Override
        public boolean addComponentParts(IWorld world, Random random, MutableBoundingBox bounds, ChunkPos chunkPos) {
            PlacementSettings settings = new PlacementSettings()
                    .setRotation(this.rotation)
                    .setMirror(Mirror.NONE)
                    .addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);

            BlockPos origin = this.templatePosition.add(Template.transformedBlockPos(settings, new BlockPos(5, 0, 3)));
            int height = world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, origin.getX(), origin.getZ());

            BlockPos templateOrigin = this.templatePosition;

            this.templatePosition = this.templatePosition.add(0, height - 90 - 1, 0);
            boolean result = super.addComponentParts(world, random, bounds, chunkPos);
            this.templatePosition = templateOrigin;

            return result;
        }
    }
}
