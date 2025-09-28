package dev.kirro.extendedcombat.enchantment.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import org.apache.commons.lang3.mutable.MutableFloat;

public record FluidWalkerEnchantmentEffect(EnchantmentValueEffect speed) {
    public static final Codec<FluidWalkerEnchantmentEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EnchantmentValueEffect.CODEC.fieldOf("speed").forGetter(FluidWalkerEnchantmentEffect::speed)
    ).apply(instance, FluidWalkerEnchantmentEffect::new));

    public static float getValue(LivingEntity entity) {
        MutableFloat mutableFloat = new MutableFloat();
        for (ItemStack stack : entity.getArmorItems()) {
            EnchantmentHelper.forEachEnchantment(stack, (enchantment, level) -> {
                FluidWalkerEnchantmentEffect effect = enchantment.value().effects().get(ModEnchantmentEffectComponentTypes.FLUID_WALKER);
                if (effect != null && submerged(entity)) {
                    if (level == 1) {
                        mutableFloat.setValue(1.5);
                    } else if (level == 2) {
                        mutableFloat.setValue(1.7);
                    } else if (level == 3) {
                        mutableFloat.setValue(1.9);
                    }
                }
            });
        }

        if (mutableFloat.floatValue() == 1.5) {
            return 1.5f;
        } else if (mutableFloat.floatValue() == 1.7) {
            return 2.0f;
        } else {
            return 2.5f;
        }
    }

    private static boolean submerged(LivingEntity entity) {
        return ExtendedCombatUtil.isSubmerged(entity);
    }
}