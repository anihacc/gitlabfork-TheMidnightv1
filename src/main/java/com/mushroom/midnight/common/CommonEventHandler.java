package com.mushroom.midnight.common;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.RiftTraveller;
import com.mushroom.midnight.common.capability.RifterCapturable;
import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.event.RifterCaptureEvent;
import com.mushroom.midnight.common.event.RifterReleaseEvent;
import com.mushroom.midnight.common.registry.MidnightEffects;
import com.mushroom.midnight.common.util.MidnightUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class CommonEventHandler {
    public static final float SOUND_TRAVEL_DISTANCE_MULTIPLIER = 2.0F;

    private static final ThreadLocal<Boolean> IS_TICKING_MIDNIGHT = ThreadLocal.withInitial(() -> false);

    @SubscribeEvent
    public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity) {
            event.addCapability(new ResourceLocation(Midnight.MODID, "rift_traveller"), new RiftTraveller());

            if (!(event.getObject() instanceof PlayerEntity)) {
                ResourceLocation rl = EntityType.getKey(event.getObject().getType());
                if (rl == null) {
                    return;
                }
                if (event.getObject() instanceof AnimalEntity) {
                    if (MidnightConfig.compat.notCapturableAnimals.get().stream().anyMatch(p -> p.contains(":") ? rl.toString().equals(p) : rl.getNamespace().equals(p))) {
                        return;
                    }
                } else if (!MidnightConfig.compat.capturableEntities.get().contains(rl.toString())) {
                    return;
                }
            }
            event.addCapability(new ResourceLocation(Midnight.MODID, "rifter_captured"), new RifterCapturable());
        }
    }

    @SubscribeEvent
    public static void onEntityTick(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        entity.getCapability(Midnight.RIFT_TRAVELLER_CAP).ifPresent(traveller -> traveller.update(entity));
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            World world = event.world;
            IS_TICKING_MIDNIGHT.set(MidnightUtil.isMidnightDimension(world));
        } else {
            IS_TICKING_MIDNIGHT.set(false);
        }
    }

    @SubscribeEvent
    public static void onEntityAttack(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        Entity trueSource = source.getTrueSource();
        if (trueSource instanceof LivingEntity && ((LivingEntity) trueSource).isPotionActive(MidnightEffects.STUNNED)) {
            event.setAmount(0.0F);
        }
    }

    @SubscribeEvent
    public static void onRifterCapture(RifterCaptureEvent event) {
        LivingEntity captured = event.getCaptured();
        if (captured instanceof PlayerEntity) {
            captured.recalculateSize();
        }
    }

    @SubscribeEvent
    public static void onRifterRelease(RifterReleaseEvent event) {
        LivingEntity captured = event.getCaptured();
        if (captured instanceof PlayerEntity) {
            captured.recalculateSize();
        }
    }

    @SubscribeEvent
    public static void setEyeHeight(EntityEvent.EyeHeight event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            if (RifterCapturable.isCaptured(player)) {
                event.setNewHeight(player.getWidth() / 2f);
            }
        }
    }

    // TODO: Reimplement with new rifts
    // No.
    /*@SubscribeEvent
    public static void onSleep(PlayerSleepInBedEvent event) {
        if (event.getResultStatus() == null) {
            PlayerEntity player = event.getPlayer();
            BlockPos bedPos = player.getPosition(); // event.getPos() is nullable in the forge event

            List<RiftEntity> rifts = player.world.getEntitiesWithinAABB(RiftEntity.class, new AxisAlignedBB(bedPos).grow(6.0));
            if (!rifts.isEmpty()) {
                event.setResult(PlayerEntity.SleepResult.OTHER_PROBLEM);
                player.sendStatusMessage(new TranslationTextComponent("status.midnight.rift_nearby"), true);
            }
        }
    }*/

    @SubscribeEvent
    public static void onPlaySound(PlaySoundAtEntityEvent event) {
        if (IS_TICKING_MIDNIGHT.get()) {
            event.setVolume(event.getVolume() * SOUND_TRAVEL_DISTANCE_MULTIPLIER);
        }
    }

    @SubscribeEvent
    public static void commandEvent(CommandEvent event)
    {
        String message = event.getParseResults().getReader().getString();

        try
        {
            PlayerEntity player = event.getParseResults().getContext().getSource().asPlayer();
            World world = player.getEntityWorld();
            message = message.replace("/", "");

            Command command = event.getParseResults().getContext().getCommand();

            if(command == null)
                return;

            if (message.contains("locate midnight:shadowroot_guardtower") || message.contains("locate midnight:well"))
            {
//                System.out.println("message = " + message);
                player.sendMessage(new TranslationTextComponent("The ability to " + message + " has been disabled. This is due to a bug that will be fixed in The Midnight 0.6.0.").setStyle(new Style().setColor(TextFormatting.RED)));
                // On servers, you will simply get the default error.
                event.setCanceled(true);
            }
        }
        catch (CommandSyntaxException ignored) { }

    }
}
