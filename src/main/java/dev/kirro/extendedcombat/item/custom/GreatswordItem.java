package dev.kirro.extendedcombat.item.custom;

import dev.kirro.extendedcombat.effects.ModStatusEffects;
import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import dev.kirro.extendedcombat.enchantment.custom.ConcussionEnchantmentEffect;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import static dev.kirro.extendedcombat.item.custom.ModToolMaterials.ECHO_STEEL;
import static dev.kirro.extendedcombat.item.custom.ModToolMaterials.NETHER_STEEL;
import static net.minecraft.item.ToolMaterials.*;

public class GreatswordItem extends SwordItem implements ScaledItem {
    public GreatswordItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof ServerPlayerEntity attackingPlayer) {
            ServerWorld world = (ServerWorld) attacker.getWorld();
            if (stack.getItem() instanceof ToolItem greatsword) {
                ToolMaterial material = greatsword.getMaterial();
                switch (material) {
                    case WOOD ->
                        world.playSound(null, attackingPlayer.getX(), attackingPlayer.getY(), attackingPlayer.getZ(), SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.PLAYERS, 1.0f, (float) (1.0f + attackingPlayer.getRandom().nextGaussian() / 10f));
                    case STONE ->
                        world.playSound(null, attackingPlayer.getX(), attackingPlayer.getY(), attackingPlayer.getZ(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.PLAYERS, 1.0f, (float) (1.0f + attackingPlayer.getRandom().nextGaussian() / 10f));
                    case IRON, GOLD, DIAMOND ->
                        world.playSound(null, attackingPlayer.getX(), attackingPlayer.getY(), attackingPlayer.getZ(), SoundEvents.BLOCK_METAL_BREAK, SoundCategory.PLAYERS, 1.0f, (float) (1.0f + attackingPlayer.getRandom().nextGaussian() / 10f));
                    case NETHERITE, NETHER_STEEL ->
                        world.playSound(null, attackingPlayer.getX(), attackingPlayer.getY(), attackingPlayer.getZ(), SoundEvents.BLOCK_NETHERITE_BLOCK_BREAK, SoundCategory.PLAYERS, 1.0f, (float) (1.0f + attackingPlayer.getRandom().nextGaussian() / 10f));
                    case ECHO_STEEL ->
                        world.playSound(null, attackingPlayer.getX(), attackingPlayer.getY(), attackingPlayer.getZ(), SoundEvents.BLOCK_SCULK_BREAK, SoundCategory.PLAYERS, 1.0f, (float) (1.0f + attackingPlayer.getRandom().nextGaussian() / 10f));
                    default -> super.postHit(stack, target, attacker);
                }
            }
        }
        return true;
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getMainHandStack().isOf(this) && !((PlayerEntity) attacker).getItemCooldownManager().isCoolingDown(this)) {
            int duration = ConcussionEnchantmentEffect.getDuration(attacker);
            if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, ModEnchantmentEffectComponentTypes.CONCUSSION)) {
                target.addStatusEffect(new StatusEffectInstance(ModStatusEffects.CONCUSSION, duration), null);
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, duration), null);
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, duration), null);
                ((PlayerEntity) attacker).getItemCooldownManager().set(this, duration * 2);
            }
        }
        super.postDamageEntity(stack, target, attacker);
    }
}
