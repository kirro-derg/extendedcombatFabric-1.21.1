package dev.kirro.extendedcombat.mixin.potion.shrinking;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.kirro.extendedcombat.effects.ModStatusEffects;
import dev.kirro.extendedcombat.item.ModItems;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.Collection;
import java.util.Iterator;

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
        return entity.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.NETHER_STEEL_WEARABLES) &&
                entity.getEquippedStack(EquipmentSlot.CHEST).isIn(ModItemTags.NETHER_STEEL_WEARABLES) &&
                entity.getEquippedStack(EquipmentSlot.LEGS).isIn(ModItemTags.NETHER_STEEL_WEARABLES) &&
                entity.getEquippedStack(EquipmentSlot.FEET).isIn(ModItemTags.NETHER_STEEL_WEARABLES);
    }

    @Unique
    private boolean wearingEchoSteel(LivingEntity entity) {
        return entity.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.ECHO_WEARABLES) &&
                entity.getEquippedStack(EquipmentSlot.CHEST).isIn(ModItemTags.ECHO_WEARABLES) &&
                entity.getEquippedStack(EquipmentSlot.LEGS).isIn(ModItemTags.ECHO_WEARABLES) &&
                entity.getEquippedStack(EquipmentSlot.FEET).isIn(ModItemTags.ECHO_WEARABLES) ;
    }
}
