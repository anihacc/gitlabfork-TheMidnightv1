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
        "BiomeColorsTransformer": {
            "target": {
                "type": "CLASS",
                "name": "net.minecraft.world.biome.BiomeColors"
            },
            "transformer": patch_biome_colors
        },
        "LadderNoises": {
            "target": {
                "type": "CLASS",
                "name": "net.minecraft.entity.Entity"
            },
            "transformer": patch_ladder_noises
        },
        "RecipeBook": {
            target: {
                type: "CLASS",
                name: "net.minecraft.client.util.ClientRecipeBook"
            },
            transformer: patch_client_recipe_book
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

function patch_biome_colors(class_node) {
    var api = Java.type('net.minecraftforge.coremod.api.ASMAPI');

    var get_color_method = get_method(class_node, api.mapMethod("func_228358_a_"));

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
    insert.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/mushroom/midnight/common/LadderNoiseModifier", "modifyLadderNoises", "(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;)Z", false));
    insert.add(new JumpInsnNode(Opcodes.IFNE, label));
    instructions.insertBefore(target, insert);

    return class_node;
}

function patch_client_recipe_book( node ) {
    for( var i in node.methods ) {
        var method = node.methods[ i ];
        if( method.name == "newRecipeList" && method.desc == "(Lnet/minecraft/client/util/RecipeBookCategories;)Lnet/minecraft/client/gui/recipebook/RecipeList;" ) {
            var allRecipesFieldName = method.name == "newRecipeList" ? "allRecipes" : "field_197932_f";
            var ctgryRecipesFieldName = method.name == "newRecipeList" ? "recipesByCategory" : "field_197931_e";

            var insn = method.instructions.get( 0 );
            var label = new LabelNode();
            method.instructions.insertBefore( insn, new VarInsnNode( Opcodes.ALOAD, 1 ) );
            method.instructions.insertBefore( insn, new VarInsnNode( Opcodes.ALOAD, 0 ) );
            method.instructions.insertBefore( insn, new FieldInsnNode( Opcodes.GETFIELD, "net/minecraft/client/util/ClientRecipeBook", "allRecipes", "Ljava/util/List;" ) );
            method.instructions.insertBefore( insn, new VarInsnNode( Opcodes.ALOAD, 0 ) );
            method.instructions.insertBefore( insn, new FieldInsnNode( Opcodes.GETFIELD, "net/minecraft/client/util/ClientRecipeBook", "recipesByCategory", "Ljava/util/Map;" ) );
            method.instructions.insertBefore( insn, new MethodInsnNode( Opcodes.INVOKESTATIC, "com/mushroom/midnight/client/RecipeHandler", "newRecipeList", "(Lnet/minecraft/client/util/RecipeBookCategories;Ljava/util/List;Ljava/util/Map;)Lnet/minecraft/client/gui/recipebook/RecipeList;", false ) );
            method.instructions.insertBefore( insn, new InsnNode( Opcodes.DUP ) );
            method.instructions.insertBefore( insn, new JumpInsnNode( Opcodes.IFNULL, label ) );
            method.instructions.insertBefore( insn, new InsnNode( Opcodes.ARETURN ) );
            method.instructions.insertBefore( insn, label )
            method.instructions.insertBefore( insn, new InsnNode( Opcodes.POP ) );
        }

        if( method.name == "func_202889_b" && method.desc == "(Lnet/minecraft/client/util/RecipeBookCategories;)Lnet/minecraft/client/gui/recipebook/RecipeList;" ) {
            var insn = method.instructions.get( 0 );
            var label = new LabelNode();
            method.instructions.insertBefore( insn, new VarInsnNode( Opcodes.ALOAD, 1 ) );
            method.instructions.insertBefore( insn, new VarInsnNode( Opcodes.ALOAD, 0 ) );
            method.instructions.insertBefore( insn, new FieldInsnNode( Opcodes.GETFIELD, "net/minecraft/client/util/ClientRecipeBook", "field_197932_f", "Ljava/util/List;" ) );
            method.instructions.insertBefore( insn, new VarInsnNode( Opcodes.ALOAD, 0 ) );
            method.instructions.insertBefore( insn, new FieldInsnNode( Opcodes.GETFIELD, "net/minecraft/client/util/ClientRecipeBook", "field_197931_e", "Ljava/util/Map;" ) );
            method.instructions.insertBefore( insn, new MethodInsnNode( Opcodes.INVOKESTATIC, "com/mushroom/midnight/client/RecipeHandler", "newRecipeList", "(Lnet/minecraft/client/util/RecipeBookCategories;Ljava/util/List;Ljava/util/Map;)Lnet/minecraft/client/gui/recipebook/RecipeList;", false ) );
            method.instructions.insertBefore( insn, new InsnNode( Opcodes.DUP ) );
            method.instructions.insertBefore( insn, new JumpInsnNode( Opcodes.IFNULL, label ) );
            method.instructions.insertBefore( insn, new InsnNode( Opcodes.ARETURN ) );
            method.instructions.insertBefore( insn, label )
            method.instructions.insertBefore( insn, new InsnNode( Opcodes.POP ) );
        }

        if( (method.name == "getCategory" || method.name == "func_202887_g") && method.desc == "(Lnet/minecraft/item/crafting/IRecipe;)Lnet/minecraft/client/util/RecipeBookCategories;" ) {
            var insn = method.instructions.get( 0 );
            var label = new LabelNode();
            method.instructions.insertBefore( insn, new VarInsnNode( Opcodes.ALOAD, 0 ) );
            method.instructions.insertBefore( insn, new MethodInsnNode( Opcodes.INVOKESTATIC, "com/mushroom/midnight/client/RecipeHandler", "getRecipeCategory", "(Lnet/minecraft/item/crafting/IRecipe;)Lnet/minecraft/client/util/RecipeBookCategories;", false ) );
            method.instructions.insertBefore( insn, new InsnNode( Opcodes.DUP ) );
            method.instructions.insertBefore( insn, new JumpInsnNode( Opcodes.IFNULL, label ) );
            method.instructions.insertBefore( insn, new InsnNode( Opcodes.ARETURN ) );
            method.instructions.insertBefore( insn, label )
            method.instructions.insertBefore( insn, new InsnNode( Opcodes.POP ) );
        }
    }
    return node;
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
