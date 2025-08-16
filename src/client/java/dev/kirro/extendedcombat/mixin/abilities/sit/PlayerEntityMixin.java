package dev.kirro.extendedcombat.mixin.abilities.sit;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.kirro.extendedcombat.behavior.abilities.SitBehavior;
import dev.kirro.extendedcombat.util.ExtendedCombatClientUtil;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }

    /*@Override
    protected EntityDimensions getBaseDimensions(EntityPose pose) {
        EntityDimensions original = super.getBaseDimensions(pose);

        if (ExtendedCombatClientUtil.sitting_players.contains(this.getUuid()) && SitBehavior.isSitting()) {
            return EntityDimensions.fixed(original.width(), original.height() - 0.5f);
        }

        return original;
    }*/

    /*@ModifyReturnValue(method = "getBaseDimensions", at = @At("RETURN"))
    protected EntityDimensions getBaseDimensions(EntityDimensions original) {
        if (ExtendedCombatClientUtil.sitting_players.contains(this.getUuid())) {
            return EntityDimensions.fixed(original.width(), original.height() - 0.5f);
        }
        return original;
    }*/
}
