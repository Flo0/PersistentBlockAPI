package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.MoistureChangeEvent;

public interface MoistureChangeReaction {
	
	public void handle(MoistureChangeEvent event);
	
}