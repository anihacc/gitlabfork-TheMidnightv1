package com.mushroom.midnight.common.registry;

import com.google.common.collect.ImmutableList;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.world.MidnightDimension;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.FMLHandshakeMessages;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.util.function.BiFunction;

@Mod.EventBusSubscriber(modid = Midnight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MidnightDimensions {
    public static final ModDimension MIDNIGHT = new ModDimension() {
        @Override
        public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
            return MidnightDimension::new;
        }
    };

    private static final ResourceLocation MIDNIGHT_ID = new ResourceLocation(Midnight.MODID, "midnight");

    static {
        // TODO: Temporary hack until Forge fix
        try {
            Field channelField = FMLNetworkConstants.class.getDeclaredField("handshakeChannel");
            channelField.setAccessible(true);

            SimpleChannel handshakeChannel = (SimpleChannel) channelField.get(null);
            handshakeChannel.messageBuilder(S2CDimensionSync.class, 100)
                    .loginIndex(S2CDimensionSync::getLoginIndex, S2CDimensionSync::setLoginIndex)
                    .decoder(S2CDimensionSync::decode)
                    .encoder(S2CDimensionSync::encode)
                    .buildLoginPacketList(isLocal -> {
                        if (isLocal) return ImmutableList.of();
                        return ImmutableList.of(Pair.of("Midnight Dim Sync", new S2CDimensionSync(MidnightDimensions.midnight())));
                    })
                    .consumer((msg, ctx) -> {
                        if (DimensionManager.getRegistry().getByValue(msg.id) == null) {
                            DimensionManager.registerDimensionInternal(msg.id, msg.name, msg.dimension, null, msg.skyLight);
                        }
                        ctx.get().setPacketHandled(true);
                        handshakeChannel.reply(new FMLHandshakeMessages.C2SAcknowledge(), ctx.get());
                    })
                    .add();
        } catch (ReflectiveOperationException e) {
            Midnight.LOGGER.error("Failed to add dimension sync to handshake channel", e);
        }
    }

    @SubscribeEvent
    public static void registerModDimensions(RegistryEvent.Register<ModDimension> event) {
        RegUtil.generic(event.getRegistry()).add("midnight", MIDNIGHT);
    }

    public static DimensionType midnight() {
        return DimensionType.byName(MIDNIGHT_ID);
    }

    @Mod.EventBusSubscriber(modid = Midnight.MODID)
    private static class ForgeEvents {
        @SubscribeEvent
        public static void registerDimensions(RegisterDimensionsEvent event) {
            if (DimensionType.byName(MIDNIGHT_ID) == null) {
                DimensionManager.registerDimension(MIDNIGHT_ID, MIDNIGHT, null, false);
            }
        }
    }

    public static class S2CDimensionSync {
        final int id;
        final ResourceLocation name;
        final ModDimension dimension;
        final boolean skyLight;

        private int loginIndex;

        public S2CDimensionSync(DimensionType dimensionType) {
            this.id = dimensionType.getId() + 1;
            this.name = DimensionType.getKey(dimensionType);
            this.dimension = dimensionType.getModType();
            this.skyLight = dimensionType.func_218272_d();
        }

        S2CDimensionSync(int id, ResourceLocation name, ModDimension dimension, boolean skyLight) {
            this.id = id;
            this.name = name;
            this.dimension = dimension;
            this.skyLight = skyLight;
        }

        void setLoginIndex(final int loginIndex) {
            this.loginIndex = loginIndex;
        }

        int getLoginIndex() {
            return this.loginIndex;
        }

        void encode(PacketBuffer buffer) {
            buffer.writeInt(this.id);
            buffer.writeResourceLocation(this.name);
            buffer.writeResourceLocation(this.dimension.getRegistryName());
            buffer.writeBoolean(this.skyLight);
        }

        public static S2CDimensionSync decode(PacketBuffer buffer) {
            int id = buffer.readInt();
            ResourceLocation name = buffer.readResourceLocation();
            ModDimension dimension = ForgeRegistries.MOD_DIMENSIONS.getValue(buffer.readResourceLocation());
            boolean skyLight = buffer.readBoolean();

            return new S2CDimensionSync(id, name, dimension, skyLight);
        }
    }
}
