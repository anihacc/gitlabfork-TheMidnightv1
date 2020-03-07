package com.mushroom.midnight.client.render.block;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.tile.MidnightChestTileEntity;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.mushroom.midnight.Midnight.MODID;

@OnlyIn(Dist.CLIENT)
public class MidnightChestBlockRenderer extends TileEntityRenderer<MidnightChestTileEntity> {
    public static final ResourceLocation TEXTURE_SHADOWROOT_DOUBLE = new ResourceLocation(MODID, "textures/entities/chest/shadowroot_chest_double.png");
    public static final ResourceLocation TEXTURE_SHADOWROOT_NORMAL = new ResourceLocation(MODID, "textures/entities/chest/shadowroot_chest.png");
    public static final ResourceLocation TEXTURE_DARK_WILLOW_DOUBLE = new ResourceLocation(MODID, "textures/entities/chest/dark_willow_chest_double.png");
    public static final ResourceLocation TEXTURE_DARK_WILLOW_NORMAL = new ResourceLocation(MODID, "textures/entities/chest/dark_willow_chest.png");
    public static final ResourceLocation TEXTURE_DEAD_WOOD_DOUBLE = new ResourceLocation(MODID, "textures/entities/chest/dead_wood_chest_double.png");
    public static final ResourceLocation TEXTURE_DEAD_WOOD_NORMAL = new ResourceLocation(MODID, "textures/entities/chest/dead_wood_chest.png");
    public static final ResourceLocation TEXTURE_NIGHTSHROOM_DOUBLE = new ResourceLocation(MODID, "textures/entities/chest/nightshroom_chest_double.png");
    public static final ResourceLocation TEXTURE_NIGHTSHROOM_NORMAL = new ResourceLocation(MODID, "textures/entities/chest/nightshroom_chest.png");
    public static final ResourceLocation TEXTURE_DEWSHROOM_DOUBLE = new ResourceLocation(MODID, "textures/entities/chest/dewshroom_chest_double.png");
    public static final ResourceLocation TEXTURE_DEWSHROOM_NORMAL = new ResourceLocation(MODID, "textures/entities/chest/dewshroom_chest.png");
    public static final ResourceLocation TEXTURE_VIRIDSHROOM_DOUBLE = new ResourceLocation(MODID, "textures/entities/chest/viridshroom_chest_double.png");
    public static final ResourceLocation TEXTURE_VIRIDSHROOM_NORMAL = new ResourceLocation(MODID, "textures/entities/chest/viridshroom_chest.png");
    public static final ResourceLocation TEXTURE_BOGSHROOM_DOUBLE = new ResourceLocation(MODID, "textures/entities/chest/bogshroom_chest_double.png");
    public static final ResourceLocation TEXTURE_BOGSHROOM_NORMAL = new ResourceLocation(MODID, "textures/entities/chest/bogshroom_chest.png");

    private final ModelRenderer field_228862_a_;
    private final ModelRenderer field_228863_c_;
    private final ModelRenderer field_228864_d_;
    private final ModelRenderer field_228865_e_;
    private final ModelRenderer field_228866_f_;
    private final ModelRenderer field_228867_g_;
    private final ModelRenderer field_228868_h_;
    private final ModelRenderer field_228869_i_;
    private final ModelRenderer field_228870_j_;

