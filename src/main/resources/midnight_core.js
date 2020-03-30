var Opcodes = org.objectweb.asm.Opcodes;
var VarInsnNode = org.objectweb.asm.tree.VarInsnNode;
var FieldInsnNode = org.objectweb.asm.tree.FieldInsnNode;
var MethodInsnNode = org.objectweb.asm.tree.MethodInsnNode;
var InsnList = org.objectweb.asm.tree.InsnList;

function initializeCoreMod() {
    return {
        "SoundSourceTransformer": {
            "target": {
                "type": "CLASS",
                "name": "net.minecraft.client.audio.SoundSource"
            },
            "transformer": patch_sound_source
        },
        "LivingRendererTransformer": {
            "target": {
                "type": "CLASS",
                "name": "net.minecraft.client.renderer.entity.LivingRenderer"
            },
            "transformer": patch_living_renderer
        },
        "BiomeColorsTransformer": {
            "target": {
                "type": "CLASS",
                "name": "net.minecraft.world.biome.BiomeColors"
            },
            "transformer": patch_biome_colors
        }
    }
}

function patch_sound_source(class_node) {
    var api = Java.type('net.minecraftforge.coremod.api.ASMAPI');

    var set_pos_method = get_method(class_node, api.mapMethod("func_216420_a"));

    var instructions = set_pos_method.instructions;
    for (var i = 0; i < instructions.size(); i++) {
        var insn = instructions.get(i);
        if (insn.getOpcode() == Opcodes.RETURN) {
            instructions.insertBefore(insn, new VarInsnNode(Opcodes.ALOAD, 0));
            instructions.insertBefore(insn, new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/audio/SoundSource", api.mapField("field_216441_b"), "I"));
            instructions.insertBefore(insn, new MethodInsnNode(Opcodes.INVOKESTATIC, "com/mushroom/midnight/client/SoundReverbHandler", "onPlaySound", "(I)V", false));
            break;
        }
    }

    return class_node;
}

function patch_living_renderer(class_node) {
    var api = Java.type('net.minecraftforge.coremod.api.ASMAPI');

    // TODO: What is this? func_77043_a doesn't show up anywhere in the MCP Mapping Viewer, not even as an unmapped function.
    var apply_rotations_method = get_method(class_node, api.mapMethod("func_77043_a"));

    var instructions = apply_rotations_method.instructions;
    for (var i = 0; i < instructions.size(); i++) {
        var insn = instructions.get(i);
        if (insn instanceof MethodInsnNode && insn.owner.equals("com/mojang/blaze3d/platform/GlStateManager") && insn.name.equals("rotatef")) {
            var insert = new InsnList();
            insert.add(new VarInsnNode(Opcodes.ALOAD, 1));
            insert.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/mushroom/midnight/client/ClientEventHandler", "onApplyRotations", "(Lnet/minecraft/entity/LivingEntity;)V", false));
            instructions.insert(insn, insert);
            break;
        }
    }

    return class_node;
}

function patch_biome_colors(class_node) {
    var api = Java.type('net.minecraftforge.coremod.api.ASMAPI');

    var get_color_method = get_method(class_node, api.mapMethod("func_217614_a"));

    var targets = [];

    var instructions = get_color_method.instructions;
    for (var i = 0; i < instructions.size(); i++) {
        var insn = instructions.get(i);
        if (insn.getOpcode() == Opcodes.IRETURN) {
            targets.push(insn);
        }
    }

    for (var i = 0; i < targets.length; i++) {
        var insert = new InsnList();
        insert.add(new VarInsnNode(Opcodes.ALOAD, 1));
        insert.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/mushroom/midnight/client/GrassColorModifier", "modifyGrassColor", "(ILnet/minecraft/util/math/BlockPos;)I", false));
        instructions.insertBefore(targets[i], insert);
    }

    return class_node;
}

function get_method(class_node, name) {
    for (var index in class_node.methods) {
        var method = class_node.methods[index];
        if (method.name.equals(name)) {
            return method;
        }
    }
    throw "couldn't find method with name '" + name + "' in '" + class_node.name + "'"
}
