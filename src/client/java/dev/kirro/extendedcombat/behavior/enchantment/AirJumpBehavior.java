package dev.kirro.extendedcombat.behavior.enchantment;

import dev.kirro.ModConfig;
import dev.kirro.extendedcombat.enchantment.custom.AirJumpEnchantmentEffect;
import dev.kirro.extendedcombat.enchantment.payload.AirJumpParticlePayload;
import dev.kirro.extendedcombat.enchantment.payload.AirJumpPayload;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundEvents;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class AirJumpBehavior implements AutoSyncedComponent, CommonTickingComponent {
    private final PlayerEntity player;
    private boolean refresh = false, canUse = false;
    private int cooldown = 0, lastCooldown = 0, jumpCooldown = 10, jumpAmount = 0, ticksInAir = 0, maxJumps;


    public AirJumpBehavior(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        refresh = nbtCompound.getBoolean("Refresh");
        cooldown = nbtCompound.getInt("Cooldown");
        lastCooldown = nbtCompound.getInt("LastCooldown");
        jumpCooldown = nbtCompound.getInt("JumpCooldown");
        jumpAmount = nbtCompound.getInt("JumpAmount");
        ticksInAir = nbtCompound.getInt("TicksInAir");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("Refresh", refresh);
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
            if (!refresh) {
                if (player.isOnGround()) {
                    refresh = true;
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
            refresh = false;
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

        player.jump();
        player.setVelocity(player.getVelocity().getX(), player.getVelocity().getY() * strength, player.getVelocity().getZ());
        player.playSound(SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST.value(), 2.0f, 1.0f);
        if (cooldown == 0 || jumpAmount == maxJumps) {
            setCooldown(AirJumpEnchantmentEffect.getCooldown(player));
        }
        refresh = false;
        jumpCooldown = AirJumpEnchantmentEffect.getJumpCooldown(player);
        jumpAmount--;
    }

    public void reset() {
        setCooldown(AirJumpEnchantmentEffect.getCooldown(player));
        jumpAmount = 0;
    }
}
