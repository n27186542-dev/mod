package com.example.oceanworld;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

/**
 * Strip mask along the X axis: 1.0 inside the strip, smoothly falling to 0.0 outside.
 * half_width = 50 gives a strip 101 blocks wide (Z from -50 to 50).
 * falloff = number of blocks over which the mask fades (coast / shallows).
 */
public record StripMask(int halfWidth, int falloff) implements DensityFunction.SimpleFunction {

    public static final MapCodec<StripMask> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.intRange(1, 100000).fieldOf("half_width").forGetter(StripMask::halfWidth),
            Codec.intRange(1, 100000).fieldOf("falloff").forGetter(StripMask::falloff)
    ).apply(i, StripMask::new));

    public static final KeyDispatchDataCodec<StripMask> KEY_CODEC = KeyDispatchDataCodec.of(CODEC);

    @Override
    public double compute(FunctionContext ctx) {
        double d = Math.abs(ctx.blockZ()) - halfWidth;
        if (d <= 0) return 1.0;
        double t = Math.max(0.0, 1.0 - d / falloff);
        return t * t * (3 - 2 * t);
    }

    @Override
    public double minValue() { return 0.0; }

    @Override
    public double maxValue() { return 1.0; }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() { return KEY_CODEC; }
}
