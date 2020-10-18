package com.mushroom.midnight.common.item;

import com.mushroom.midnight.common.block.SuavisBlock;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;

public class RawSuavisItem extends BlockItem {
    public RawSuavisItem(Item.Properties properties) {
        super(MidnightBlocks.SUAVIS, properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

//    @Override
//    public ActionResultType onItemUse(ItemUseContext context) {
//        World world = context.getWorld();
//        PlayerEntity player = context.getPlayer();
//        BlockPos pos = context.getPos();
//        Direction face = context.getFace();
//        ItemStack heldItem = context.getItem();
//
//        BlockState state = world.getBlockState(pos);
//        Block block = state.getBlock();
//        BlockItemUseContext blockContext = new BlockItemUseContext(context);
//        if (!block.isReplaceable(state, blockContext)) {
//            pos = pos.offset(face);
//        }
//
//        if (!heldItem.isEmpty() && player.canPlayerEdit(pos, face, heldItem) && MidnightBlocks.SUAVIS.getDefaultState().isValidPosition(world, pos) && context.getWorld().placedBlockWouldCollide(blockStateIn, context.getPos(), iselectioncontext)) {
//            BlockState suavisState = MidnightBlocks.SUAVIS.getDefaultState().with(SuavisBlock.STAGE, 0);
//            if (placeSuavisAt(heldItem, player, world, pos, suavisState)) {
//                SoundType soundtype = suavisState.getBlock().getSoundType(suavisState, world, pos, player);
//                world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
//                heldItem.shrink(1);
//            }
//            return ActionResultType.SUCCESS;
//        }
//
//        return ActionResultType.FAIL;
//    }
//
//    private boolean placeSuavisAt(ItemStack stack, PlayerEntity player, World world, BlockPos pos, BlockState newState) {
//        if (!world.setBlockState(pos, newState, 11)) {
//            return false;
//        }
//        BlockState state = world.getBlockState(pos);
//        if (state.getBlock() == newState.getBlock()) {
//            newState.getBlock().onBlockPlacedBy(world, pos, state, player, stack);
//            if (player instanceof ServerPlayerEntity) {
//                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) player, pos, stack);
//            }
//        }
//        return true;
//    }


    @Nullable
    @Override
    protected BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = MidnightBlocks.SUAVIS.getDefaultState().with(SuavisBlock.STAGE, 0);
        return canPlace(context, state) ? state : null;
    }

    @Override
    public void addToBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {

    }

    @Override
    public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {

    }

    @Override
    public String getTranslationKey() {
        return getDefaultTranslationKey();
    }
}
