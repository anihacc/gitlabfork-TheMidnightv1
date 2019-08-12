package com.mushroom.midnight.common.world.feature.structure;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.world.template.CompiledTemplate;
import com.mushroom.midnight.common.world.template.RotatedSettingConfigurator;
import com.mushroom.midnight.common.world.template.TemplateCompiler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;
import java.util.function.Function;

public class WellStructure extends Feature<NoFeatureConfig> {
    protected static final ResourceLocation[] TEMPLATES = new ResourceLocation[] {
            new ResourceLocation(Midnight.MODID, "well"),
            new ResourceLocation(Midnight.MODID, "well_dead"),
            new ResourceLocation(Midnight.MODID, "well_shadowroot")
    };

    private TemplateCompiler templateCompiler;

    public WellStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos pos, NoFeatureConfig config) {
        if (!isMidnightGrassBlock(world, pos.down())) {
            return false;
        }

        if (this.templateCompiler == null) {
            this.templateCompiler = this.buildCompiler();
        }

        CompiledTemplate template = this.templateCompiler.compile(world, random, pos.down(10));
        template.addTo(world, random, 2 | 16);

        return true;
    }

    protected static boolean isMidnightGrassBlock(IWorldGenerationBaseReader world, BlockPos pos) {
        return world.hasBlockState(pos, state -> {
            Block block = state.getBlock();
            return block == MidnightBlocks.GRASS_BLOCK;
        });
    }

    protected TemplateCompiler buildCompiler() {
        return TemplateCompiler.of(TEMPLATES)
                .withSettingConfigurator(RotatedSettingConfigurator.INSTANCE)
                .withProcessor(this::processState);
    }

    protected Template.BlockInfo processState(IWorldReader world, BlockPos origin, Template.BlockInfo srcInfo, Template.BlockInfo info, PlacementSettings settings) {
        BlockState state = info.state;
        Block block = state.getBlock();
        return block == Blocks.STRUCTURE_BLOCK || block == Blocks.AIR ? null : info;
    }
}
