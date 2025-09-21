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

public class AirMovementBehavior implements CommonTickingComponent {
    private final PlayerEntity player;
    private int resetDelay = 0, airTime = 0;

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
        float multiply = getAirTime() >= 5 ? 2.0f : 1.0f;
        return original * multiply;
    }

    public void delayReset() {
        resetDelay = 3;
    }

    public void bypassAirTimeDelay() {
        resetDelay = 3;
        airTime = 5;
    }
}
