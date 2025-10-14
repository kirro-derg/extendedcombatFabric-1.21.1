package dev.kirro.mixin.block;

import dev.kirro.extendedcombat.block.custom.WardingStoneBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ActiveTargetGoal.class)
public abstract class ActiveTargetGoalMixin extends TrackTargetGoal {


    @Shadow
    @Nullable
    protected LivingEntity targetEntity;

    public ActiveTargetGoalMixin(MobEntity mob, boolean checkVisibility) {
        super(mob, checkVisibility);
    }

    @Inject(method = "canStart", at = @At("RETURN"), cancellable = true)
    private void canStart(CallbackInfoReturnable<Boolean> cir) {
        if (this.targetEntity instanceof PlayerEntity player) {
            World world = player.getWorld();
            if (world instanceof ServerWorld serverWorld) {
                if (!(this.mob instanceof WardenEntity || !(this.mob instanceof WitherEntity))) {
                    if (WardingStoneBlock.canPacify(serverWorld, this.mob.getBlockPos(), this.mob)) {
                        cir.setReturnValue(false);
                    }
                }
            }
        }
    }
}
