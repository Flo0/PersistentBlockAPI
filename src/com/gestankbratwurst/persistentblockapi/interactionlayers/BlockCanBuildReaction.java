package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.BlockCanBuildEvent;

public interface BlockCanBuildReaction {
	
	public void handle(BlockCanBuildEvent event);
	
}
