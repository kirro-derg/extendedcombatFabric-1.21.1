package dev.kirro.extendedcombat.mixin.enchantment.dash;

import dev.kirro.extendedcombat.behavior.enchantment.DashBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    private void extendedcombat$dash(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof PlayerEntity player && !(player.getWorld().isClient)) {
            if (DashBehavior.getImmunityTicks() > 0 && DashBehavior.wasUsedMidair()) {
                cir.setReturnValue(false);
            }
        }
    }
}
