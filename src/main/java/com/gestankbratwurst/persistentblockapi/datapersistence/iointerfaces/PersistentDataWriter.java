package com.gestankbratwurst.persistentblockapi.datapersistence.iointerfaces;

import java.io.File;

import com.gestankbratwurst.persistentblockapi.PersistentBlock;
import com.gestankbratwurst.persistentblockapi.PersistentBlockFactory;

public interface PersistentDataWriter {
	
	public void write(final PersistentBlockFactory<?> factory, final PersistentBlock pBlock, final File dataFolder);
	
}
