package dev.kirro.extendedcombat.behavior.item;

import dev.kirro.ModConfig;
import dev.kirro.extendedcombat.item.ModItems;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModRepairManager {

    public static void repairItemsWithXP(PlayerEntity player, int xpAmount) {
        List<ItemStack> allItems = new ArrayList<>();
        allItems.addAll(player.getInventory().main);
        allItems.addAll(player.getInventory().armor);


        for (ItemStack stack : allItems) {
            if (ModConfig.xpRepairActive && stack != null && stack.isIn(ModItemTags.REPAIRABLE_ITEMS) && stack.isDamaged()) {
                int repairAmount = xpAmount * 16;
                int damage = stack.getDamage();
                int toRepair = Math.min(repairAmount, damage);

                stack.setDamage(damage - toRepair);
                xpAmount -= toRepair /2;

                if (xpAmount <= 0) break;
            }
        }

    }
}
