package dev.kirro.extendedcombat.item.custom;

import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.item.ElytraItem;

public class ModElytra extends ElytraItem implements FabricElytraItem {
    public ModElytra(Settings settings) {
        super(settings);
    }
}
