package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.BlockPlaceEvent;

public interface BlockPlaceReaction {
	
	public void handle(BlockPlaceEvent event);
	
}
