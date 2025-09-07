package dev.kirro.extendedcombat.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.kirro.ModConfig;
import dev.kirro.extendedcombat.villager.ModPOI;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HostileEntity.class)
public class WardingStoneDisableSpawnMixin {
    @ModifyReturnValue(method = "isSpawnDark", at = @At("RETURN"))
    private static boolean isSpawnDark(boolean original, @Local(argsOnly = true) ServerWorldAccess world, @Local(argsOnly = true) BlockPos pos) {
        return isWardingStoneNearby(world, pos, original);
    }

    @ModifyReturnValue(method = "canSpawnInDark", at = @At("RETURN"))
    private static boolean canSpawnInDark(boolean original, @Local(argsOnly = true) ServerWorldAccess world, @Local(argsOnly = true) BlockPos pos) {
       return isWardingStoneNearby(world, pos, original);
    }

    @ModifyReturnValue(method = "canSpawnIgnoreLightLevel", at = @At("RETURN"))
    private static boolean canSpawnIgnoreLightLevel(boolean original, @Local(argsOnly = true) WorldAccess world, @Local(argsOnly = true) BlockPos pos) {
        return isWardingStoneNearby(world, pos, original);
    }

    @Unique
    private static boolean isWardingStoneNearby(WorldAccess world, BlockPos pos, boolean original) {
        int radius = ModConfig.wardingStoneActiveRadius;

        if (!(world instanceof ServerWorld serverWorld)) {
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
