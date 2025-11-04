package dev.kirro.mixin.item;

import dev.kirro.extendedcombat.behavior.item.HideWoolHoodBehavior;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import dev.kirro.extendedcombat.item.custom.WoolArmorItem;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.collection.DefaultedList;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public class ScreenHandlerMixin {
    @Shadow
    @Final
    public DefaultedList<Slot> slots;

    @Inject(method = "internalOnSlotClick", at = @At("HEAD"), cancellable = true)
    private void onClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT && slotIndex >= 0 && slotIndex < this.slots.size()) {
            Slot slot = this.slots.get(slotIndex);
            ItemStack stack = slot.getStack();
            HideWoolHoodBehavior hood = ModEntityComponents.HIDE_HOOD.getNullable(player);
            if (hood != null && actionType == SlotActionType.QUICK_MOVE && stack.getItem() instanceof WoolArmorItem) {
                if (stack.isIn(ModItemTags.CLOAK)) hood.useHood();
                else if (stack.isIn(ModItemTags.MASK)) hood.useMask();
                ci.cancel();
            }
        }
    }
}
