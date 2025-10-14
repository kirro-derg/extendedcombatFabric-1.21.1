package dev.kirro.mixin.potion.shrinking;

import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "updatePotionVisibility", at = @At("HEAD"))
    private void updatePotionVisibility(CallbackInfo ci) {
            ExtendedCombatUtil.removeEffect((LivingEntity) (Object) this);
    }
}
