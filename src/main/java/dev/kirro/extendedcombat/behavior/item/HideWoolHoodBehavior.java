package dev.kirro.extendedcombat.behavior.item;

import dev.kirro.extendedcombat.sound.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class HideWoolHoodBehavior implements AutoSyncedComponent, CommonTickingComponent {
    private final PlayerEntity player;
    private boolean hoodHidden, hoodUsed;
    private boolean maskHidden, maskUsed;
    private int hoodTick = 0;
    private int maskTick = 0;


    public HideWoolHoodBehavior(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putBoolean("HoodHidden", this.hoodHidden);
        nbt.putBoolean("MaskHidden", this.maskHidden);
    }

    @Override
    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.getBoolean("HoodHidden");
        nbt.getBoolean("MaskHidden");
    }

    @Override
    public void tick() {
        if (hoodTick > 0) {
            hoodTick--;
        } else if (hoodTick == 0) {
            hoodUsed = false;
        }
        if (maskTick > 0) {
            maskTick--;
        } else if (maskTick == 0) {
            maskUsed = false;
        }

    }

    @Override
    public void clientTick() {
        tick();
    }

    public void useHood() {
        if (isHoodHidden()) player.playSound(ModSounds.HOOD_RAISE, 1.0f, 1.0f);
        else player.playSound(ModSounds.HOOD_LOWER, 1.0f, 1.0f);
        setHoodHidden(!hoodHidden);
        setHoodUsed(true);
        setHoodTick(1);
    }

    public void useMask() {
        if (isMaskHidden()) player.playSound(ModSounds.MASK_EQUIP, 1.0f, 1.0f);
        else player.playSound(ModSounds.MASK_UNEQUIP, 1.0f, 1.0f);
        setMaskHidden(!maskHidden);
        setMaskUsed(true);
        setMaskTick(1);
    }

    public void setHoodHidden(boolean hidden) {
        this.hoodHidden = hidden;
    }

    public boolean isHoodHidden() {
        return this.hoodHidden;
    }

    public void setMaskHidden(boolean hidden) {
        this.maskHidden = hidden;
    }

    public boolean isMaskHidden() {
        return this.maskHidden;
    }

    public boolean isHoodUsed() {
        return hoodUsed;
    }

    public boolean isMaskUsed() {
        return maskUsed;
    }

    public void setHoodUsed(boolean used) {
        this.hoodUsed = used;
    }

    public void setMaskUsed(boolean used) {
        this.maskUsed = used;
    }

    public void setHoodTick(int ticks) {
        this.hoodTick = ticks;
    }

    public void setMaskTick(int ticks) {
        this.maskTick = ticks;
    }
}
