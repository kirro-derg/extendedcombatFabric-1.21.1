package dev.kirro.extendedcombat.tags;

import dev.kirro.ExtendedCombat;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItemTags {
    public static final TagKey<Item> PERSISTENT_DURABILITY = TagKey.of(RegistryKeys.ITEM, Identifier.of(ExtendedCombat.MOD_ID, "persistent_durability"));
    public static final TagKey<Item> REPAIRABLE_ITEMS = TagKey.of(RegistryKeys.ITEM, Identifier.of(ExtendedCombat.MOD_ID, "repairable_items"));
    public static final TagKey<Item> KEEPSAKE_ENCHANTABLE = TagKey.of(RegistryKeys.ITEM, Identifier.of(ExtendedCombat.MOD_ID, "enchantable/keepsake"));
    public static final TagKey<Item> NETHER_STEEL_ARMOR = TagKey.of(RegistryKeys.ITEM, Identifier.of(ExtendedCombat.MOD_ID, "nether_steel_armor"));
    public static final TagKey<Item> ECHO_ARMOR = TagKey.of(RegistryKeys.ITEM, Identifier.of(ExtendedCombat.MOD_ID, "echo_armor"));
}
