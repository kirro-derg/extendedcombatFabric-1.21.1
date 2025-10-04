package dev.kirro.extendedcombat.item.custom;

import dev.kirro.extendedcombat.tags.ModItemTags;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

public class NetherSteelArmorItem extends ModArmorItem {

    public NetherSteelArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient()) {
            if (entity instanceof LivingEntity living && hasFullSuitOfArmorOn(living)) {
                evaluateArmorEffects(living);
            }
            super.inventoryTick(stack, world, entity, slot, selected);
        }
    }

    private void evaluateArmorEffects(LivingEntity entity) {
            if (hasCorrectArmorOn(entity) && !entity.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 20, 1, false, false, false));
            }
    }

    private boolean hasFullSuitOfArmorOn(LivingEntity entity) {
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        ItemStack leggings = entity.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack chestplate = entity.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack helmet = entity.getEquippedStack(EquipmentSlot.HEAD);

        return !helmet.isEmpty() && !chestplate.isEmpty()
                && !leggings.isEmpty() && !boots.isEmpty();
    }

    private boolean hasCorrectArmorOn(LivingEntity entity) {
        for (ItemStack armorStack: entity.getArmorItems()) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ItemStack boots = (entity.getEquippedStack(EquipmentSlot.FEET));
        ItemStack leggings = (entity.getEquippedStack(EquipmentSlot.LEGS));
        ItemStack chestplate = (entity.getEquippedStack(EquipmentSlot.CHEST));
        ItemStack helmet = (entity.getEquippedStack(EquipmentSlot.HEAD));

        return helmet.isIn(ModItemTags.FLAME_RESISTANT_ARMOR)
                && chestplate.isIn(ModItemTags.FLAME_RESISTANT_ARMOR)
                && leggings.isIn(ModItemTags.FLAME_RESISTANT_ARMOR)
                && boots.isIn(ModItemTags.FLAME_RESISTANT_ARMOR);
    }
}
