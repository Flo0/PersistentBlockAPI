package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.SpongeAbsorbEvent;

public interface SpongeAbsorbReaction {
	
	public void handle(SpongeAbsorbEvent event);
	
}
