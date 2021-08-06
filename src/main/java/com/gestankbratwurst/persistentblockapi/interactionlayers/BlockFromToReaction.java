package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.BlockFromToEvent;

public interface BlockFromToReaction {
	
	public void handle(BlockFromToEvent event);
	
}
