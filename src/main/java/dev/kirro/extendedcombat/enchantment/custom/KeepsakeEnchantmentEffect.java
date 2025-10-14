package dev.kirro.extendedcombat.enchantment.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.*;
import net.minecraft.entity.Entity;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record KeepsakeEnchantmentEffect(EnchantmentValueEffect noDurability) {
        public static final Codec<KeepsakeEnchantmentEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EnchantmentValueEffect.CODEC.fieldOf("noDurability").forGetter(KeepsakeEnchantmentEffect::noDurability)
        ).apply(instance, KeepsakeEnchantmentEffect::new));
}
