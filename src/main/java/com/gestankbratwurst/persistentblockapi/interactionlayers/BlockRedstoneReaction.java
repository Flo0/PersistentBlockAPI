package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.BlockRedstoneEvent;

public interface BlockRedstoneReaction {
	
	public void handle(BlockRedstoneEvent event);
	
}
