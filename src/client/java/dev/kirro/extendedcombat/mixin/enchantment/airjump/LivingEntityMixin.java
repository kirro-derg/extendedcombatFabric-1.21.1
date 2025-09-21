package dev.kirro.extendedcombat.mixin.enchantment.airjump;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import dev.kirro.extendedcombat.behavior.enchantment.AirJumpBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @WrapOperation(method = "computeFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getAttributeValue(Lnet/minecraft/registry/entry/RegistryEntry;)D", ordinal = 0))
    private double extendedcombat$airJump(LivingEntity instance, RegistryEntry<EntityAttribute> attribute, Operation<Double> original) {
        double value = original.call(instance, attribute);
        AirJumpBehavior behavior = ModEntityComponents.AIR_JUMP.getNullable(this);
        if (behavior != null && behavior.getCanUse()) {
            return value + behavior.getMaxJumps() - behavior.getJumpAmount();
        }
        return value;
    }
}
