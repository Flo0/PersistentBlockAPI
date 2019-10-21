package com.gestankbratwurst.persistentblockapi.datapersistence;

import java.io.File;

import com.gestankbratwurst.persistentblockapi.PersistentBlock;
import com.gestankbratwurst.persistentblockapi.PersistentBlockFactory;
import com.gestankbratwurst.persistentblockapi.PersistentBlockPlugin;
import com.gestankbratwurst.persistentblockapi.PersistentBlockRegistry;

public class PersistentDataIO {
	
	public PersistentDataIO(PersistentBlockPlugin plugin) {
		
	}
	
	public void removeDirectory(File directory) {
		for(File subFile : directory.listFiles()) {
			if(subFile.isDirectory()) this.removeDirectory(subFile);
			subFile.delete();
		}
	}
	
	public PersistentBlock loadBlock(final PersistentBlockFactory<?> factory, PersistentBlockRegistry registry, final File dataFile) {
		return factory.getDataFormat().getDataReader().read(factory, registry, dataFile);
	}
	
	public void saveBlock(final PersistentBlockFactory<?> factory, final PersistentBlock pBlock, final File dataFolder) {
		factory.getDataFormat().getDataWriter().write(factory, pBlock, dataFolder);
	}
	
}
