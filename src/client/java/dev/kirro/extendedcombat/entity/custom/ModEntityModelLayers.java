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
    public static final EntityModelLayer PLAYER_SLIM_INNER_ARMOR = createInnerArmor("player_slim");
    public static final EntityModelLayer PLAYER_INNER_ARMOR = createInnerArmor("player");

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

    private static EntityModelLayer createInnerArmor(String id) {
        return register(id, "sleeves");
    }

    public static void registerLayerDefinitions() {
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.STATUE, StatueModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.STATUE_INNER_ARMOR, () -> StatueModel.getTexturedModelData(new Dilation(0.5f)));
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.STATUE_OUTER_ARMOR, () -> StatueModel.getTexturedModelData(new Dilation(1.0f)));
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.PLAYER_INNER_ARMOR, () -> TexturedModelData.of(PlayerEntityModel.getTexturedModelData(new Dilation(0.3f), false), 24, 16));
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.PLAYER_SLIM_INNER_ARMOR, () -> TexturedModelData.of(PlayerEntityModel.getTexturedModelData(new Dilation(0.3f), true), 24, 16));
        EntityRendererRegistry.register(ModEntities.STATUE, StatueRenderer::new);
    }
}
