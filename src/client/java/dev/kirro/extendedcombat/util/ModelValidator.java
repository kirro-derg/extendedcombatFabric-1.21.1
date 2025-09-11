package dev.kirro.extendedcombat.util;

import dev.kirro.extendedcombat.tags.ModItemTags;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class ModelValidator {
    public static void validateScaledItemModels(String suffix) {
        Optional<Path> modelsPath = FabricLoader.getInstance()
                .getModContainer("extendedcombat")
                .flatMap(mod -> mod.findPath("assets/extendedcombat/models/item"));

        if (modelsPath.isEmpty()) {
            System.out.println("⚠ Could not locate model path for extendedcombat");
            return;
        }
        Path modelPath = modelsPath.get();
        System.out.println("[ExtendedCombat] Validating scaled item models...");

        for (RegistryEntry<Item> entry : Registries.ITEM.iterateEntries(ModItemTags.SCALED_ITEMS)) {
            Identifier itemId = Registries.ITEM.getId(entry.value());
            String modelName = itemId.getPath() + suffix;
            Path modelFile = modelPath.resolve(modelName + ".json");

            if (!Files.exists(modelFile)) {
                System.out.println("⚠ Missing model file: " + modelFile);
            } else {
                System.out.println("✓ Found model: " + modelFile);
            }
        }
    }

}
