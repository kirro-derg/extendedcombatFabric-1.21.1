package dev.kirro.mixin.client;

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
        ItemStack chest = this.getEquippedStack(EquipmentSlot.CHEST);
        boolean wearingCloak = chest.isIn(ModItemTags.CLOAK);
        //switch (modelPart) {
            //case JACKET, LEFT_SLEEVE, RIGHT_SLEEVE, RIGHT_PANTS_LEG, LEFT_PANTS_LEG -> {
            //    if (wearingCloak) cir.setReturnValue(false);
            //}
        //}
    }
}
