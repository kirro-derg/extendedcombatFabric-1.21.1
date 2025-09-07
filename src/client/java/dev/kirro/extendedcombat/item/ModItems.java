package dev.kirro.extendedcombat.item;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.item.custom.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.render.VertexFormatElement;
import net.minecraft.component.ComponentType;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.List;

public class ModItems {
    public static Item NETHER_STEEL_INGOT = registerItem("nether_steel_ingot", new Item(new Item.Settings().fireproof()));
    public static Item ECHO_STEEL_INGOT = registerItem("echo_steel_ingot", new Item(new Item.Settings().fireproof()));
    public static final Item HANDLE = registerItem("handle", new Item(new Item.Settings().fireproof()));
    public static final Item NETHER_STEEL_UPGRADE = registerItem("nether_steel_upgrade", new Item(new Item.Settings().fireproof()));
    public static final Item ECHO_STEEL_UPGRADE = registerItem("echo_steel_upgrade", new Item(new Item.Settings().fireproof()));
    public static final Item STATUE = registerItem("statue", new StatueItem(new Item.Settings()));

    public static final Item NETHER_STEEL_GREATSWORD = registerItem("nether_steel_greatsword",
            new PickSwordItem(ModToolMaterials.NETHER_STEEL, new Item.Settings().fireproof()
                    .attributeModifiers(PickSwordItem.createAttributeModifiers(ModToolMaterials.NETHER_STEEL, 7, -2.7f))));
    public static final Item NETHER_STEEL_PICKAXE = registerItem("nether_steel_pickaxe",
            new ModPickaxeItem(ModToolMaterials.NETHER_STEEL,  new Item.Settings().fireproof()
                    .attributeModifiers(ModPickaxeItem.createAttributeModifiers(ModToolMaterials.NETHER_STEEL, 1, -2.8f))));

    public static final Item WOODEN_HAMMER = registerItem("wooden_hammer",
            new HammerItem(ToolMaterials.WOOD, new Item.Settings()
                    .attributeModifiers(MiningToolItem.createAttributeModifiers(ToolMaterials.WOOD, 1f, -3.8f))));
    public static final Item STONE_HAMMER = registerItem("stone_hammer",
            new HammerItem(ToolMaterials.STONE, new Item.Settings()
                    .attributeModifiers(MiningToolItem.createAttributeModifiers(ToolMaterials.STONE, 1f, -3.8f))));
    public static final Item IRON_HAMMER = registerItem("iron_hammer",
            new HammerItem(ToolMaterials.IRON, new Item.Settings()
                    .attributeModifiers(MiningToolItem.createAttributeModifiers(ToolMaterials.IRON, 1f, -3.8f))));
    public static final Item GOLDEN_HAMMER = registerItem("golden_hammer",
            new HammerItem(ToolMaterials.GOLD, new Item.Settings()
                    .attributeModifiers(MiningToolItem.createAttributeModifiers(ToolMaterials.GOLD, 1f, -3.8f))));
    public static final Item DIAMOND_HAMMER = registerItem("diamond_hammer",
            new HammerItem(ToolMaterials.DIAMOND, new Item.Settings()
                    .attributeModifiers(MiningToolItem.createAttributeModifiers(ToolMaterials.DIAMOND, 1f, -3.8f))));
    public static final Item NETHERITE_HAMMER = registerItem("netherite_hammer",
            new HammerItem(ToolMaterials.NETHERITE, new Item.Settings().fireproof()
                    .attributeModifiers(MiningToolItem.createAttributeModifiers(ToolMaterials.NETHERITE, 1f, -3.8f))));
    public static final Item NETHER_STEEL_HAMMER = registerItem("nether_steel_hammer",
            new HammerItem(ModToolMaterials.NETHER_STEEL,  new Item.Settings().fireproof()
                    .attributeModifiers(MiningToolItem.createAttributeModifiers(ModToolMaterials.NETHER_STEEL, 4, -3.8f))));
    public static final Item ECHO_STEEL_HAMMER = registerItem("echo_steel_hammer",
            new HammerItem(ModToolMaterials.ECHO_STEEL, new Item.Settings().fireproof()
                    .attributeModifiers(MiningToolItem.createAttributeModifiers(ModToolMaterials.ECHO_STEEL, 4f, -3.8f))));


    public static final Item NETHER_STEEL_HELMET = registerItem("nether_steel_helmet",
            new NetherSteelArmorItem(ModArmorMaterials.NETHER_STEEL, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(8124).fireproof()));
    public static final Item NETHER_STEEL_CHESTPLATE = registerItem("nether_steel_chestplate",
            new NetherSteelArmorItem(ModArmorMaterials.NETHER_STEEL, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(8124).fireproof()
                    .attributeModifiers(NetherSteelArmorItem.createAttributeModifiers(ModArmorMaterials.NETHER_STEEL.value(), -0.0f, -1.0f, 9, 3, -0.5f))));
    public static final Item NETHER_STEEL_LEGGINGS = registerItem("nether_steel_leggings",
            new NetherSteelArmorItem(ModArmorMaterials.NETHER_STEEL, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(8124).fireproof()));
    public static final Item NETHER_STEEL_BOOTS = registerItem("nether_steel_boots",
            new NetherSteelArmorItem(ModArmorMaterials.NETHER_STEEL, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(8124).fireproof()
                    .attributeModifiers(NetherSteelArmorItem.createAttributeModifiers(ModArmorMaterials.NETHER_STEEL.value(), -0.3f, -0.0f, 4, 3, -0.5f))));

