package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.RiftTraveller;
import com.mushroom.midnight.common.entity.creature.RifterEntity;
import com.mushroom.midnight.common.registry.MidnightEntities;
import com.mushroom.midnight.common.registry.MidnightParticleTypes;
import com.mushroom.midnight.common.registry.MidnightSounds;
import com.mushroom.midnight.common.registry.MidnightTileEntities;
import com.mushroom.midnight.common.tile.RiftPortalTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class RiftPortalBlock extends Block {
    private static final VoxelShape SHAPE = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);

    public RiftPortalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        super.animateTick(stateIn, worldIn, pos, rand);


        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof RiftPortalTileEntity) {
            if (!((RiftPortalTileEntity) tileEntity).isClosed()) {
                if (rand.nextInt(30) == 0) {

                    worldIn.playSound((PlayerEntity) null, pos, MidnightSounds.RIFT_IDLE, SoundCategory.BLOCKS, 2.0F, 0.95F + (float) rand.nextFloat() * 0.1F);
                }


                if (rand.nextInt(15) == 0) {
                    worldIn.playSound((PlayerEntity) null, pos, MidnightSounds.RIFT_IDLE, SoundCategory.BLOCKS, 2.0F, 0.95F + (float) rand.nextFloat() * 0.1F);
                    worldIn.addParticle(MidnightParticleTypes.SPORE, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.2D, (double) pos.getZ() + 0.5D, (double) ((float) rand.nextFloat()) - 0.5D, (double) ((float) rand.nextFloat() * 1.2F), (double) (rand.nextFloat()) - 0.5D);
                }
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    @Deprecated
    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return isClosed(world, pos) ? SHAPE : VoxelShapes.empty();
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {

        if (!worldIn.isDaytime() && worldIn.dimension.isSurfaceWorld() && worldIn.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING) && rand.nextInt(1600) < worldIn.getDifficulty().getId()) {
            for (int i = -2; i <= 2; ++i) {
                for (int j = -2; j <= 2; ++j) {
                    BlockPos spawnPos = pos.add(i, 0, j);

                    if (worldIn.getBlockState(spawnPos).getBlock() != this && worldIn.getBlockState(spawnPos.up()).isAir() && worldIn.getBlockState(spawnPos.up(2)).isAir()) {
                        Entity entity = MidnightEntities.RIFTER.spawn(worldIn, (CompoundNBT) null, (ITextComponent) null, (PlayerEntity) null, spawnPos.up(), SpawnReason.STRUCTURE, false, false);
                        if (entity != null) {
                            ((RifterEntity) entity).spawnedThroughRift = true;
                            ((RifterEntity) entity).setRiftPosition(pos);
                        }
                        break;
                    }
                }
            }
        }

    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (entity.posY >= pos.getY() + 15.0 / 16.0) return;

        if (isClosed(world, pos)) {
            entity.setMotion(entity.getMotion().add(0.0, 0.1, 0.0));
            return;
        }

        entity.getCapability(Midnight.RIFT_TRAVELLER_CAP).ifPresent(RiftTraveller::setInRift);
    }

    private static boolean isClosed(IBlockReader world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof RiftPortalTileEntity) {
            RiftPortalTileEntity riftEntity = (RiftPortalTileEntity) tileEntity;
            return riftEntity.isClosed();
        }
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return MidnightTileEntities.RIFT_PORTAL.create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}
