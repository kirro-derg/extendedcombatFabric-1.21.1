package dev.kirro.extendedcombat.mixin.airMovement;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.kirro.extendedcombat.behavior.enchantment.AirMovementBehavior;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Shadow
    @Final
    private PlayerAbilities abilities;

    @ModifyReturnValue(method = "getOffGroundSpeed", at = @At("RETURN"))
    private float getAirMovementSpeed(float original) {
        AirMovementBehavior airMovement = ModEntityComponents.AIR_MOVEMENT.get(this);
        if (!this.abilities.flying) {
            return airMovement.movementMultiplier(original);
        }
        return original;
    }
}