    public static final Item ECHO_STEEL_HELMET = registerItem("echo_steel_helmet",
            new EchoSteelArmorItem(ModArmorMaterials.ECHO_STEEL, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(9124).fireproof()));
    public static final Item ECHO_STEEL_CHESTPLATE = registerItem("echo_steel_chestplate",
            new EchoSteelArmorItem(ModArmorMaterials.ECHO_STEEL, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(9124).fireproof()));
    public static final Item ECHO_STEEL_LEGGINGS = registerItem("echo_steel_leggings",
            new EchoSteelArmorItem(ModArmorMaterials.ECHO_STEEL, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(9124).fireproof()));
    public static final Item ECHO_STEEL_BOOTS = registerItem("echo_steel_boots",
            new EchoSteelArmorItem(ModArmorMaterials.ECHO_STEEL, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(9124).fireproof()));

    public static final Item ECHO_REINFORCED_ELYTRA = registerItem("echo_reinforced_elytra",
            new ModElytra(new Item.Settings().maxDamage(864).rarity(Rarity.RARE)));

    public static final Item BLACK_APPLE = registerItem("black_apple",
            new Item(new Item.Settings().rarity(Rarity.UNCOMMON).food(ModFoodComponents.BLACK_APPLE)));
    public static final Item GOLDEN_STEAK = registerItem("golden_steak",
            new Item(new Item.Settings().rarity(Rarity.RARE).food(ModFoodComponents.GOLDEN_STEAK)));
    public static final Item HONEY_BREAD = registerItem("honey_bread",
            new Item(new Item.Settings().food(ModFoodComponents.HONEY_BREAD)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(ExtendedCombat.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ExtendedCombat.LOGGER.info("Registering items for" + ExtendedCombat.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.addAfter(Items.NETHERITE_INGOT, NETHER_STEEL_INGOT);
            fabricItemGroupEntries.addAfter(NETHER_STEEL_INGOT, ECHO_STEEL_INGOT);
            fabricItemGroupEntries.addAfter(Items.STICK, HANDLE);
            fabricItemGroupEntries.addAfter(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, NETHER_STEEL_UPGRADE);
            fabricItemGroupEntries.addAfter(NETHER_STEEL_UPGRADE, ECHO_STEEL_UPGRADE);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.addAfter(Items.NETHERITE_SWORD, NETHER_STEEL_GREATSWORD);
            fabricItemGroupEntries.addAfter(Items.NETHERITE_BOOTS, NETHER_STEEL_HELMET);
            fabricItemGroupEntries.addAfter(NETHER_STEEL_HELMET, NETHER_STEEL_CHESTPLATE);
            fabricItemGroupEntries.addAfter(NETHER_STEEL_CHESTPLATE, NETHER_STEEL_LEGGINGS);
            fabricItemGroupEntries.addAfter(NETHER_STEEL_LEGGINGS, NETHER_STEEL_BOOTS);
            fabricItemGroupEntries.addAfter(NETHER_STEEL_BOOTS, ECHO_STEEL_HELMET);
            fabricItemGroupEntries.addAfter(ECHO_STEEL_HELMET, ECHO_STEEL_CHESTPLATE);
            fabricItemGroupEntries.addAfter(ECHO_STEEL_CHESTPLATE, ECHO_STEEL_LEGGINGS);
            fabricItemGroupEntries.addAfter(ECHO_STEEL_LEGGINGS, ECHO_STEEL_BOOTS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.addAfter(Items.ENCHANTED_GOLDEN_APPLE, BLACK_APPLE);
            fabricItemGroupEntries.addAfter(Items.ENCHANTED_GOLDEN_APPLE, GOLDEN_STEAK);
            fabricItemGroupEntries.addAfter(Items.BREAD, HONEY_BREAD);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.addAfter(Items.WOODEN_HOE, WOODEN_HAMMER);
            fabricItemGroupEntries.addAfter(Items.STONE_HOE, STONE_HAMMER);
            fabricItemGroupEntries.addAfter(Items.IRON_HOE, IRON_HAMMER);
            fabricItemGroupEntries.addAfter(Items.GOLDEN_HOE, GOLDEN_HAMMER);
            fabricItemGroupEntries.addAfter(Items.DIAMOND_HOE, DIAMOND_HAMMER);
            fabricItemGroupEntries.addAfter(Items.NETHERITE_HOE, NETHERITE_HAMMER);

            fabricItemGroupEntries.addAfter(NETHERITE_HAMMER, NETHER_STEEL_PICKAXE);
            fabricItemGroupEntries.addAfter(NETHER_STEEL_PICKAXE, NETHER_STEEL_HAMMER);
            fabricItemGroupEntries.addAfter(NETHER_STEEL_HAMMER, ECHO_STEEL_HAMMER);
            fabricItemGroupEntries.addAfter(Items.ELYTRA, ECHO_REINFORCED_ELYTRA);
        });
    }
}
