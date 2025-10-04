package dev.kirro.extendedcombat.tags;

import dev.kirro.ExtendedCombat;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface ModItemTags {
    TagKey<Item> ALWAYS_HAS_DURABILITY = create("persistent_durability");
    TagKey<Item> REPAIRABLE_ITEMS = create("repairable_items");
    TagKey<Item> KEEPSAKE_ENCHANTABLE = create("enchantable/keepsake");
    TagKey<Item> NETHER_STEEL_WEARABLES = create("nether_steel_wearables");
    TagKey<Item> NETHER_STEEL_TOOLS = create("nether_steel_tools");
    TagKey<Item> ECHO_STEEL_WEARABLES = create("echo_steel_wearables");
    TagKey<Item> ECHO_STEEL_ITEMS = create("echo_items");
    TagKey<Item> GREATSWORDS = create("greatswords");
    TagKey<Item> SLEEVED_ARMOR = create("sleeved_armor");
    TagKey<Item> ELYTRA_ENCHANTABLE = create("enchantable/elytra");
    TagKey<Item> FLUID_WALKER_ENCHANTABLE = create("enchantable/fluid_walker");
    TagKey<Item> FLAME_RESISTANT_ARMOR = create("flame_resistant_armor");

    private static TagKey<Item> create(String id) {
        return TagKey.of(RegistryKeys.ITEM, ExtendedCombat.id(id));
    }
}
