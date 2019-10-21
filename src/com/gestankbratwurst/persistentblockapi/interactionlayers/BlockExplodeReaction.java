package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public interface BlockExplodeReaction {
	
	public void handleBlockExplosion(BlockExplodeEvent event);
	public void handleEntityExplosion(EntityExplodeEvent event);
	
}
