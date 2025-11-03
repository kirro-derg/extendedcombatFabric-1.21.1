package dev.kirro.mixin.item;

import dev.kirro.extendedcombat.item.ModItems;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CowEntity.class)
public class CowEntityMixin {
    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void fillBottle(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isOf(Items.GLASS_BOTTLE) && !((CowEntity)(Object)this).isBaby()) {
            player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0f, 1.0f);
            ItemStack filledBottle = ItemUsage.exchangeStack(stack, player, ModItems.MILK_BOTTLE.getDefaultStack());
            player.setStackInHand(hand, filledBottle);
            cir.setReturnValue(ActionResult.success(((CowEntity)(Object)this).getWorld().isClient));
        }
    }
}
