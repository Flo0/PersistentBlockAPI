package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.BlockGrowEvent;

public interface BlockGrowReaction {
	
	public void handle(BlockGrowEvent event);
	
}
