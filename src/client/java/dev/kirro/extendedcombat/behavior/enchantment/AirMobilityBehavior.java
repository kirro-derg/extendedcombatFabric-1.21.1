package dev.kirro.extendedcombat.behavior.enchantment;

import dev.kirro.ModConfig;
import dev.kirro.extendedcombat.entity.components.ModComponentTypes;
import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class AirMobilityBehavior implements CommonTickingComponent {
    private final LivingEntity entity;
    private int resetBypassTicks = 0, ticksInAir = 0;

    public AirMobilityBehavior(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        resetBypassTicks = nbtCompound.getInt("ResetBypassTicks");
        ticksInAir = nbtCompound.getInt("TicksInAir");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("ResetBypassTicks", resetBypassTicks);
        nbtCompound.putInt("TicksInAir", ticksInAir);
    }

    @Override
    public void tick() {
        ItemStack stack = entity.getEquippedStack(EquipmentSlot.CHEST);
        if (ModConfig.airMobilityActive && stack.getOrDefault(ModComponentTypes.AIR_MOBILITY, false)) {
            if (!stack.hasEnchantments()) {
                return;
            }
            if (resetBypassTicks > 0) {
                resetBypassTicks--;
            }
            if (entity.isOnGround()) {
                if (resetBypassTicks == 0) {
                    ticksInAir = 0;
                }
            } else if (ExtendedCombatUtil.isGrounded(entity) && ExtendedCombatUtil.inAir(entity, 1)) {
                ticksInAir++;
            }
        } else {
            resetBypassTicks = ticksInAir = 0;
        }
    }

    public int getTicksInAir() {
        return ticksInAir;
    }

    public void resetTicksInAir() {
        ticksInAir = 0;
    }

    public void enableResetBypass() {
        resetBypassTicks = 3;
    }
}
