package dev.kirro.extendedcombat.mixin;

import net.minecraft.entity.decoration.ArmorStandEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStandEntity.class)
public class ArmorStandMixin {
    @Inject(method = "shouldShowArms", at = @At("RETURN"), cancellable = true)
    public void shouldShowArms(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
