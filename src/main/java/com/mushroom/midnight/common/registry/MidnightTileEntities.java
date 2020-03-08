package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.common.tile.CacheTileEntity;
import com.mushroom.midnight.common.tile.MidnightChestTileEntity;
import com.mushroom.midnight.common.tile.MidnightFurnaceTileEntity;
import com.mushroom.midnight.common.tile.RiftPortalTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import static com.mushroom.midnight.Midnight.MODID;

@ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MidnightTileEntities {
    public static final TileEntityType<MidnightChestTileEntity> MIDNIGHT_CHEST = TileEntityType.Builder.create(MidnightChestTileEntity::new,
            MidnightBlocks.SHADOWROOT_CHEST, MidnightBlocks.DEAD_WOOD_CHEST, MidnightBlocks.DARK_WILLOW_CHEST,
            MidnightBlocks.NIGHTSHROOM_CHEST, MidnightBlocks.DEWSHROOM_CHEST, MidnightBlocks.VIRIDSHROOM_CHEST, MidnightBlocks.BOGSHROOM_CHEST
    ).build(null);
    public static final TileEntityType<?> MIDNIGHT_FURNACE = TileEntityType.FURNACE;
    public static final TileEntityType<?> CACHE = TileEntityType.CHEST;

    public static final TileEntityType<?> RIFT_PORTAL = TileEntityType.END_PORTAL;

    @SubscribeEvent
    public static void registerTileEntity(final RegistryEvent.Register<TileEntityType<?>> event) {
        RegUtil.generic(event.getRegistry())
                .add("midnight_chest", MIDNIGHT_CHEST)
                .add("midnight_furnace", TileEntityType.Builder.create(MidnightFurnaceTileEntity::new, MidnightBlocks.NIGHTSTONE_FURNACE).build(null))
                .add("cache", TileEntityType.Builder.create(CacheTileEntity::new, MidnightBlocks.VIRIDSHROOM_STEM_CACHE).build(null))
                .add("rift_portal", TileEntityType.Builder.create(RiftPortalTileEntity::new, MidnightBlocks.RIFT_PORTAL).build(null));
    }
}
