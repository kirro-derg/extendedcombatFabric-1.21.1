package dev.kirro.mixin.block;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.kirro.extendedcombat.block.custom.WardingStoneBlock;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HostileEntity.class)
public class HostileEntityMixin {
    @ModifyReturnValue(method = "isSpawnDark", at = @At("RETURN"))
    private static boolean isSpawnDark(boolean original, @Local(argsOnly = true) ServerWorldAccess world, @Local(argsOnly = true) BlockPos pos) {
        return WardingStoneBlock.isNearby(world, pos, original);
    }

    @ModifyReturnValue(method = "canSpawnInDark", at = @At("RETURN"))
    private static boolean canSpawnInDark(boolean original, @Local(argsOnly = true) ServerWorldAccess world, @Local(argsOnly = true) BlockPos pos) {
       return WardingStoneBlock.isNearby(world, pos, original);
    }

    @ModifyReturnValue(method = "canSpawnIgnoreLightLevel", at = @At("RETURN"))
    private static boolean canSpawnIgnoreLightLevel(boolean original, @Local(argsOnly = true) WorldAccess world, @Local(argsOnly = true) BlockPos pos) {
        return WardingStoneBlock.isNearby(world, pos, original);
    }
}