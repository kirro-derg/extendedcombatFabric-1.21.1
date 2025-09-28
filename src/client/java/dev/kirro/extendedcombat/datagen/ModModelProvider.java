package dev.kirro.extendedcombat.datagen;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.block.ModBlocks;
import dev.kirro.extendedcombat.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public static final Model GREATSWORD_TEMPLATE = model("item/greatsword_template_handheld", "_handheld", TextureKey.LAYER0);

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NETHER_STEEL_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ECHO_STEEL_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generator.register(ModItems.NETHER_STEEL_HANDLE, Models.GENERATED);
        generator.register(ModItems.WOODEN_HANDLE, Models.GENERATED);
        generator.register(ModItems.STATUE, Models.GENERATED);
        generator.register(ModItems.NETHER_STEEL_INGOT, Models.GENERATED);
        generator.register(ModItems.ECHO_STEEL_INGOT, Models.GENERATED);
        generator.register(ModItems.NETHER_STEEL_UPGRADE, Models.GENERATED);
        generator.register(ModItems.ECHO_STEEL_UPGRADE, Models.GENERATED);
        generator.register(ModItems.NETHER_STEEL_PICKAXE, Models.HANDHELD);
        generator.register(ModItems.POISON_DAGGER, Models.HANDHELD);
        generator.register(ModItems.WOODEN_HAMMER, Models.HANDHELD);
        generator.register(ModItems.STONE_HAMMER, Models.HANDHELD);
        generator.register(ModItems.IRON_HAMMER, Models.HANDHELD);
        generator.register(ModItems.GOLDEN_HAMMER, Models.HANDHELD);
        generator.register(ModItems.DIAMOND_HAMMER, Models.HANDHELD);
        generator.register(ModItems.NETHERITE_HAMMER, Models.HANDHELD);
        generator.register(ModItems.NETHER_STEEL_HAMMER, Models.HANDHELD);
        generator.register(ModItems.ECHO_STEEL_HAMMER, Models.HANDHELD);
        generator.register(ModItems.BLACK_APPLE_SEED, Models.GENERATED);
        generator.register(ModItems.BLACK_APPLE, Models.GENERATED);
        generator.register(ModItems.GOLDEN_STEAK, Models.GENERATED);
        generator.register(ModItems.HONEY_BREAD, Models.GENERATED);

        createItemVariants(GREATSWORD_TEMPLATE, ModItems.WOODEN_GREATSWORD, generator);
        createItemVariants(GREATSWORD_TEMPLATE, ModItems.STONE_GREATSWORD, generator);
        createItemVariants(GREATSWORD_TEMPLATE, ModItems.IRON_GREATSWORD, generator);
        createItemVariants(GREATSWORD_TEMPLATE, ModItems.GOLDEN_GREATSWORD, generator);
        createItemVariants(GREATSWORD_TEMPLATE, ModItems.DIAMOND_GREATSWORD, generator);
        createItemVariants(GREATSWORD_TEMPLATE, ModItems.NETHERITE_GREATSWORD, generator);
        createItemVariants(GREATSWORD_TEMPLATE, ModItems.NETHER_STEEL_GREATSWORD, generator);
        createItemVariants(GREATSWORD_TEMPLATE, ModItems.ECHO_STEEL_GREATSWORD, generator);

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

    private static Model model(String parent, @Nullable String variant, TextureKey... keys) {
        return new Model(Optional.of(ExtendedCombat.id(parent)), Optional.ofNullable(variant), keys);
    }

    private void createItemVariants(Model template, Item item, ItemModelGenerator generator) {
        registerHandheldVariant(template, Registries.ITEM.getId(item), generator);
        registerInventoryVariant(Registries.ITEM.getId(item), generator);
    }

    private void registerHandheldVariant(Model template, Identifier id, ItemModelGenerator generator) {
        Identifier modelName = idWithSuffix(id, "_handheld");
        Identifier textureName = idWithSuffix(id, "_handheld");
        template.upload(modelName, TextureMap.layer0(textureName), generator.writer);
    }

    private void registerInventoryVariant(Identifier id, ItemModelGenerator generator) {
        Identifier modelName = id(id);
        Identifier textureName = id(id);
        Models.HANDHELD.upload(modelName, TextureMap.layer0(textureName), generator.writer);
    }

    public static Identifier id(Identifier itemId) {
        return itemId.withPrefixedPath("item/");
    }

    public static Identifier idWithSuffix(Identifier itemId, String suffix) {
        return itemId.withPath(path -> "item/" + path + suffix);
    }
}
