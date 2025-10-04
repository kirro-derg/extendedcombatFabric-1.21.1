package dev.kirro.extendedcombat.mixin.enchantment.fluidWalker;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.kirro.extendedcombat.item.ModItems;
import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "canWalkOnFluid", at = @At("HEAD"), cancellable = true)
    private void canFluidWalk(FluidState state, CallbackInfoReturnable<Boolean> cir) {
        if (ExtendedCombatUtil.canWalkOn((LivingEntity) (Object) this)) {
            cir.setReturnValue(true);
        }
    }

    @ModifyReturnValue(method = "computeFallDamage", at = @At(value = "RETURN"))
    private int handFallDamage(int original) {
        if ((Object) this instanceof PlayerEntity player) {
            ItemStack stack = player.getEquippedStack(EquipmentSlot.FEET);
            if (stack.isOf(ModItems.NETHER_STEEL_BOOTS) || stack.isOf(ModItems.ECHO_STEEL_BOOTS)) {
                return (int) (original * 0.7f);
            }
        }
        return original;
    }
}
