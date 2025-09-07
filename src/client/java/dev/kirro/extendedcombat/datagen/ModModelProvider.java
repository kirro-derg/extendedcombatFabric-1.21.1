package dev.kirro.extendedcombat.datagen;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.block.ModBlocks;
import dev.kirro.extendedcombat.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.data.client.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NETHER_STEEL_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generator.register(ModItems.HANDLE, Models.GENERATED);
        generator.register(ModItems.STATUE, Models.GENERATED);
        generator.register(ModItems.NETHER_STEEL_INGOT, Models.GENERATED);
        generator.register(ModItems.ECHO_STEEL_INGOT, Models.GENERATED);
        generator.register(ModItems.NETHER_STEEL_UPGRADE, Models.GENERATED);
        generator.register(ModItems.ECHO_STEEL_UPGRADE, Models.GENERATED);
        generator.register(ModItems.NETHER_STEEL_PICKAXE, Models.HANDHELD);
        generator.register(ModItems.WOODEN_HAMMER, Models.HANDHELD);
        generator.register(ModItems.STONE_HAMMER, Models.HANDHELD);
        generator.register(ModItems.IRON_HAMMER, Models.HANDHELD);
        generator.register(ModItems.GOLDEN_HAMMER, Models.HANDHELD);
        generator.register(ModItems.DIAMOND_HAMMER, Models.HANDHELD);
        generator.register(ModItems.NETHERITE_HAMMER, Models.HANDHELD);
        generator.register(ModItems.NETHER_STEEL_HAMMER, Models.HANDHELD);
        generator.register(ModItems.ECHO_STEEL_HAMMER, Models.HANDHELD);
        generator.register(ModItems.BLACK_APPLE, Models.GENERATED);
        generator.register(ModItems.GOLDEN_STEAK, Models.GENERATED);
        generator.register(ModItems.HONEY_BREAD, Models.GENERATED);


        generator.registerArmor((ArmorItem) ModItems.NETHER_STEEL_BOOTS);
        generator.registerArmor((ArmorItem) ModItems.NETHER_STEEL_LEGGINGS);
        generator.registerArmor((ArmorItem) ModItems.NETHER_STEEL_CHESTPLATE);
        generator.registerArmor((ArmorItem) ModItems.NETHER_STEEL_HELMET);

        generator.registerArmor((ArmorItem) ModItems.ECHO_STEEL_BOOTS);
        generator.registerArmor((ArmorItem) ModItems.ECHO_STEEL_LEGGINGS);
        generator.registerArmor((ArmorItem) ModItems.ECHO_STEEL_CHESTPLATE);
        generator.registerArmor((ArmorItem) ModItems.ECHO_STEEL_HELMET);

        generator.register(ModItems.ECHO_REINFORCED_ELYTRA, Models.GENERATED);
    }
}
