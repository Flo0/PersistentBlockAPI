package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.BlockDispenseEvent;

public interface BlockDispenseReaction {
	
	public void handle(BlockDispenseEvent event);
	
}
