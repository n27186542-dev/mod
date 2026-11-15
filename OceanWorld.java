package com.example.oceanworld;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(OceanWorld.MODID)
public class OceanWorld {
    public static final String MODID = "oceanworld";

    public static final DeferredRegister<MapCodec<? extends DensityFunction>> DENSITY_TYPES =
            DeferredRegister.create(Registries.DENSITY_FUNCTION_TYPE, MODID);

    public static final DeferredHolder<MapCodec<? extends DensityFunction>, MapCodec<StripMask>> STRIP_MASK =
            DENSITY_TYPES.register("strip_mask", () -> StripMask.CODEC);

    public OceanWorld(IEventBus modEventBus) {
        DENSITY_TYPES.register(modEventBus);
    }
}
