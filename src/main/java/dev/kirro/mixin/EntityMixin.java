package dev.kirro.mixin;

import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "isOnFire", at = @At("HEAD"), cancellable = true)
    private void isOnFire(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof LivingEntity entity) {
            if (ExtendedCombatUtil.isFlameResistant(entity)) {
                cir.setReturnValue(false);
            }
        }
    }


    /*@Inject(method = "doesRenderOnFire", at = @At("HEAD"), cancellable = true)
    private void renderOnFire(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof LivingEntity entity) {
            if (ExtendedCombatUtil.isFlameResistant(entity)) {
                cir.setReturnValue(false);
            }
        }
    }*/
}
