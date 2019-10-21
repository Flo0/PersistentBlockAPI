package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.BlockPistonExtendEvent;

public interface BlockPistonExtendReaction {
	
	public void handle(BlockPistonExtendEvent event);
	
}
