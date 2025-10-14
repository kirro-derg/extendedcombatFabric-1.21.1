package dev.kirro.extendedcombat.item.custom;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class MilkBottleItem extends Item {
    private static final int MAX_USE_TIME = 40;
    protected final MilkType type;

    public MilkBottleItem(Item.Settings settings, MilkType type) {
        super(settings);
        this.type = type;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }

        if (!world.isClient) {
            switch (type) {
                case PLAIN ->
                    removeEffectOfType(user, StatusEffectCategory.NEUTRAL);
                case SWEET_BERRY ->
                    removeEffectOfType(user, StatusEffectCategory.HARMFUL);
                case CHOCOLATE ->
                    removeEffectOfType(user, StatusEffectCategory.BENEFICIAL);
            }
        }

        if (user instanceof PlayerEntity player) {
            return ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE), false);
        } else {
            stack.decrementUnlessCreative(1, user);
            return stack;
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        MilkType milkType = this.type;
        switch (milkType) {
            case PLAIN -> tooltip.add(Text.translatable("tooltip.milk_bottle.milk").formatted(Formatting.GOLD));
            case SWEET_BERRY -> tooltip.add(Text.translatable("tooltip.milk_bottle.sweet_berry_milk").formatted(Formatting.GOLD));
            case CHOCOLATE -> tooltip.add(Text.translatable("tooltip.milk_bottle.chocolate_milk").formatted(Formatting.GOLD));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

    private void removeEffectOfType(LivingEntity living, StatusEffectCategory category) {
        Iterator<StatusEffectInstance> iterator = living.getActiveStatusEffects().values().iterator();
        iterator.forEachRemaining(instance -> {
            if (instance.getEffectType().value().getCategory() == category) {
                iterator.remove();
                living.onStatusEffectRemoved(instance);
            }
        });
    }

    public MilkType getType() {
        return this.type;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return MAX_USE_TIME;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public SoundEvent getDrinkSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    @Override
    public SoundEvent getEatSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    public enum MilkType implements StringIdentifiable {
        PLAIN("neutral"),
        SWEET_BERRY("harmful"),
        CHOCOLATE("beneficial");

        private final String name;

        MilkType (final String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }
}
