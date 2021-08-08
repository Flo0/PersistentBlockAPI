package com.gestankbratwurst.persistentblockapi.datapersistence;

import com.google.gson.JsonObject;

public interface JsonPersistent extends DataPersistent<JsonObject>{
	
	@Override
	public default PersistentDataFormat getDataFormat() {
		return PersistentDataFormat.JSON;
	}
	
}
