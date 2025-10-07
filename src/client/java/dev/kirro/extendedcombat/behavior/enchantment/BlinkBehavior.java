package dev.kirro.extendedcombat.behavior.enchantment;

import dev.kirro.ExtendedCombatClient;
import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import dev.kirro.extendedcombat.enchantment.custom.BlinkEnchantmentEffect;
import dev.kirro.extendedcombat.enchantment.payload.BlinkParticlePayload;
import dev.kirro.extendedcombat.enchantment.payload.BlinkPayload;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import dev.kirro.extendedcombat.sound.ModSounds;
import dev.kirro.extendedcombat.util.ExtendedCombatClientUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundEvents;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class BlinkBehavior implements AutoSyncedComponent, CommonTickingComponent {
    private final LivingEntity player;
    private boolean canRecharge = false, hasBlink = false, wasPressingKey = false, invisible = false;
    private int cooldown = 0, lastCooldown = 0, duration = 0;

    public BlinkBehavior(LivingEntity player) {
        this.player = player;
    }



    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        canRecharge = nbtCompound.getBoolean("CanRecharge");
        cooldown = nbtCompound.getInt("Cooldown");
        lastCooldown = nbtCompound.getInt("LastCooldown");
        invisible = nbtCompound.getBoolean("Invisible");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("CanRecharge", canRecharge);
        nbtCompound.putInt("Cooldown", cooldown);
        nbtCompound.putInt("LastCooldown", lastCooldown);
        nbtCompound.putBoolean("Invisible", invisible);
    }

    @Override
    public void tick() {
        int playerCooldown = BlinkEnchantmentEffect.getCooldown(player);
        hasBlink = playerCooldown > 0;
        if (hasBlink) {
            if (!canRecharge) {
                if (!player.isInvisible()) {
                    canRecharge = true;
                }
            } else if (cooldown > 0) {
                cooldown--;
            }
        } else {
            canRecharge = false;
            setCooldown(0);
        }
        if (duration > 0) {
            duration--;
        }
        if (duration == 0) {
            invisible = false;
            sync();
            ExtendedCombatClientUtil.setBlinking(player.getUuid(), false, 0);
        }
    }

    @Override
    public void clientTick() {
        tick();
        if (hasBlink && !player.isSpectator() && player == MinecraftClient.getInstance().player) {
            boolean pressingKey = ExtendedCombatClient.BLINK.isPressed();
            if (pressingKey && !wasPressingKey && canUse()) {
                use();
                BlinkParticlePayload.addParticles(player);
                BlinkPayload.send();
            }
            wasPressingKey = pressingKey;
        } else {
            wasPressingKey = false;
        }
    }

    public void sync() {
        ModEntityComponents.BLINK.sync(player);
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

    public boolean hasBlink() {
        return hasBlink;
    }

    public boolean canUse() {
        return cooldown == 0 && duration == 0;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public void use() {
        reset();
        sync();
        setInvisible(true);
        player.playSound(ModSounds.BLINK, 1.0f, 1.0f);
    }

    public void setDuration(int uDuration) {
        duration = uDuration;
    }

    public int getDuration() {
        return duration;
    }

    public void reset() {
        setCooldown(BlinkEnchantmentEffect.getCooldown(player));
        canRecharge = false;
        setInvisible(false);
        setDuration(BlinkEnchantmentEffect.getUsageDuration(player));
    }


}
