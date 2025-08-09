package dev.kirro.extendedcombat.tags;

import dev.kirro.ExtendedCombat;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItemTags {
    public static final TagKey<Item> PERSISTENT_DURABILITY = TagKey.of(RegistryKeys.ITEM, Identifier.of(ExtendedCombat.MOD_ID, "persistent_durability"));
    public static final TagKey<Item> REPAIRABLE_ITEMS = TagKey.of(RegistryKeys.ITEM, Identifier.of(ExtendedCombat.MOD_ID, "repairable_items"));
}
