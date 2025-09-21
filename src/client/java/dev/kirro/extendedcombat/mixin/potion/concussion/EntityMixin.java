package dev.kirro.extendedcombat.mixin.potion.concussion;

import dev.kirro.extendedcombat.effects.ModStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    private void move(MovementType movementType, Vec3d movement, CallbackInfo ci) {
        if ((Object) this instanceof LivingEntity entity) {
            if (entity.hasStatusEffect(ModStatusEffects.CONCUSSION)) {
                ci.cancel();
            }
        }
    }
}
