package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.common.registry.MidnightEntities;
import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NonTamedTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class ShadeSquirrelEntity extends TameableEntity {
    private static final Ingredient BREEDING_ITEMS = Ingredient.fromItems(MidnightItems.GLOB_FUNGUS_HAND);
    private static final int GROWING_TIME = -24000;

    private static final Predicate<LivingEntity> ATTACK_PREDICATE = (entity) -> {
        EntityType<?> type = entity.getType();
        return type != EntityType.CAT && type != MidnightEntities.SHADE_SQUIRREL && type.getWidth() <= 0.8F && type.getHeight() <= 0.8F;
    };

    public ShadeSquirrelEntity(EntityType<? extends ShadeSquirrelEntity> type, World world) {
        super(type, world);
        this.setTamed(false);
    }

    @Override
    protected void registerGoals() {
        this.sitGoal = new SitGoal(this);
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, this.sitGoal);
        this.goalSelector.addGoal(3, new TemptGoal(this, 0.7D, BREEDING_ITEMS, true) {
            @Override
            public boolean shouldExecute() {
                return super.shouldExecute() && !ShadeSquirrelEntity.this.isTamed();
            }
        });
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.4, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.35, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.3, 0.0015F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setCallsForHelp(ShadeSquirrelEntity.class));
        this.targetSelector.addGoal(4, new NonTamedTargetGoal<>(this, AnimalEntity.class, false, ATTACK_PREDICATE));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
        if (this.isTamed()) {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0);
        } else {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0);
        }

        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0);
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize size) {
        return this.isChild() ? size.height * 0.45F : size.height * 0.85F;
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0);
        } else {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0);
        }

        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0);
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        Item item = heldItem.getItem();

        if (this.isTamed()) {
            if (!heldItem.isEmpty()) {
                if (item.getFood() != null) {
                    if (this.isBreedingItem(heldItem) && this.getHealth() < this.getMaxHealth()) {
                        ItemStack newStack = heldItem.onItemUseFinish(this.world, this);
                        if (!player.abilities.isCreativeMode) {
                            newStack.shrink(1);
                        }

                        this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.8F, 1.2F + this.rand.nextFloat() * 0.3F);

                        this.heal((float) item.getFood().getHealing());
                        return true;
                    }
                }
            }

            if (this.isOwner(player) && !this.world.isRemote && !this.isBreedingItem(heldItem)) {
                this.sitGoal.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPath();
                this.setAttackTarget(null);
            }
        } else if (this.isBreedingItem(heldItem)) {
            this.consumeItemFromStack(player, heldItem);

            if (!this.world.isRemote) {
                if (this.rand.nextInt(4) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.setAttackTarget(null);
                    this.sitGoal.setSitting(true);
                    this.setHealth(20.0F);
                    this.playTameEffect(true);
                    this.world.setEntityState(this, (byte) 7);
                } else {
                    this.playTameEffect(false);
                    this.world.setEntityState(this, (byte) 6);
                }
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_ITEMS.test(stack);
    }

    @Override
    public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof CreeperEntity) && !(target instanceof GhastEntity)) {
            if (target instanceof WolfEntity) {
                WolfEntity wolfEntity = (WolfEntity) target;
                if (wolfEntity.isTamed() && wolfEntity.getOwner() == owner) {
                    return false;
                }
            }

            if (target instanceof ShadeSquirrelEntity) {
                ShadeSquirrelEntity squirrelEntity = (ShadeSquirrelEntity) target;
                if (squirrelEntity.isTamed() && squirrelEntity.getOwner() == owner) {
                    return false;
                }
            }

            if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity) owner).canAttackPlayer((PlayerEntity) target)) {
                return false;
            } else if (target instanceof AbstractHorseEntity && ((AbstractHorseEntity) target).isTame()) {
                return false;
            } else {
                return !(target instanceof CatEntity) || !((CatEntity) target).isTamed();
            }
        }

        return false;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        ShadeSquirrelEntity child = MidnightEntities.SHADE_SQUIRREL.create(this.world);
        if (child != null) {
            child.setGrowingAge(GROWING_TIME);
            return child;
        }
        return null;
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData data, @Nullable CompoundNBT dataTag) {
        if (this.rand.nextInt(5) == 0) {
            this.setGrowingAge(GROWING_TIME);
        }
        return data;
    }

    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }
}
