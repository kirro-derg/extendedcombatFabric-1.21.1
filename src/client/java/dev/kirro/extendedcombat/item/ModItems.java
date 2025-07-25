package dev.kirro.extendedcombat.item;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.item.custom.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModItems {
    public static Item NETHER_STEEL_INGOT = registerItem("nether_steel_ingot", new Item(new Item.Settings().fireproof()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.extendedcombat.nether_steel_ingot"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });
    public static final Item HANDLE = registerItem("handle", new Item(new Item.Settings().fireproof()));
    public static final Item NETHER_STEEL_UPGRADE = registerItem("nether_steel_upgrade", new Item(new Item.Settings().fireproof()));
    public static final Item STATUE = registerItem("statue", new StatueItem(new Item.Settings()));

    public static final Item NETHER_STEEL_GREATSWORD = registerItem("nether_steel_greatsword",
            new PickSwordItem(ModToolMaterials.NETHER_STEEL, new Item.Settings().fireproof()
                    .attributeModifiers(PickSwordItem.createAttributeModifiers(ModToolMaterials.NETHER_STEEL, 7, -2.4f))));
    public static final Item NETHER_STEEL_PICKAXE = registerItem("nether_steel_pickaxe",
            new ModPickaxeItem(ModToolMaterials.NETHER_STEEL,  new Item.Settings().fireproof()
                    .attributeModifiers(ModPickaxeItem.createAttributeModifiers(ModToolMaterials.NETHER_STEEL, 1, -2.8f))));


    public static final Item NETHER_STEEL_HELMET = registerItem("nether_steel_helmet",
            new ModArmorItem(ModArmorMaterials.NETHER_STEEL, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(8124).fireproof()));
    public static final Item NETHER_STEEL_CHESTPLATE = registerItem("nether_steel_chestplate",
            new ModArmorItem(ModArmorMaterials.NETHER_STEEL, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(8124).fireproof()));
    public static final Item NETHER_STEEL_LEGGINGS = registerItem("nether_steel_leggings",
            new ModArmorItem(ModArmorMaterials.NETHER_STEEL, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(8124).fireproof()));
    public static final Item NETHER_STEEL_BOOTS = registerItem("nether_steel_boots",
            new ModArmorItem(ModArmorMaterials.NETHER_STEEL, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(8124).fireproof()
                    .attributeModifiers(ModArmorItem.createAttributeModifiers(ModArmorMaterials.NETHER_STEEL.value(), -0.7f))));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(ExtendedCombat.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ExtendedCombat.LOGGER.info("Registering items for" + ExtendedCombat.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(NETHER_STEEL_INGOT);
            fabricItemGroupEntries.add(HANDLE);
            fabricItemGroupEntries.add(NETHER_STEEL_UPGRADE);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(NETHER_STEEL_GREATSWORD);
            fabricItemGroupEntries.add(NETHER_STEEL_HELMET);
            fabricItemGroupEntries.add(NETHER_STEEL_CHESTPLATE);
            fabricItemGroupEntries.add(NETHER_STEEL_LEGGINGS);
            fabricItemGroupEntries.add(NETHER_STEEL_BOOTS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(NETHER_STEEL_PICKAXE);
        });
    }
}
