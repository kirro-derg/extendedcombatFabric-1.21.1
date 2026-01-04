package dev.kirro.extendedcombat.item.custom;

import dev.kirro.extendedcombat.item.ModDataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class HeatBladeItem extends SwordItem {
    private final int MAX_CHARGE = 8;
    private int CHARGE;
    private int COOLDOWN;

    public HeatBladeItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    public int getMaxCharge() {
        return this.MAX_CHARGE;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {
            ItemStack handStack = player.getMainHandStack();
            if (player.isInLava() && stack.isOf(this) && !player.getItemCooldownManager().isCoolingDown(stack.getItem()) && getCharge(stack) < getMaxCharge()) {
                stack.set(ModDataComponentTypes.CHARGE, Math.min(getCharge(stack) + 1, this.MAX_CHARGE));
                player.getItemCooldownManager().set(stack.getItem(), 20);
            }
            this.CHARGE = handStack.getOrDefault(ModDataComponentTypes.CHARGE, 0);
            if (this.COOLDOWN > 0) this.COOLDOWN--;
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public int getCharge(ItemStack stack) {
        return MathHelper.clamp(stack.getOrDefault(ModDataComponentTypes.CHARGE, 0), 0, this.getMaxCharge());
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        ItemStack handStack = attacker.getMainHandStack();
        if (attacker.getMainHandStack().isOf(this)) {
            if (getCharge(stack) >= 5) {
                target.setOnFire(true);
                target.setOnFireFor(getCharge(stack));
            }
            handStack.set(ModDataComponentTypes.CHARGE, Math.max(getCharge(stack) - 1, 0));
        }
    }

    @Override
    public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource) {
        return super.getBonusAttackDamage(target, baseAttackDamage, damageSource) + this.CHARGE;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (user.isSneaking()) {
            stack.set(ModDataComponentTypes.CHARGE, Math.min(getCharge(stack) + 1, this.MAX_CHARGE));
        } else {
            stack.set(ModDataComponentTypes.CHARGE, Math.max(getCharge(stack) - 1, 0));
        }
        return super.use(world, user, hand);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.min(Math.max(getCharge(stack) + getCharge(stack) - 3, 0), 13);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return super.getItemBarColor(stack) + getCharge(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal("charge" + ":" + getCharge(stack)));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
