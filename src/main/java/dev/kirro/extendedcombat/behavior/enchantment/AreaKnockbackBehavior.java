package dev.kirro.extendedcombat.behavior.enchantment;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class AreaKnockbackBehavior implements AutoSyncedComponent, CommonTickingComponent {
    private final PlayerEntity player;


    public AreaKnockbackBehavior(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {

    }

    @Override
    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {

    }



    @Override
    public void tick() {

    }

    @Override
    public void clientTick() {
        tick();
    }
}
