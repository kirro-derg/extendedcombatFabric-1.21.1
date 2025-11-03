package dev.kirro.mixin.item;

import dev.kirro.extendedcombat.item.custom.MilkBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShapelessRecipe.class)
public abstract class RecipeMixin<T extends RecipeInput> implements CraftingRecipe {
    @Shadow
    public abstract RecipeSerializer<?> getSerializer();

    @Shadow
    public abstract ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup);

    //@Inject(method = "getRemainder", at = @At("RETURN"), cancellable = true)
    private void conditionalRemainder(T input, CallbackInfoReturnable<DefaultedList<ItemStack>> cir) {
        DefaultedList<ItemStack> defaultedList = cir.getReturnValue();
        CraftingRecipe recipe = (CraftingRecipe)this;
        ItemStack result = recipe.getResult(null);

        if (result.getItem() instanceof MilkBottleItem) {
            for (int i = 0; i < defaultedList.size(); i++) {
                Item item = input.getStackInSlot(i).getItem();
                if (item.hasRecipeRemainder()) {
                    defaultedList.set(i, ItemStack.EMPTY);
                }
            }
            cir.setReturnValue(defaultedList);
        }
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingRecipeInput input) {
        if (this.getResult(null).getItem() instanceof MilkBottleItem) {
            DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(input.getSize(), ItemStack.EMPTY);
            for (int i = 0; i < defaultedList.size(); i++) {
                Item item = input.getStackInSlot(i).getItem();
                if (item.hasRecipeRemainder()) {
                    defaultedList.set(i, ItemStack.EMPTY);
                }
            }
            return defaultedList;
        }
        return CraftingRecipe.super.getRemainder(input);
    }
}
