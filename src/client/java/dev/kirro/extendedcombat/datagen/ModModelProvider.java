package dev.kirro.extendedcombat.datagen;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.block.ModBlocks;
import dev.kirro.extendedcombat.block.custom.WardingStoneBlock;
import dev.kirro.extendedcombat.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NETHER_STEEL_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.HANDLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.STATUE, Models.GENERATED);
        itemModelGenerator.register(ModItems.NETHER_STEEL_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.NETHER_STEEL_UPGRADE, Models.GENERATED);
        itemModelGenerator.register(ModItems.NETHER_STEEL_PICKAXE, Models.HANDHELD);


        itemModelGenerator.registerArmor((ArmorItem) ModItems.NETHER_STEEL_BOOTS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.NETHER_STEEL_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.NETHER_STEEL_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.NETHER_STEEL_HELMET);
    }
}
