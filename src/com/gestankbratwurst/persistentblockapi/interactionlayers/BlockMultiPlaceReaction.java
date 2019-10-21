package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.BlockMultiPlaceEvent;

public interface BlockMultiPlaceReaction {
	
	public void handle(BlockMultiPlaceEvent event);
	
}
