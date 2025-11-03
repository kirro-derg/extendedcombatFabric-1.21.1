package dev.kirro.extendedcombat.entity.custom;

import dev.kirro.ExtendedCombat;
import dev.kirro.extendedcombat.entity.ModEntities;
import dev.kirro.extendedcombat.entity.client.StatueModel;
import dev.kirro.extendedcombat.entity.render.StatueRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Identifier;

public class ModEntityModelLayers {
    public static final EntityModelLayer STATUE = create("statue");
    public static final EntityModelLayer STATUE_INNER_ARMOR = create("statue", "inner_armor");
    public static final EntityModelLayer STATUE_OUTER_ARMOR = create("statue", "outer_armor");
    public static final EntityModelLayer PLAYER_SLIM_SLEEVES = createSleeveLayer("player_slim");
    public static final EntityModelLayer PLAYER_SLEEVES = createSleeveLayer("player");
    public static final EntityModelLayer CLOAK_INNER = create("cloak", "inner");
    public static final EntityModelLayer CLOAK_INNER_SLIM = create("cloak", "inner_slim");
    public static final EntityModelLayer CLOAK_OUTER = create("cloak", "outer");
    public static final EntityModelLayer MASK = create("mask");

    public static EntityModelLayer create(String id) {
        return create(id, "main");
    }

    private static EntityModelLayer register(String id, String layer) {
        EntityModelLayer entityModelLayer = create(id, layer);
        if (!EntityModelLayers.LAYERS.add(entityModelLayer)) {
            throw new IllegalStateException("Duplicate registration for " + entityModelLayer);
        } else {
            return entityModelLayer;
        }
    }

    public static EntityModelLayer create(String id, String layer) {
        return new EntityModelLayer(Identifier.of(ExtendedCombat.MOD_ID, id), layer);
    }

    private static EntityModelLayer createSleeveLayer(String id) {
        return register(id, "sleeves");
    }

    public static void registerLayerDefinitions() {
        EntityModelLayerRegistry.registerModelLayer(STATUE, StatueModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(STATUE_INNER_ARMOR, () -> StatueModel.getTexturedModelData(new Dilation(0.5f)));
        EntityModelLayerRegistry.registerModelLayer(STATUE_OUTER_ARMOR, () -> StatueModel.getTexturedModelData(new Dilation(1.0f)));
        EntityModelLayerRegistry.registerModelLayer(PLAYER_SLEEVES, () -> TexturedModelData.of(PlayerEntityModel.getTexturedModelData(new Dilation(0.3f), false), 64, 64));
        EntityModelLayerRegistry.registerModelLayer(PLAYER_SLIM_SLEEVES, () -> TexturedModelData.of(PlayerEntityModel.getTexturedModelData(new Dilation(0.3f), true), 64, 64));
        EntityModelLayerRegistry.registerModelLayer(CLOAK_INNER, () -> TexturedModelData.of(PlayerEntityModel.getTexturedModelData(new Dilation(0.1f), false), 64, 64));
        EntityModelLayerRegistry.registerModelLayer(CLOAK_INNER_SLIM, () -> TexturedModelData.of(PlayerEntityModel.getTexturedModelData(new Dilation(0.1f), true), 64, 64));
        EntityModelLayerRegistry.registerModelLayer(CLOAK_OUTER, () -> TexturedModelData.of(PlayerEntityModel.getTexturedModelData(new Dilation(1.0f), false), 64, 64));
        EntityModelLayerRegistry.registerModelLayer(MASK, () -> TexturedModelData.of(PlayerEntityModel.getTexturedModelData(new Dilation(0.5f), false), 64, 64));
        EntityRendererRegistry.register(ModEntities.STATUE, StatueRenderer::new);
    }
}
