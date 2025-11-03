package dev.kirro.mixin.item;

import dev.kirro.extendedcombat.item.custom.MilkBottleItem;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingRecipeRegistry.Builder.class)
public class BrewingRecipeBuilderMixin {
    @Inject(method = "assertPotion", at = @At("HEAD"), cancellable = true)
    private static void assertPotion(Item potionType, CallbackInfo ci) {
        if (potionType instanceof GlassBottleItem || potionType instanceof MilkBottleItem) {
            ci.cancel();
        }
    }

    @Mixin(BrewingRecipeRegistry.class)
    public static class BrewingRecipeRegistryMixin {
        @Inject(method = "isValidIngredient", at = @At("HEAD"), cancellable = true)
        private void isIngredient(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
            if (stack.getItem() instanceof GlassBottleItem || stack.getItem() instanceof MilkBottleItem) {
                cir.setReturnValue(true);
            }
        }
    }

    @Mixin(BrewingStandBlockEntity.class)
    public static class BrewingStandBlockEntityMixin {
        @Inject(method = "isValid", at = @At("HEAD"), cancellable = true)
        private void isValid(int slot, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
            if (slot == 4) {
                if (stack.getItem() instanceof GlassBottleItem || stack.getItem() instanceof MilkBottleItem) {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
