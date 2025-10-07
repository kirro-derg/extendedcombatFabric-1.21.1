package dev.kirro.extendedcombat.mixin.client;

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
        boolean hideOverlay = !(stack.isIn(ModItemTags.SLEEVED_ARMOR) && ModConfig.showArmorSleeves);
        switch (modelPart) {
            case HAT, JACKET, LEFT_PANTS_LEG, RIGHT_PANTS_LEG -> {}
            case LEFT_SLEEVE, RIGHT_SLEEVE -> cir.setReturnValue(hideOverlay);
        }
    }
}
