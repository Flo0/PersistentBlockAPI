package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.BlockFadeEvent;

public interface BlockFadeReaction {
	
	public void handle(BlockFadeEvent event);
	
}
