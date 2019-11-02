package com.mushroom.midnight.common.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;

public class ColorParticleData implements IParticleData {
    public static final IDeserializer<ColorParticleData> DESERIALIZER = new IDeserializer<ColorParticleData>() {
        @Override
        public ColorParticleData deserialize(ParticleType<ColorParticleData> particleType, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            int red = MathHelper.clamp(reader.readInt(), 0, 255);
            reader.expect(' ');
            int green = MathHelper.clamp(reader.readInt(), 0, 255);
            reader.expect(' ');
            int blue = MathHelper.clamp(reader.readInt(), 0, 255);
            int color = (red << 16) | (green << 8) | blue;
            return new ColorParticleData(particleType, color);
        }

        @Override
        public ColorParticleData read(ParticleType<ColorParticleData> particleType, PacketBuffer buf) {
            return new ColorParticleData(particleType, buf.readInt());
        }
    };

    private final ParticleType<ColorParticleData> particleType;
    public final int color;

    public ColorParticleData(ParticleType<ColorParticleData> particleType, int color) {
        this.particleType = particleType;
        this.color = color;
    }

    @Override
    public ParticleType<?> getType() {
        return this.particleType;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeInt(this.color);
    }

    @Override
    public String getParameters() {
        return String.format(Locale.ROOT, "%s %2d", ForgeRegistries.PARTICLE_TYPES.getKey(getType()), this.color);
    }
}
