package com.gestankbratwurst.persistentblockapi.datapersistence;

import org.bukkit.configuration.ConfigurationSection;

public interface YmlPersistent extends DataPersistent<ConfigurationSection>{
	
	@Override
	public default PersistentDataFormat getDataFormat() {
		return PersistentDataFormat.YML;
	}
	
}
