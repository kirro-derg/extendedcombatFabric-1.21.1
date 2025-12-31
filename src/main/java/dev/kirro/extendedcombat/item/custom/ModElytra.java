package dev.kirro.extendedcombat.item.custom;

import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.registry.entry.RegistryEntry;

public class ModElytra extends ModArmorItem implements FabricElytraItem {
    public ModElytra(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }
}
