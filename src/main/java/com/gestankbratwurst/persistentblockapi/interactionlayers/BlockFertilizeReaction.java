package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.BlockFertilizeEvent;

public interface BlockFertilizeReaction {
	
	public void handle(BlockFertilizeEvent event);
	
}