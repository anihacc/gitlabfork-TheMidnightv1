package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightCriterion;
import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potions;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;
import java.util.Random;

@SuppressWarnings("deprecation")
public class SuavisBlock extends Block implements IGrowable {
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 3);

    protected static final VoxelShape[] BOUNDS = {
            makeCuboidShape(0, 0, 0, 16, 3.4, 16),
            makeCuboidShape(0, 0, 0, 16, 7, 16),
            makeCuboidShape(0, 0, 0, 16, 13, 16),
            makeCuboidShape(0, 0, 0, 16, 16, 16),
    };

    public SuavisBlock() {
        super(Properties.create(Material.GOURD, MaterialColor.LIGHT_BLUE)
                .lightValue(12)
                .hardnessAndResistance(1f, 0f)
                .sound(SoundType.SLIME)
                .tickRandomly());
        setDefaultState(
                getStateContainer()
                        .getBaseState()
                        .with(STAGE, 3)
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockState state, Direction facing, BlockState state2, IWorld world, BlockPos pos1, BlockPos pos2, Hand hand) {
        return getDefaultState();
    }

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance) {
        super.onFallenUpon(world, pos, entity, fallDistance);
        if (!world.isRemote && fallDistance > 0.8f && entity.canTrample(world.getBlockState(pos), pos, fallDistance)) {
            BlockState state = world.getBlockState(pos);
            world.playSound(null, pos, SoundEvents.BLOCK_SLIME_BLOCK_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + world.rand.nextFloat() * 0.2F);
            boolean isFirstStage = state.get(STAGE) == 0;
            if (!isFirstStage && world.rand.nextInt(100) == 0) {
                world.destroyBlock(pos, true);
            } else {
                spawnAsEntity(world, pos, new ItemStack(MidnightItems.RAW_SUAVIS));
                world.setBlockState(pos, isFirstStage ? Blocks.AIR.getDefaultState() : state.with(STAGE, state.get(STAGE) - 1), 2);
                world.playEvent(2001, pos, getStateId(state));
                if (!(entity instanceof PlayerEntity) || !((PlayerEntity) entity).isCreative()) {
                    createNauseaCloud(world, pos, 0);
                }
            }
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction dir, BlockState adjState, IWorld world, BlockPos pos, BlockPos adjPos) {
        if (dir == Direction.DOWN) {
            if (!canRemain(world, pos)) {
                world.destroyBlock(pos, false);
                return Blocks.AIR.getDefaultState();
            }
        }
        return super.updatePostPlacement(state, dir, adjState, world, pos, adjPos);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        return canRemain(world, pos);
    }

    private boolean canRemain(IWorldReader world, BlockPos pos) {
        BlockState down = world.getBlockState(pos.down());
        return down.getBlock() != MidnightBlocks.SUAVIS && (down.isSolidSide(world, pos.down(), Direction.UP) || down.getBlock() == MidnightBlocks.DECEITFUL_MUD);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return state.get(STAGE) == 3 ? new ItemStack(MidnightBlocks.SUAVIS) : new ItemStack(MidnightItems.RAW_SUAVIS);
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return false;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return state.get(STAGE) < 3;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }

    @Override
    public void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(world, player, pos, state, te, stack);
        if (!world.isRemote()) {
            if (!player.isCreative() && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
                createNauseaCloud(world, pos, state.get(STAGE));
            }
        }

        if (!world.isRemote && !player.isCreative() && player instanceof ServerPlayerEntity) {
            MidnightCriterion.HARVESTED_SUAVIS.trigger((ServerPlayerEntity) player);
        }
    }

    /*

     */

    private static void createNauseaCloud(World world, BlockPos pos, int intensity) {
        AreaEffectCloudEntity entity = new AreaEffectCloudEntity(world, pos.getX(), pos.getY(), pos.getZ());
        entity.setRadius(1.5f + 0.5f * intensity);
        entity.setRadiusOnUse(-0.3f);
        entity.setWaitTime(10);
        entity.setRadiusPerTick(-entity.getRadius() / (float) entity.getDuration());
        entity.setPotion(Potions.EMPTY);
        entity.setColor(0x355796);
        entity.addEffect(new EffectInstance(Effects.NAUSEA, 20 * (intensity + 1) * 6, 0, false, true));
        world.addEntity(entity);
    }

   /* @Override
    @OnlyIn(Dist.CLIENT)
    public int getPackedLightmapCoords(BlockState state, ILightReader worldIn, BlockPos pos) {
        int skyLight = 15;
        int blockLight = 15;
        return skyLight << 20 | blockLight << 4;
    }*/

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(STAGE) < 3;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(STAGE, state.get(STAGE) + 1), 2);
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        if (state.get(STAGE) < 3 && ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt(5) == 0)) {
            grow(world, rand, pos, state);
            ForgeHooks.onCropsGrowPost(world, pos, state);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return BOUNDS[state.get(STAGE)];
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader world, BlockPos pos, PathType type) {
        return state.get(STAGE) < 2;
    }
}
