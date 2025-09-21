package dev.kirro.extendedcombat.util;

import dev.kirro.ExtendedCombat;
import dev.kirro.ModConfig;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class ModItemModels {
    public static List<ModelIdentifier> getModels(String suffix) {
        List<ModelIdentifier> identifiers = new ArrayList<>();

        for (Identifier id : ModConfig.acceptableItems) {
            Identifier modelId = Identifier.of(id + suffix);
            identifiers.add(ModelIdentifier.ofInventoryVariant(modelId));
            ExtendedCombat.LOGGER.info(String.valueOf(modelId));
        }

        return identifiers;
    }

    public static Identifier getItemId(ItemStack stack) {
        return Registries.ITEM.getId(stack.getItem());
    }

    public static Identifier getModelPath(ItemStack stack, boolean correctMode) {
        Identifier textureId = Registries.ITEM.getId(stack.getItem());

        if (correctMode) {
            return Identifier.of(textureId.getNamespace(), textureId.getPath() + "_handheld");
        } else {
            return Identifier.of(textureId.getNamespace(), textureId.getPath());
        }

    }
}
