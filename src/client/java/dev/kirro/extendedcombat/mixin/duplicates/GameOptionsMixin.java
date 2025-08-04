package dev.kirro.extendedcombat.mixin.duplicates;

import dev.kirro.extendedcombat.util.ExtendedCombatClientUtil;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(value = GameOptions.class, priority = -1)
public class GameOptionsMixin {
    @Shadow
    @Final
    public KeyBinding[] allKeys;

    @Inject(method = "load", at = @At("HEAD"))
    private void extendedcombat$allowDuplicateKeybinds(CallbackInfo ci) {
        ExtendedCombatClientUtil.MOVEMENT.addAll(Arrays.asList(allKeys));
    }
}
