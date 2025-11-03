package dev.kirro.extendedcombat.behavior.enchantment;

import dev.kirro.ExtendedCombatClient;
import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import dev.kirro.extendedcombat.enchantment.custom.BurstEnchantmentEffect;
import dev.kirro.extendedcombat.enchantment.custom.DashEnchantmentEffect;
import dev.kirro.extendedcombat.enchantment.payload.DashParticlePayload;
import dev.kirro.extendedcombat.enchantment.payload.DashPayload;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import dev.kirro.extendedcombat.sound.ModSounds;
import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.Vec3d;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class DashBehavior implements AutoSyncedComponent, CommonTickingComponent {
    private final PlayerEntity player;
    private boolean canRecharge = false, hasDash = false, wasPressingKey = false;
    private int cooldown = 0, lastCooldown = 0, immunityTicks = 0;

    public DashBehavior(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        canRecharge = nbtCompound.getBoolean("CanRecharge");
        cooldown = nbtCompound.getInt("Cooldown");
        lastCooldown = nbtCompound.getInt("LastCooldown");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("CanRecharge", canRecharge);
        nbtCompound.putInt("Cooldown", cooldown);
        nbtCompound.putInt("LastCooldown", lastCooldown);
    }

    @Override
    public void tick() {
        int playerCooldown = DashEnchantmentEffect.getCooldown(player);
        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
        hasDash = playerCooldown > 0;
        boolean recharge = EnchantmentHelper.hasAnyEnchantmentsWith(chest, ModEnchantmentEffectComponentTypes.BURST) && player.isFallFlying();
        if (hasDash) {
            if (!canRecharge) {
                if (player.isOnGround() || recharge) {
                    canRecharge = true;
                }
            } else if (cooldown > 0) {
                cooldown--;
            }
        } else {
            canRecharge = false;
            setCooldown(0);
        }
        if (immunityTicks > 0) {
            immunityTicks--;
        }
    }

    @Override
    public void clientTick() {
        tick();
        if (hasDash && !player.isSpectator() && player == MinecraftClient.getInstance().player) {
            boolean pressingKey = ExtendedCombatClient.DASH.isPressed();
            ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);
            ItemStack offhandStack = player.getOffHandStack();
            boolean hasBurst = EnchantmentHelper.hasAnyEnchantmentsWith(stack, ModEnchantmentEffectComponentTypes.BURST);
            if (pressingKey && !wasPressingKey && canUse() && !hasBurst) {
                use();
                DashParticlePayload.addParticles(player);
                DashPayload.send();
            } else if (pressingKey && !wasPressingKey && canUseWithElytra() && hasBurst /*&& offhandStack.isOf(Items.GUNPOWDER)*/) {
                useWithElytra();
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

    public boolean canUseWithElytra() {
        /*ItemStack offhandItem = player.getOffHandStack();
        boolean hasCorrectAmount = offhandItem.getCount() >= BurstEnchantmentEffect.getLevel(player);*/
        return cooldown == 0 && !player.isOnGround() && ExtendedCombatUtil.isGroundedElytra(player) /*&& hasCorrectAmount*/;
    }

    public void use() {
        reset();
        setImmunityTicks(6);
        float volume = hasStealth(player.getEquippedStack(EquipmentSlot.CHEST)) ? 0.05f : 0.25f;
        float strength = DashEnchantmentEffect.getStrength(player);
        Vec3d velocity = player.getRotationVector().normalize().multiply(strength);
        player.setVelocity(velocity.getX(), velocity.getY(), velocity.getZ());
        player.fallDistance = 0;
        player.velocityModified = true;
        player.playSound(ModSounds.DASH, volume, 1.0f);
    }

    public void useWithElytra() {
        resetWithElytra();
        setImmunityTicks(3);
        //useGunpowder(offhand);
        float volume = hasStealth(player.getEquippedStack(EquipmentSlot.CHEST)) ? 0.05f : 0.25f;
        float strength = BurstEnchantmentEffect.getStrength(player);
        Vec3d velocity = player.getRotationVector().normalize().multiply(strength);
        player.setVelocity(velocity.getX(), velocity.getY(), velocity.getZ());
        player.fallDistance = 0;
        player.velocityModified = true;
        player.playSound(ModSounds.DASH, volume, 1.0f);
    }

    private void useGunpowder(ItemStack offhand) {
        if (offhand.isOf(Items.GUNPOWDER)) {
            offhand.decrementUnlessCreative(BurstEnchantmentEffect.getLevel(player), player);
        }
    }

    private boolean hasStealth(ItemStack chest) {
        return EnchantmentHelper.hasAnyEnchantmentsWith(chest, ModEnchantmentEffectComponentTypes.STEALTH);
    }

    public void setImmunityTicks(int ticks) {
        immunityTicks = ticks;
    }

    public int getImmunityTicks() {
        return immunityTicks;
    }

    public void reset() {
        setCooldown(DashEnchantmentEffect.getCooldown(player));
        canRecharge = false;
    }

    public void resetWithElytra() {
        setCooldown(DashEnchantmentEffect.getCooldown(player) * 10);
        canRecharge = false;
    }
}
