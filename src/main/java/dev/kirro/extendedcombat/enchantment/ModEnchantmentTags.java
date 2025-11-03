package dev.kirro.extendedcombat.enchantment;

import dev.kirro.ExtendedCombat;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.TagKey;

public interface ModEnchantmentTags extends EnchantmentTags {

    TagKey<Enchantment> COMBAT_EXCLUSIVE_SET = create("exclusive_set/combat");
    TagKey<Enchantment> DURABILITY_EXCLUSIVE_SET = create("exclusive_set/durability");
    TagKey<Enchantment> ELYTRA_EXCLUSIVE_SET = create("exclusive_set/elytra");
    TagKey<Enchantment> EXTENDEDCOMBAT_ENCHANTMENTS = create("extendedcombat_enchantments");

    private static TagKey<Enchantment> create(String id) {
        return TagKey.of(RegistryKeys.ENCHANTMENT, ExtendedCombat.id(id));
    }
}
