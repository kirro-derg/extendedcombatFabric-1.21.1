package dev.kirro.extendedcombat.util;

import dev.kirro.ModConfig;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;

public class ExtendedCombatUtil {

    public static boolean isUnbreakable(ItemStack stack) {
        return ModConfig.disableDurability && !stack.isEmpty() && stack.contains(DataComponentTypes.MAX_DAMAGE) && !stack.isIn(ModItemTags.PERSISTENT_DURABILITY);
    }
}
