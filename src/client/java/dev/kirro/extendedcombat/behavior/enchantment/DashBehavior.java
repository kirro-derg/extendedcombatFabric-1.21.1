package dev.kirro.extendedcombat.behavior.enchantment;

import dev.kirro.ExtendedcombatClient;
import dev.kirro.ModConfig;
import dev.kirro.extendedcombat.enchantment.custom.DashEnchantmentEffect;
import dev.kirro.extendedcombat.enchantment.payload.DashParticlePayload;
import dev.kirro.extendedcombat.enchantment.payload.DashPayload;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class DashBehavior implements AutoSyncedComponent, CommonTickingComponent {
    private final PlayerEntity player;
    private boolean refresh = false;
    private static boolean usedMidair = false;
    private int cooldown = 0, lastCooldown = 0;
    private static int immunityTicks = 0;

    private boolean hasDash = false, wasPressingKey = false;

    public DashBehavior(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        refresh = nbtCompound.getBoolean("Refresh");
        cooldown = nbtCompound.getInt("Cooldown");
        lastCooldown = nbtCompound.getInt("LastCooldown");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("Refresh", refresh);
        nbtCompound.putInt("Cooldown", cooldown);
        nbtCompound.putInt("LastCooldown", lastCooldown);
    }

    @Override
    public void tick() {
        int playerCooldown = DashEnchantmentEffect.getCooldown(player);
        hasDash = playerCooldown > 0;
        if (hasDash) {
            if (!refresh) {
                if (player.isOnGround()) {
                    refresh = true;
                }
            } else if (cooldown > 0) {
                cooldown--;
            }
        } else {
            refresh = false;
            setCooldown(0);
        }
        if (immunityTicks > 0) {
            immunityTicks--;
        }
        if (immunityTicks == 0) {
            usedMidair = false;
        }
    }

    @Override
    public void clientTick() {
        tick();
        if (hasDash && !player.isSpectator() && player == MinecraftClient.getInstance().player) {
            boolean pressingKey = ExtendedcombatClient.DASH.isPressed();
            if (pressingKey && !wasPressingKey && canUse()) {
                use();
                DashParticlePayload.addParticles(player);
                DashPayload.send();
            }
            wasPressingKey = pressingKey;
        } else {
            wasPressingKey = false;
        }
    }

    public void sync() {
        ModEntityComponents.DASH.sync(player);
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

    public boolean hasDash() {
        return hasDash;
    }

    public boolean canUse() {
        return cooldown == 0 && !player.isOnGround() && ExtendedCombatUtil.isGrounded(player);
    }

    public void use() {
        reset();
        usedMidair = true;
        setImmunityTicks(20);
        double strength = DashEnchantmentEffect.getStrength(player);
        Vec3d velocity = player.getRotationVector().normalize().multiply(strength);
        player.setVelocity(velocity.getX(), velocity.getY(), velocity.getZ());
        player.fallDistance = 0;
        player.velocityModified = true;
        player.playSound(SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST.value(), 1.0f, 1.0f);
    }

    public static boolean wasUsedMidair() {
        return usedMidair;
    }

    public void setImmunityTicks(int ticks) {
        immunityTicks = ticks;
    }

    public static int getImmunityTicks() {
        return immunityTicks;
    }

    public void reset() {
        setCooldown(DashEnchantmentEffect.getCooldown(player));
        refresh = false;
        usedMidair = false;
    }
}
