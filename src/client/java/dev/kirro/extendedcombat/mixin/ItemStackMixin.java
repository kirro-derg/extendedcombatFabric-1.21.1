package dev.kirro.extendedcombat.mixin;

import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract int getDamage();

    @Inject(method = "damage(ILnet/minecraft/server/world/ServerWorld;Lnet/minecraft/server/network/ServerPlayerEntity;Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isDamageable()Z"))
    private <T extends LivingEntity> void extendedcombat$disablesDurability(int amount, ServerWorld world, @Nullable ServerPlayerEntity player, Consumer<Item> breakCallback, CallbackInfo ci) {
        ItemStack stack = (ItemStack) (Object) this;
        if (player != null && ExtendedCombatUtil.isUnbreakable(stack)) {
            Criteria.ITEM_DURABILITY_CHANGED.trigger(player, stack, getDamage());
        }
    }

    @Inject(method = "isDamageable", at = @At("HEAD"), cancellable = true)
    private void extendedcombat$disablesDurability(CallbackInfoReturnable<Boolean> cir) {
        if (ExtendedCombatUtil.isUnbreakable((ItemStack) (Object) this)) {
            cir.setReturnValue(false);
        }
    }
}
