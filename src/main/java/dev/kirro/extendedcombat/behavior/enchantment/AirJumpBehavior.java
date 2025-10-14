package dev.kirro.extendedcombat.behavior.enchantment;

import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import dev.kirro.extendedcombat.enchantment.custom.AirJumpEnchantmentEffect;
import dev.kirro.extendedcombat.enchantment.payload.AirJumpParticlePayload;
import dev.kirro.extendedcombat.enchantment.payload.AirJumpPayload;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import dev.kirro.extendedcombat.sound.ModSounds;
import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundEvents;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class AirJumpBehavior implements AutoSyncedComponent, CommonTickingComponent {
    private final PlayerEntity player;
    private boolean canRecharge = false, canUse = false;
    private int cooldown = 0, lastCooldown = 0, jumpCooldown = 10, jumpAmount = 0, ticksInAir = 0, maxJumps;


    public AirJumpBehavior(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        canRecharge = nbtCompound.getBoolean("CanRecharge");
        cooldown = nbtCompound.getInt("Cooldown");
        lastCooldown = nbtCompound.getInt("LastCooldown");
        jumpCooldown = nbtCompound.getInt("JumpCooldown");
        jumpAmount = nbtCompound.getInt("JumpAmount");
        ticksInAir = nbtCompound.getInt("TicksInAir");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("CanRecharge", canRecharge);
        nbtCompound.putInt("Cooldown", cooldown);
        nbtCompound.putInt("LastCooldown", lastCooldown);
        nbtCompound.putInt("JumpCooldown", jumpCooldown);
        nbtCompound.putInt("JumpAmount", jumpAmount);
        nbtCompound.putInt("TicksInAir", ticksInAir);
    }

    @Override
    public void tick() {
        int playerCooldown = AirJumpEnchantmentEffect.getCooldown(player);
        maxJumps = AirJumpEnchantmentEffect.getJumpAmount(player);
        canUse = maxJumps > 0;
        if (canUse) {
            if (!canRecharge) {
                if (player.isOnGround()) {
                    canRecharge = true;
                }
            } else if (playerCooldown > 0) {
                cooldown--;
                if (cooldown == 0 && jumpAmount < maxJumps) {
                    jumpAmount++;
                    setCooldown(playerCooldown);
                }
            }
            if (jumpCooldown > 0) {
                jumpCooldown--;
            }
            if (player.isOnGround()) {
                ticksInAir = 0;
            } else {
                ticksInAir++;
            }
        } else {
            canRecharge = false;
            setCooldown(0);
            jumpCooldown = 0;
            jumpAmount = 0;
            ticksInAir = 0;
        }
    }

    @Override
    public void clientTick() {
        tick();
        if (canUse && player.jumping && canUse()) {
            use();
            AirJumpParticlePayload.addParticles(player);
            AirJumpPayload.send();
        }
    }

    public void sync() {
        ModEntityComponents.AIR_JUMP.sync(player);
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
        lastCooldown = cooldown;
    }

    public int getLastCooldown() {
        return lastCooldown;
    }

    public int getJumpAmount() {
        return jumpAmount;
    }

    public int getMaxJumps() {
        return maxJumps;
    }

    public boolean getCanUse() {
        return canUse;
    }

    public boolean canUse() {
        int playerJumpCooldown = AirJumpEnchantmentEffect.getJumpCooldown(player);
        return jumpCooldown == 0 && jumpAmount > 0 && ticksInAir >= (player.getWorld().isClient ? playerJumpCooldown : playerJumpCooldown - 1) && !player.isOnGround() && ExtendedCombatUtil.isGrounded(player);
    }

    public void use() {
        float strength = AirJumpEnchantmentEffect.getAirJumpStrength(player);
        float volume = hasStealth(player.getEquippedStack(EquipmentSlot.CHEST)) ? 0.05f : 0.25f;
        player.jump();
        player.setVelocity(player.getVelocity().getX(), player.getVelocity().getY() * strength, player.getVelocity().getZ());
        player.playSound(ModSounds.AIR_JUMP, volume, 1.0f);
        if (cooldown == 0 || jumpAmount == maxJumps) {
            setCooldown(AirJumpEnchantmentEffect.getCooldown(player));
        }
        canRecharge = false;
        jumpCooldown = AirJumpEnchantmentEffect.getJumpCooldown(player);
        jumpAmount--;
    }

    private boolean hasStealth(ItemStack chest) {
        return EnchantmentHelper.hasAnyEnchantmentsWith(chest, ModEnchantmentEffectComponentTypes.STEALTH);
    }

    public void reset() {
        setCooldown(AirJumpEnchantmentEffect.getCooldown(player));
        jumpAmount = 0;
    }
}
