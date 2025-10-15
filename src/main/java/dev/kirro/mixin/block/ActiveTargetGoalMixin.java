package dev.kirro.mixin.block;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.kirro.extendedcombat.block.custom.WardingStoneBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ActiveTargetGoal.class)
public abstract class ActiveTargetGoalMixin extends TrackTargetGoal {
    @Shadow
    @Nullable
    protected LivingEntity targetEntity;

    @Shadow
    public abstract void setTargetEntity(@Nullable LivingEntity targetEntity);

    public ActiveTargetGoalMixin(MobEntity mob, boolean checkVisibility) {
        super(mob, checkVisibility);
    }

    @ModifyReturnValue(method = "canStart", at = @At("RETURN"))
    private boolean canTrack(boolean original) {
        if (this.targetEntity != null) {
            WorldAccess world = this.targetEntity.getWorld();
            return !WardingStoneBlock.canPacify(world, this.mob.getBlockPos());
        }
        return original;
    }

    @Inject(method = "findClosestTarget", at = @At("HEAD"), cancellable = true)
    private void target(CallbackInfo ci) {
        if (this.targetEntity != null) {
            WorldAccess world = this.mob.getWorld();
            if (WardingStoneBlock.canPacify(world, this.mob.getBlockPos())) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "start", at = @At("HEAD"), cancellable = true)
    private void start(CallbackInfo ci) {
        if (this.targetEntity != null) {
            WorldAccess world = this.mob.getWorld();
            if (WardingStoneBlock.canPacify(world, this.mob.getBlockPos())) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "setTargetEntity", at = @At("HEAD"))
    private void setTargetEntity(LivingEntity targetEntity, CallbackInfo ci) {
        WorldAccess world = this.mob.getWorld();
        if (WardingStoneBlock.canPacify(world, this.mob.getBlockPos())) {
            setTargetEntity(null);
        } else {
            setTargetEntity(targetEntity);
        }
    }
}
