package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.BlockSpreadEvent;

public interface BlockSpreadReaction {
	
	public void handle(BlockSpreadEvent event);
	
}
