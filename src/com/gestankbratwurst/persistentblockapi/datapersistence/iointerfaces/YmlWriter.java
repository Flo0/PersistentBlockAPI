package com.gestankbratwurst.persistentblockapi.datapersistence.iointerfaces;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gestankbratwurst.persistentblockapi.PersistentBlock;
import com.gestankbratwurst.persistentblockapi.PersistentBlockFactory;
import com.gestankbratwurst.persistentblockapi.datapersistence.PersistentDataFormat;
import com.gestankbratwurst.persistentblockapi.datapersistence.YmlPersistent;
import com.gestankbratwurst.persistentblockapi.util.UtilLoc;

public class YmlWriter implements PersistentDataWriter {

	@Override
	public void write(PersistentBlockFactory<?> factory, PersistentBlock pBlock, File dataFolder) {
		File dataFile = new File(dataFolder, pBlock.getInternalSaveId().toString() + "." + PersistentDataFormat.YML.getExtension());
		
		FileConfiguration config = new YamlConfiguration();
		
		UtilLoc.toSection(pBlock.getLocation(), config.createSection("Location"));
		config.set("InternalID", pBlock.getInternalSaveId().toString());
		
		((YmlPersistent)pBlock).saveData(config.createSection("Data"));
		
		try {
			config.save(dataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
