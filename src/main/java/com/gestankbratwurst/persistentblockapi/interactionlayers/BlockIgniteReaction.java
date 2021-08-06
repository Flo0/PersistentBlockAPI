package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.BlockIgniteEvent;

public interface BlockIgniteReaction {
	
	public void handle(BlockIgniteEvent event);
	
}
