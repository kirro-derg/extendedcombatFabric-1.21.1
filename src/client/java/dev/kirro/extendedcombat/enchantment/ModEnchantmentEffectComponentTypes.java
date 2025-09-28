package dev.kirro.extendedcombat.enchantment;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.enchantment.custom.*;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class ModEnchantmentEffectComponentTypes {
    public static final ComponentType<AirJumpEnchantmentEffect> AIR_JUMP = register("air_jump", builder -> builder.codec(AirJumpEnchantmentEffect.CODEC));
    public static final ComponentType<DashEnchantmentEffect> DASH = register("dash", builder -> builder.codec(DashEnchantmentEffect.CODEC));
    public static final ComponentType<BurstEnchantmentEffect> BURST = register("burst", builder -> builder.codec(BurstEnchantmentEffect.CODEC));
    public static final ComponentType<BlinkEnchantmentEffect> BLINK = register("blink", builder -> builder.codec(BlinkEnchantmentEffect.CODEC));
    public static final ComponentType<ObscurityEnchantmentEffect> OBSCURITY = register("obscurity", builder -> builder.codec(ObscurityEnchantmentEffect.CODEC));
    public static final ComponentType<VanityEnchantmentEffect> VANITY = register("vanity", builder -> builder.codec(VanityEnchantmentEffect.CODEC));
    public static final ComponentType<StealthEnchantmentEffect> STEALTH = register("stealth", builder -> builder.codec(StealthEnchantmentEffect.CODEC));
    public static final ComponentType<KeepsakeEnchantmentEffect> KEEPSAKE = register("keepsake", builder -> builder.codec(KeepsakeEnchantmentEffect.CODEC));
    public static final ComponentType<ConcussionEnchantmentEffect> CONCUSSION = register("concussion", builder -> builder.codec(ConcussionEnchantmentEffect.CODEC));
    public static final ComponentType<FluidWalkerEnchantmentEffect> FLUID_WALKER = register("fluid_walker", builder -> builder.codec(FluidWalkerEnchantmentEffect.CODEC));

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, Identifier.of(ExtendedCombat.MOD_ID, id), builderOperator.apply(ComponentType.builder()).build());
    }

    public static void register() {

    }
}
