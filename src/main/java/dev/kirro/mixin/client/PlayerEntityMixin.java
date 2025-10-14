package dev.kirro.mixin.client;

import dev.kirro.ModConfig;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Inject(method = "isPartVisible", at = @At("HEAD"), cancellable = true)
    private void hideOverlay(PlayerModelPart modelPart, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = this.getEquippedStack(EquipmentSlot.CHEST);
        boolean hasSleeves = (stack.isIn(ModItemTags.SLEEVED_ARMOR) && ModConfig.showArmorSleeves);
        boolean hideHeadOverlay = false;
        boolean hideArmOverlay = hasSleeves;
        boolean hideChestOverlay = false;
        boolean hideLegOverlay = false;
        switch (modelPart) {
            case HAT -> {
                if (hideHeadOverlay) {
                    cir.setReturnValue(false);
                }
            }
            case LEFT_SLEEVE, RIGHT_SLEEVE -> {
                if (hideArmOverlay) {
                    cir.setReturnValue(false);
                }
            }
            case JACKET -> {
                if (hideChestOverlay) {
                    cir.setReturnValue(false);
                }
            }
            case LEFT_PANTS_LEG, RIGHT_PANTS_LEG -> {
                if (hideLegOverlay) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
