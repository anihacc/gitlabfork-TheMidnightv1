package com.mushroom.midnight.common.world.rift;

import com.google.common.collect.Lists;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.block.DoubleMalignantFlowerBlock;
import com.mushroom.midnight.common.block.MossBlock;
import com.mushroom.midnight.common.config.MidnightConfig;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItems;
import com.mushroom.midnight.common.registry.MidnightSounds;
import com.mushroom.midnight.common.world.noise.INoiseSampler;
import com.mushroom.midnight.common.world.noise.PerlinNoiseSampler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.*;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class EntranceRiftGenerator {
    private static final INoiseSampler WARP_NOISE = new PerlinNoiseSampler(new Random(8555034668646880878L));
    private static final INoiseSampler SPIRE_WARP_NOISE = new PerlinNoiseSampler(new Random(8555034668646880878L));

    private static final int OUTER_RADIUS = 2;

    private static final double PORTAL_RADIUS = 3.0;

    private static final Direction[] VERTICAL_DIRECTIONS = {Direction.UP, Direction.DOWN};

    private static final Block[] VINES = {
            MidnightBlocks.MALIGNANT_RED_HANGING_VINES,
            MidnightBlocks.MALIGNANT_RED_BRIDGING_VINES,
    };

    private static final Block[] SHORT_FLOWERS = {
            MidnightBlocks.MALIGNANT_FOXGLOVE,
            MidnightBlocks.MALIGNANT_HEMLOCK,
            MidnightBlocks.MALIGNANT_MANDRAKE,
    };
    protected final IWorld world;

    public EntranceRiftGenerator(IWorld world) {
        this.world = world;
        WARP_NOISE.setFrequency(0.1);
        WARP_NOISE.setAmplitude(3.0);

        SPIRE_WARP_NOISE.setFrequency(0.5);
        SPIRE_WARP_NOISE.setAmplitude(0.9);
    }

    // TODO: Test code
    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        World world = event.getWorld();
        if (world.isRemote) return;

        event.getPlayer().swingArm(event.getHand());

        if (MidnightConfig.logic.riftsFromDarkPearls.get() && event.getItemStack().getItem() == MidnightItems.DARK_PEARL) {
            PlayerEntity player = event.getPlayer();

            Vec3d lookVec = player.getLook(1.0F);
            Vec3d origin = player.getEyePosition(1.0F);
            Vec3d target = origin.add(lookVec.scale(64.0));

            BlockRayTraceResult result = world.rayTraceBlocks(new RayTraceContext(origin, target, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
            if (result.getType() == RayTraceResult.Type.BLOCK) {
                new EntranceRiftGenerator(world).generate(result.getPos(), new Random());

                world.playSound(null, result.getPos(), MidnightSounds.EGG_CRACKED, SoundCategory.BLOCKS, 3.0F, 1.0F);
            }
        }
    }

    public void generate(BlockPos origin, Random random) {
        int craterRadius = random.nextInt(3) + 7;
        int radius = craterRadius + OUTER_RADIUS;

        CoverMask coverMask = new CoverMask(radius + 6);

        this.generateCrater(random, origin, craterRadius, radius, coverMask);
        this.generateTendrils(random, origin, radius, coverMask);
        this.generateSpires(random, origin, craterRadius + 1.5F);

        this.decorateRift(random, origin, radius + 4);
    }

    private void generateCrater(Random random, BlockPos origin, int craterRadius, int radius, CoverMask coverMask) {
        DistanceField distanceField = this.generateDistanceField(origin.getX(), origin.getZ(), radius + 2);
        int totalDepth = craterRadius / 2;

        BlockPos.Mutable mutablePos = new BlockPos.Mutable();

        for (int z = -radius; z <= radius; z++) {
            for (int x = -radius; x <= radius; x++) {
                mutablePos.setPos(x + origin.getX(), origin.getY(), z + origin.getZ());

                float distance = distanceField.get(x, z);
                if (distance > radius) continue;

                int depth = this.computeDepth(distance, craterRadius, totalDepth);

                while (mutablePos.getY() > origin.getY() - depth) {
                    this.carveBlock(mutablePos);
                    mutablePos.move(Direction.DOWN);
                }

                BlockState surfaceState = this.computeSurfaceState(distance, radius, random);

                mutablePos.setY(origin.getY() - depth);

                this.setBlockState(mutablePos, surfaceState);

                /*
                 * Fill the entrance rift with air. I'm so sorry that this code is atrocious, but so is the rest of
                 * the code in this god forsaken repository. Rewrite will save us.
                 */
                if (depth > 0)
                {
                    for (int y = mutablePos.getY() + 1; y <= origin.getY() + 7; y++) {
                        BlockPos blockPosAboveBlock = mutablePos.add(0, y - mutablePos.getY(), 0);
                        BlockState blockStateAboveBlock = world.getBlockState(blockPosAboveBlock);
                        if (blockStateAboveBlock != MidnightBlocks.RIFT_PORTAL.getDefaultState()
                                && blockStateAboveBlock != MidnightBlocks.GLOWING_MALIGNANT_RED_PLANT_BLOCK.getDefaultState()
                                && blockStateAboveBlock != MidnightBlocks.MALIGNANT_RED_PLANT_BLOCK.getDefaultState()
                                && blockStateAboveBlock != MidnightBlocks.MALIGNANT_BLOODROOT.getDefaultState()
                                && blockStateAboveBlock.getMaterial() != Material.AIR) {
//                            System.out.println("NON-RIFT BLOCK FOUND");
                            System.out.println(mutablePos.add(0, y - mutablePos.getY(), 0).toString());
                            System.out.println(blockStateAboveBlock.toString());
                            this.setBlockState(blockPosAboveBlock, Blocks.AIR.getDefaultState());
                        }/* else {
                            break;
                        }
                        ONLY ADD THIS IN IF WE NEED TO NOT PRODUCE AIR AFTER IT FINDS A MALIGNANT RED BLOCK!
                        */
                    }
                }

                coverMask.put(x, z);
            }
        }
    }

    private BlockState computeSurfaceState(double distance, double radius, Random random) {
        if (distance < PORTAL_RADIUS) {
            return MidnightBlocks.RIFT_PORTAL.getDefaultState();
        }

        double distanceFromEdge = radius - distance;
        double glowChance = distanceFromEdge / radius * 0.5;

        if (random.nextFloat() < glowChance) {
            return MidnightBlocks.GLOWING_MALIGNANT_RED_PLANT_BLOCK.getDefaultState();
        } else {
            return MidnightBlocks.MALIGNANT_RED_PLANT_BLOCK.getDefaultState();
        }
    }

    private DistanceField generateDistanceField(int originX, int originZ, int radius) {
        DistanceField field = new DistanceField(radius);

        for (int z = -radius; z <= radius; z++) {
            for (int x = -radius; x <= radius; x++) {
                int distanceSq = x * x + z * z;

                double warp = WARP_NOISE.get(originX + x, originZ + z);
                double distance = Math.sqrt(distanceSq) + warp;

                field.put(x, z, (float) distance);
            }
        }

        return field;
    }

    private int computeDepth(double distance, int craterRadius, int totalDepth) {
        int depth = MathHelper.ceil((1.0 - distance / craterRadius) * totalDepth);
        depth = MathHelper.clamp(depth, 0, totalDepth - 1);
        return depth;
    }

    private void generateTendrils(Random random, BlockPos origin, int radius, CoverMask coverMask) {
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();

        float angle = 0.0F;
        float angleStep = 15.0F;

        float distance = radius + 3.0F;

        while (angle < 360.0F - angleStep) {
            angle += angleStep;

            double angleRad = Math.toRadians(angle);
            int x = MathHelper.floor(Math.sin(angleRad) * distance);
            int z = MathHelper.floor(Math.cos(angleRad) * distance);

            x += random.nextInt(3) - random.nextInt(3);
            z += random.nextInt(3) - random.nextInt(3);

            while (!coverMask.has(x, z) && coverMask.contains(x, z)) {
                mutablePos.setPos(origin.getX() + x, origin.getY(), origin.getZ() + z);

                this.setBlockState(mutablePos, MidnightBlocks.MALIGNANT_RED_PLANT_BLOCK.getDefaultState());
                coverMask.put(x, z);

                Direction.Axis axis = this.chooseTendrilAxis(random, x, z);
                if (axis == Direction.Axis.X) {
                    x -= MathHelper.signum(x);
                } else {
                    z -= MathHelper.signum(z);
                }
            }
        }
    }

    private Direction.Axis chooseTendrilAxis(Random random, int x, int z) {
        if (x == 0) {
            return Direction.Axis.Z;
        } else if (z == 0) {
            return Direction.Axis.X;
        }
        return random.nextBoolean() ? Direction.Axis.X : Direction.Axis.Z;
    }

    private void generateSpires(Random random, BlockPos origin, float spireDistance) {
        float angle = random.nextFloat() * 90.0F;
        for (int i = 0; i < 4; i++) {
            this.generateSpire(random, origin, spireDistance, angle);
            angle += 90.0F;
        }
    }

    private void generateSpire(Random random, BlockPos origin, float spireDistance, float angle) {
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();

        double angleRad = Math.toRadians(angle);

        double stepX = Math.sin(angleRad);
        double stepZ = Math.cos(angleRad);

        double x = origin.getX() + stepX * spireDistance;
        double z = origin.getZ() + stepZ * spireDistance;
        int y = origin.getY();

        float radius = 3.0F + random.nextFloat() * 0.5F;

        while (radius >= 1.0F) {
            int maxRadius = MathHelper.ceil(radius);

            for (int layerZ = -maxRadius; layerZ <= maxRadius; layerZ++) {
                for (int layerX = -maxRadius; layerX <= maxRadius; layerX++) {
                    mutablePos.setPos(x + layerX, y, z + layerZ);

                    double distance = Math.sqrt(layerX * layerX + layerZ * layerZ);
                    distance += SPIRE_WARP_NOISE.get(mutablePos.getX(), mutablePos.getY(), mutablePos.getZ());

                    if (distance <= radius) {
                        this.setBlockState(mutablePos, MidnightBlocks.MALIGNANT_RED_PLANT_BLOCK.getDefaultState());
                    }
                }
            }

            x -= stepX * (1.5F / radius);
            z -= stepZ * (1.5F / radius);
            y++;

            radius *= 0.835F;
        }
    }

    private void decorateRift(Random random, BlockPos origin, int radius) {
        Decorator decorator = new Decorator(world, origin, random, radius);

        decorator.scatter(32, 16, pos -> {
            for (Direction direction : shuffledDirections(random)) {
                BlockState state = MidnightBlocks.MALIGNANT_RED_PLANT_SURFACE.getDefaultState().with(MossBlock.FACING, direction);
                if (state.isValidPosition(world, pos) && (!world.getBlockState(pos.down()).getBlock().equals(Blocks.VOID_AIR) || !world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK))) {
                    this.setBlockState(pos, state);
                    break;
                }
            }
        });

        decorator.scatter(24, 16, pos -> {
            for (Direction direction : shuffledDirections(random)) {
                Block block = SHORT_FLOWERS[random.nextInt(SHORT_FLOWERS.length)];
                BlockState state = block.getDefaultState();

                if (state.isValidPosition(world, pos)) {
                    this.setBlockState(pos, state);
                    break;
                }
            }
        });

        decorator.scatter(8, 6, pos -> {
            for (Direction direction : VERTICAL_DIRECTIONS) {
                BlockState state = MidnightBlocks.MALIGNANT_BLOODROOT.getDefaultState().with(DoubleMalignantFlowerBlock.FACING, direction);
                if (state.isValidPosition(world, pos) &&
                        /*(!world.getBlockState(pos.down()).getBlock().equals(Blocks.VOID_AIR) || !world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK))*/ world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
//                    System.out.println("Bloodroot dir: " + direction + ", block: " + world.getBlockState(pos).getBlock()
//                            + ", block below: " + world.getBlockState(pos.down()).getBlock());

                    DoubleMalignantFlowerBlock.placeAt(world, pos, state, Constants.BlockFlags.BLOCK_UPDATE);
                    break;
                }
            }
        });

        decorator.scatter(16, 16, pos -> {
            Block block = VINES[random.nextInt(VINES.length)];
            BlockState state = block.getDefaultState();

            if (state.isValidPosition(world, pos)) {
                this.setBlockState(pos, state);
            }
        });
    }

    private void carveBlock(BlockPos pos) {
        if (world.getBlockState(pos).getBlock() == Blocks.AIR) {
            return;
        }

        this.setBlockState(pos, Blocks.AIR.getDefaultState());
    }

    private void setBlockState(BlockPos pos, BlockState state) {
        if (world.getBlockState(pos).getBlock() == Blocks.BEDROCK) return;

        world.setBlockState(pos, state, Constants.BlockFlags.BLOCK_UPDATE | Constants.BlockFlags.NOTIFY_NEIGHBORS);
    }

    private static Collection<Direction> shuffledDirections(Random random) {
        List<Direction> directions = Lists.newArrayList(Direction.values());
        Collections.shuffle(directions, random);
        return directions;
    }

    private static class DistanceField extends CircleField {
        private final float[] field;

        DistanceField(int radius) {
            super(radius);
            this.field = new float[this.diameter * this.diameter];
        }

        void put(int x, int z, float distance) {
            if (this.contains(x, z)) {
                this.field[this.index(x, z)] = distance;
            }
        }

        float get(int x, int z) {
            if (!this.contains(x, z)) {
                return this.radius;
            }
            return this.field[this.index(x, z)];
        }
    }

    private static class CoverMask extends CircleField {
        private final BitSet bitSet;

        CoverMask(int radius) {
            super(radius);
            this.bitSet = new BitSet(this.diameter * this.diameter);
        }

        void put(int x, int z) {
            if (this.contains(x, z)) {
                this.bitSet.set(this.index(x, z));
            }
        }

        boolean has(int x, int z) {
            if (!this.contains(x, z)) {
                return false;
            }
            return this.bitSet.get(this.index(x, z));
        }
    }

    private static abstract class CircleField {
        final int radius;
        final int diameter;

        CircleField(int radius) {
            this.radius = radius;
            this.diameter = radius * 2 + 1;
        }

        public boolean contains(int x, int z) {
            return x >= -this.radius && z >= -this.radius && x <= this.radius && z <= this.radius;
        }

        int index(int x, int z) {
            return x + this.radius + (z + this.radius) * this.diameter;
        }
    }

    private static class Decorator {
        private final IWorld world;
        private final BlockPos origin;
        private final Random random;
        private final int radius;

        private final BlockPos.Mutable mutablePos = new BlockPos.Mutable();

        private Decorator(IWorld world, BlockPos origin, Random random, int radius) {
            this.world = world;
            this.origin = origin;
            this.random = random;
            this.radius = radius;
        }

        void scatter(int count, int size, Consumer<BlockPos> consumer) {
            for (int i = 0; i < count; i++) {
                int centerX = this.origin.getX() + this.random.nextInt(this.radius) - this.random.nextInt(this.radius);
                int centerY = this.origin.getY() + this.random.nextInt(5) - this.random.nextInt(3);
                int centerZ = this.origin.getZ() + this.random.nextInt(this.radius) - this.random.nextInt(this.radius);

                for (int j = 0; j < size; j++) {
                    this.mutablePos.setPos(
                            centerX + this.random.nextInt(4) - this.random.nextInt(4),
                            centerY + this.random.nextInt(3) - this.random.nextInt(3),
                            centerZ + this.random.nextInt(4) - this.random.nextInt(4)
                    );

                    if (this.world.isAirBlock(this.mutablePos)) {
                        consumer.accept(this.mutablePos);
                    }
                }
            }
        }
    }
}
