package dev.kirro.extendedcombat.behavior.abilities;

import dev.kirro.ExtendedcombatClient;
import dev.kirro.extendedcombat.entity.ModEntities;
import dev.kirro.extendedcombat.entity.custom.ChairEntity;
import dev.kirro.extendedcombat.util.ExtendedCombatClientUtil;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.CommandBlockExecutor;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.List;

public class SitBehavior implements AutoSyncedComponent, CommonTickingComponent {
    private final PlayerEntity player;
    private static boolean sitting;


    public SitBehavior(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.getBoolean("Sitting");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("Sitting", sitting);
    }

    @Override
    public void tick() {
        if (ExtendedcombatClient.SIT.isPressed()) {
            sitting = true;
        } else {
            sitting = false;
        }
    }

    public EntityDimensions getDimensions(EntityPose pose) {
        EntityDimensions dimensions = player.getBaseDimensions(pose);
        return isSitting() ? EntityDimensions.fixed(dimensions.width(), dimensions.height() - 0.5f) : dimensions;
    }

    @Override
    public void clientTick() {
        tick();
        if (sitting) {
            player.setBoundingBox(getDimensions(EntityPose.SITTING).getBoxAt(player.getPos()));
            ExtendedCombatClientUtil.setSitting(player.getUuid(), true);
            player.setPose(EntityPose.SITTING);
        } else {
            ExtendedCombatClientUtil.setSitting(player.getUuid(), false);
        }
    }

    public boolean isSitting() {
        return sitting;
    }
}
