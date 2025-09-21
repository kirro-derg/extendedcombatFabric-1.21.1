package dev.kirro.extendedcombat.mixin.enchantment.dash;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.kirro.extendedcombat.behavior.enchantment.DashBehavior;
import dev.kirro.extendedcombat.effects.ModStatusEffects;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class LivingEntityMixin {
    @ModifyReturnValue(method = "getFinalGravity", at = @At("RETURN"))
    private double extendedcombat$dash(double original) {
        DashBehavior dash = ModEntityComponents.DASH.getNullable(this);
        if (dash != null && dash.getImmunityTicks() > 0) {
            return 0;
        }

        return original;
    }
}
