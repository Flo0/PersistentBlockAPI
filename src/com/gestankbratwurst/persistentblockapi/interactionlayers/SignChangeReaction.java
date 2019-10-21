package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.SignChangeEvent;

public interface SignChangeReaction {

	public void handle(SignChangeEvent event);
	
}
