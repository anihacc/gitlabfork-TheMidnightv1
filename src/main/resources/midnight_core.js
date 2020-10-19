var Opcodes = org.objectweb.asm.Opcodes;
var VarInsnNode = org.objectweb.asm.tree.VarInsnNode;
var FieldInsnNode = org.objectweb.asm.tree.FieldInsnNode;
var MethodInsnNode = org.objectweb.asm.tree.MethodInsnNode;
var JumpInsnNode = org.objectweb.asm.tree.JumpInsnNode;
var LabelNode = org.objectweb.asm.tree.LabelNode;
var InsnList = org.objectweb.asm.tree.InsnList;
var InsnNode = org.objectweb.asm.tree.InsnNode;

function initializeCoreMod() {
    return {
//        "LivingRendererTransformer": {
//            "target": {
//                "type": "CLASS",
//                "name": "net.minecraft.client.renderer.entity.LivingRenderer"
//            },
//            "transformer": patch_living_renderer
//        },
        "LadderNoises": {
            "target": {
                "type": "CLASS",
                "name": "net.minecraft.entity.Entity"
            },
            "transformer": patch_ladder_noises
        }
    }
}

// TODO: Re-implement this with rifter dragging behaviour
// Commented out now to solve some nonsense error message

//function patch_living_renderer(class_node) {
//    var api = Java.type('net.minecraftforge.coremod.api.ASMAPI');
//
//    // TODO: What is this? func_77043_a doesn't show up anywhere in the MCP Mapping Viewer, not even as an unmapped function.
//    var apply_rotations_method = get_method(class_node, api.mapMethod("func_77043_a"));
//
//    var instructions = apply_rotations_method.instructions;
//    for (var i = 0; i < instructions.size(); i++) {
//        var insn = instructions.get(i);
//        if (insn instanceof MethodInsnNode && insn.owner.equals("com/mojang/blaze3d/platform/GlStateManager") && insn.name.equals("rotatef")) {
//            var insert = new InsnList();
//            insert.add(new VarInsnNode(Opcodes.ALOAD, 1));
//            insert.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/mushroom/midnight/client/ClientEventHandler", "onApplyRotations", "(Lnet/minecraft/entity/LivingEntity;)V", false));
//            instructions.insert(insn, insert);
//            break;
//        }
//    }
//
//    return class_node;
//}

function patch_ladder_noises(class_node) {
    var api = Java.type('net.minecraftforge.coremod.api.ASMAPI');

    var move_method = get_method(class_node, api.mapMethod("func_213315_a")); // Entity.move(...)

    var ladder_field_name = api.mapField("field_150468_ap"); // Blocks.LADDER

    var target;
    var goTo;

    var instructions = move_method.instructions;
    for (var i = 0; i < instructions.size(); i++) {
        var insn = instructions.get(i);
        if (insn instanceof FieldInsnNode) {
            if (insn.getOpcode() == Opcodes.GETSTATIC && insn.name.equals(ladder_field_name)) {
                target = instructions.get( i - 1 );
                goTo = instructions.get( i + 1 );
            }
        }
    }


    if (goTo.getOpcode() != Opcodes.IF_ACMPEQ) {
        return;
    }

    var label = goTo.label;

    var insert = new InsnList();
    insert.add(new VarInsnNode(Opcodes.ALOAD, 5));
    insert.add(new VarInsnNode(Opcodes.ALOAD, 4));
    insert.add(new VarInsnNode(Opcodes.ALOAD, 0));
    insert.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/mushroom/midnight/common/asm/LadderNoiseModifier", "modifyLadderNoises", "(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;)Z", false));
    insert.add(new JumpInsnNode(Opcodes.IFNE, label));
    instructions.insertBefore(target, insert);

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
