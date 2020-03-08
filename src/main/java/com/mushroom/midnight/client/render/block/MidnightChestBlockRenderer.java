package com.mushroom.midnight.client.render.block;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.tile.MidnightChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import static com.mushroom.midnight.Midnight.MODID;

@OnlyIn(Dist.CLIENT)
public class MidnightChestBlockRenderer extends ChestTileEntityRenderer<MidnightChestTileEntity> {
    public static final Material TEXTURE_SHADOWROOT_DOUBLE = getChestMaterial(new ResourceLocation(MODID, "entities/chest/shadowroot_chest_double"));
    public static final Material TEXTURE_SHADOWROOT_NORMAL = getChestMaterial(new ResourceLocation(MODID, "entities/chest/shadowroot_chest"));
    public static final Material TEXTURE_DARK_WILLOW_DOUBLE = getChestMaterial(new ResourceLocation(MODID, "entities/chest/dark_willow_chest_double"));
    public static final Material TEXTURE_DARK_WILLOW_NORMAL = getChestMaterial(new ResourceLocation(MODID, "entities/chest/dark_willow_chest"));
    public static final Material TEXTURE_DEAD_WOOD_DOUBLE = getChestMaterial(new ResourceLocation(MODID, "entities/chest/dead_wood_chest_double"));
    public static final Material TEXTURE_DEAD_WOOD_NORMAL = getChestMaterial(new ResourceLocation(MODID, "entities/chest/dead_wood_chest"));
    public static final Material TEXTURE_NIGHTSHROOM_DOUBLE = getChestMaterial(new ResourceLocation(MODID, "entities/chest/nightshroom_chest_double"));
    public static final Material TEXTURE_NIGHTSHROOM_NORMAL = getChestMaterial(new ResourceLocation(MODID, "entities/chest/nightshroom_chest"));
    public static final Material TEXTURE_DEWSHROOM_DOUBLE = getChestMaterial(new ResourceLocation(MODID, "entities/chest/dewshroom_chest_double"));
    public static final Material TEXTURE_DEWSHROOM_NORMAL = getChestMaterial(new ResourceLocation(MODID, "entities/chest/dewshroom_chest"));
    public static final Material TEXTURE_VIRIDSHROOM_DOUBLE = getChestMaterial(new ResourceLocation(MODID, "entities/chest/viridshroom_chest_double"));
    public static final Material TEXTURE_VIRIDSHROOM_NORMAL = getChestMaterial(new ResourceLocation(MODID, "entities/chest/viridshroom_chest"));
    public static final Material TEXTURE_BOGSHROOM_DOUBLE = getChestMaterial(new ResourceLocation(MODID, "entities/chest/bogshroom_chest_double"));
    public static final Material TEXTURE_BOGSHROOM_NORMAL = getChestMaterial(new ResourceLocation(MODID, "entities/chest/bogshroom_chest"));

    public MidnightChestBlockRenderer(TileEntityRendererDispatcher p_i226008_1_) {
        super(p_i226008_1_);
    }

    public Material getMaterial(MidnightChestTileEntity chest, @Nonnull ChestType chestType) {
        Block chestModel = chest.getChestModel();
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


    private static Material getChestMaterial(ResourceLocation p_228774_0_) {
        return new Material(Atlases.CHEST_ATLAS, p_228774_0_);
    }

    private static Material getChestMaterial(ChestType p_228772_0_, Material p_228772_1_, Material p_228772_2_, Material p_228772_3_) {
        switch (p_228772_0_) {
            case LEFT:
                return p_228772_2_;
            case RIGHT:
                return p_228772_3_;
            case SINGLE:
            default:
                return p_228772_1_;
        }
    }
}
