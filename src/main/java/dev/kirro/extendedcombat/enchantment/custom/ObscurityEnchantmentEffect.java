package dev.kirro.extendedcombat.enchantment.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;

public record ObscurityEnchantmentEffect(EnchantmentValueEffect obscurity) {
    public static final Codec<ObscurityEnchantmentEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EnchantmentValueEffect.CODEC.fieldOf("obscurity").forGetter(ObscurityEnchantmentEffect::obscurity)
    ).apply(instance, ObscurityEnchantmentEffect::new));
}
