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
    private static boolean isSpawnDark(boolean original, @Local LocalRef<ServerWorldAccess> world, @Local BlockPos pos) {
        int radius = 55;

        if (!(world.get() instanceof ServerWorld serverWorld)) {
            return original;
        }


        PointOfInterestStorage poiStorage = serverWorld.getPointOfInterestStorage();


        boolean poiNearby = poiStorage.getInCircle(
                registryEntry -> registryEntry.value() == ModPOI.WARDING_STONE_POI,
                pos,
                radius,
                PointOfInterestStorage.OccupationStatus.ANY
        ).findAny().isPresent();

        return original && !poiNearby;
    }
}
