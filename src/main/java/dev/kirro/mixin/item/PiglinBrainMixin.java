package dev.kirro.mixin.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterials;
import net.minecraft.registry.tag.ItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinBrain.class)
public class PiglinBrainMixin {
    @Inject(method = "wearsGoldArmor", at = @At("HEAD"), cancellable = true)
    private static void wearingAcceptableArmor(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        for (ItemStack stack : entity.getAllArmorItems()) {
            ArmorTrim trim = stack.get(DataComponentTypes.TRIM);
            if (stack.getItem() instanceof ArmorItem && (stack.isIn(ItemTags.PIGLIN_LOVED)
                    || (trim != null && trim.getMaterial().matchesId(ArmorTrimMaterials.GOLD.getValue())))) {
                cir.setReturnValue(true);
            }
        }
    }
}
