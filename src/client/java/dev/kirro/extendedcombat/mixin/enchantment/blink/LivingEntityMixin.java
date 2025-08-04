package dev.kirro.extendedcombat.mixin.enchantment.blink;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.kirro.extendedcombat.behavior.enchantment.BlinkBehavior;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @ModifyArgs(method = "updatePotionVisibility", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setInvisible(Z)V"))
    protected void updatePotionVisibility(Args args) {
        if ((LivingEntity)(Object) this instanceof PlayerEntity player) {
            BlinkBehavior behavior = ModEntityComponents.BLINK.get(player);

            if (behavior.isInvisible() && behavior.getDuration() > 0) {
                args.set(0, true);
            }
        }
    }
}
