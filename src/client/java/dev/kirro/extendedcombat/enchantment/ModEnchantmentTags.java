package dev.kirro.extendedcombat.enchantment;

import dev.kirro.ExtendedCombat;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public interface ModEnchantmentTags extends EnchantmentTags {

    TagKey<Enchantment> COMBAT_EXCLUSIVE_SET = of("exclusive_set/combat");

    private static TagKey<Enchantment> of(String id) {
        return TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.ofVanilla(id));
    }
}
