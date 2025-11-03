package dev.kirro.extendedcombat.entity.components;

import com.mojang.serialization.Codec;
import dev.kirro.ExtendedCombat;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModComponentTypes {
    public static final ComponentType<Boolean> AIR_MOBILITY = new ComponentType.Builder<Boolean>().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL).build();
    public static final ComponentType<Boolean> HIDDEN = new ComponentType.Builder<Boolean>().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL).build();

    public static void register() {
        Registry.register(Registries.DATA_COMPONENT_TYPE, ExtendedCombat.id("air_mobility"), AIR_MOBILITY);
        Registry.register(Registries.DATA_COMPONENT_TYPE, ExtendedCombat.id("hidden"), HIDDEN);
    }
}
