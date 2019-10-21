package com.gestankbratwurst.persistentblockapi.datapersistence.iointerfaces;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import com.gestankbratwurst.persistentblockapi.PersistentBlock;
import com.gestankbratwurst.persistentblockapi.PersistentBlockFactory;
import com.gestankbratwurst.persistentblockapi.PersistentBlockRegistry;
import com.gestankbratwurst.persistentblockapi.datapersistence.JsonPersistent;
import com.gestankbratwurst.persistentblockapi.util.UtilLoc;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class JsonReader implements PersistentDataReader {
	
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Override
	public PersistentBlock read(PersistentBlockFactory<?> factory, PersistentBlockRegistry registry, File dataFile) {
		JsonObject json = null;
		try(InputStreamReader isr = new InputStreamReader(new FileInputStream(dataFile))){
			json = gson.fromJson(isr, JsonObject.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(json == null) return null;
		
		PersistentBlock pBlock = factory.initPersistentBlock(UtilLoc.fromJson(json.get("Location").getAsJsonObject()));
		registry.injectID(pBlock, UUID.fromString(json.get("InternalID").getAsString()));
		((JsonPersistent)pBlock).loadData(json.get("Data").getAsJsonObject());
		
		return pBlock;
	}

}
