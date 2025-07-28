package dev.kirro;

import dev.kirro.extendedcombat.block.ModBlocks;
import dev.kirro.extendedcombat.entity.ModEntities;
import dev.kirro.extendedcombat.entity.custom.ModEntityModelLayers;
import dev.kirro.extendedcombat.entity.render.ChairRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class ExtendedcombatClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FRAMED_GLASS_PANEL, RenderLayer.getCutout());

		ModEntityModelLayers.registerLayerDefinitions();
		EntityRendererRegistry.register(ModEntities.CHAIR, ChairRenderer::new);
	}
}