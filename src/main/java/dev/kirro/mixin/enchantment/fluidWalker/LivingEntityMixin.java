package dev.kirro.mixin.enchantment.fluidWalker;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import dev.kirro.extendedcombat.enchantment.custom.FluidWalkerEnchantmentEffect;
import dev.kirro.extendedcombat.item.ModItems;
import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Inject(method = "canWalkOnFluid", at = @At("HEAD"), cancellable = true)
    private void canFluidWalk(FluidState state, CallbackInfoReturnable<Boolean> cir) {
        if (ExtendedCombatUtil.canWalkOn((LivingEntity) (Object) this)) {
            cir.setReturnValue(true);
        }
    }

    @ModifyArg(method = "applyMovementInput", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;updateVelocity(FLnet/minecraft/util/math/Vec3d;)V"))
    private float increaseFluidMovementSpeed(float original) {
        ItemStack stack = this.getEquippedStack(EquipmentSlot.FEET);
        boolean submergedWithEnchant = ExtendedCombatUtil.isTouchingFluid(this) && EnchantmentHelper.hasAnyEnchantmentsWith(stack, ModEnchantmentEffectComponentTypes.FLUID_WALKER);
        float value = submergedWithEnchant ? FluidWalkerEnchantmentEffect.getValue((LivingEntity) (Object) this) : 1;
        return original * value;
    }

    @ModifyArg(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;updateVelocity(FLnet/minecraft/util/math/Vec3d;)V"))
    private float increaseLavaMovementSpeed(float original) {
        ItemStack stack = this.getEquippedStack(EquipmentSlot.FEET);
        boolean submergedWithEnchant = ExtendedCombatUtil.isTouchingFluidOfType(this, FluidTags.LAVA) && EnchantmentHelper.hasAnyEnchantmentsWith(stack, ModEnchantmentEffectComponentTypes.FLUID_WALKER);
        float value = submergedWithEnchant ? FluidWalkerEnchantmentEffect.getValue((LivingEntity) (Object) this) * 2 : 1;
        return original * value;
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
