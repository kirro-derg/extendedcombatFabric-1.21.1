package dev.kirro.extendedcombat.item;

import com.mojang.serialization.Codec;
import dev.kirro.ExtendedCombat;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes extends DataComponentTypes {

    public static final ComponentType<Boolean> HIDDEN = register("hidden", builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL));

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, (builderOperator.apply(ComponentType.builder())).build());
    }

    public static void register() {
        ExtendedCombat.LOGGER.info("Registering Data Component Types for " + ExtendedCombat.MOD_ID);
    }
}
