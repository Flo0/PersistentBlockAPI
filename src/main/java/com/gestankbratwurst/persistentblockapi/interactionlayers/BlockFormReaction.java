package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.BlockFormEvent;

public interface BlockFormReaction {
	
	public void handle(BlockFormEvent event);
	
}
