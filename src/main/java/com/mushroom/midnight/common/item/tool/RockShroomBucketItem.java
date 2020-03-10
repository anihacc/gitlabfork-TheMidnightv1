package com.mushroom.midnight.common.item.tool;

import com.mushroom.midnight.common.registry.MidnightFluids;
import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class RockShroomBucketItem extends BucketItem {
    private final Fluid containedBlock;

    public RockShroomBucketItem(Supplier<? extends Fluid> supplier, Item.Properties builder) {
        super(supplier, builder);
        this.containedBlock = supplier.get();
    }

    protected ItemStack emptyBucket(ItemStack p_203790_1_, PlayerEntity p_203790_2_) {
        return !p_203790_2_.abilities.isCreativeMode ? new ItemStack(MidnightItems.ROCKSHROOM_BUCKET) : p_203790_1_;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, this.containedBlock == Fluids.EMPTY ? RayTraceContext.FluidMode.SOURCE_ONLY : RayTraceContext.FluidMode.NONE);
        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(playerIn, worldIn, itemstack, raytraceresult);
        if (ret != null) return ret;
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return ActionResult.func_226250_c_(itemstack);
        } else if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.func_226250_c_(itemstack);
        } else {
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceresult;
            BlockPos blockpos = blockraytraceresult.getPos();
            Direction direction = blockraytraceresult.getFace();
            BlockPos blockpos1 = blockpos.offset(direction);
            if (worldIn.isBlockModifiable(playerIn, blockpos) && playerIn.canPlayerEdit(blockpos1, direction, itemstack)) {
                if (this.containedBlock == Fluids.EMPTY) {
                    BlockState blockstate1 = worldIn.getBlockState(blockpos);
                    if (blockstate1.getBlock() instanceof IBucketPickupHandler) {
                        Fluid fluid = ((IBucketPickupHandler) blockstate1.getBlock()).pickupFluid(worldIn, blockpos, blockstate1);
                        if (fluid != Fluids.EMPTY) {
                            playerIn.addStat(Stats.ITEM_USED.get(this));

                            SoundEvent soundevent = this.containedBlock.getAttributes().getEmptySound();
                            if (soundevent == null)
                                soundevent = fluid.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_FILL_LAVA : SoundEvents.ITEM_BUCKET_FILL;
                            playerIn.playSound(soundevent, 1.0F, 1.0F);
                            ItemStack itemstack1 = this.fillBucket(itemstack, playerIn, getFilledBucket(fluid).getItem());
                            if (!worldIn.isRemote) {
                                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity) playerIn, new ItemStack(getFilledBucket(fluid).getItem()));
                            }

                            return ActionResult.func_226248_a_(itemstack1);
                        }
                    }

                    return ActionResult.func_226251_d_(itemstack);
                } else {
                    BlockState blockstate = worldIn.getBlockState(blockpos);
                    BlockPos blockpos2 = blockstate.getBlock() instanceof ILiquidContainer && this.containedBlock == Fluids.WATER ? blockpos : blockpos1;
                    if (this.tryPlaceContainedLiquid(playerIn, worldIn, blockpos2, blockraytraceresult)) {
                        this.onLiquidPlaced(worldIn, itemstack, blockpos2);
                        if (playerIn instanceof ServerPlayerEntity) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerIn, blockpos2, itemstack);
                        }

                        playerIn.addStat(Stats.ITEM_USED.get(this));
                        return ActionResult.func_226248_a_(this.emptyBucket(itemstack, playerIn));
                    } else {
                        return ActionResult.func_226251_d_(itemstack);
                    }
                }
            } else {
                return ActionResult.func_226251_d_(itemstack);
            }
        }
    }

    @Nonnull
    public static ItemStack getFilledBucket(@Nonnull Fluid fluid) {

        if (fluid == Fluids.WATER) {
            return new ItemStack(MidnightItems.WATER_ROCKSHROOM_BUCKET);
        } else if (fluid == MidnightFluids.DARK_WATER) {
            return new ItemStack(MidnightItems.DARK_WATER_BUCKET);
        }


        return new ItemStack(MidnightItems.DARK_WATER_BUCKET);
    }

    private ItemStack fillBucket(ItemStack emptyBuckets, PlayerEntity player, Item fullBucket) {
        if (player.abilities.isCreativeMode) {
            return emptyBuckets;
        } else {
            emptyBuckets.shrink(1);
            if (emptyBuckets.isEmpty()) {
                return new ItemStack(fullBucket);
            } else {
                if (!player.inventory.addItemStackToInventory(new ItemStack(fullBucket))) {
                    player.dropItem(new ItemStack(fullBucket), false);
                }

                return emptyBuckets;
            }
        }
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @Nullable net.minecraft.nbt.CompoundNBT nbt) {
        if (this.getClass() == RockShroomBucketItem.class)
            return new MidnightFluidBucketWrapper(stack);
        else
            return super.initCapabilities(stack, nbt);
    }
}