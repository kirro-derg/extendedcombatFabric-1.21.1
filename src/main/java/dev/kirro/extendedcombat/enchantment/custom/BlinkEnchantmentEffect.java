package dev.kirro.extendedcombat.enchantment.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.mutable.MutableFloat;

public record BlinkEnchantmentEffect(EnchantmentValueEffect cooldown, EnchantmentValueEffect usageDuration) {
    public static final Codec<BlinkEnchantmentEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EnchantmentValueEffect.CODEC.fieldOf("cooldown").forGetter(BlinkEnchantmentEffect::cooldown),
            EnchantmentValueEffect.CODEC.fieldOf("usage_duration").forGetter(BlinkEnchantmentEffect::usageDuration)
    ).apply(instance, BlinkEnchantmentEffect::new));

    public static int getCooldown(LivingEntity entity) {
        MutableFloat mutableFloat = new MutableFloat(0);
        for (ItemStack stack : entity.getArmorItems()) {
            EnchantmentHelper.forEachEnchantment(stack, ((enchantment, level) ->  {
                BlinkEnchantmentEffect effect = enchantment.value().effects().get(ModEnchantmentEffectComponentTypes.BLINK);
                if (effect != null) {
                    mutableFloat.setValue(effect.cooldown().apply(level, entity.getRandom(), mutableFloat.floatValue()));
                }
            }));
        }
        return MathHelper.floor(mutableFloat.floatValue() * 20);
    }

    public static int getUsageDuration(LivingEntity entity) {
        MutableFloat mutableFloat = new MutableFloat(0);
        for (ItemStack stack : entity.getArmorItems()) {
            EnchantmentHelper.forEachEnchantment(stack, ((enchantment, level) ->  {
                BlinkEnchantmentEffect effect = enchantment.value().effects().get(ModEnchantmentEffectComponentTypes.BLINK);
                if (effect != null) {
                    mutableFloat.setValue(effect.usageDuration().apply(level, entity.getRandom(), mutableFloat.floatValue()));
                }
            }));
        }
        return MathHelper.floor(mutableFloat.floatValue() * 20);
    }
}
