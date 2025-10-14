package dev.kirro.extendedcombat.behavior.enchantment;

import dev.kirro.extendedcombat.enchantment.ModEnchantmentEffectComponentTypes;
import dev.kirro.extendedcombat.entity.components.ModEntityComponents;
import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public record FluidMovementBehavior(PlayerEntity player) implements AutoSyncedComponent, CommonTickingComponent {

    @Override
    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {

    }

    @Override
    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {

    }

    @Override
    public void tick() {
    }

    private ItemStack getArmor(EquipmentSlot slot) {
        return player.getEquippedStack(slot);
    }

    @Override
    public void clientTick() {
        tick();
        AirMovementBehavior airMovement = ModEntityComponents.AIR_MOVEMENT.get(player);
        if (EnchantmentHelper.hasAnyEnchantmentsWith(getArmor(EquipmentSlot.FEET), ModEnchantmentEffectComponentTypes.FLUID_WALKER) && ExtendedCombatUtil.isTouchingFluid(player)) {
            airMovement.bypass();
        }
    }
}
