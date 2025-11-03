package dev.kirro.extendedcombat.item.custom;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.item.ModItems;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static final RegistryEntry<ArmorMaterial> NETHER_STEEL = registerArmorMaterials("nether_steel",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 5);
                map.put(ArmorItem.Type.LEGGINGS, 8);
                map.put(ArmorItem.Type.CHESTPLATE, 10);
                map.put(ArmorItem.Type.HELMET, 5);
                map.put(ArmorItem.Type.BODY, 13);
            }), 44, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT),
                    List.of(new ArmorMaterial.Layer(Identifier.of(ExtendedCombat.MOD_ID, "nether_steel"))), 4, 0.5f));

    public static final RegistryEntry<ArmorMaterial> ECHO_STEEL = registerArmorMaterials("echo_steel",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 5);
                map.put(ArmorItem.Type.LEGGINGS, 8);
                map.put(ArmorItem.Type.CHESTPLATE, 12);
                map.put(ArmorItem.Type.HELMET, 5);
                map.put(ArmorItem.Type.BODY, 13);
            }), 44, SoundEvents.ITEM_ARMOR_EQUIP_WOLF, () -> Ingredient.ofItems(ModItems.NETHER_STEEL_INGOT),
                    List.of(new ArmorMaterial.Layer(Identifier.of(ExtendedCombat.MOD_ID, "echo_steel"))), 5, 0.7f));

    public static final RegistryEntry<ArmorMaterial> WOOL = registerArmorMaterials("wool",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 3);
                map.put(ArmorItem.Type.LEGGINGS, 6);
                map.put(ArmorItem.Type.CHESTPLATE, 8);
                map.put(ArmorItem.Type.HELMET, 3);
                map.put(ArmorItem.Type.BODY, 11);
            }), 25, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> Ingredient.fromTag(ItemTags.WOOL),
                    List.of(new ArmorMaterial.Layer(ExtendedCombat.id("wool"))), 3.0f, 0.1f));

    public static RegistryEntry<ArmorMaterial> registerArmorMaterials(String name, Supplier<ArmorMaterial> material) {
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(ExtendedCombat.MOD_ID, name), material.get());
    }
}
