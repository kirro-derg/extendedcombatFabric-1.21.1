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

    public static final RegistryEntry<Potion> CONCUSSION = register("concussion",
        new Potion(new StatusEffectInstance(ModStatusEffects.CONCUSSION, 20 * 60)));

    private static RegistryEntry<Potion> register(String id, Potion potion) {
        return Registry.registerReference(Registries.POTION, Identifier.of(ExtendedCombat.MOD_ID, id), potion);
    }

    public static void registerPotions() {
        ExtendedCombat.LOGGER.info("Registering potions for " + ExtendedCombat.MOD_ID);
    }
}
