package com.gestankbratwurst.persistentblockapi.datapersistence.iointerfaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.gestankbratwurst.persistentblockapi.PersistentBlock;
import com.gestankbratwurst.persistentblockapi.PersistentBlockFactory;
import com.gestankbratwurst.persistentblockapi.datapersistence.JsonPersistent;
import com.gestankbratwurst.persistentblockapi.datapersistence.PersistentDataFormat;
import com.gestankbratwurst.persistentblockapi.util.UtilLoc;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class JsonWriter implements PersistentDataWriter {
	
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Override
	public void write(PersistentBlockFactory<?> factory, PersistentBlock pBlock, File dataFolder) {
		File dataFile = new File(dataFolder, pBlock.getInternalSaveId().toString() + "." + PersistentDataFormat.JSON.getExtension());
		
		JsonObject json = new JsonObject();
		
		json.add("Location", UtilLoc.toJson(pBlock.getLocation()));
		json.addProperty("InternalID", pBlock.getInternalSaveId().toString());
		
		JsonObject dataJson = new JsonObject();
		((JsonPersistent)pBlock).saveData(dataJson);
		
		json.add("Data", dataJson);
		try(OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(dataFile))){
			gson.toJson(json, osw);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
