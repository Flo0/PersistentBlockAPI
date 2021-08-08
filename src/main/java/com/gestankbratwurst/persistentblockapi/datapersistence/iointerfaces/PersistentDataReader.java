package com.gestankbratwurst.persistentblockapi.datapersistence.iointerfaces;

import java.io.File;

import com.gestankbratwurst.persistentblockapi.PersistentBlock;
import com.gestankbratwurst.persistentblockapi.PersistentBlockFactory;
import com.gestankbratwurst.persistentblockapi.PersistentBlockRegistry;

public interface PersistentDataReader {
	
	public PersistentBlock read(final PersistentBlockFactory<?> factory, final PersistentBlockRegistry registry, final File dataFile);
	
}