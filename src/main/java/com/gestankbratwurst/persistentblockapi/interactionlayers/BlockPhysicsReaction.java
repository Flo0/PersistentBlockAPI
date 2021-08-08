package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.BlockPhysicsEvent;

public interface BlockPhysicsReaction {
	
	public void handle(BlockPhysicsEvent event);
	
}
