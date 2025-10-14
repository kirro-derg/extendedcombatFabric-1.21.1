package dev.kirro.extendedcombat.enchantment.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;

public record BurstEnchantmentEffect(EnchantmentValueEffect strength) {
    public static final Codec<BurstEnchantmentEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EnchantmentValueEffect.CODEC.fieldOf("strength").forGetter(BurstEnchantmentEffect::strength)
    ).apply(instance, BurstEnchantmentEffect::new));

    public static float getStrength(LivingEntity entity) {
        MutableFloat mutableFloat = new MutableFloat(0);
        for (ItemStack stack : entity.getArmorItems()) {
            EnchantmentHelper.forEachEnchantment(stack, ((enchantment, level) ->  {
                BurstEnchantmentEffect effect = enchantment.value().effects().get(ModEnchantmentEffectComponentTypes.BURST);
                if (effect != null) {
                    mutableFloat.setValue(effect.strength().apply(level, entity.getRandom(), mutableFloat.floatValue()));
                }
            }));
        }
        return mutableFloat.floatValue();
    }

    public static int getLevel(LivingEntity entity) {
        MutableInt mutableInt = new MutableInt(0);
        for (ItemStack stack : entity.getArmorItems()) {
            EnchantmentHelper.forEachEnchantment(stack, ((enchantment, level) ->  {
                BurstEnchantmentEffect effect = enchantment.value().effects().get(ModEnchantmentEffectComponentTypes.BURST);
                if (effect != null) {
                    mutableInt.setValue(level);
                }
            }));
        }

        if (mutableInt.intValue() == 1) {
            return 2;
        } else if (mutableInt.intValue() == 2) {
            return 4;
        } else {
            return 8;
        }
    }
}
