package dev.kirro.extendedcombat.item.custom;

import com.google.common.base.Suppliers;
import dev.kirro.extendedcombat.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Supplier;

public enum ModToolMaterials implements ToolMaterial {
    NETHER_STEEL(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 8124, 20.0F, 7F, 44, () -> Ingredient.ofItems(ModItems.NETHER_STEEL_INGOT)),
    ECHO_STEEL(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 9124, 23.0f, 8f, 44, () -> Ingredient.ofItems(ModItems.ECHO_STEEL_INGOT)),
    VOID_STEEL(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 10124, 26.0f, 9f, 44, () -> Ingredient.ofItems(ModItems.NETHER_STEEL_INGOT));

    private final TagKey<Block> inverseTag;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    ModToolMaterials(TagKey<Block> inverseTag, int itemDurability, float miningSpeed,
                     float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.inverseTag = inverseTag;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = Suppliers.memoize(repairIngredient::get);
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return this.inverseTag;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
