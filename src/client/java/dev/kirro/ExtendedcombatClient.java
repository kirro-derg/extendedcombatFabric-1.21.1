package dev.kirro;

import dev.kirro.extendedcombat.block.ModBlocks;
import dev.kirro.extendedcombat.entity.ModEntities;
import dev.kirro.extendedcombat.entity.client.StatueModel;
import dev.kirro.extendedcombat.entity.client.StatueRenderer;
import dev.kirro.extendedcombat.entity.custom.ModEntityModelLayers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.render.RenderLayer;

public class ExtendedcombatClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FRAMED_GLASS_PANEL, RenderLayer.getCutout());

		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.STATUE, StatueModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.STATUE_INNER_ARMOR, () -> StatueModel.getTexturedModelData(new Dilation(0.5f)));
		EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.STATUE_OUTER_ARMOR, () -> StatueModel.getTexturedModelData(new Dilation(1.0f)));
		EntityRendererRegistry.register(ModEntities.STATUE, StatueRenderer::new);
	}
}