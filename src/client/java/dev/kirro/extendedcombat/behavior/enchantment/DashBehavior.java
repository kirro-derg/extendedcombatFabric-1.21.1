package dev.kirro.extendedcombat.behavior.enchantment;

import dev.kirro.ExtendedCombatClient;
import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import dev.kirro.extendedcombat.enchantment.custom.BurstEnchantmentEffect;
import dev.kirro.extendedcombat.enchantment.custom.DashEnchantmentEffect;
import dev.kirro.extendedcombat.enchantment.payload.DashParticlePayload;
import dev.kirro.extendedcombat.enchantment.payload.DashPayload;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import dev.kirro.extendedcombat.tags.ModItemTags;
import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
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
                if (player.isOnGround() || EnchantmentHelper.hasAnyEnchantmentsWith(player.getEquippedStack(EquipmentSlot.CHEST), ModEnchantmentEffectComponentTypes.BURST)) {
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
            boolean pressingKey = ExtendedCombatClient.DASH.isPressed();
            ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);
            ItemStack offhandStack = player.getOffHandStack();
            if (pressingKey && !wasPressingKey && canUse() && !stack.isIn(ModItemTags.ELYTRA_ENCHANTABLE)) {
                use();
                DashParticlePayload.addParticles(player);
                DashPayload.send();
            } else if (pressingKey && !wasPressingKey && canUseWithElytra() && EnchantmentHelper.hasAnyEnchantmentsWith(stack, ModEnchantmentEffectComponentTypes.BURST) && offhandStack.isOf(Items.GUNPOWDER)) {
                useWithElytra(offhandStack);
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
        ItemStack offhandItem = player.getOffHandStack();
        boolean hasCorrectAmountOfGunpowder = offhandItem.getCount() >= BurstEnchantmentEffect.getLevel(player);
        return cooldown == 0 && !player.isOnGround() && ExtendedCombatUtil.isGroundedElytra(player) && hasCorrectAmountOfGunpowder;
    }

    public void use() {
        reset();
        usedMidair = true;
        setImmunityTicks(20);
        float strength = DashEnchantmentEffect.getStrength(player);
        Vec3d velocity = player.getRotationVector().normalize().multiply(strength);
        if (player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA)) {
            Vec3d velocity2 = velocity.multiply(5);
            player.setVelocity(velocity2.getX(), velocity2.getY(), velocity2.getZ());
        } else {
            player.setVelocity(velocity.getX(), velocity.getY(), velocity.getZ());
        }
        player.fallDistance = 0;
        player.velocityModified = true;
        player.playSound(SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST.value(), 1.0f, 1.0f);
    }

    public void useWithElytra(ItemStack stack) {
        resetWithElytra();
        usedMidair = true;
        setImmunityTicks(0);
        if (!player.isCreative()) {
            stack.decrement(BurstEnchantmentEffect.getLevel(player));
        }
        float strength = BurstEnchantmentEffect.getStrength(player);
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

    public void resetWithElytra() {
        setCooldown(DashEnchantmentEffect.getCooldown(player) * 2);
        refresh = false;
        usedMidair = false;
    }
}
