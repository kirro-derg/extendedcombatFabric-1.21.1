package dev.kirro.mixin.potion.concussion;

import dev.kirro.extendedcombat.effects.ModStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class EntityMixin {
    @Inject(method = "applyMovementInput", at = @At("HEAD"), cancellable = true)
    private void applyMovementInput(Vec3d movementInput, float slipperiness, CallbackInfoReturnable<Vec3d> cir) {
        if ((Object) this instanceof LivingEntity entity) {
            if (entity.hasStatusEffect(ModStatusEffects.CONCUSSION)) {
                cir.setReturnValue(Vec3d.ZERO);
            }
        }
    }
}
