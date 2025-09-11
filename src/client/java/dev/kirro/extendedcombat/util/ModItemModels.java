package dev.kirro.extendedcombat.util;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.tags.ModItemTags;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModItemModels {
    public static List<ModelIdentifier> getModels(String suffix) {
        List<ModelIdentifier> identifiers = new ArrayList<>();

        for (Item item : Registries.ITEM) {
            Identifier itemId = Registries.ITEM.getId(item);
            for (String suffixes : itemSuffixes()) {
                for (String namespace : nameSpaces()) {
                    if (itemId.getPath().endsWith(suffixes) && itemId.getNamespace().equalsIgnoreCase(namespace)) {
                        Identifier modelId = Identifier.of(itemId.getNamespace(), itemId.getPath() + suffix);
                        identifiers.add(ModelIdentifier.ofInventoryVariant(modelId));
                        System.out.println(modelId);
                    }
                }

            }

        }

        return identifiers;
    }

    public static List<String> itemSuffixes() {
        List<String> suffixes = new ArrayList<>();

        suffixes.add("_greatsword");

        return suffixes;
    }

    public  static List<String> nameSpaces() {
        List<String> nameSpaces = new ArrayList<>();

        nameSpaces.add(ExtendedCombat.MOD_ID);
        nameSpaces.add("minecraft");

        return nameSpaces;
    }
}
