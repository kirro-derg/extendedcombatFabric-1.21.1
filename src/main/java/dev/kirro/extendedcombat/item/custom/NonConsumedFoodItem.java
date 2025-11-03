package dev.kirro.extendedcombat.item.custom;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NonConsumedFoodItem extends Item {
    public NonConsumedFoodItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (stack.contains(DataComponentTypes.FOOD)) {
            user.eatFood(world, stack.copy(), stack.get(DataComponentTypes.FOOD));
        }
        return stack;
    }
}
