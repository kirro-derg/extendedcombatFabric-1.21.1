package dev.kirro.extendedcombat.enchantment.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.mutable.MutableFloat;

public record SwiftnessEnchantmentEffect(EnchantmentValueEffect multiplierBuff) {
    public static final Codec<SwiftnessEnchantmentEffect> CODEC = RecordCodecBuilder.create( instance -> instance.group(
        EnchantmentValueEffect.CODEC.fieldOf("multiplierBuff").forGetter(SwiftnessEnchantmentEffect::multiplierBuff)
    ).apply(instance, SwiftnessEnchantmentEffect::new));

    public static float getValue(LivingEntity entity) {
        MutableFloat mutableFloat = new MutableFloat(0);
        for (ItemStack stack : entity.getAllArmorItems()) {
            EnchantmentHelper.forEachEnchantment(stack, (enchantment, level) -> {
                SwiftnessEnchantmentEffect effect = enchantment.value().effects().get(ModEnchantmentEffectComponentTypes.SWIFTNESS);
                if (effect != null) {
                    mutableFloat.setValue(effect.multiplierBuff().apply(level, entity.getRandom(), mutableFloat.floatValue()));
                }
            });
        }

        return mutableFloat.floatValue();
    }
}
