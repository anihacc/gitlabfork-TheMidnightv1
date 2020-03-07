package com.mushroom.midnight.common.registry;

import com.mojang.brigadier.arguments.BoolArgumentType;
import joptsimple.internal.Strings;
import net.minecraft.world.GameRules;

public class MidnightGameRules {
    private static class DefaultInitBooleanValue extends GameRules.BooleanValue {
        private DefaultInitBooleanValue(GameRules.RuleType<GameRules.BooleanValue> type, boolean value) {
            super(type, value);
        }

        public static GameRules.RuleType<GameRules.BooleanValue> create(boolean value) {
            return new GameRules.RuleType<>(BoolArgumentType::bool, type -> {
                return new DefaultInitBooleanValue(type, value);
            }, (s, v) -> { });
        }

        @Override
        protected void setStringValue(String value) {
            if (!Strings.isNullOrEmpty(value)) {
                super.setStringValue(value);
            }
        }
    }
}
