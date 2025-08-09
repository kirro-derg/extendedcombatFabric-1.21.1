package dev.kirro.extendedcombat.mixin.enchantment.blink;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.kirro.extendedcombat.behavior.enchantment.BlinkBehavior;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import dev.kirro.extendedcombat.util.ExtendedCombatClientUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public class EntityMixin {
    @ModifyReturnValue(method = "isInvisible", at = @At("RETURN"))
    public boolean isInvisible(boolean original) {
        if ((Object) this instanceof PlayerEntity player) {
            BlinkBehavior blink = ModEntityComponents.BLINK.get(player);

            if (blink.isInvisible() && blink.getDuration() > 0 || ExtendedCombatClientUtil.shouldHideArmor(player)) {
                return true;
            }
        }
        return original;
    }
}
