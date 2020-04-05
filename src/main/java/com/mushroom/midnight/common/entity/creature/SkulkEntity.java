package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.common.entity.task.FindEatableFood;
import com.mushroom.midnight.common.entity.task.NeutralGoal;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.registry.MidnightSounds;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtWithoutMovingGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class SkulkEntity extends AnimalEntity {
    private static final DataParameter<Boolean> STEALTH = EntityDataManager.createKey(SkulkEntity.class, DataSerializers.BOOLEAN);
    private int stealthCooldown = 0;
    private int eatTicks;

    private static final Predicate<ItemStack> canEatFood = (food) -> {
        return food.getItem().getFood() != null && !food.getItem().getFood().isMeat() && food.getItem() != MidnightItems.RAW_SUAVIS && food.getItem() != MidnightItems.COOKED_SUAVIS;
    };

    private static final Predicate<ItemStack> dislikeFood = (food) -> {
        return food.getItem() == MidnightItems.RAW_SUAVIS || food.getItem() == MidnightItems.COOKED_SUAVIS;
    };

    public SkulkEntity(EntityType<? extends SkulkEntity> entityType, World world) {
        super(entityType, world);
        this.setCanPickUpLoot(true);
    }

    @Override
    public void registerData() {
        super.registerData();
        dataManager.register(STEALTH, Boolean.FALSE);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }

    @Override
    protected PathNavigator createNavigator(World world) {
        return new GroundPathNavigator(this, world);
    }

    @Override
    public boolean canSpawn(IWorld worldIn, SpawnReason spawnReasonIn) {
        if (getPosition().getY() <= world.getSeaLevel()) {
            return false;
        }
        return super.canSpawn(worldIn, spawnReasonIn);
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT dataTag) {
        setStealth(true);
        return livingdata;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new NeutralGoal(this, new PanicGoal(this, 1d), true));
        this.goalSelector.addGoal(2, new NeutralGoal(this, new MeleeAttackGoal(this, 1d, false), false));
        this.goalSelector.addGoal(4, new AvoidEntityGoal(this, LivingEntity.class, 8.0F, 1.6D, 1.6D) {
            @Override
            public boolean shouldExecute() {
                boolean valid = super.shouldExecute();
                return valid && this.avoidTarget != null && (dislikeFood.test(this.avoidTarget.getHeldItem(Hand.MAIN_HAND)) || dislikeFood.test(this.avoidTarget.getHeldItem(Hand.OFF_HAND)));
            }

            @Override
            public void startExecuting() {
                super.startExecuting();
                setStealth(false);
            }

            @Override
            public void resetTask() {
                super.resetTask();
                setStealth(true);
            }
        });
        this.goalSelector.addGoal(5, new FindEatableFood(this, this::canEatItem, 1.15D));

        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1d, 0.005f) {
            @Override
            public boolean shouldExecute() {
                boolean valid = super.shouldExecute();
                if (valid && canStealth()) { setStealth(true); }
                return valid;
            }
        });
        this.goalSelector.addGoal(7, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 8f, 0.02f));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this) {
            @Override
            public boolean shouldExecute() {
                boolean valid = super.shouldExecute();
                if (valid && canStealth()) { setStealth(true); }
                return valid;
            }
        });
        this.targetSelector.addGoal(1, new NeutralGoal(this, new HurtByTargetGoal(this), false));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1d);
        getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25d);
        getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6d);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        super.attackEntityAsMob(entity);
        float damage = (float) getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue();
        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
        if (flag) {
            applyEnchantments(this, entity);
        }
        setStealth(false);
        return flag;
    }

    @Override
    public void swingArm(Hand hand) {
    }

    @Override
    public void livingTick() {
        if (!this.world.isRemote && this.isAlive() && this.isServerWorld()) {
            ++this.eatTicks;
            ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
            if (this.canEatItem(itemstack)) {
                if (this.eatTicks > 200) {
                    ItemStack itemstack1 = itemstack.onItemUseFinish(this.world, this);
                    if (!itemstack1.isEmpty()) {
                        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack1);
                    }

                    this.heal(2.0F);

                    this.eatTicks = 0;
                } else if (this.eatTicks > 160 && this.rand.nextFloat() < 0.1F) {
                    this.playSound(this.getEatSound(itemstack), 1.0F, 1.0F);
                    this.world.setEntityState(this, (byte) 45);
                }
            }
        }

        super.livingTick();
        if (stealthCooldown > 0) {
            stealthCooldown--;
        }
    }

    public boolean isStealth() {
        return dataManager.get(STEALTH);
    }

    public boolean canStealth() {
        return stealthCooldown <= 0;
    }

    public void setStealth(boolean flag) {
        dataManager.set(STEALTH, flag);
        stealthCooldown = 60;
    }

    @Override
    public float getAIMoveSpeed() {
        return super.getAIMoveSpeed() * (isStealth() ? 0.3f : 1f);
    }

    @Override
    protected void damageEntity(DamageSource damageSrc, float damageAmount) {
        super.damageEntity(damageSrc, damageAmount);
        setStealth(false);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("stealth", isStealth());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("stealth", Constants.NBT.TAG_BYTE)) {
            setStealth(compound.getBoolean("stealth"));
        }
    }

    @Override
    public float getStandingEyeHeight(Pose pose, EntitySize size) {
        return super.getStandingEyeHeight(pose, size) * 0.5f;
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 45) {
            ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
            if (!itemstack.isEmpty()) {
                for (int i = 0; i < 8; ++i) {
                    Vec3d vec3d = (new Vec3d(((double) this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D)).rotatePitch(-this.rotationPitch * ((float) Math.PI / 180F)).rotateYaw(-this.rotationYaw * ((float) Math.PI / 180F));
                    this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, itemstack), this.getPosX() + this.getLookVec().x / 2.0D, this.getPosY(), this.getPosZ() + this.getLookVec().z / 2.0D, vec3d.x, vec3d.y + 0.05D, vec3d.z);
                }
            }
        } else {
            super.handleStatusUpdate(id);
        }

    }

    private boolean canEatItem(ItemStack itemstack) {
        return canEatFood.test(itemstack);
    }

    private void spitOutItem(ItemStack stackIn) {
        if (!stackIn.isEmpty() && !this.world.isRemote) {
            ItemEntity itementity = new ItemEntity(this.world, this.getPosX() + this.getLookVec().x, this.getPosY() + 1.0D, this.getPosZ() + this.getLookVec().z, stackIn);
            itementity.setPickupDelay(40);
            itementity.setThrowerId(this.getUniqueID());
            this.world.addEntity(itementity);
        }
    }

    private void spawnItem(ItemStack stackIn) {
        ItemEntity itementity = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), stackIn);
        this.world.addEntity(itementity);
    }

    @Override
    protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
        ItemStack itemstack = itemEntity.getItem();
        if (!this.canEatItem(this.getItemStackFromSlot(EquipmentSlotType.MAINHAND)) && this.canEatItem(itemstack)) {
            int i = itemstack.getCount();
            if (i > 1) {
                this.spawnItem(itemstack.split(i - 1));
            }

            this.spitOutItem(this.getItemStackFromSlot(EquipmentSlotType.MAINHAND));
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack.split(1));
            this.inventoryHandsDropChances[EquipmentSlotType.MAINHAND.getIndex()] = 2.0F;
            this.onItemPickup(itemEntity, itemstack.getCount());
            itemEntity.remove();
            this.eatTicks = 0;
        }

    }

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }

    @Override
    public int getTalkInterval() {
        return 100;
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 1 + this.world.rand.nextInt(3);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return MidnightSounds.SKULK_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MidnightSounds.SKULK_DEATH;
    }

    @Override
    public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
        return worldIn.getBlockState(pos.down()).getBlock() == MidnightBlocks.GRASS_BLOCK ? 10.0F : 1.0F;
    }

    //when you stands near skulk, you can see skulk
    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean isInvisibleToPlayer(PlayerEntity player) {
        return this.getDistanceSq(player) < 8d;
    }
}
