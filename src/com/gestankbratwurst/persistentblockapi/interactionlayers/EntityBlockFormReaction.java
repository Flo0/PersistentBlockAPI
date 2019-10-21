package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.EntityBlockFormEvent;

public interface EntityBlockFormReaction {
	
	public void handle(EntityBlockFormEvent event);
	
}
