package dev.kirro.extendedcombat.datagen;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.block.ModBlocks;
import dev.kirro.extendedcombat.item.ModItems;
import dev.kirro.extendedcombat.potion.ModPotions;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {

        // metal block recipes
        offer2x2CompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.NETHER_STEEL_BLOCK, ModItems.NETHER_STEEL_INGOT);

        offer2x2CompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ECHO_STEEL_BLOCK, ModItems.ECHO_STEEL_INGOT);

        // ingot from block recipes
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.NETHER_STEEL_INGOT, 4)
                .input(ModBlocks.NETHER_STEEL_BLOCK)
                .criterion(hasItem(ModBlocks.NETHER_STEEL_BLOCK), conditionsFromItem(ModBlocks.NETHER_STEEL_BLOCK))
                .offerTo(exporter, Identifier.of(ExtendedCombat.MOD_ID, "nether_steel_ingot_from_block"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.ECHO_STEEL_INGOT, 4)
                .input(ModBlocks.ECHO_STEEL_BLOCK)
                .criterion(hasItem(ModBlocks.ECHO_STEEL_BLOCK), conditionsFromItem(ModBlocks.ECHO_STEEL_BLOCK))
                .offerTo(exporter, ExtendedCombat.id("echo_steel_ingot_from_block"));

        // nether steel item recipes
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

        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(ModItems.NETHER_STEEL_UPGRADE), Ingredient.ofItems(Items.NETHERITE_PICKAXE), Ingredient.ofItems(ModItems.NETHER_STEEL_INGOT), RecipeCategory.MISC, ModItems.NETHER_STEEL_PICKAXE)
                .criterion(hasItem(ModItems.NETHER_STEEL_UPGRADE), conditionsFromItem(ModItems.NETHER_STEEL_UPGRADE))
                .offerTo(exporter, Identifier.of(ExtendedCombat.MOD_ID, "nether_steel_pickaxe_smithing"));

        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(Items.BLAZE_POWDER), Ingredient.ofItems(Items.BLACKSTONE), Ingredient.ofItems(ModItems.NETHER_STEEL_INGOT), RecipeCategory.MISC, ModItems.NETHER_STEEL_UPGRADE)
                .criterion(hasItem(ModItems.NETHER_STEEL_INGOT), conditionsFromItem(ModItems.NETHER_STEEL_INGOT))
                .offerTo(exporter, Identifier.of(ExtendedCombat.MOD_ID, "nether_steel_upgrade_smithing"));

        // echo steel item recipes
        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(ModItems.ECHO_STEEL_UPGRADE), Ingredient.ofItems(ModItems.NETHER_STEEL_HELMET), Ingredient.ofItems(ModItems.ECHO_STEEL_INGOT), RecipeCategory.MISC, ModItems.ECHO_STEEL_HELMET)
                .criterion(hasItem(ModItems.ECHO_STEEL_INGOT), conditionsFromItem(ModItems.ECHO_STEEL_INGOT))
                .offerTo(exporter, Identifier.of(ExtendedCombat.MOD_ID, "echo_steel_helmet_smithing"));

        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(ModItems.ECHO_STEEL_UPGRADE), Ingredient.ofItems(ModItems.NETHER_STEEL_CHESTPLATE), Ingredient.ofItems(ModItems.ECHO_STEEL_INGOT), RecipeCategory.MISC, ModItems.ECHO_STEEL_CHESTPLATE)
                .criterion(hasItem(ModItems.ECHO_STEEL_INGOT), conditionsFromItem(ModItems.ECHO_STEEL_INGOT))
                .offerTo(exporter, Identifier.of(ExtendedCombat.MOD_ID, "echo_steel_chestplate_smithing"));

        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(ModItems.ECHO_STEEL_UPGRADE), Ingredient.ofItems(ModItems.NETHER_STEEL_LEGGINGS), Ingredient.ofItems(ModItems.ECHO_STEEL_INGOT), RecipeCategory.MISC, ModItems.ECHO_STEEL_LEGGINGS)
                .criterion(hasItem(ModItems.ECHO_STEEL_INGOT), conditionsFromItem(ModItems.ECHO_STEEL_INGOT))
                .offerTo(exporter, Identifier.of(ExtendedCombat.MOD_ID, "echo_steel_leggings_smithing"));

        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(ModItems.ECHO_STEEL_UPGRADE), Ingredient.ofItems(ModItems.NETHER_STEEL_BOOTS), Ingredient.ofItems(ModItems.ECHO_STEEL_INGOT), RecipeCategory.MISC, ModItems.ECHO_STEEL_BOOTS)
                .criterion(hasItem(ModItems.ECHO_STEEL_INGOT), conditionsFromItem(ModItems.ECHO_STEEL_INGOT))
                .offerTo(exporter, Identifier.of(ExtendedCombat.MOD_ID, "echo_steel_boots_smithing"));

        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(ModItems.ECHO_STEEL_UPGRADE), Ingredient.ofItems(ModItems.NETHER_STEEL_INGOT), Ingredient.ofItems(Items.ECHO_SHARD), RecipeCategory.MISC, ModItems.ECHO_STEEL_INGOT)
                .criterion(hasItem(Items.ECHO_SHARD), conditionsFromItem(Items.ECHO_SHARD))
                .offerTo(exporter, Identifier.of(ExtendedCombat.MOD_ID, "echo_steel_ingot_smithing"));

        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(ModItems.ECHO_STEEL_UPGRADE), Ingredient.ofItems(Items.ELYTRA), Ingredient.ofItems(ModItems.ECHO_STEEL_INGOT), RecipeCategory.MISC, ModItems.ECHO_REINFORCED_ELYTRA)
                .criterion(hasItem(ModItems.ECHO_STEEL_INGOT), conditionsFromItem(ModItems.ECHO_STEEL_INGOT))
                .offerTo(exporter, Identifier.of(ExtendedCombat.MOD_ID, "echo_reinforced_elytra_smithing"));

        // miscellaneous item recipes
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WOODEN_HANDLE)
                .pattern(" LN")
                .pattern("LNL")
                .pattern("NL ")
                .input('N', Items.STICK)
                .input('L', Items.LEATHER)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.NETHER_STEEL_HANDLE)
                .pattern("GLN")
                .pattern("LNL")
                .pattern("NLG")
                .input('N', ModItems.NETHER_STEEL_INGOT)
                .input('L', Items.LEATHER)
                .input('G', Items.GOLD_INGOT)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.NETHER_STEEL_UPGRADE, 2)
                .pattern("DND")
                .pattern("DUD")
                .pattern("DDD")
                .input('D', Items.DIAMOND)
                .input('N', Items.NETHERITE_SCRAP)
                .input('U', ModItems.NETHER_STEEL_UPGRADE)
                .criterion(hasItem(ModItems.NETHER_STEEL_UPGRADE), conditionsFromItem(ModItems.NETHER_STEEL_UPGRADE))
                .offerTo(exporter, "nether_steel_upgrade_duplication");

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.ECHO_STEEL_UPGRADE, 2)
                .pattern("DED")
                .pattern("DUD")
                .pattern("DDD")
                .input('D', Items.DIAMOND)
                .input('E', Items.ECHO_SHARD)
                .input('U', ModItems.ECHO_STEEL_UPGRADE)
                .criterion(hasItem(ModItems.ECHO_STEEL_UPGRADE), conditionsFromItem(ModItems.ECHO_STEEL_UPGRADE))
                .offerTo(exporter, "echo_steel_upgrade_duplication");

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.HEAVY_DOOR)
                .pattern("SS ")
                .pattern("SS ")
                .pattern("SS ")
                .input('S', Items.SPRUCE_LOG)
                .criterion(hasItem(Items.SPRUCE_LOG), conditionsFromItem(Items.SPRUCE_LOG))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.MILK_BOTTLE, 3)
                .pattern("   ")
                .pattern("GMG")
                .pattern(" G ")
                .input('M', Items.MILK_BUCKET)
                .input('G', Items.GLASS_BOTTLE)
                .criterion(hasItem(Items.MILK_BUCKET), conditionsFromItem(Items.MILK_BUCKET))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.CHOCOLATE_MILK_BOTTLE)
                .input(ModItems.MILK_BOTTLE)
                .input(Items.COCOA_BEANS)
                .criterion(hasItem(ModItems.MILK_BOTTLE), conditionsFromItem(ModItems.MILK_BOTTLE))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.SWEET_BERRY_MILK_BOTTLE)
                .input(ModItems.MILK_BOTTLE)
                .input(Items.SWEET_BERRIES)
                .criterion(hasItem(ModItems.MILK_BOTTLE), conditionsFromItem(ModItems.MILK_BOTTLE))
                .offerTo(exporter);

        // ∴ᔑ∷↸╎リ⊣  ᓭℸ ̣ 𝙹リᒷ
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.WARDING_STONE)
                .pattern("DDD")
                .pattern("DSD")
                .pattern("DSD")
                .input('S', Items.SOUL_SOIL)
                .input('D', Items.COBBLED_DEEPSLATE)
                .criterion(hasItem(Items.SOUL_SOIL), conditionsFromItem(Items.SOUL_SOIL))
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

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.SEAT, 3)
                .pattern("LLL")
                .pattern("PPP")
                .input('L', Items.LEATHER)
                .input('P', Items.OAK_SLAB)
                .criterion(hasItem(Items.OAK_SLAB), conditionsFromItem(Items.OAK_SLAB))
                .offerTo(exporter);

        // weapon recipes
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.WOODEN_GREATSWORD)
                .pattern(" II")
                .pattern("III")
                .pattern("HI ")
                .input('I', ItemTags.PLANKS)
                .input('H', ModItems.WOODEN_HANDLE)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.STONE_GREATSWORD)
                .pattern(" II")
                .pattern("III")
                .pattern("HI ")
                .input('I', ItemTags.STONE_TOOL_MATERIALS)
                .input('H', ModItems.WOODEN_HANDLE)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.IRON_GREATSWORD)
                .pattern(" II")
                .pattern("III")
                .pattern("HI ")
                .input('I', Items.IRON_INGOT)
                .input('H', ModItems.WOODEN_HANDLE)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.GOLDEN_GREATSWORD)
                .pattern(" II")
                .pattern("III")
                .pattern("HI ")
                .input('I', Items.GOLD_INGOT)
                .input('H', ModItems.WOODEN_HANDLE)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.DIAMOND_GREATSWORD)
                .pattern(" II")
                .pattern("III")
                .pattern("HI ")
                .input('I', Items.DIAMOND)
                .input('H', ModItems.WOODEN_HANDLE)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter);

        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.ofItems(ModItems.DIAMOND_GREATSWORD), Ingredient.ofItems(Items.NETHERITE_INGOT), RecipeCategory.COMBAT, ModItems.NETHERITE_GREATSWORD)
                .criterion(hasItem(Items.NETHERITE_INGOT), conditionsFromItem(Items.NETHERITE_INGOT))
                .offerTo(exporter, "netherite_greatsword_smithing");

        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(ModItems.NETHER_STEEL_UPGRADE), Ingredient.ofItems(ModItems.NETHERITE_GREATSWORD), Ingredient.ofItems(ModItems.NETHER_STEEL_INGOT), RecipeCategory.COMBAT, ModItems.NETHER_STEEL_GREATSWORD)
                .criterion(hasItem(ModItems.NETHER_STEEL_INGOT), conditionsFromItem(ModItems.NETHER_STEEL_INGOT))
                .offerTo(exporter, "nether_steel_greatsword_smithing");

        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(ModItems.ECHO_STEEL_UPGRADE), Ingredient.ofItems(ModItems.NETHER_STEEL_GREATSWORD), Ingredient.ofItems(ModItems.ECHO_STEEL_INGOT), RecipeCategory.COMBAT, ModItems.ECHO_STEEL_GREATSWORD)
                .criterion(hasItem(ModItems.ECHO_STEEL_INGOT), conditionsFromItem(ModItems.ECHO_STEEL_INGOT))
                .offerTo(exporter, "echo_steel_greatsword_smithing");

        ItemStack potion = PotionContentsComponent.createStack(Items.LINGERING_POTION, Potions.POISON);
        Item poison = potion.getItem();

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.POISON_DAGGER)
                .pattern(" PD")
                .pattern(" D ")
                .pattern("E  ")
                .input('E', Items.ECHO_SHARD)
                .input('D', Items.DISC_FRAGMENT_5)
                .input('P', poison)
                .criterion(hasItem(Items.ECHO_SHARD), conditionsFromItem(Items.ECHO_SHARD))
                .offerTo(exporter);

        // food recipes
        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.GOLDEN_STEAK)
                .pattern("GGG")
                .pattern("GSG")
                .pattern("GGG")
                .input('G', Items.GOLD_INGOT)
                .input('S', Items.COOKED_BEEF)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .offerTo(exporter);

        /*ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.BLACK_APPLE)
                .pattern("OOO")
                .pattern("OAO")
                .pattern("OOO")
                .input('O', Items.OBSIDIAN)
                .input('A', Items.APPLE)
                .criterion(hasItem(Items.OBSIDIAN), conditionsFromItem(Items.OBSIDIAN))
                .offerTo(exporter);*/

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.HONEY_BREAD)
                .input(Items.BREAD)
                .input(Items.HONEY_BOTTLE)
                .criterion(hasItem(Items.BREAD), conditionsFromItem(Items.BREAD))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, Items.BREAD)
                .pattern("  O")
                .pattern(" OO")
                .input('O', Items.WHEAT)
                .criterion(hasItem(Items.WHEAT), conditionsFromItem(Items.WHEAT))
                .offerTo(exporter);

        // QOL recipes
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.PAPER, 3)
                .pattern("  O")
                .pattern(" OO")
                .input('O', Items.SUGAR_CANE)
                .criterion(hasItem(Items.SUGAR_CANE), conditionsFromItem(Items.SUGAR_CANE))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.STRING, 4)
                .input(ItemTags.WOOL)
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.COBWEB, 4)
                .pattern("S S")
                .pattern(" S ")
                .pattern("S S")
                .input('S', Items.STRING)
                .criterion(hasItem(Items.STRING), conditionsFromItem(Items.STRING))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BLACK_APPLE_SEED, 4)
                .input(ModItems.BLACK_APPLE)
                .criterion(hasItem(ModItems.BLACK_APPLE), conditionsFromItem(ModItems.BLACK_APPLE))
                .offerTo(exporter);

        // hammer recipes
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.WOODEN_HAMMER)
                .pattern("PPP")
                .pattern("PSP")
                .pattern(" S ")
                .input('P', ItemTags.PLANKS)
                .input('S', Items.STICK)
                .criterion(hasItem(Items.OAK_PLANKS), conditionsFromItem(Items.OAK_PLANKS))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.STONE_HAMMER)
                .pattern("PPP")
                .pattern("PSP")
                .pattern(" S ")
                .input('P', ItemTags.STONE_TOOL_MATERIALS)
                .input('S', Items.STICK)
                .criterion(hasItem(Items.COBBLESTONE), conditionsFromItem(Items.COBBLESTONE))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.IRON_HAMMER)
                .pattern("PPP")
                .pattern("PSP")
                .pattern(" S ")
                .input('P', Items.IRON_INGOT)
                .input('S', Items.STICK)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.GOLDEN_HAMMER)
                .pattern("PPP")
                .pattern("PSP")
                .pattern(" S ")
                .input('P', Items.GOLD_INGOT)
                .input('S', Items.STICK)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.DIAMOND_HAMMER)
                .pattern("PPP")
                .pattern("PSP")
                .pattern(" S ")
                .input('P', Items.DIAMOND)
                .input('S', Items.STICK)
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);
        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.ofItems(ModItems.DIAMOND_HAMMER), Ingredient.ofItems(Items.NETHERITE_INGOT), RecipeCategory.TOOLS, ModItems.NETHERITE_HAMMER)
                .criterion(hasItem(Items.NETHERITE_INGOT), conditionsFromItem(Items.NETHERITE_INGOT))
                .offerTo(exporter, ExtendedCombat.id("netherite_hammer_smithing"));
        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(ModItems.NETHER_STEEL_UPGRADE), Ingredient.ofItems(ModItems.NETHERITE_HAMMER), Ingredient.ofItems(ModItems.NETHER_STEEL_INGOT), RecipeCategory.TOOLS, ModItems.NETHER_STEEL_HAMMER)
                .criterion(hasItem(ModItems.NETHER_STEEL_INGOT), conditionsFromItem(ModItems.NETHER_STEEL_INGOT))
                .offerTo(exporter, ExtendedCombat.id("nether_steel_hammer_smithing"));
        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(ModItems.ECHO_STEEL_UPGRADE), Ingredient.ofItems(ModItems.NETHER_STEEL_HAMMER), Ingredient.ofItems(ModItems.ECHO_STEEL_INGOT), RecipeCategory.TOOLS, ModItems.ECHO_STEEL_HAMMER)
                .criterion(hasItem(ModItems.ECHO_STEEL_INGOT), conditionsFromItem(ModItems.ECHO_STEEL_INGOT))
                .offerTo(exporter, ExtendedCombat.id("echo_steel_hammer_smithing"));
    }

    public static void registerPotionRecipes() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(Potions.AWKWARD, Items.AMETHYST_SHARD, ModPotions.SHRINKING_POTION_0);
            builder.registerPotionRecipe(ModPotions.SHRINKING_POTION_0, Items.REDSTONE, ModPotions.SHRINKING_POTION_1);
            builder.registerPotionRecipe(ModPotions.SHRINKING_POTION_1, Items.REDSTONE, ModPotions.SHRINKING_POTION_2);
            builder.registerPotionRecipe(ModPotions.SHRINKING_POTION_2, Items.REDSTONE, ModPotions.SHRINKING_POTION_3);

            builder.registerPotionRecipe(ModPotions.SHRINKING_POTION_0, Items.OBSIDIAN, ModPotions.PERMANENT_SHRINKING_POTION);
            builder.registerPotionRecipe(ModPotions.SHRINKING_POTION_1, Items.OBSIDIAN, ModPotions.PERMANENT_SHRINKING_POTION);
            builder.registerPotionRecipe(ModPotions.SHRINKING_POTION_2, Items.OBSIDIAN, ModPotions.PERMANENT_SHRINKING_POTION);
            builder.registerPotionRecipe(ModPotions.SHRINKING_POTION_3, Items.OBSIDIAN, ModPotions.PERMANENT_SHRINKING_POTION);
        });
    }
}
