package com.mushroom.midnight.common.item.tool;

import com.google.common.collect.ImmutableMap;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

public class MidnightAxeItem extends AxeItem {
    static {
        try {
            Field field = ObfuscationReflectionHelper.findField(AxeItem.class, "field_203176_a");

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            Map<Block, Block> strippableBlocks = (Map<Block, Block>) field.get(null);

            ImmutableMap.Builder<Block, Block> builder = new ImmutableMap.Builder<>();
            builder.putAll(strippableBlocks);

            builder.put(MidnightBlocks.SHADOWROOT_LOG, MidnightBlocks.SHADOWROOT_STRIPPED_LOG);
            builder.put(MidnightBlocks.DARK_WILLOW_LOG, MidnightBlocks.DARK_WILLOW_STRIPPED_LOG);
            builder.put(MidnightBlocks.DEAD_WOOD_LOG, MidnightBlocks.DEAD_WOOD_STRIPPED_LOG);

            builder.put(MidnightBlocks.SHADOWROOT_WOOD, MidnightBlocks.SHADOWROOT_STRIPPED_WOOD);
            builder.put(MidnightBlocks.DARK_WILLOW_WOOD, MidnightBlocks.DARK_WILLOW_STRIPPED_WOOD);
            builder.put(MidnightBlocks.DEAD_WOOD, MidnightBlocks.DEAD_WOOD_STRIPPED);

            field.set(null, builder.build());
        } catch (ReflectiveOperationException e) {
            Midnight.LOGGER.warn("Failed to reflect strippable logs field", e);
        }
    }

    public MidnightAxeItem(IItemTier tier, Properties properties) {
        super(tier, 6f, -3.2f, properties);
    }
}
