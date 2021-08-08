package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.LeavesDecayEvent;

public interface LeavesDecayReaction {

	public void handle(LeavesDecayEvent event);
	
}
