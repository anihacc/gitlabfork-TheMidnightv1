package com.mushroom.midnight.common.entity.creature;

import static com.mushroom.midnight.common.registry.ModLootTables.LOOT_TABLE_NIGHTSTAG;

import java.util.UUID;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.block.BlockUnstableBush;
import com.mushroom.midnight.common.block.BlockUnstableBushBloomed;
import com.mushroom.midnight.common.capability.AnimationCapability;
import com.mushroom.midnight.common.capability.AnimationCapability.AnimationType;
import com.mushroom.midnight.common.entity.navigation.CustomPathNavigateGround;
import com.mushroom.midnight.common.entity.task.EntityTaskCharge;
import com.mushroom.midnight.common.entity.task.EntityTaskEatGrass;
import com.mushroom.midnight.common.entity.task.EntityTaskNeutral;
import com.mushroom.midnight.common.entity.task.EntityTaskSearchBlock;
import com.mushroom.midnight.common.helper.Helper;
import com.mushroom.midnight.common.item.ItemUnstableFruit;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModCriterion;
import com.mushroom.midnight.common.registry.ModEffects;
import com.mushroom.midnight.common.registry.ModSounds;
import com.mushroom.midnight.common.registry.ModSurfaceBiomes;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;

public class EntityNightStag extends EntityAnimal {
    private static final DataParameter<Integer> ANTLER_TYPE = EntityDataManager.createKey(EntityNightStag.class, DataSerializers.VARINT);
    public static final int MAX_ANTLER_TYPE = 9;
    private static final AttributeModifier CHILD_ATTACK_MALUS = new AttributeModifier(UUID.fromString("c0f32cda-a4fd-4fe4-8b3f-15612ef9a52f"), "nightstag_child_attack_malus", -2d, 0);
    private static final int GROWING_TIME = -24000;
    private static final Predicate<IBlockState> FRUIT_PREDICATE = p -> p.getBlock() instanceof BlockUnstableBushBloomed && p.getValue(BlockUnstableBushBloomed.HAS_FRUIT);
    private final AnimationCapability animCap = new AnimationCapability();
    private int temptTime = 400;

    public EntityNightStag(World world) {
        super(world);
        setSize(0.9f, 1.87f);
    }

    @Override
    @Nullable
    public EntityAgeable createChild(EntityAgeable entity) {
        EntityNightStag child = new EntityNightStag(world);
        child.setGrowingAge(GROWING_TIME);
        child.setAntlerType(((EntityNightStag)entity).getAntlerType());
        return child;
    }

