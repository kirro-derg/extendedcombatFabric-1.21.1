package dev.kirro.extendedcombat.tags;

import dev.kirro.ExtendedCombat;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface ModItemTags {
    TagKey<Item> PERSISTENT_DURABILITY = create("persistent_durability");
    TagKey<Item> REPAIRABLE_ITEMS = create("repairable_items");
    TagKey<Item> KEEPSAKE_ENCHANTABLE = create("enchantable/keepsake");
    TagKey<Item> NETHER_STEEL_WEARABLES = create("nether_steel_wearables");
    TagKey<Item> NETHER_STEEL_TOOLS = create("nether_steel_tools");
    TagKey<Item> ECHO_WEARABLES = create("echo_wearables");
    TagKey<Item> ECHO_ITEMS = create("echo_items");
    TagKey<Item> GREATSWORDS = create("greatswords");
    TagKey<Item> SLEEVED_ARMOR = create("sleeved_armor");
    TagKey<Item> ELYTRA_ENCHANTABLE = create("enchantable/elytra");

    private static TagKey<Item> create(String id) {
        return TagKey.of(RegistryKeys.ITEM, ExtendedCombat.id(id));
    }
}
