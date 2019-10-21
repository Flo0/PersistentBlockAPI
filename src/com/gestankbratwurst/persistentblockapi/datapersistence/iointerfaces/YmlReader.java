package com.gestankbratwurst.persistentblockapi.datapersistence.iointerfaces;

import java.io.File;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gestankbratwurst.persistentblockapi.PersistentBlock;
import com.gestankbratwurst.persistentblockapi.PersistentBlockFactory;
import com.gestankbratwurst.persistentblockapi.PersistentBlockRegistry;
import com.gestankbratwurst.persistentblockapi.datapersistence.YmlPersistent;
import com.gestankbratwurst.persistentblockapi.util.UtilLoc;

public class YmlReader implements PersistentDataReader {

	@Override
	public PersistentBlock read(PersistentBlockFactory<?> factory, PersistentBlockRegistry registry, File dataFile) {
		FileConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
		PersistentBlock pBlock = factory.initPersistentBlock(UtilLoc.fromSection(config.getConfigurationSection("Location")));
		registry.injectID(pBlock, UUID.fromString(config.getString("InternalID")));
		((YmlPersistent)pBlock).loadData(config.getConfigurationSection("Data"));
		return pBlock;
	}
	
}