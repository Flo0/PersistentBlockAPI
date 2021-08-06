package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.BlockDamageEvent;

public interface BlockDamageReaction {
	
	public void handle(BlockDamageEvent event);
	
}
