package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.BlockBurnEvent;

public interface BlockBurnReaction {
	
	public void handle(BlockBurnEvent event);
	
}
