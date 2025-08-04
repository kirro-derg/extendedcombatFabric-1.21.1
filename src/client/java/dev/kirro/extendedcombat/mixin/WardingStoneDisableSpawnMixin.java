package dev.kirro.extendedcombat.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import dev.kirro.extendedcombat.villager.ModPOI;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HostileEntity.class)
public class WardingStoneDisableSpawnMixin {
    @ModifyReturnValue(method = "isSpawnDark", at = @At("RETURN"))
    private static boolean isSpawnDark(boolean original, @Local(argsOnly = true) ServerWorldAccess world, @Local(argsOnly = true) BlockPos pos) {
        int radius = 55;

        if (!(world instanceof ServerWorld sWorld)) {
            return original;
        }


        PointOfInterestStorage poi = sWorld.getPointOfInterestStorage();


        boolean poiNearby = poi.getInCircle(
                registryEntry -> registryEntry.value() == ModPOI.WARDING_STONE_POI,
                pos,
                radius,
                PointOfInterestStorage.OccupationStatus.ANY
        ).findAny().isPresent();

        return original && !poiNearby;
    }
}
