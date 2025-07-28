package dev.kirro.extendedcombat.datagen;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.block.ModBlocks;
import dev.kirro.extendedcombat.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {

        offer2x2CompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.NETHER_STEEL_BLOCK, ModItems.NETHER_STEEL_INGOT);

        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(ModItems.NETHER_STEEL_UPGRADE), Ingredient.ofItems(Items.NETHERITE_BOOTS), Ingredient.ofItems(ModItems.NETHER_STEEL_INGOT), RecipeCategory.COMBAT, ModItems.NETHER_STEEL_BOOTS)
                .criterion(hasItem(ModItems.NETHER_STEEL_UPGRADE), conditionsFromItem(ModItems.NETHER_STEEL_UPGRADE))
                .offerTo(exporter, Identifier.of(ExtendedCombat.MOD_ID, "nether_steel_boots_smithing"));

        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(ModItems.NETHER_STEEL_UPGRADE), Ingredient.ofItems(Items.NETHERITE_CHESTPLATE), Ingredient.ofItems(ModItems.NETHER_STEEL_INGOT), RecipeCategory.COMBAT, ModItems.NETHER_STEEL_CHESTPLATE)
                .criterion(hasItem(ModItems.NETHER_STEEL_UPGRADE), conditionsFromItem(ModItems.NETHER_STEEL_UPGRADE))
                .offerTo(exporter, Identifier.of(ExtendedCombat.MOD_ID, "nether_steel_chestplate_smithing"));

        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(ModItems.NETHER_STEEL_UPGRADE), Ingredient.ofItems(Items.NETHERITE_LEGGINGS), Ingredient.ofItems(ModItems.NETHER_STEEL_INGOT), RecipeCategory.COMBAT, ModItems.NETHER_STEEL_LEGGINGS)
                .criterion(hasItem(ModItems.NETHER_STEEL_UPGRADE), conditionsFromItem(ModItems.NETHER_STEEL_UPGRADE))
                .offerTo(exporter, Identifier.of(ExtendedCombat.MOD_ID, "nether_steel_leggings_smithing"));

        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(ModItems.NETHER_STEEL_UPGRADE), Ingredient.ofItems(Items.NETHERITE_HELMET), Ingredient.ofItems(ModItems.NETHER_STEEL_INGOT), RecipeCategory.COMBAT, ModItems.NETHER_STEEL_HELMET)
                .criterion(hasItem(ModItems.NETHER_STEEL_UPGRADE), conditionsFromItem(ModItems.NETHER_STEEL_UPGRADE))
                .offerTo(exporter, Identifier.of(ExtendedCombat.MOD_ID, "nether_steel_helmet_smithing"));

        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(ModItems.NETHER_STEEL_UPGRADE), Ingredient.ofItems(Items.NETHERITE_PICKAXE), Ingredient.ofItems(ModItems.NETHER_STEEL_INGOT), RecipeCategory.MISC, ModItems.NETHER_STEEL_UPGRADE)
                .criterion(hasItem(ModItems.NETHER_STEEL_UPGRADE), conditionsFromItem(ModItems.NETHER_STEEL_UPGRADE))
                .offerTo(exporter, Identifier.of(ExtendedCombat.MOD_ID, "nether_steel_pickaxe_smithing"));

        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(Items.BLAZE_POWDER), Ingredient.ofItems(Items.BLACKSTONE), Ingredient.ofItems(ModItems.NETHER_STEEL_INGOT), RecipeCategory.MISC, ModItems.NETHER_STEEL_UPGRADE)
                .criterion(hasItem(ModItems.NETHER_STEEL_INGOT), conditionsFromItem(ModItems.NETHER_STEEL_INGOT))
                .offerTo(exporter, Identifier.of(ExtendedCombat.MOD_ID, "nether_steel_upgrade_smithing"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.HANDLE)
                .pattern("GLN")
                .pattern("LNL")
                .pattern("NLG")
                .input('N', ModItems.NETHER_STEEL_INGOT)
                .input('L', Items.LEATHER)
                .input('G', Items.GOLD_INGOT)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.HEAVY_DOOR)
                .pattern("SS ")
                .pattern("SS ")
                .pattern("SS ")
                .input('S', Items.SPRUCE_LOG)
                .criterion(hasItem(Items.SPRUCE_LOG), conditionsFromItem(Items.SPRUCE_LOG))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.WARDING_STONE)
                .pattern("DDD")
                .pattern("DSD")
                .pattern("DSD")
                .input('S', Items.SOUL_SOIL)
                .input('D', Items.COBBLED_DEEPSLATE)
                .criterion(hasItem(Items.SOUL_SOIL), conditionsFromItem(Items.SOUL_SOIL))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.NETHER_STEEL_GREATSWORD)
                .pattern(" IN")
                .pattern("GNI")
                .pattern("HG ")
                .input('I', Items.NETHERITE_INGOT)
                .input('G', Items.GOLD_INGOT)
                .input('H', ModItems.HANDLE)
                .input('N', ModItems.NETHER_STEEL_INGOT)
                .criterion(hasItem(ModItems.NETHER_STEEL_INGOT), conditionsFromItem(ModItems.NETHER_STEEL_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.NETHER_STEEL_INGOT)
                .pattern("MMM")
                .pattern("NGN")
                .pattern("MMM")
                .input('N', Items.NETHERITE_INGOT)
                .input('M', Items.MAGMA_CREAM)
                .input('G', Items.GOLD_INGOT)
                .criterion(hasItem(Items.NETHERITE_INGOT), conditionsFromItem(Items.NETHERITE_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.FRAMED_GLASS_PANEL, 18)
                .pattern("NNN")
                .pattern("NPN")
                .pattern("NNN")
                .input('N', Items.IRON_NUGGET)
                .input('P', Items.GLASS_PANE)
                .criterion(hasItem(Items.IRON_NUGGET), conditionsFromItem(Items.IRON_NUGGET))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.FLAT_BLOCK, 16)
                .pattern("CCC")
                .pattern("CGC")
                .pattern("CCC")
                .input('C', Items.GRAY_CONCRETE)
                .input('G', Items.GLOWSTONE_DUST)
                .criterion(hasItem(Items.GLOWSTONE_DUST), conditionsFromItem(Items.GLOWSTONE_DUST))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.SEAT)
                        .pattern("   ")
                        .pattern("LLL")
                        .pattern("PPP")
                        .input('L', Items.LEATHER)
                        .input('P', Items.OAK_SLAB)
                        .criterion(hasItem(Items.OAK_SLAB), conditionsFromItem(Items.OAK_SLAB))
                        .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.NETHER_STEEL_INGOT, 4)
                .input(ModBlocks.NETHER_STEEL_BLOCK)
                .criterion(hasItem(ModBlocks.NETHER_STEEL_BLOCK), conditionsFromItem(ModBlocks.NETHER_STEEL_BLOCK))
                .offerTo(exporter, Identifier.of(ExtendedCombat.MOD_ID, "nether_steel_ingot_from_block"));
    }
}
