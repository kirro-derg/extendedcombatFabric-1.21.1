package dev.kirro.mixin.potion.concussion;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.kirro.extendedcombat.effects.ModStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class EntityMixin {
    @ModifyReturnValue(method = "applyMovementInput", at = @At("RETURN"))
    private Vec3d applyMovementInput(Vec3d original, Vec3d movementInput, float slipperiness) {
        if ((Object) this instanceof LivingEntity entity) {
            if (entity.hasStatusEffect(ModStatusEffects.CONCUSSION)) {
                return original.multiply(0, 1.0f, 0);
            }
        }
        return original;
    }
}
