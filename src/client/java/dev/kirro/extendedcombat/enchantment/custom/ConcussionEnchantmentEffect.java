package dev.kirro.extendedcombat.enchantment.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.mutable.MutableInt;

public record ConcussionEnchantmentEffect(EnchantmentValueEffect duration) {
    public static final Codec<ConcussionEnchantmentEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EnchantmentValueEffect.CODEC.fieldOf("duration").forGetter(ConcussionEnchantmentEffect::duration)
    ).apply(instance, ConcussionEnchantmentEffect::new));

    public static int getDuration(LivingEntity entity) {
        MutableInt mutableFloat = new MutableInt(0);
        for (ItemStack stack : entity.getHandItems()) {
            EnchantmentHelper.forEachEnchantment(stack, ((enchantment, level) ->  {
                ConcussionEnchantmentEffect effect = enchantment.value().effects().get(ModEnchantmentEffectComponentTypes.CONCUSSION);
                if (effect != null) {
                    mutableFloat.setValue(level);
                }
            }));
        }

        return mutableFloat.intValue() * 20;
    }
}
