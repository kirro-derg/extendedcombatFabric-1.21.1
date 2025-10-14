package dev.kirro.mixin.enchantment.dash;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.kirro.extendedcombat.behavior.enchantment.DashBehavior;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

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