    public MidnightChestBlockRenderer(TileEntityRendererDispatcher p_i226008_1_) {
        super(p_i226008_1_);
        this.field_228863_c_ = new ModelRenderer(64, 64, 0, 19);
        this.field_228863_c_.addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);
        this.field_228862_a_ = new ModelRenderer(64, 64, 0, 0);
        this.field_228862_a_.addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
        this.field_228862_a_.rotationPointY = 9.0F;
        this.field_228862_a_.rotationPointZ = 1.0F;
        this.field_228864_d_ = new ModelRenderer(64, 64, 0, 0);
        this.field_228864_d_.addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
        this.field_228864_d_.rotationPointY = 8.0F;
        this.field_228866_f_ = new ModelRenderer(64, 64, 0, 19);
        this.field_228866_f_.addBox(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
        this.field_228865_e_ = new ModelRenderer(64, 64, 0, 0);
        this.field_228865_e_.addBox(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.field_228865_e_.rotationPointY = 9.0F;
        this.field_228865_e_.rotationPointZ = 1.0F;
        this.field_228867_g_ = new ModelRenderer(64, 64, 0, 0);
        this.field_228867_g_.addBox(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.field_228867_g_.rotationPointY = 8.0F;
        this.field_228869_i_ = new ModelRenderer(64, 64, 0, 19);
        this.field_228869_i_.addBox(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
        this.field_228868_h_ = new ModelRenderer(64, 64, 0, 0);
        this.field_228868_h_.addBox(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.field_228868_h_.rotationPointY = 9.0F;
        this.field_228868_h_.rotationPointZ = 1.0F;
        this.field_228870_j_ = new ModelRenderer(64, 64, 0, 0);
        this.field_228870_j_.addBox(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.field_228870_j_.rotationPointY = 8.0F;
    }

    private Material getMainMaterial(Block chestModel, ChestType chestType) {
        if (chestModel == MidnightBlocks.SHADOWROOT_CHEST) {
            return getChestMaterial(chestType, TEXTURE_SHADOWROOT_NORMAL, TEXTURE_SHADOWROOT_DOUBLE, TEXTURE_SHADOWROOT_DOUBLE);
        } else if (chestModel == MidnightBlocks.DARK_WILLOW_CHEST) {
            return getChestMaterial(chestType, TEXTURE_DARK_WILLOW_NORMAL, TEXTURE_DARK_WILLOW_DOUBLE, TEXTURE_DARK_WILLOW_DOUBLE);
        } else if (chestModel == MidnightBlocks.DEAD_WOOD_CHEST) {
            return getChestMaterial(chestType, TEXTURE_DEAD_WOOD_NORMAL, TEXTURE_DEAD_WOOD_DOUBLE, TEXTURE_DEAD_WOOD_DOUBLE);
        } else if (chestModel == MidnightBlocks.NIGHTSHROOM_CHEST) {
            return getChestMaterial(chestType, TEXTURE_NIGHTSHROOM_NORMAL, TEXTURE_NIGHTSHROOM_DOUBLE, TEXTURE_NIGHTSHROOM_DOUBLE);
        } else if (chestModel == MidnightBlocks.DEWSHROOM_CHEST) {
            return getChestMaterial(chestType, TEXTURE_DEWSHROOM_NORMAL, TEXTURE_DEWSHROOM_DOUBLE, TEXTURE_DEWSHROOM_DOUBLE);
        } else if (chestModel == MidnightBlocks.VIRIDSHROOM_CHEST) {
            return getChestMaterial(chestType, TEXTURE_VIRIDSHROOM_NORMAL, TEXTURE_VIRIDSHROOM_DOUBLE, TEXTURE_VIRIDSHROOM_DOUBLE);
        } else if (chestModel == MidnightBlocks.BOGSHROOM_CHEST) {
            return getChestMaterial(chestType, TEXTURE_BOGSHROOM_NORMAL, TEXTURE_BOGSHROOM_DOUBLE, TEXTURE_BOGSHROOM_DOUBLE);
        } else {
            return getChestMaterial(chestType, TEXTURE_SHADOWROOT_NORMAL, TEXTURE_SHADOWROOT_DOUBLE, TEXTURE_SHADOWROOT_DOUBLE);
        }
    }

    @Override
    public void render(MidnightChestTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        World world = tileEntityIn.getWorld();
        boolean flag = world != null;
        BlockState blockstate = flag ? tileEntityIn.getBlockState() : MidnightBlocks.BOGSHROOM_CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        ChestType chesttype = blockstate.has(ChestBlock.TYPE) ? blockstate.get(ChestBlock.TYPE) : ChestType.SINGLE;
        Block block = blockstate.getBlock();
        if (block instanceof AbstractChestBlock) {
            AbstractChestBlock<?> abstractchestblock = (AbstractChestBlock) block;
            boolean flag1 = chesttype != ChestType.SINGLE;
            matrixStackIn.push();
            float f = blockstate.get(ChestBlock.FACING).getHorizontalAngle();
            matrixStackIn.translate(0.5D, 0.5D, 0.5D);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-f));
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            TileEntityMerger.ICallbackWrapper<? extends ChestTileEntity> icallbackwrapper;
            if (flag) {
                icallbackwrapper = abstractchestblock.func_225536_a_(blockstate, world, tileEntityIn.getPos(), true);
            } else {
                icallbackwrapper = TileEntityMerger.ICallback::func_225537_b_;
            }

            float f1 = icallbackwrapper.apply(ChestBlock.func_226917_a_((IChestLid) tileEntityIn)).get(partialTicks);
            f1 = 1.0F - f1;
            f1 = 1.0F - f1 * f1 * f1;
            int i = icallbackwrapper.apply(new DualBrightnessCallback<>()).applyAsInt(combinedLightIn);
            Material material = getMainMaterial(block, chesttype);
            IVertexBuilder ivertexbuilder = material.getBuffer(bufferIn, RenderType::entityCutout);
            if (flag1) {
                if (chesttype == ChestType.LEFT) {
                    this.func_228871_a_(matrixStackIn, ivertexbuilder, this.field_228868_h_, this.field_228870_j_, this.field_228869_i_, f1, i, combinedOverlayIn);
                } else {
                    this.func_228871_a_(matrixStackIn, ivertexbuilder, this.field_228865_e_, this.field_228867_g_, this.field_228866_f_, f1, i, combinedOverlayIn);
                }
            } else {
                this.func_228871_a_(matrixStackIn, ivertexbuilder, this.field_228862_a_, this.field_228864_d_, this.field_228863_c_, f1, i, combinedOverlayIn);
            }

            matrixStackIn.pop();
        }
    }

    private void func_228871_a_(MatrixStack p_228871_1_, IVertexBuilder p_228871_2_, ModelRenderer p_228871_3_, ModelRenderer p_228871_4_, ModelRenderer p_228871_5_, float p_228871_6_, int p_228871_7_, int p_228871_8_) {
        p_228871_3_.rotateAngleX = -(p_228871_6_ * ((float) Math.PI / 2F));
        p_228871_4_.rotateAngleX = p_228871_3_.rotateAngleX;
        p_228871_3_.render(p_228871_1_, p_228871_2_, p_228871_7_, p_228871_8_);
        p_228871_4_.render(p_228871_1_, p_228871_2_, p_228871_7_, p_228871_8_);
        p_228871_5_.render(p_228871_1_, p_228871_2_, p_228871_7_, p_228871_8_);
    }


    private static Material getChestMaterial(ResourceLocation p_228774_0_) {
        return new Material(Atlases.CHEST_ATLAS, p_228774_0_);
    }

    private static Material getChestMaterial(ChestType p_228772_0_, ResourceLocation p_228772_1_, ResourceLocation p_228772_2_, ResourceLocation p_228772_3_) {
        switch (p_228772_0_) {
            case LEFT:
                return getChestMaterial(p_228772_2_);
            case RIGHT:
                return getChestMaterial(p_228772_3_);
            case SINGLE:
            default:
                return getChestMaterial(p_228772_1_);
        }
    }
}
