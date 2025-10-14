package dev.kirro.extendedcombat.effects;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.effects.custom.ConcussionStatusEffect;
import dev.kirro.extendedcombat.effects.custom.VulnerabilityStatusEffect;
import dev.kirro.extendedcombat.effects.custom.ShrinkingStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModStatusEffects{

    public static final RegistryEntry<StatusEffect> SHRINKING = register("shrinking", new ShrinkingStatusEffect(StatusEffectCategory.NEUTRAL, 15978425));
    public static final RegistryEntry<StatusEffect> VULNERABILITY = register("vulnerability", new VulnerabilityStatusEffect(StatusEffectCategory.HARMFUL, 4866583));
    public static final RegistryEntry<StatusEffect> CONCUSSION = register("concussion", new ConcussionStatusEffect(StatusEffectCategory.HARMFUL, 34573734));

    private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(ExtendedCombat.MOD_ID, id), statusEffect);
    }

    public static void registerStatusEffects() {
        ExtendedCombat.LOGGER.info("Registering status effects for " + ExtendedCombat.MOD_ID);
    }
}
