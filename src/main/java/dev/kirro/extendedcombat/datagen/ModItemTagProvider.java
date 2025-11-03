package dev.kirro.extendedcombat.datagen;

import dev.kirro.extendedcombat.item.ModItems;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output) {
        super(output, CompletableFuture.supplyAsync(BuiltinRegistries::createWrapperLookup));
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModItemTags.ALWAYS_HAS_DURABILITY)
                .add(Items.MACE)
                .add(ModItems.POISON_DAGGER)
        ;

        getOrCreateTagBuilder(ModItemTags.GREATSWORDS)
                .add(ModItems.WOODEN_GREATSWORD)
                .add(ModItems.STONE_GREATSWORD)
                .add(ModItems.IRON_GREATSWORD)
                .add(ModItems.GOLDEN_GREATSWORD)
                .add(ModItems.DIAMOND_GREATSWORD)
                .add(ModItems.NETHERITE_GREATSWORD)
                .add(ModItems.NETHER_STEEL_GREATSWORD)
                .add(ModItems.ECHO_STEEL_GREATSWORD)
        ;

        getOrCreateTagBuilder(ModItemTags.SLEEVED_ARMOR)
                .add(Items.CHAINMAIL_CHESTPLATE)
                .add(Items.LEATHER_CHESTPLATE)
                .add(Items.IRON_CHESTPLATE)
                .add(Items.GOLDEN_CHESTPLATE)
                .add(Items.DIAMOND_CHESTPLATE)
                .add(Items.NETHERITE_CHESTPLATE)
                .add(ModItems.NETHER_STEEL_CHESTPLATE)
                .add(ModItems.ECHO_STEEL_CHESTPLATE)
                .addOptional(Identifier.of("toughasnails", "wool_chestplate"))
        ;

        getOrCreateTagBuilder(ModItemTags.REPAIRABLE_ITEMS)
                .addTag(ModItemTags.ECHO_STEEL_WEARABLES)
                .addTag(ModItemTags.ECHO_STEEL_ITEMS)
        ;

        getOrCreateTagBuilder(ModItemTags.DASH_ENCHANTABLE)
                .addTag(ItemTags.LEG_ARMOR_ENCHANTABLE)
        ;

        getOrCreateTagBuilder(ModItemTags.AIR_JUMP_ENCHANTABLE)
                .addTag(ItemTags.FOOT_ARMOR_ENCHANTABLE)
        ;

        getOrCreateTagBuilder(ModItemTags.BLINK_ENCHANTABLE)
                .addTag(ItemTags.CHEST_ARMOR_ENCHANTABLE)
                .add(ModItems.WOOL_CLOAK)
                .add(ModItems.NETHER_STEEL_CLOAK)
                .add(ModItems.ECHO_STEEL_CLOAK)
        ;

        getOrCreateTagBuilder(ModItemTags.OBSCURITY_ENCHANTABLE)
                .addTag(ItemTags.HEAD_ARMOR_ENCHANTABLE)
                .addTag(ModItemTags.MASK)
        ;

        getOrCreateTagBuilder(ModItemTags.VANITY_ENCHANTABLE)
                .addTag(ItemTags.ARMOR_ENCHANTABLE)
        ;

        getOrCreateTagBuilder(ModItemTags.CLOAK)
                .add(ModItems.WOOL_CLOAK)
                .add(ModItems.NETHER_STEEL_CLOAK)
                .add(ModItems.ECHO_STEEL_CLOAK)
        ;

        getOrCreateTagBuilder(ModItemTags.MASK)
                .add(ModItems.HUNTER_MASK)
                .add(ModItems.NETHER_STEEL_MASK)
                .add(ModItems.ECHO_STEEL_MASK)
        ;

        getOrCreateTagBuilder(ModItemTags.STEALTH_ENCHANTABLE)
                .addTag(ItemTags.CHEST_ARMOR_ENCHANTABLE)
                .addTag(ModItemTags.CLOAK)
        ;

        getOrCreateTagBuilder(ModItemTags.CONCUSSION_ENCHANTABLE)
                .addTag(ModItemTags.GREATSWORDS)
        ;

        getOrCreateTagBuilder(ModItemTags.FLUID_WALKER_ENCHANTABLE)
                .addTag(ItemTags.FOOT_ARMOR_ENCHANTABLE)
        ;

        getOrCreateTagBuilder(ModItemTags.SWIFTNESS_ENCHANTABLE)
                .addTag(ItemTags.LEG_ARMOR_ENCHANTABLE)
        ;

        getOrCreateTagBuilder(ModItemTags.FLAME_RESISTANT_ARMOR)
                .addTag(ModItemTags.NETHER_STEEL_WEARABLES)
                .addTag(ModItemTags.ECHO_STEEL_WEARABLES)
        ;

        getOrCreateTagBuilder(ModItemTags.NETHER_STEEL_WEARABLES)
                .add(ModItems.NETHER_STEEL_BOOTS)
                .add(ModItems.NETHER_STEEL_LEGGINGS)
                .add(ModItems.NETHER_STEEL_CHESTPLATE)
                .add(ModItems.NETHER_STEEL_HELMET)
                .add(ModItems.NETHER_STEEL_CLOAK)
        ;

        getOrCreateTagBuilder(ModItemTags.NETHER_STEEL_TOOLS)
                .add(ModItems.NETHER_STEEL_GREATSWORD)
                .add(ModItems.NETHER_STEEL_PICKAXE)
                .add(ModItems.NETHER_STEEL_HAMMER)
        ;

        getOrCreateTagBuilder(ModItemTags.ECHO_STEEL_WEARABLES)
                .add(ModItems.ECHO_STEEL_BOOTS)
                .add(ModItems.ECHO_STEEL_LEGGINGS)
                .add(ModItems.ECHO_STEEL_CHESTPLATE)
                .add(ModItems.ECHO_STEEL_HELMET)
                .add(ModItems.ECHO_REINFORCED_ELYTRA)
                .add(ModItems.ECHO_STEEL_CLOAK)
                .add(ModItems.ECHO_STEEL_MASK)
        ;

        getOrCreateTagBuilder(ModItemTags.ECHO_STEEL_ITEMS)
                .add(ModItems.ECHO_STEEL_HAMMER)
                .add(ModItems.ECHO_STEEL_GREATSWORD)
        ;

        getOrCreateTagBuilder(ModItemTags.KEEPSAKE_ENCHANTABLE)
                .add(ModItems.ECHO_STEEL_BOOTS)
                .add(ModItems.ECHO_STEEL_LEGGINGS)
                .add(ModItems.ECHO_STEEL_CHESTPLATE)
                .add(ModItems.ECHO_STEEL_HELMET)
                .add(ModItems.ECHO_REINFORCED_ELYTRA)
                .add(ModItems.ECHO_STEEL_HAMMER)
                .add(ModItems.ECHO_STEEL_GREATSWORD)
                .addTag(ItemTags.DURABILITY_ENCHANTABLE)
        ;

        getOrCreateTagBuilder(ModItemTags.BURST_ENCHANTABLE)
                .add(Items.ELYTRA)
                .add(ModItems.ECHO_REINFORCED_ELYTRA)
        ;

        getOrCreateTagBuilder(ItemTags.PIGLIN_LOVED)
                .addTag(ModItemTags.NETHER_STEEL_WEARABLES)
                .addTag(ModItemTags.NETHER_STEEL_TOOLS)
                .addTag(ModItemTags.ECHO_STEEL_ITEMS)
                .addTag(ModItemTags.ECHO_STEEL_WEARABLES)
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
                .add(ModItems.ECHO_STEEL_GREATSWORD)
                .setReplace(false)
        ;
        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .setReplace(false)
        ;
        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(ModItems.WOODEN_GREATSWORD)
                .add(ModItems.STONE_GREATSWORD)
                .add(ModItems.IRON_GREATSWORD)
                .add(ModItems.GOLDEN_GREATSWORD)
                .add(ModItems.DIAMOND_GREATSWORD)
                .add(ModItems.NETHERITE_GREATSWORD)
                .add(ModItems.NETHER_STEEL_GREATSWORD)
                .add(ModItems.ECHO_STEEL_GREATSWORD)
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
