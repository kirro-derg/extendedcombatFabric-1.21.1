package dev.kirro.extendedcombat.mixin.duplicates;

import com.llamalad7.mixinextras.sugar.Local;
import dev.kirro.extendedcombat.util.ExtendedCombatClientUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyBinding.class)
public class KeyBindingMixin {
    @Inject(method = "onKeyPressed", at = @At("TAIL"))
    private static void extendedcombat$allowDuplicateKeybinds(InputUtil.Key key, CallbackInfo ci, @Local KeyBinding keyBinding) {
        if (ExtendedCombatClientUtil.allowDuplicateKeybinds(keyBinding)) {
            for (KeyBinding keyBinding2 : MinecraftClient.getInstance().options.allKeys) {
                if (keyBinding != keyBinding2 && keyBinding.equals(keyBinding2) && ExtendedCombatClientUtil.allowDuplicateKeybinds(keyBinding2)) {
                    keyBinding2.timesPressed++;
                }
            }
        }
    }

    @Inject(method = "setKeyPressed", at = @At("TAIL"))
    private static void extendedcombat$allowDuplicateKeybinds(InputUtil.Key key, boolean pressed, CallbackInfo ci, @Local KeyBinding keyBinding) {
        if (ExtendedCombatClientUtil.allowDuplicateKeybinds(keyBinding)) {
            for (KeyBinding keyBinding2 : MinecraftClient.getInstance().options.allKeys) {
                if (keyBinding != keyBinding2 && keyBinding.equals(keyBinding2) && ExtendedCombatClientUtil.allowDuplicateKeybinds(keyBinding2)) {
                    keyBinding2.setPressed(pressed);
                }
            }
        }
    }
}
