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
                .add(Items.WOLF_ARMOR)
                .addOptionalTag(of("create", "sandpaper"))
                .addOptional(of("create", "super_glue"));

        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.NETHER_STEEL_BOOTS)
                .add(ModItems.NETHER_STEEL_LEGGINGS)
                .add(ModItems.NETHER_STEEL_CHESTPLATE)
                .add(ModItems.NETHER_STEEL_HELMET);

        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ModItems.NETHER_STEEL_PICKAXE);
        getOrCreateTagBuilder(ItemTags.AXES);
        getOrCreateTagBuilder(ItemTags.SHOVELS);
        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(ModItems.NETHER_STEEL_GREATSWORD);
        getOrCreateTagBuilder(ItemTags.HOES);


        getOrCreateTagBuilder(ItemTags.ARMOR_ENCHANTABLE)
                .add(ModItems.NETHER_STEEL_HELMET)
                .add(ModItems.NETHER_STEEL_CHESTPLATE)
                .add(ModItems.NETHER_STEEL_LEGGINGS)
                .add(ModItems.NETHER_STEEL_BOOTS);


        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR)
                .add(ModItems.NETHER_STEEL_HELMET);
        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR)
                .add(ModItems.NETHER_STEEL_CHESTPLATE);
        getOrCreateTagBuilder(ItemTags.LEG_ARMOR)
                .add(ModItems.NETHER_STEEL_LEGGINGS);
        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR)
                .add(ModItems.NETHER_STEEL_BOOTS);

        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR_ENCHANTABLE)
                .add(ModItems.NETHER_STEEL_HELMET);
        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR_ENCHANTABLE)
                .add(ModItems.NETHER_STEEL_CHESTPLATE);
        getOrCreateTagBuilder(ItemTags.LEG_ARMOR_ENCHANTABLE)
                .add(ModItems.NETHER_STEEL_LEGGINGS);
        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR_ENCHANTABLE)
                .add(ModItems.NETHER_STEEL_BOOTS);
    }
}