    @Override
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        if (this.rand.nextInt(5) == 0) {
            setGrowingAge(GROWING_TIME);
        }
        if (Helper.isMidnightDimension(world)) {
            Biome biome = world.getBiome(getPosition());
            int random = this.rand.nextInt(3);
            if (biome == ModSurfaceBiomes.VIGILANT_FOREST || biome == ModSurfaceBiomes.HILLY_VIGILANT_FOREST) {
                setAntlerType(random == 0 ? 0 : random == 1 ? 3 : 7);
            } else if (biome == ModSurfaceBiomes.NIGHT_PLAINS || biome == ModSurfaceBiomes.WARPED_FIELDS) {
                setAntlerType(random == 0 ? 0 : 1);
            } else if (biome == ModSurfaceBiomes.RUNEBUSH_GROVE) {
                setAntlerType(random == 0 ? 3 : 8);
            } else if (biome == ModSurfaceBiomes.DECEITFUL_BOG) {
                setAntlerType(random == 0 ? 0 : random == 1 ? 2 : 4);
            } else if (biome == ModSurfaceBiomes.FUNGI_FOREST || biome == ModSurfaceBiomes.HILLY_FUNGI_FOREST) {
                setAntlerType(random == 0 ? 0 : random == 1 ? 2 : 3);
            } else if (biome == ModSurfaceBiomes.CRYSTAL_SPIRES) {
                setAntlerType(random == 0 ? 5 : 6);
            } else if (biome == ModSurfaceBiomes.PHANTASMAL_VALLEY || biome == ModSurfaceBiomes.OBSCURED_PEAKS || biome == ModSurfaceBiomes.OBSCURED_PLATEAU || biome == ModSurfaceBiomes.BLACK_RIDGE) {
                setAntlerType(random == 0 ? 0 : random == 1 ? 5 : 7);
            } else {
                setAntlerType(this.rand.nextInt(MAX_ANTLER_TYPE));
            }
        } else {
            setAntlerType(this.rand.nextInt(MAX_ANTLER_TYPE));
        }
        return livingdata;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ANTLER_TYPE, 0);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("antler_type", getAntlerType());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("antler_type", Constants.NBT.TAG_INT)) {
            int antlerType = compound.getInteger("antler_type");
            setAntlerType(antlerType >= 0 && antlerType < MAX_ANTLER_TYPE ? antlerType : 0);
        }
    }

    public void setAntlerType(int antlerType) {
        dataManager.set(ANTLER_TYPE, antlerType >= 0 && antlerType < MAX_ANTLER_TYPE ? antlerType : 0);
    }

    public int getAntlerType() {
        return dataManager.get(ANTLER_TYPE);
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }

    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.NIGHTSTAG_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSounds.NIGHTSTAG_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.NIGHTSTAG_DEATH;
    }

    @Override
    public int getTalkInterval() {
        return 200;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        playSound(ModSounds.NIGHTSTAG_STEP, 0.15f, 1f);
    }

    @Override
    public float getBlockPathWeight(BlockPos pos) {
        Block belowBlock = world.getBlockState(pos.down()).getBlock();
        return belowBlock == ModBlocks.MIDNIGHT_GRASS || belowBlock == ModBlocks.NIGHTSTONE ? 10f : 9f - (world.getLightBrightness(pos) * 10f);
    }

    @Override
    public boolean getCanSpawnHere() {
        if (getPosition().getY() <= world.getSeaLevel()) {
            return false;
        }
        IBlockState belowState = world.getBlockState(new BlockPos(this).down());
        return belowState.isFullCube() && belowState.canEntitySpawn(this);
    }

    @Override
    protected PathNavigate createNavigator(World world) {
        return new CustomPathNavigateGround(this, world);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityTaskNeutral(this, new EntityAIPanic(this, 1.2d), true));
        this.tasks.addTask(1, new EntityTaskNeutral(this, new EntityTaskCharge(this, 1.2d, 200, 0.25f), false));
        this.tasks.addTask(2, new EntityTaskNeutral(this, new EntityAIAttackMelee(this, 1d, false), false));
        this.tasks.addTask(2, new EntityAIMate(this, 1d));
        this.tasks.addTask(3, new EntityAITempt(this, 1d, Items.AIR, false) {
            @Override
            protected boolean isTempting(ItemStack stack) {
                return isBreedingItem(stack);
            }
            @Override
            public boolean shouldExecute() {
                boolean valid = super.shouldExecute();
                if (valid && !this.temptingPlayer.isCreative()) {
                    if (--temptTime < 0) {
                        temptTime = 400;
                        setAttackTarget(this.temptingPlayer);
                    }
                }
                return valid;
            }
        });
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1d));
        this.tasks.addTask(4, new EntityTaskSearchBlock(this, FRUIT_PREDICATE, 0.7d, 8, 200));
        this.tasks.addTask(5, new EntityTaskEatGrass(this, 40, false, FRUIT_PREDICATE) {
            @Override
            public boolean shouldExecute() {
                BlockPos currentPos;
                return super.shouldExecute() || ((currentPos = getPosition()).equals(getHomePosition()) && FRUIT_PREDICATE.test(world.getBlockState(currentPos)));
            }

            @Override
            protected void eatPlant(IBlockState state, BlockPos pos) {
                this.owner.world.setBlockState(pos, ModBlocks.UNSTABLE_BUSH.getDefaultState().withProperty(BlockUnstableBush.STAGE, BlockUnstableBush.MAX_STAGE), 2);
                if (isChild()) {
                    setGrowingAge(Math.min(getGrowingAge() + 5000, 0));
                }
                addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 200, 0, false, true));
                addPotionEffect(new PotionEffect(ModEffects.UNSTABLE_FALL, 400, 0, false, true));
            }
        });
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.7d, 0.005f));
        this.tasks.addTask(7, new EntityTaskCurtsey(this, EntityPlayer.class, 12f, 0.02f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityTaskNeutral(this, new EntityAIHurtByTarget(this, true), false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4d);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25d);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20d);
        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1d);
    }

    @Override
    protected void onGrowingAdult() {
        IAttributeInstance attackAttrib = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        if (attackAttrib.hasModifier(CHILD_ATTACK_MALUS)) {
            attackAttrib.removeModifier(CHILD_ATTACK_MALUS);
        }
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20d);
        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1d);
        setHealth(20f);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        super.attackEntityAsMob(entity);
        float damage = (float) getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        if (animCap.getAnimationType() == AnimationType.CHARGE) {
            damage *= 2f;
        }
        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
        if (flag) {
            if (!isChild() && entity instanceof EntityPlayer) {
                ((EntityPlayer) entity).addPotionEffect(new PotionEffect(ModEffects.DARKNESS, 200, 0, false, true));
            }
            applyEnchantments(this, entity);
            animCap.setAnimation(this, AnimationType.ATTACK, 10);
        }
        return flag;
    }

    @Override
    public void swingArm(EnumHand hand) {
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
        super.fall(distance, damageMultiplier * 0.2f);
    }

    @Override
    public double getMountedYOffset() {
        return (double) this.height * 0.67d;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() instanceof ItemUnstableFruit;
    }

    @Override
    protected void consumeItemFromStack(EntityPlayer player, ItemStack stack) {
        if (isBreedingItem(stack)) {
            this.temptTime = 400;
            addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 200, 0, false, true));
            addPotionEffect(new PotionEffect(ModEffects.UNSTABLE_FALL, 400, 0, false, true));
        }
        super.consumeItemFromStack(player, stack);
    }

    @Override
    public void eatGrassBonus() {
        heal(1f);
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player) {
        return isChild() ? 4 : 7;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LOOT_TABLE_NIGHTSTAG;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.animCap.updateAnimation();
        if (!this.world.isRemote && isChild()) {
            IAttributeInstance attackAttrib = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
            if (!attackAttrib.hasModifier(CHILD_ATTACK_MALUS)) {
                attackAttrib.applyModifier(CHILD_ATTACK_MALUS);
                getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10d);
                getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0d);
                setHealth(10f);
            }
        }
    }

    @Override
    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == Midnight.ANIMATION_CAP) {
            return Midnight.ANIMATION_CAP.cast(this.animCap);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == Midnight.ANIMATION_CAP || super.hasCapability(capability, facing);
    }

    public class EntityTaskCurtsey extends EntityAIWatchClosest {

        EntityTaskCurtsey(EntityLiving entity, Class<? extends Entity> watchTargetClass, float maxDistance, float chance) {
            super(entity, watchTargetClass, maxDistance, chance);
            setMutexBits(3);
        }

        @Override
        public void startExecuting() {
            super.startExecuting();
            if (!isChild() && getAttackTarget() == null && getRNG().nextFloat() < 0.1f) {
                if (closestEntity instanceof EntityPlayerMP && Helper.isNotFakePlayer(closestEntity)) {
                    ModCriterion.NIGHTSTAG_BOW[getAntlerType()].trigger((EntityPlayerMP) closestEntity);
                }
                animCap.setAnimation(this.entity, AnimationType.CURTSEY, 40);
            }
        }

        @Override
        public void resetTask() {
            super.resetTask();
            if (animCap.isAnimate()) {
                animCap.resetAnimation(entity);
            }
        }
    }
}
