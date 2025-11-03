package dev.kirro.extendedcombat.behavior.item;

import dev.kirro.extendedcombat.tags.ModItemTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ModRepairManager {

    public static void repairItemsWithXP(PlayerEntity player, int xpAmount) {
        List<ItemStack> allItems = new ArrayList<>();
        allItems.addAll(player.getInventory().main);
        allItems.addAll(player.getInventory().armor);
        allItems.addAll(player.getInventory().offHand);

        for (ItemStack stack : allItems) {
            if (stack != null && stack.isIn(ModItemTags.REPAIRABLE_ITEMS) && stack.isDamaged()) {
                int repairAmount = (xpAmount * 16);
                int damage = stack.getDamage();
                int toRepair = Math.min(repairAmount, damage);

                stack.setDamage(damage - toRepair);
                xpAmount -= toRepair /2;

                if (xpAmount <= 0) break;
            }
        }
    }
}
