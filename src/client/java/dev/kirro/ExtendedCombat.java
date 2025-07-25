package dev.kirro;

import dev.kirro.extendedcombat.block.ModBlocks;
import dev.kirro.extendedcombat.block.entity.ModBlockEntityTypes;
import dev.kirro.extendedcombat.entity.ModEntities;
import dev.kirro.extendedcombat.entity.client.StatueModel;
import dev.kirro.extendedcombat.entity.client.StatueRenderer;
import dev.kirro.extendedcombat.entity.custom.ModEntityModelLayers;
import dev.kirro.extendedcombat.entity.custom.StatueEntity;
import dev.kirro.extendedcombat.item.ModItemGroups;
import dev.kirro.extendedcombat.item.ModItems;
import dev.kirro.extendedcombat.item.behavior.XPRepairTracker;
import dev.kirro.extendedcombat.sound.ModSounds;
import dev.kirro.extendedcombat.villager.ModPOI;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtendedCombat implements ModInitializer {
	public static final String MOD_ID = "extendedcombat";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		ModSounds.registerSounds();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModPOI.registerPOIs();
		ModBlockEntityTypes.registerBlockEntityTypes();
		ModEntities.registerModEntities();

		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
				XPRepairTracker.tick(player);
			}
		});

		FabricDefaultAttributeRegistry.register(ModEntities.STATUE, StatueEntity.createAttributes());
	}
}