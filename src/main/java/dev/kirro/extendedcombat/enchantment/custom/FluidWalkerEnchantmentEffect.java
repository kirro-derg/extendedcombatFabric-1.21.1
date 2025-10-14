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
        MutableFloat mutableFloat = new MutableFloat(0);
        for (ItemStack stack : entity.getArmorItems()) {
            EnchantmentHelper.forEachEnchantment(stack, (enchantment, level) -> {
                FluidWalkerEnchantmentEffect effect = enchantment.value().effects().get(ModEnchantmentEffectComponentTypes.FLUID_WALKER);
                if (effect != null) {
                    mutableFloat.setValue(effect.speed().apply(level, entity.getRandom(), mutableFloat.floatValue()));
                }
            });
        }

        return mutableFloat.floatValue();
    }
}