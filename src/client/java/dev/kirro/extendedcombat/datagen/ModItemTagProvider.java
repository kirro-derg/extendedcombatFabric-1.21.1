package dev.kirro.extendedcombat.datagen;

import dev.kirro.extendedcombat.item.ModItems;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EnchantmentTags;
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

        getOrCreateTagBuilder(ModItemTags.REPAIRABLE_ITEMS)
                .add(ModItems.ECHO_STEEL_BOOTS)
                .add(ModItems.ECHO_STEEL_LEGGINGS)
                .add(ModItems.ECHO_STEEL_CHESTPLATE)
                .add(ModItems.ECHO_STEEL_HELMET)
        ;

        getOrCreateTagBuilder(ModItemTags.NETHER_STEEL_ARMOR)
                .add(ModItems.NETHER_STEEL_BOOTS)
                .add(ModItems.NETHER_STEEL_LEGGINGS)
                .add(ModItems.NETHER_STEEL_CHESTPLATE)
                .add(ModItems.NETHER_STEEL_HELMET)
        ;

        getOrCreateTagBuilder(ModItemTags.ECHO_ARMOR)
                .add(ModItems.ECHO_STEEL_BOOTS)
                .add(ModItems.ECHO_STEEL_LEGGINGS)
                .add(ModItems.ECHO_STEEL_CHESTPLATE)
                .add(ModItems.ECHO_STEEL_HELMET)
        ;

        getOrCreateTagBuilder(ModItemTags.KEEPSAKE_ENCHANTABLE)
                .add(ModItems.ECHO_STEEL_BOOTS)
                .add(ModItems.ECHO_STEEL_LEGGINGS)
                .add(ModItems.ECHO_STEEL_CHESTPLATE)
                .add(ModItems.ECHO_STEEL_HELMET)
        ;

        getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE)
                .add(ModItems.NETHER_STEEL_BOOTS)
                .add(ModItems.NETHER_STEEL_LEGGINGS)
                .add(ModItems.NETHER_STEEL_CHESTPLATE)
                .add(ModItems.NETHER_STEEL_HELMET)
                .add(ModItems.NETHER_STEEL_PICKAXE)
                .add(ModItems.NETHER_STEEL_HAMMER)
                .add(ModItems.NETHER_STEEL_GREATSWORD)
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

        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ModItems.NETHER_STEEL_PICKAXE)
                .add(ModItems.NETHER_STEEL_HAMMER)
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
