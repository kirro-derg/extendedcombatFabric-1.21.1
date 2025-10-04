package dev.kirro.extendedcombat.mixin.potion.shrinking;

import dev.kirro.extendedcombat.effects.ModStatusEffects;
import dev.kirro.extendedcombat.tags.ModItemTags;
import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
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
            ExtendedCombatUtil.removeEffect(entity);
        }
    }
}
