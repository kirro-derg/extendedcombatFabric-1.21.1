package dev.kirro.extendedcombat.item.custom;

import dev.kirro.extendedcombat.util.ExtendedCombatUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

public class ModArmorItem extends ArmorItem {
    public ModArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient()) {
            if (entity instanceof LivingEntity living && ExtendedCombatUtil.isFlameResistant(living)) {
                evaluateArmorEffects(living);
            }
            super.inventoryTick(stack, world, entity, slot, selected);
        }
    }

    private void evaluateArmorEffects(LivingEntity entity) {
        if (!entity.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 20, 1, false, false, false));
        }
    }
}
