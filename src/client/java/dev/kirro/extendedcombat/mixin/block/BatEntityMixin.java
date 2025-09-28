package dev.kirro.extendedcombat.mixin.block;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.kirro.extendedcombat.block.custom.WardingStoneBlock;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BatEntity.class)
public class BatEntityMixin {
    @ModifyReturnValue(method = "canSpawn", at = @At("RETURN"))
    private static boolean canSpawn(boolean original, @Local(argsOnly = true) WorldAccess world, @Local(argsOnly = true) BlockPos pos) {
        return WardingStoneBlock.isNearby(world, pos, original);
    }
}
