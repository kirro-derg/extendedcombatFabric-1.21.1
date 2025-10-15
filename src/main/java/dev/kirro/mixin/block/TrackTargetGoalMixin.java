package dev.kirro.mixin.block;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.kirro.extendedcombat.block.custom.WardingStoneBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TrackTargetGoal.class)
public class TrackTargetGoalMixin {

    @Shadow
    @Final
    protected MobEntity mob;

    @Inject(method = "canTrack", at = @At("HEAD"), cancellable = true)
    private void canStart(LivingEntity target, TargetPredicate targetPredicate, CallbackInfoReturnable<Boolean> cir) {
        WorldAccess world = target.getWorld();
        if (!(this.mob instanceof WardenEntity || !(this.mob instanceof WitherEntity))) {
            if (WardingStoneBlock.isNearby(world, this.mob.getBlockPos(), cir.getReturnValue())) {
                cir.setReturnValue(false);
            }
        }
    }

    @ModifyReturnValue(method = "canTrack", at = @At("RETURN"))
    private boolean canTrack(boolean original, @Local(argsOnly = true)LivingEntity target) {
        WorldAccess world = target.getWorld();
        return WardingStoneBlock.isNearby(world, this.mob.getBlockPos(), original);
    }
}
