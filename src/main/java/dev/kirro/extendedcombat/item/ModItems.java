package dev.kirro.extendedcombat.item;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.block.ModBlocks;
import dev.kirro.extendedcombat.item.custom.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public interface ModItems {
    Item NETHER_STEEL_INGOT = registerItem("nether_steel_ingot", new Item(new Item.Settings().fireproof()));
    Item ECHO_STEEL_INGOT = registerItem("echo_steel_ingot", new Item(new Item.Settings().fireproof()));
    Item WOODEN_HANDLE = registerItem("wooden_handle", new Item(new Item.Settings()));
    Item NETHER_STEEL_UPGRADE = registerItem("nether_steel_upgrade", new Item(new Item.Settings().fireproof()));
    Item ECHO_STEEL_UPGRADE = registerItem("echo_steel_upgrade", new Item(new Item.Settings().fireproof()));
    Item STATUE = registerItem("statue", new StatueItem(new Item.Settings()));

    Item POISON_DAGGER = registerItem("poison_dagger", new PoisonDaggerItem(new Item.Settings().maxDamage(3)));

    Item WOODEN_GREATSWORD = registerItem("wooden_greatsword",
            new GreatswordItem(ToolMaterials.WOOD, new Item.Settings()
                    .attributeModifiers(GreatswordItem.createAttributeModifiers(ToolMaterials.WOOD, 7, -2.5f, 5.0f))));
    Item STONE_GREATSWORD = registerItem("stone_greatsword",
            new GreatswordItem(ToolMaterials.STONE, new Item.Settings().maxDamage(ToolMaterials.STONE.getDurability())
                    .attributeModifiers(GreatswordItem.createAttributeModifiers(ToolMaterials.STONE, 7, -2.5f, 5.0f))));
    Item IRON_GREATSWORD = registerItem("iron_greatsword",
            new GreatswordItem(ToolMaterials.IRON, new Item.Settings().maxDamage(ToolMaterials.IRON.getDurability())
                    .attributeModifiers(GreatswordItem.createAttributeModifiers(ToolMaterials.IRON, 7, -2.5f, 5.0f))));
    Item GOLDEN_GREATSWORD = registerItem("golden_greatsword",
            new GreatswordItem(ToolMaterials.GOLD, new Item.Settings().maxDamage(ToolMaterials.GOLD.getDurability())
                    .attributeModifiers(GreatswordItem.createAttributeModifiers(ToolMaterials.GOLD, 7, -2.5f, 5.0f))));
    Item DIAMOND_GREATSWORD = registerItem("diamond_greatsword",
            new GreatswordItem(ToolMaterials.DIAMOND, new Item.Settings().maxDamage(ToolMaterials.DIAMOND.getDurability())
                    .attributeModifiers(GreatswordItem.createAttributeModifiers(ToolMaterials.DIAMOND, 7, -2.5f, 5.0f))));
    Item NETHERITE_GREATSWORD = registerItem("netherite_greatsword",
            new GreatswordItem(ToolMaterials.NETHERITE, new Item.Settings().maxDamage(ToolMaterials.NETHERITE.getDurability())
                    .attributeModifiers(GreatswordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 7, -2.5f, 5.0f))));
    Item NETHER_STEEL_GREATSWORD = registerItem("nether_steel_greatsword",
            new PickSwordItem(ModToolMaterials.NETHER_STEEL, new Item.Settings().fireproof().maxDamage(ModToolMaterials.NETHER_STEEL.getDurability())
                    .attributeModifiers(PickSwordItem.createAttributeModifiers(ModToolMaterials.NETHER_STEEL, 7, -2.5f, 5.0f))));
    Item ECHO_STEEL_GREATSWORD = registerItem("echo_steel_greatsword",
            new AxeSwordItem(ModToolMaterials.ECHO_STEEL, new Item.Settings().fireproof().maxDamage(ModToolMaterials.ECHO_STEEL.getDurability())
                    .attributeModifiers(AxeSwordItem.createAttributeModifiers(ModToolMaterials.ECHO_STEEL, 7, -2.5f, 5.0f))));

    Item NETHER_STEEL_PICKAXE = registerItem("nether_steel_pickaxe",
            new ModPickaxeItem(ModToolMaterials.NETHER_STEEL,  new Item.Settings().fireproof()
                    .attributeModifiers(ModPickaxeItem.createAttributeModifiers(ModToolMaterials.NETHER_STEEL, 1, -2.8f))));

    Item WOODEN_HAMMER = registerItem("wooden_hammer",
            new HammerItem(ToolMaterials.WOOD, new Item.Settings()
                    .attributeModifiers(MiningToolItem.createAttributeModifiers(ToolMaterials.WOOD, 1f, -3.2f))));
    Item STONE_HAMMER = registerItem("stone_hammer",
            new HammerItem(ToolMaterials.STONE, new Item.Settings()
                    .attributeModifiers(MiningToolItem.createAttributeModifiers(ToolMaterials.STONE, 1f, -3.2f))));
    Item IRON_HAMMER = registerItem("iron_hammer",
            new HammerItem(ToolMaterials.IRON, new Item.Settings()
                    .attributeModifiers(MiningToolItem.createAttributeModifiers(ToolMaterials.IRON, 1f, -3.2f))));
    Item GOLDEN_HAMMER = registerItem("golden_hammer",
            new HammerItem(ToolMaterials.GOLD, new Item.Settings()
                    .attributeModifiers(MiningToolItem.createAttributeModifiers(ToolMaterials.GOLD, 1f, -3.2f))));
    Item DIAMOND_HAMMER = registerItem("diamond_hammer",
            new HammerItem(ToolMaterials.DIAMOND, new Item.Settings()
                    .attributeModifiers(MiningToolItem.createAttributeModifiers(ToolMaterials.DIAMOND, 1f, -3.2f))));
    Item NETHERITE_HAMMER = registerItem("netherite_hammer",
            new HammerItem(ToolMaterials.NETHERITE, new Item.Settings().fireproof()
                    .attributeModifiers(MiningToolItem.createAttributeModifiers(ToolMaterials.NETHERITE, 1f, -3.2f))));
    Item NETHER_STEEL_HAMMER = registerItem("nether_steel_hammer",
            new HammerItem(ModToolMaterials.NETHER_STEEL,  new Item.Settings().fireproof()
                    .attributeModifiers(MiningToolItem.createAttributeModifiers(ModToolMaterials.NETHER_STEEL, 1f, -3.2f))));
    Item ECHO_STEEL_HAMMER = registerItem("echo_steel_hammer",
            new HammerItem(ModToolMaterials.ECHO_STEEL, new Item.Settings().fireproof()
                    .attributeModifiers(MiningToolItem.createAttributeModifiers(ModToolMaterials.ECHO_STEEL, 1f, -3.2f))));


    Item NETHER_STEEL_HELMET = registerItem("nether_steel_helmet",
            new ModArmorItem(ModArmorMaterials.NETHER_STEEL, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(8124).fireproof()));
    Item NETHER_STEEL_CHESTPLATE = registerItem("nether_steel_chestplate",
            new ModArmorItem(ModArmorMaterials.NETHER_STEEL, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(8124).fireproof()));
    Item NETHER_STEEL_LEGGINGS = registerItem("nether_steel_leggings",
            new ModArmorItem(ModArmorMaterials.NETHER_STEEL, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(8124).fireproof()));
    Item NETHER_STEEL_BOOTS = registerItem("nether_steel_boots",
            new ModArmorItem(ModArmorMaterials.NETHER_STEEL, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(8124).fireproof()));

    Item ECHO_STEEL_HELMET = registerItem("echo_steel_helmet",
            new ModArmorItem(ModArmorMaterials.ECHO_STEEL, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(9124).fireproof()));
    Item ECHO_STEEL_CHESTPLATE = registerItem("echo_steel_chestplate",
            new ModArmorItem(ModArmorMaterials.ECHO_STEEL, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(9124).fireproof()));
    Item ECHO_STEEL_LEGGINGS = registerItem("echo_steel_leggings",
            new ModArmorItem(ModArmorMaterials.ECHO_STEEL, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(9124).fireproof()));
    Item ECHO_STEEL_BOOTS = registerItem("echo_steel_boots",
            new ModArmorItem(ModArmorMaterials.ECHO_STEEL, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(9124).fireproof()));

    Item HUNTER_MASK = registerItem("hunter_mask", new HunterMaskItem(ModArmorMaterials.WOOL, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(512)));
    Item NETHER_STEEL_MASK = registerItem("nether_steel_mask", new HunterMaskItem(ModArmorMaterials.NETHER_STEEL, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(8124)));
    Item ECHO_STEEL_MASK = registerItem("echo_steel_mask", new HunterMaskItem(ModArmorMaterials.ECHO_STEEL, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(9124)));

    Item HUNTER_CLOAK = registerItem("hunter_cloak", new WoolArmorItem(ModArmorMaterials.WOOL, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(512)));
    Item NETHER_STEEL_CLOAK = registerItem("nether_steel_cloak", new WoolArmorItem(ModArmorMaterials.NETHER_STEEL, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(8124)));
    Item ECHO_STEEL_CLOAK = registerItem("echo_steel_cloak", new WoolArmorItem(ModArmorMaterials.ECHO_STEEL, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(9124)));

    Item HUNTER_LEGGINGS = registerItem("hunter_leggings", new HunterMaskItem(ModArmorMaterials.WOOL, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(512)));
    Item NETHER_STEEL_HUNTER_LEGGINGS = registerItem("nether_steel_hunter_leggings", new HunterMaskItem(ModArmorMaterials.NETHER_STEEL, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(8124)));
    Item ECHO_STEEL_HUNTER_LEGGINGS = registerItem("echo_steel_hunter_leggings", new HunterMaskItem(ModArmorMaterials.ECHO_STEEL, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(9124)));

    Item HUNTER_BOOTS = registerItem("hunter_boots", new HunterMaskItem(ModArmorMaterials.WOOL, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(512)));
    Item NETHER_STEEL_HUNTER_BOOTS = registerItem("nether_steel_hunter_boots", new HunterMaskItem(ModArmorMaterials.NETHER_STEEL, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(8124)));
    Item ECHO_STEEL_HUNTER_BOOTS = registerItem("echo_steel_hunter_boots", new HunterMaskItem(ModArmorMaterials.ECHO_STEEL, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(9124)));

    Item ECHO_REINFORCED_ELYTRA = registerItem("echo_reinforced_elytra",
            new ModElytra(new Item.Settings().maxDamage(864).rarity(Rarity.RARE)));

    Item BLACK_APPLE = registerItem("black_apple",
            new Item(new Item.Settings().rarity(Rarity.UNCOMMON).food(ModFoodComponents.BLACK_APPLE)));
    Item BLACK_APPLE_SEED = registerItem("black_apple_seed",
            new AliasedBlockItem(ModBlocks.BLACK_APPLE_BUSH, new Item.Settings().food(ModFoodComponents.BLACK_APPLE_SEED)));
    Item GOLDEN_STEAK = registerItem("golden_steak",
            new Item(new Item.Settings().rarity(Rarity.RARE).food(ModFoodComponents.GOLDEN_STEAK)));
    Item HONEY_BREAD = registerItem("honey_bread",
            new Item(new Item.Settings().food(ModFoodComponents.HONEY_BREAD)));

    Item MILK_BOTTLE = registerItem("milk_bottle",
            new MilkBottleItem(new Item.Settings().maxCount(32), MilkBottleItem.MilkType.PLAIN));
    Item SWEET_BERRY_MILK_BOTTLE = registerItem("sweet_berry_milk_bottle",
            new MilkBottleItem(new Item.Settings().maxCount(32), MilkBottleItem.MilkType.SWEET_BERRY));
    Item CHOCOLATE_MILK_BOTTLE = registerItem("chocolate_milk_bottle",
            new MilkBottleItem(new Item.Settings().maxCount(32), MilkBottleItem.MilkType.CHOCOLATE));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(ExtendedCombat.MOD_ID, name), item);
    }

    static void registerModItems() {
        ExtendedCombat.LOGGER.info("Registering items for" + ExtendedCombat.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.addAfter(Items.NETHERITE_INGOT, NETHER_STEEL_INGOT);
            entries.addAfter(NETHER_STEEL_INGOT, ECHO_STEEL_INGOT);
            entries.addAfter(Items.STICK, WOODEN_HANDLE);
            entries.addAfter(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, NETHER_STEEL_UPGRADE);
            entries.addAfter(NETHER_STEEL_UPGRADE, ECHO_STEEL_UPGRADE);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.addAfter(Items.WOODEN_SWORD, WOODEN_GREATSWORD);
            entries.addAfter(Items.STONE_SWORD, STONE_GREATSWORD);
            entries.addAfter(Items.IRON_SWORD, IRON_GREATSWORD);
            entries.addAfter(Items.GOLDEN_SWORD, GOLDEN_GREATSWORD);
            entries.addAfter(Items.DIAMOND_SWORD, DIAMOND_GREATSWORD);
            entries.addAfter(Items.NETHERITE_SWORD, NETHERITE_GREATSWORD);
            entries.addAfter(NETHERITE_GREATSWORD, NETHER_STEEL_GREATSWORD);
            entries.addAfter(NETHER_STEEL_GREATSWORD, ECHO_STEEL_GREATSWORD);
            entries.addAfter(Items.WIND_CHARGE, POISON_DAGGER);
            entries.addAfter(Items.NETHERITE_BOOTS, NETHER_STEEL_HELMET);
            entries.addAfter(NETHER_STEEL_HELMET, NETHER_STEEL_CHESTPLATE);
            entries.addAfter(NETHER_STEEL_CHESTPLATE, NETHER_STEEL_LEGGINGS);
            entries.addAfter(NETHER_STEEL_LEGGINGS, NETHER_STEEL_BOOTS);
            entries.addAfter(NETHER_STEEL_BOOTS, ECHO_STEEL_HELMET);
            entries.addAfter(ECHO_STEEL_HELMET, ECHO_STEEL_CHESTPLATE);
            entries.addAfter(ECHO_STEEL_CHESTPLATE, ECHO_STEEL_LEGGINGS);
            entries.addAfter(ECHO_STEEL_LEGGINGS, ECHO_STEEL_BOOTS);
            entries.addAfter(Items.TURTLE_HELMET, HUNTER_MASK);
            entries.addAfter(HUNTER_MASK, NETHER_STEEL_MASK);
            entries.addAfter(NETHER_STEEL_MASK, ECHO_STEEL_MASK);
            entries.addAfter(ECHO_STEEL_MASK, HUNTER_CLOAK);
            entries.addAfter(HUNTER_CLOAK, NETHER_STEEL_CLOAK);
            entries.addAfter(NETHER_STEEL_CLOAK, ECHO_STEEL_CLOAK);
            entries.add(HUNTER_LEGGINGS);
            entries.add(NETHER_STEEL_HUNTER_LEGGINGS);
            entries.add(ECHO_STEEL_HUNTER_LEGGINGS);
            entries.add(HUNTER_BOOTS);
            entries.add(NETHER_STEEL_HUNTER_BOOTS);
            entries.add(ECHO_STEEL_HUNTER_BOOTS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.addAfter(Items.ENCHANTED_GOLDEN_APPLE, GOLDEN_STEAK);
            entries.addAfter(GOLDEN_STEAK, BLACK_APPLE);
            entries.addAfter(BLACK_APPLE, BLACK_APPLE_SEED);
            entries.addAfter(Items.BREAD, HONEY_BREAD);
            entries.addAfter(Items.HONEY_BOTTLE, MILK_BOTTLE);
            entries.addAfter(MILK_BOTTLE, SWEET_BERRY_MILK_BOTTLE);
            entries.addAfter(SWEET_BERRY_MILK_BOTTLE, CHOCOLATE_MILK_BOTTLE);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.addAfter(Items.WOODEN_HOE, WOODEN_HAMMER);
            entries.addAfter(Items.STONE_HOE, STONE_HAMMER);
            entries.addAfter(Items.IRON_HOE, IRON_HAMMER);
            entries.addAfter(Items.GOLDEN_HOE, GOLDEN_HAMMER);
            entries.addAfter(Items.DIAMOND_HOE, DIAMOND_HAMMER);
            entries.addAfter(Items.NETHERITE_HOE, NETHERITE_HAMMER);

            entries.addAfter(NETHERITE_HAMMER, NETHER_STEEL_PICKAXE);
            entries.addAfter(NETHER_STEEL_PICKAXE, NETHER_STEEL_HAMMER);
            entries.addAfter(NETHER_STEEL_HAMMER, ECHO_STEEL_HAMMER);
            entries.addAfter(Items.ELYTRA, ECHO_REINFORCED_ELYTRA);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
            entries.addAfter(Items.ARMOR_STAND, STATUE);
        });
    }
}
