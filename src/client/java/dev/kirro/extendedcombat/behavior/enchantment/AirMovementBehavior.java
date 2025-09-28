package dev.kirro.extendedcombat.behavior.enchantment;

import dev.kirro.ModConfig;
import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import virtuoel.pehkui.api.ScaleTypes;

public class AirMovementBehavior implements CommonTickingComponent {
    private final PlayerEntity player;
    private int resetDelay = 0, airTime = 0, multiplierTicks = 0;
    private final int multiplyAfter = 10;

    public AirMovementBehavior(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        resetDelay = nbt.getInt("ResetDelay");
        airTime = nbt.getInt("AirTime");
    }

    private float scaleModifier() {
        float maxMovementMultiplier = 3.5f;
        float armorScalars = ScaleTypes.BASE.getScaleData(player).getScale() <= 1.25f ? maxMovementMultiplier - 0.5f : maxMovementMultiplier - 1.0f;
        return ScaleTypes.BASE.getScaleData(player).getScale() == 1.0f ? maxMovementMultiplier : armorScalars;
    }

    @Override
    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbt.putInt("ResetDelay", resetDelay);
        nbt.putInt("AirTime", airTime);
    }

    @Override
    public void tick() {
        ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);
        if (ModConfig.airMovementActive) {
            if (!stack.hasEnchantments()) {
                return;
            }
            if (resetDelay > 0) {
                resetDelay--;
            }
            if (player.isOnGround() || player.hasStatusEffect(StatusEffects.SLOWNESS)) {
                if (resetDelay == 0) {
                    airTime = 0;
                }
            } else if (ExtendedCombatUtil.inAir(player, 1)) {
                airTime++;
                if (airTime >= 10) {
                    multiplierTicks++;
                }
            }
            if (player.hasStatusEffect(StatusEffects.SPEED)) {
                delayReset();
            }

        } else {
            resetDelay = airTime = 0;
        }
    }

    public int getAirTime() {
        return airTime;
    }

    public void resetTicksInAir() {
        airTime = 0;
    }

    public float movementMultiplier(float original) {
        float maxMovementMultiplier = scaleModifier();
        float multiply = getAirTime() >= multiplyAfter ? multiplierTicks : 1.0f;
        float scaleMultiplier = getAirTime() >= multiplyAfter ? 1.5f : 1.0f;
        float scaleModifier = ScaleTypes.BASE.getScaleData(player).getScale() > 1.0 ? multiply : multiply * scaleMultiplier;
        return original * Math.min(maxMovementMultiplier, scaleModifier);
    }

    public void delayReset() {
        resetDelay = 3;
    }

    public void bypassAirTimeDelay() {
        resetDelay = 3;
        airTime = multiplyAfter;
    }
}
