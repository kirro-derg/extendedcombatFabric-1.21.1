package dev.kirro.extendedcombat.enchantment.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import dev.kirro.extendedcombat.enchantment.ModEnchantments;
import dev.kirro.extendedcombat.entity.custom.StatueEntity;
import dev.kirro.extendedcombat.item.custom.NetherSteelArmorItem;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableFloat;

public record DashEnchantmentEffect(EnchantmentValueEffect cooldown, EnchantmentValueEffect strength) {
    public static final Codec<DashEnchantmentEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EnchantmentValueEffect.CODEC.fieldOf("cooldown").forGetter(DashEnchantmentEffect::cooldown),
            EnchantmentValueEffect.CODEC.fieldOf("strength").forGetter(DashEnchantmentEffect::strength)
    ).apply(instance, DashEnchantmentEffect::new));

    public static int getCooldown(LivingEntity entity) {
        MutableFloat mutableFloat = new MutableFloat(0);
        for (ItemStack stack : entity.getArmorItems()) {
            EnchantmentHelper.forEachEnchantment(stack, ((enchantment, level) ->  {
                DashEnchantmentEffect effect = enchantment.value().effects().get(ModEnchantmentEffectComponentTypes.DASH);
                if (effect != null) {
                    mutableFloat.setValue(effect.cooldown().apply(level, entity.getRandom(), mutableFloat.floatValue()));
                }
            }));
        }
        return MathHelper.floor(mutableFloat.floatValue() * 20);
    }

    public static float getStrength(LivingEntity entity) {
        MutableFloat mutableFloat = new MutableFloat(0);
        for (ItemStack stack : entity.getArmorItems()) {
            EnchantmentHelper.forEachEnchantment(stack, ((enchantment, level) ->  {
                DashEnchantmentEffect effect = enchantment.value().effects().get(ModEnchantmentEffectComponentTypes.DASH);
                if (effect != null) {
                    mutableFloat.setValue(effect.strength().apply(level, entity.getRandom(), mutableFloat.floatValue()));
                }
            }));
        }
        return mutableFloat.floatValue();
    }
}
