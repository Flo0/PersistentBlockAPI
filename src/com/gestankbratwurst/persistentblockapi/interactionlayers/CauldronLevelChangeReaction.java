package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.CauldronLevelChangeEvent;

public interface CauldronLevelChangeReaction {
	
	public void handle(CauldronLevelChangeEvent event);
	
}
