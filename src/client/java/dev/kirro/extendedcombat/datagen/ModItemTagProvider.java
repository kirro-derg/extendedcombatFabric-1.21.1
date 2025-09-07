package dev.kirro.extendedcombat.datagen;

import dev.kirro.extendedcombat.item.ModItems;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.util.Identifier.of;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output) {
        super(output, CompletableFuture.supplyAsync(BuiltinRegistries::createWrapperLookup));
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModItemTags.PERSISTENT_DURABILITY)
                .add(Items.SHIELD)
                .add(Items.MACE)
        ;

        getOrCreateTagBuilder(ModItemTags.SCALED_ITEMS)
                .add(ModItems.NETHER_STEEL_GREATSWORD)
        ;

        getOrCreateTagBuilder(ModItemTags.SLEEVED_ARMOR)
                .add(Items.LEATHER_CHESTPLATE)
                .add(Items.CHAINMAIL_CHESTPLATE)
                .add(Items.IRON_CHESTPLATE)
                .add(Items.GOLDEN_CHESTPLATE)
                .add(Items.DIAMOND_CHESTPLATE)
                .add(Items.NETHERITE_CHESTPLATE)
                .add(ModItems.NETHER_STEEL_CHESTPLATE)
                .add(ModItems.ECHO_STEEL_CHESTPLATE)
        ;

        getOrCreateTagBuilder(ModItemTags.REPAIRABLE_ITEMS)
                .addTag(ModItemTags.ECHO_WEARABLES)
                .addTag(ModItemTags.ECHO_ITEMS)
        ;

        getOrCreateTagBuilder(ModItemTags.NETHER_STEEL_WEARABLES)
                .add(ModItems.NETHER_STEEL_BOOTS)
                .add(ModItems.NETHER_STEEL_LEGGINGS)
                .add(ModItems.NETHER_STEEL_CHESTPLATE)
                .add(ModItems.NETHER_STEEL_HELMET)
        ;

        getOrCreateTagBuilder(ModItemTags.NETHER_STEEL_TOOLS)
                .add(ModItems.NETHER_STEEL_GREATSWORD)
                .add(ModItems.NETHER_STEEL_PICKAXE)
                .add(ModItems.NETHER_STEEL_HAMMER)
        ;

        getOrCreateTagBuilder(ModItemTags.ECHO_WEARABLES)
                .add(ModItems.ECHO_STEEL_BOOTS)
                .add(ModItems.ECHO_STEEL_LEGGINGS)
                .add(ModItems.ECHO_STEEL_CHESTPLATE)
                .add(ModItems.ECHO_STEEL_HELMET)
                .add(ModItems.ECHO_REINFORCED_ELYTRA)
        ;

        getOrCreateTagBuilder(ModItemTags.ECHO_ITEMS)
                .add(ModItems.ECHO_STEEL_HAMMER)
        ;

        getOrCreateTagBuilder(ModItemTags.KEEPSAKE_ENCHANTABLE)
                .add(ModItems.ECHO_STEEL_BOOTS)
                .add(ModItems.ECHO_STEEL_LEGGINGS)
                .add(ModItems.ECHO_STEEL_CHESTPLATE)
                .add(ModItems.ECHO_STEEL_HELMET)
                .add(ModItems.ECHO_REINFORCED_ELYTRA)
                .add(ModItems.ECHO_STEEL_HAMMER)
                .addTag(ItemTags.DURABILITY_ENCHANTABLE)
        ;

        getOrCreateTagBuilder(ModItemTags.ELYTRA_ENCHANTABLE)
                .add(Items.ELYTRA)
                .add(ModItems.ECHO_REINFORCED_ELYTRA)
        ;

        getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE)
                .add(ModItems.NETHER_STEEL_BOOTS)
                .add(ModItems.NETHER_STEEL_LEGGINGS)
                .add(ModItems.NETHER_STEEL_CHESTPLATE)
                .add(ModItems.NETHER_STEEL_HELMET)
                .add(ModItems.NETHER_STEEL_PICKAXE)
                .add(ModItems.NETHER_STEEL_HAMMER)
                .add(ModItems.NETHER_STEEL_GREATSWORD)
                .add(ModItems.WOODEN_HAMMER)
                .add(ModItems.STONE_HAMMER)
                .add(ModItems.IRON_HAMMER)
                .add(ModItems.GOLDEN_HAMMER)
                .add(ModItems.DIAMOND_HAMMER)
                .add(ModItems.NETHERITE_HAMMER)
                .add(ModItems.NETHER_STEEL_HAMMER)
                .add(ModItems.ECHO_STEEL_HAMMER)
                .setReplace(false)
        ;

        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.NETHER_STEEL_BOOTS)
                .add(ModItems.NETHER_STEEL_LEGGINGS)
                .add(ModItems.NETHER_STEEL_CHESTPLATE)
                .add(ModItems.NETHER_STEEL_HELMET)
                .add(ModItems.ECHO_STEEL_BOOTS)
                .add(ModItems.ECHO_STEEL_LEGGINGS)
                .add(ModItems.ECHO_STEEL_CHESTPLATE)
                .add(ModItems.ECHO_STEEL_HELMET)
                .setReplace(false)
        ;

        getOrCreateTagBuilder(ItemTags.MINING_ENCHANTABLE)
                .add(ModItems.NETHER_STEEL_GREATSWORD)
                .add(ModItems.NETHER_STEEL_PICKAXE)
                .add(ModItems.WOODEN_HAMMER)
                .add(ModItems.STONE_HAMMER)
                .add(ModItems.IRON_HAMMER)
                .add(ModItems.GOLDEN_HAMMER)
                .add(ModItems.DIAMOND_HAMMER)
                .add(ModItems.NETHERITE_HAMMER)
                .add(ModItems.NETHER_STEEL_HAMMER)
                .add(ModItems.ECHO_STEEL_HAMMER)
                .setReplace(false)
        ;
        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ModItems.NETHER_STEEL_GREATSWORD)
                .add(ModItems.NETHER_STEEL_PICKAXE)
                .add(ModItems.WOODEN_HAMMER)
                .add(ModItems.STONE_HAMMER)
                .add(ModItems.IRON_HAMMER)
                .add(ModItems.GOLDEN_HAMMER)
                .add(ModItems.DIAMOND_HAMMER)
                .add(ModItems.NETHERITE_HAMMER)
                .add(ModItems.NETHER_STEEL_HAMMER)
                .add(ModItems.ECHO_STEEL_HAMMER)
                .setReplace(false)
        ;
        getOrCreateTagBuilder(ItemTags.AXES)
                .setReplace(false)
        ;
        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .setReplace(false)
        ;
        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(ModItems.NETHER_STEEL_GREATSWORD)
                .setReplace(false)
        ;
        getOrCreateTagBuilder(ItemTags.HOES)
                .setReplace(false)
        ;

        getOrCreateTagBuilder(ItemTags.ARMOR_ENCHANTABLE)
                .add(ModItems.NETHER_STEEL_HELMET)
                .add(ModItems.NETHER_STEEL_CHESTPLATE)
                .add(ModItems.NETHER_STEEL_LEGGINGS)
                .add(ModItems.NETHER_STEEL_BOOTS)
                .add(ModItems.ECHO_STEEL_BOOTS)
                .add(ModItems.ECHO_STEEL_LEGGINGS)
                .add(ModItems.ECHO_STEEL_CHESTPLATE)
                .add(ModItems.ECHO_STEEL_HELMET)
                .setReplace(false)
        ;


        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR)
                .add(ModItems.NETHER_STEEL_HELMET)
                .setReplace(false)
        ;
        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR)
                .add(ModItems.NETHER_STEEL_CHESTPLATE)
                .setReplace(false)
        ;
        getOrCreateTagBuilder(ItemTags.LEG_ARMOR)
                .add(ModItems.NETHER_STEEL_LEGGINGS)
                .setReplace(false)
        ;
        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR)
                .add(ModItems.NETHER_STEEL_BOOTS)
                .setReplace(false)
        ;

        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR_ENCHANTABLE)
                .add(ModItems.NETHER_STEEL_HELMET)
                .add(ModItems.ECHO_STEEL_HELMET)
                .setReplace(false)
        ;
        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR_ENCHANTABLE)
                .add(ModItems.NETHER_STEEL_CHESTPLATE)
                .add(ModItems.ECHO_STEEL_CHESTPLATE)
                .setReplace(false)
        ;
        getOrCreateTagBuilder(ItemTags.LEG_ARMOR_ENCHANTABLE)
                .add(ModItems.NETHER_STEEL_LEGGINGS)
                .add(ModItems.ECHO_STEEL_LEGGINGS)
                .setReplace(false)
        ;
        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR_ENCHANTABLE)
                .add(ModItems.NETHER_STEEL_BOOTS)
                .add(ModItems.ECHO_STEEL_BOOTS)
                .setReplace(false)
        ;
    }
}
