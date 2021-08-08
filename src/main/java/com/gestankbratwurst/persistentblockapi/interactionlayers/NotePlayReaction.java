package com.gestankbratwurst.persistentblockapi.interactionlayers;

import org.bukkit.event.block.NotePlayEvent;

public interface NotePlayReaction {
	
	public void handle(NotePlayEvent event);
	
}
