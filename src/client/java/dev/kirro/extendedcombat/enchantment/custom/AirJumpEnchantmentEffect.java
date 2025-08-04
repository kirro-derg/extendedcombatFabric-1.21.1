package dev.kirro.extendedcombat.enchantment.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import dev.kirro.extendedcombat.enchantment.ModEnchantments;
import dev.kirro.extendedcombat.item.custom.NetherSteelArmorItem;
import net.minecraft.enchantment.Enchantment;
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
import org.apache.commons.lang3.mutable.MutableFloat;

public record AirJumpEnchantmentEffect(EnchantmentValueEffect airJumpStrength, EnchantmentValueEffect cooldown,
                                       EnchantmentValueEffect jumpCooldown, EnchantmentValueEffect jumpAmount) {
    public static final Codec<AirJumpEnchantmentEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EnchantmentValueEffect.CODEC.fieldOf("air_jump_strength").forGetter(AirJumpEnchantmentEffect::airJumpStrength),
            EnchantmentValueEffect.CODEC.fieldOf("cooldown").forGetter(AirJumpEnchantmentEffect::cooldown),
            EnchantmentValueEffect.CODEC.fieldOf("jump_cooldown").forGetter(AirJumpEnchantmentEffect::jumpCooldown),
            EnchantmentValueEffect.CODEC.fieldOf("jump_amount").forGetter(AirJumpEnchantmentEffect::jumpAmount)
    ).apply(instance, AirJumpEnchantmentEffect::new));

    public static float getAirJumpStrength(LivingEntity entity) {
        MutableFloat mutable = new MutableFloat(0);
        for (ItemStack stack : entity.getArmorItems()) {
            EnchantmentHelper.forEachEnchantment(stack, (enchantment, level) -> {
                AirJumpEnchantmentEffect effect = enchantment.value().effects().get(ModEnchantmentEffectComponentTypes.AIR_JUMP);
                if (effect != null) {
                    mutable.setValue(effect.airJumpStrength().apply(level, entity.getRandom(), mutable.floatValue()));
                }
            });
        }
        return mutable.floatValue();
    }

    public static int getCooldown(LivingEntity entity) {
        MutableFloat mutableFloat = new MutableFloat(0);
        for (ItemStack stack : entity.getArmorItems()) {
            EnchantmentHelper.forEachEnchantment(stack, (enchantment, level) -> {
                AirJumpEnchantmentEffect effect = enchantment.value().effects().get(ModEnchantmentEffectComponentTypes.AIR_JUMP);
                if (effect != null) {
                    mutableFloat.setValue(effect.cooldown().apply(level, entity.getRandom(), mutableFloat.floatValue()));
                }
            });
        }
        return MathHelper.floor(mutableFloat.floatValue() * 20);
    }

    public static int getJumpCooldown(LivingEntity entity) {
        MutableFloat mutableFloat = new MutableFloat(0);
        for (ItemStack stack : entity.getArmorItems()) {
            EnchantmentHelper.forEachEnchantment(stack, (enchantment, level) -> {
                AirJumpEnchantmentEffect effect = enchantment.value().effects().get(ModEnchantmentEffectComponentTypes.AIR_JUMP);
                if (effect != null) {
                    mutableFloat.setValue(effect.jumpCooldown().apply(level, entity.getRandom(), mutableFloat.floatValue()));
                }
            });
        }
        return MathHelper.floor(mutableFloat.floatValue() * 20);
    }

    public static int getJumpAmount(LivingEntity entity) {
        MutableFloat mutableFloat = new MutableFloat(0);
        for (ItemStack stack : entity.getArmorItems()) {
            EnchantmentHelper.forEachEnchantment(stack, (enchantment, level) -> {
                AirJumpEnchantmentEffect effect = enchantment.value().effects().get(ModEnchantmentEffectComponentTypes.AIR_JUMP);
                if (effect != null) {
                    mutableFloat.setValue(effect.jumpAmount().apply(level, entity.getRandom(), mutableFloat.floatValue()));
                }
            });
        }
        return MathHelper.floor(mutableFloat.floatValue());
    }
}
