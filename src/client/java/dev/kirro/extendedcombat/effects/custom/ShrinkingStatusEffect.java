package dev.kirro.extendedcombat.effects.custom;

import dev.kirro.extendedcombat.effects.ModStatusEffects;
import dev.kirro.extendedcombat.item.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import virtuoel.pehkui.api.ScaleTypes;

public class ShrinkingStatusEffect extends StatusEffect {

    public ShrinkingStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (wearingNetherSteel(entity)) {
            ScaleTypes.BASE.getScaleData(entity).setTargetScale(0.85f);
        } else if (wearingEchoSteel(entity)) {
            ScaleTypes.BASE.getScaleData(entity).setTargetScale(0.95f);
        }else if (!wearingNetherSteel(entity) && !wearingEchoSteel(entity)) {
            ScaleTypes.BASE.getScaleData(entity).setTargetScale(0.75f);
        }

        return super.applyUpdateEffect(entity, amplifier);
    }

    private boolean wearingNetherSteel(LivingEntity entity) {
        return entity.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.NETHER_STEEL_HELMET) &&
                entity.getEquippedStack(EquipmentSlot.CHEST).isOf(ModItems.NETHER_STEEL_CHESTPLATE) &&
                entity.getEquippedStack(EquipmentSlot.LEGS).isOf(ModItems.NETHER_STEEL_LEGGINGS) &&
                entity.getEquippedStack(EquipmentSlot.FEET).isOf(ModItems.NETHER_STEEL_BOOTS);
    }

    private boolean wearingEchoSteel(LivingEntity entity) {
        return entity.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.ECHO_STEEL_HELMET) &&
                entity.getEquippedStack(EquipmentSlot.CHEST).isOf(ModItems.ECHO_STEEL_CHESTPLATE) &&
                entity.getEquippedStack(EquipmentSlot.LEGS).isOf(ModItems.ECHO_STEEL_LEGGINGS) &&
                entity.getEquippedStack(EquipmentSlot.FEET).isOf(ModItems.ECHO_STEEL_BOOTS);
    }



    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
