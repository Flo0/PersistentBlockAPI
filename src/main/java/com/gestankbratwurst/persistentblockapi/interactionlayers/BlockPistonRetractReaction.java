package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.BlockPistonRetractEvent;

public interface BlockPistonRetractReaction {
	
	public void handle(BlockPistonRetractEvent event);
	
}
