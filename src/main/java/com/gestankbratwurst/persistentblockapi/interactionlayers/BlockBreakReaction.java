package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.BlockBreakEvent;

public interface BlockBreakReaction {
	
	public void handle(BlockBreakEvent event);
	
}
