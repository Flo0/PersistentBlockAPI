package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.BlockExpEvent;

public interface BlockExpReaction {
	
	public void handle(BlockExpEvent event);
	
}
