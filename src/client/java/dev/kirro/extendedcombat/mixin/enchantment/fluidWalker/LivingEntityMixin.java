package dev.kirro.extendedcombat.mixin.enchantment.fluidWalker;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.kirro.extendedcombat.enchantment.custom.FluidWalkerEnchantmentEffect;
import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
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
}
