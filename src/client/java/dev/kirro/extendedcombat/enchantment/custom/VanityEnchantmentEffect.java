package dev.kirro.extendedcombat.enchantment.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;

public record VanityEnchantmentEffect(EnchantmentValueEffect vanity) {
    public static final Codec<VanityEnchantmentEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EnchantmentValueEffect.CODEC.fieldOf("vanity").forGetter(VanityEnchantmentEffect::vanity)
    ).apply(instance, VanityEnchantmentEffect::new));
}
