package dev.kirro.extendedcombat.tags;

import dev.kirro.ExtendedCombat;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface ModItemTags {
    TagKey<Item> ALWAYS_HAS_DURABILITY = create("persistent_durability");
    TagKey<Item> REPAIRABLE_ITEMS = create("repairable_items");
    TagKey<Item> NETHER_STEEL_WEARABLES = create("nether_steel_wearables");
    TagKey<Item> NETHER_STEEL_TOOLS = create("nether_steel_tools");
    TagKey<Item> ECHO_STEEL_WEARABLES = create("echo_steel_wearables");
    TagKey<Item> ECHO_STEEL_ITEMS = create("echo_items");
    TagKey<Item> GREATSWORDS = create("greatswords");
    TagKey<Item> SLEEVED_ARMOR = create("sleeved_armor");
    TagKey<Item> CLOAK = create("cloak");
    TagKey<Item> MASK = create("mask");
    TagKey<Item> FLAME_RESISTANT_ARMOR = create("flame_resistant_armor");
    TagKey<Item> DASH_ENCHANTABLE = create("enchantable/dash");
    TagKey<Item> AIR_JUMP_ENCHANTABLE = create("enchantable/air_jump");
    TagKey<Item> BLINK_ENCHANTABLE = create("enchantable/blink");
    TagKey<Item> OBSCURITY_ENCHANTABLE = create("enchantable/obscurity");
    TagKey<Item> VANITY_ENCHANTABLE = create("enchantable/vanity");
    TagKey<Item> STEALTH_ENCHANTABLE = create("enchantable/stealth");
    TagKey<Item> KEEPSAKE_ENCHANTABLE = create("enchantable/keepsake");
    TagKey<Item> BURST_ENCHANTABLE = create("enchantable/burst");
    TagKey<Item> CONCUSSION_ENCHANTABLE = create("enchantable/concussion");
    TagKey<Item> FLUID_WALKER_ENCHANTABLE = create("enchantable/fluid_walker");
    TagKey<Item> SWIFTNESS_ENCHANTABLE = create("enchantable/swiftness");


    private static TagKey<Item> create(String id) {
        return TagKey.of(RegistryKeys.ITEM, ExtendedCombat.id(id));
    }
}
