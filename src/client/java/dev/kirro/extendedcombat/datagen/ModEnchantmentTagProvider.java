package dev.kirro.extendedcombat.datagen;

import dev.kirro.extendedcombat.enchantment.ModEnchantmentTags;
import dev.kirro.extendedcombat.enchantment.ModEnchantments;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EnchantmentTags;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentTagProvider extends FabricTagProvider.EnchantmentTagProvider {
    public ModEnchantmentTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(EnchantmentTags.IN_ENCHANTING_TABLE)
                .add(ModEnchantments.DASH)
                .add(ModEnchantments.AIR_JUMP)
                .add(ModEnchantments.BLINK)
                .add(ModEnchantments.OBSCURITY)
                .add(ModEnchantments.VANITY)
                .add(ModEnchantments.STEALTH)
                .add(Enchantments.MENDING)
                .add(ModEnchantments.BURST)
        ;

        getOrCreateTagBuilder(ModEnchantmentTags.COMBAT_EXCLUSIVE_SET)
                .add(ModEnchantments.STEALTH, ModEnchantments.BLINK)
        ;

        getOrCreateTagBuilder(ModEnchantmentTags.DURABILITY_EXCLUSIVE_SET)
                .add(ModEnchantments.KEEPSAKE, Enchantments.MENDING)
        ;

        getOrCreateTagBuilder(ModEnchantmentTags.ELYTRA_EXCLUSIVE_SET)
                .add(ModEnchantments.BURST, Enchantments.MENDING, ModEnchantments.KEEPSAKE)
        ;
    }
}
