package dev.kirro.extendedcombat.event;

import dev.kirro.extendedcombat.behavior.enchantment.BlinkBehavior;
import dev.kirro.extendedcombat.effects.ModStatusEffects;
import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import dev.kirro.extendedcombat.behavior.enchantment.AirJumpBehavior;
import dev.kirro.extendedcombat.behavior.enchantment.DashBehavior;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import virtuoel.pehkui.api.ScaleTypes;

public class EquipmentChange implements ServerEntityEvents.EquipmentChange {
    @Override
    public void onChange(LivingEntity livingEntity, EquipmentSlot equipmentSlot, ItemStack previousStack, ItemStack currentStack) {
        if (equipmentSlot.isArmorSlot()) {
            if (EnchantmentHelper.hasAnyEnchantmentsWith(currentStack, ModEnchantmentEffectComponentTypes.AIR_JUMP)) {
                AirJumpBehavior airJump = ModEntityComponents.AIR_JUMP.getNullable(livingEntity);
                if (airJump != null) {
                    airJump.reset();
                    airJump.sync();
                }
            }
            if (EnchantmentHelper.hasAnyEnchantmentsWith(currentStack, ModEnchantmentEffectComponentTypes.DASH)) {
                DashBehavior dash = ModEntityComponents.DASH.getNullable(livingEntity);
                if (dash != null) {
                    dash.reset();
                    dash.sync();
                }
            }
            if (EnchantmentHelper.hasAnyEnchantmentsWith(currentStack, ModEnchantmentEffectComponentTypes.BLINK)) {
                BlinkBehavior blink = ModEntityComponents.BLINK.getNullable(livingEntity);
                if (blink != null) {
                    blink.reset();
                    blink.sync();
                }
            }

        }

        if (livingEntity.hasStatusEffect(ModStatusEffects.SHRINKING)) {
            ScaleTypes.BASE.getScaleData(livingEntity).setTargetScale(0.85f);
        } else {
            ScaleTypes.BASE.getScaleData(livingEntity).setTargetScale(1.0f);
        }
    }
}
