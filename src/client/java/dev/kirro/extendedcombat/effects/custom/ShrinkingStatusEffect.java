package dev.kirro.extendedcombat.effects.custom;

import dev.kirro.extendedcombat.effects.ModStatusEffects;
import dev.kirro.extendedcombat.item.ModItems;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import org.spongepowered.asm.mixin.Unique;
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
        return entity.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.NETHER_STEEL_WEARABLES) &&
                entity.getEquippedStack(EquipmentSlot.CHEST).isIn(ModItemTags.NETHER_STEEL_WEARABLES) &&
                entity.getEquippedStack(EquipmentSlot.LEGS).isIn(ModItemTags.NETHER_STEEL_WEARABLES) &&
                entity.getEquippedStack(EquipmentSlot.FEET).isIn(ModItemTags.NETHER_STEEL_WEARABLES);
    }

    private boolean wearingEchoSteel(LivingEntity entity) {
        return entity.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.ECHO_WEARABLES) &&
                entity.getEquippedStack(EquipmentSlot.CHEST).isIn(ModItemTags.ECHO_WEARABLES) &&
                entity.getEquippedStack(EquipmentSlot.LEGS).isIn(ModItemTags.ECHO_WEARABLES) &&
                entity.getEquippedStack(EquipmentSlot.FEET).isIn(ModItemTags.ECHO_WEARABLES) ;
    }



    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
