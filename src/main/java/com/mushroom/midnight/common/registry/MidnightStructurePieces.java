package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.world.feature.structure.MoltenCraterStructure;
import com.mushroom.midnight.common.world.feature.structure.ShadowrootGuardTowerPieces;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public class MidnightStructurePieces {
    public static final IStructurePieceType MOLTEN_CRATER = register("molten_crater", MoltenCraterStructure.Piece::new);
    public static final IStructurePieceType SHADOWROOT_GUARDTOWER = register("shadowroot_guard_tower", ShadowrootGuardTowerPieces.Piece::new);

    private static IStructurePieceType register(String key, IStructurePieceType type) {
        return Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(Midnight.MODID, key), type);
    }
}
