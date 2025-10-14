package dev.kirro.extendedcombat.behavior.enchantment;

import dev.kirro.ModConfig;
import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class AirMovementBehavior implements CommonTickingComponent {
    private final PlayerEntity player;
    private int resetDelay = 0, airTime = 0, multiplierTicks = 0;
    private final int multiplyAfter = 10;
    private final float maxMovementMultiplier = 3.0f;

    public AirMovementBehavior(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        resetDelay = nbt.getInt("ResetDelay");
        airTime = nbt.getInt("AirTime");
    }

    @Override
    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbt.putInt("ResetDelay", resetDelay);
        nbt.putInt("AirTime", airTime);
    }

    @Override
    public void tick() {
        ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);
        if (ModConfig.airMovementActive && stack.hasEnchantments()) {
            if (resetDelay > 0) {
                resetDelay--;
            }
            if (player.isOnGround() || player.hasStatusEffect(StatusEffects.SLOWNESS)) {
                if (resetDelay == 0) {
                    airTime = 0;
                }
            } else if (ExtendedCombatUtil.inAir(player, 1)) {
                airTime++;
                if (airTime >= multiplyAfter) {
                    multiplierTicks++;
                }
            }

        } else {
            resetDelay = airTime = 0;
        }
    }

    public int getAirTime() {
        return airTime;
    }

    public float movementMultiplier(float original) {
        ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);
        float multiplier = getAirTime() >= multiplyAfter ? multiplierTicks : 1.0f;
        float multiply = EnchantmentHelper.hasAnyEnchantmentsWith(player.getEquippedStack(EquipmentSlot.LEGS), ModEnchantmentEffectComponentTypes.SWIFTNESS)
                ? 0.5f
                : 0.0f;
        if (stack.isEmpty() || getAirTime() < multiplyAfter) {
            return original;
        } else {
            return original * Math.min(maxMovementMultiplier + multiply, multiplier);
        }
    }

    public void bypass() {
        resetDelay = 3;
        airTime = multiplyAfter;
    }
}
