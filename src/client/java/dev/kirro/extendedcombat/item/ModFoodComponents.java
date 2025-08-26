package dev.kirro.extendedcombat.item;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class ModFoodComponents {
    public static final FoodComponent BLACK_APPLE = new FoodComponent.Builder()
            .nutrition(6)
            .saturationModifier(1.2f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 1), 1.0f)
            .build();
    public static final FoodComponent GOLDEN_STEAK = new FoodComponent.Builder()
            .nutrition(8)
            .saturationModifier(1.7f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 1), 1.0F)
            .alwaysEdible()
            .build();
}
