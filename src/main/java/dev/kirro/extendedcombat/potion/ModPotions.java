package dev.kirro.extendedcombat.potion;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.effects.ModStatusEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModPotions {

    public static final RegistryEntry<Potion> SHRINKING_POTION_0 = register("shrinking_1.00",
        new Potion(new StatusEffectInstance(ModStatusEffects.SHRINKING, 20 * 60)));
    public static final RegistryEntry<Potion> SHRINKING_POTION_1 = register("shrinking_2.00",
            new Potion(new StatusEffectInstance(ModStatusEffects.SHRINKING, 20 * 120)));
    public static final RegistryEntry<Potion> SHRINKING_POTION_2 = register("shrinking_3.00",
            new Potion(new StatusEffectInstance(ModStatusEffects.SHRINKING, 20 * 180)));
    public static final RegistryEntry<Potion> SHRINKING_POTION_3 = register("shrinking_4.00",
            new Potion(new StatusEffectInstance(ModStatusEffects.SHRINKING, 20 * 240)));
    public static final RegistryEntry<Potion> PERMANENT_SHRINKING_POTION = register("permanent_shrinking",
        new Potion(new StatusEffectInstance(ModStatusEffects.SHRINKING, -1)));

    private static RegistryEntry<Potion> register(String id, Potion potion) {
        return Registry.registerReference(Registries.POTION, Identifier.of(ExtendedCombat.MOD_ID, id), potion);
    }

    public static void registerPotions() {
        ExtendedCombat.LOGGER.info("Registering potions for " + ExtendedCombat.MOD_ID);
    }
}
