package com.gestankbratwurst.persistentblockapi.datapersistence;

import javax.annotation.Nullable;

import com.gestankbratwurst.persistentblockapi.datapersistence.iointerfaces.ByteArrayReader;
import com.gestankbratwurst.persistentblockapi.datapersistence.iointerfaces.ByteArrayWriter;
import com.gestankbratwurst.persistentblockapi.datapersistence.iointerfaces.JsonReader;
import com.gestankbratwurst.persistentblockapi.datapersistence.iointerfaces.JsonWriter;
import com.gestankbratwurst.persistentblockapi.datapersistence.iointerfaces.PersistentDataReader;
import com.gestankbratwurst.persistentblockapi.datapersistence.iointerfaces.PersistentDataWriter;
import com.gestankbratwurst.persistentblockapi.datapersistence.iointerfaces.YmlReader;
import com.gestankbratwurst.persistentblockapi.datapersistence.iointerfaces.YmlWriter;
import com.google.common.collect.ImmutableMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PersistentDataFormat {
	
	YML("yml", new YmlReader(), new YmlWriter()),
	JSON("json", new JsonReader(), new JsonWriter()),
	BYTE_ARRAY("pbbe", new ByteArrayReader(), new ByteArrayWriter());
	
	@Getter
	private final String extension;
	@Getter
	private final PersistentDataReader dataReader;
	@Getter
	private final PersistentDataWriter dataWriter;
	
	private static final ImmutableMap<String, PersistentDataFormat> EXTENSION_MAP = ImmutableMap.<String, PersistentDataFormat>builder()
			.put("yml", YML)
			.put("json", JSON)
			.put("pbbe", BYTE_ARRAY)
			.build();
	
	@Nullable
	public static PersistentDataFormat ofFileExtension(final String extension) {
		return EXTENSION_MAP.get(extension);
	}
	
}