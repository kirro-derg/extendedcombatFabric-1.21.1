package dev.kirro.extendedcombat.event;

import dev.kirro.extendedcombat.behavior.enchantment.BlinkBehavior;
import dev.kirro.extendedcombat.effects.ModStatusEffects;
import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import dev.kirro.extendedcombat.behavior.enchantment.AirJumpBehavior;
import dev.kirro.extendedcombat.behavior.enchantment.DashBehavior;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import virtuoel.pehkui.api.ScaleTypes;

public class EquipmentResetEvent implements ServerEntityEvents.EquipmentChange {
    @Override
    public void onChange(LivingEntity livingEntity, EquipmentSlot equipmentSlot, ItemStack previousStack, ItemStack currentStack) {
        if (equipmentSlot.isArmorSlot()) {
            if (EnchantmentHelper.hasAnyEnchantmentsWith(currentStack, ModEnchantmentEffectComponentTypes.AIR_JUMP)) {
                AirJumpBehavior behavior = ModEntityComponents.AIR_JUMP.getNullable(livingEntity);
                if (behavior != null) {
                    behavior.reset();
                    behavior.sync();
                }
            }
            if (EnchantmentHelper.hasAnyEnchantmentsWith(currentStack, ModEnchantmentEffectComponentTypes.DASH)) {
                DashBehavior behavior = ModEntityComponents.DASH.getNullable(livingEntity);
                if (behavior != null) {
                    behavior.reset();
                    behavior.sync();
                }
            }
            if (EnchantmentHelper.hasAnyEnchantmentsWith(currentStack, ModEnchantmentEffectComponentTypes.BLINK)) {
                BlinkBehavior behavior = ModEntityComponents.BLINK.getNullable(livingEntity);
                if (behavior != null) {
                    behavior.reset();
                    behavior.sync();
                }
            }

        }

        if (wearingNetherSteel(livingEntity)) {
            if (livingEntity.hasStatusEffect(ModStatusEffects.SHRINKING)) {
                ScaleTypes.BASE.getScaleData(livingEntity).setTargetScale(0.85f);
            } else {
                ScaleTypes.BASE.getScaleData(livingEntity).setTargetScale(1.25f);
            }
        } else if (wearingEchoSteel(livingEntity)) {
            if (livingEntity.hasStatusEffect(ModStatusEffects.SHRINKING)) {
                ScaleTypes.BASE.getScaleData(livingEntity).setTargetScale(0.95f);
            } else {
                ScaleTypes.BASE.getScaleData(livingEntity).setTargetScale(1.5f);
            }
        } else if (!wearingEchoSteel(livingEntity) && !wearingNetherSteel(livingEntity)) {
            if (livingEntity.hasStatusEffect(ModStatusEffects.SHRINKING)) {
                ScaleTypes.BASE.getScaleData(livingEntity).setTargetScale(0.75f);
            } else {
                ScaleTypes.BASE.getScaleData(livingEntity).setTargetScale(1.0f);
            }
        }
    }

    private boolean wearingNetherSteel(LivingEntity entity) {
        ItemStack helmet = entity.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chestplate = entity.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack leggings = entity.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);

        return helmet.isIn(ModItemTags.NETHER_STEEL_WEARABLES)
                && chestplate.isIn(ModItemTags.NETHER_STEEL_WEARABLES)
                && leggings.isIn(ModItemTags.NETHER_STEEL_WEARABLES)
                && boots.isIn(ModItemTags.NETHER_STEEL_WEARABLES);
    }

    private boolean wearingEchoSteel(LivingEntity entity) {
        ItemStack helmet = entity.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chestplate = entity.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack leggings = entity.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);

        return helmet.isIn(ModItemTags.ECHO_STEEL_WEARABLES)
                && chestplate.isIn(ModItemTags.ECHO_STEEL_WEARABLES)
                && leggings.isIn(ModItemTags.ECHO_STEEL_WEARABLES)
                && boots.isIn(ModItemTags.ECHO_STEEL_WEARABLES);
    }
}
