package dev.kirro;

import dev.kirro.extendedcombat.block.ModBlocks;
import dev.kirro.extendedcombat.block.entity.ModBlockEntityTypes;
import dev.kirro.extendedcombat.item.ModItemGroups;
import dev.kirro.extendedcombat.item.ModItems;
import dev.kirro.extendedcombat.item.behavior.XPRepairTracker;
import dev.kirro.extendedcombat.villager.ModPOI;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
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

		//ModSounds.registerSounds();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModPOI.registerPOIs();
		ModBlockEntityTypes.registerBlockEntityTypes();


		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
				XPRepairTracker.tick(player);
			}
		});
	}
}