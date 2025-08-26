package dev.kirro.extendedcombat.mixin.potion.shrinking;

import dev.kirro.extendedcombat.effects.ModStatusEffects;
import dev.kirro.extendedcombat.effects.custom.ShrinkingStatusEffect;
import dev.kirro.extendedcombat.item.ModItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.api.ScaleTypes;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "updatePotionVisibility", at = @At("HEAD"))
    private void updatePotionVisibility(CallbackInfo ci) {
        if ((Object) this instanceof LivingEntity entity) {
            removeEffect(entity);
        }
    }

    @Unique
    public void removeEffect(LivingEntity entity) {
        if (!entity.hasStatusEffect(ModStatusEffects.SHRINKING) && !wearingEchoSteel(entity) && !wearingNetherSteel(entity)) {
            ScaleTypes.BASE.getScaleData(entity).setTargetScale(1.0f);
        } else if (!entity.hasStatusEffect(ModStatusEffects.SHRINKING) && !wearingEchoSteel(entity) && wearingNetherSteel(entity)) {
            ScaleTypes.BASE.getScaleData(entity).setTargetScale(1.25f);
        } else if (!entity.hasStatusEffect(ModStatusEffects.SHRINKING) && wearingEchoSteel(entity) && !wearingNetherSteel(entity)) {
            ScaleTypes.BASE.getScaleData(entity).setTargetScale(1.5f);
        }
    }

    @Unique
    private boolean wearingNetherSteel(LivingEntity entity) {
        return entity.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.NETHER_STEEL_HELMET) &&
                entity.getEquippedStack(EquipmentSlot.CHEST).isOf(ModItems.NETHER_STEEL_CHESTPLATE) &&
                entity.getEquippedStack(EquipmentSlot.LEGS).isOf(ModItems.NETHER_STEEL_LEGGINGS) &&
                entity.getEquippedStack(EquipmentSlot.FEET).isOf(ModItems.NETHER_STEEL_BOOTS);
    }

    @Unique
    private boolean wearingEchoSteel(LivingEntity entity) {
        return entity.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.ECHO_STEEL_HELMET) &&
                entity.getEquippedStack(EquipmentSlot.CHEST).isOf(ModItems.ECHO_STEEL_CHESTPLATE) &&
                entity.getEquippedStack(EquipmentSlot.LEGS).isOf(ModItems.ECHO_STEEL_LEGGINGS) &&
                entity.getEquippedStack(EquipmentSlot.FEET).isOf(ModItems.ECHO_STEEL_BOOTS);
    }
}
