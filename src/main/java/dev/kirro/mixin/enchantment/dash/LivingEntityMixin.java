package dev.kirro.mixin.enchantment.dash;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.kirro.extendedcombat.behavior.enchantment.DashBehavior;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @ModifyReturnValue(method = "computeFallDamage", at = @At("RETURN"))
    private int extendedcombat$dash(int original) {
        DashBehavior dash = ModEntityComponents.DASH.getNullable(this);
        if (dash != null && dash.getImmunityTicks() > 0) {
            return 0;
        }
        return original;
    }
}
