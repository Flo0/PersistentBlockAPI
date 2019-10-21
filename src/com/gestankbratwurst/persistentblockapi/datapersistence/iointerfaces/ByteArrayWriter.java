package com.gestankbratwurst.persistentblockapi.datapersistence.iointerfaces;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

import org.bukkit.Location;

import com.gestankbratwurst.persistentblockapi.PersistentBlock;
import com.gestankbratwurst.persistentblockapi.PersistentBlockFactory;
import com.gestankbratwurst.persistentblockapi.datapersistence.ByteArrayPersistent;
import com.gestankbratwurst.persistentblockapi.datapersistence.PersistentDataFormat;
import com.gestankbratwurst.persistentblockapi.util.ByteStash;

public class ByteArrayWriter implements PersistentDataWriter {
	
	private static final int BASE_BYTE_ALLOCATIONS = 68;
	
	@Override
	public void write(PersistentBlockFactory<?> factory, PersistentBlock pBlock, File dataFolder) {
		File dataFile = new File(dataFolder, pBlock.getInternalSaveId().toString() + "." + PersistentDataFormat.BYTE_ARRAY.getExtension());
		
		ByteArrayPersistent persistent = ((ByteArrayPersistent)pBlock);
		Location location = pBlock.getLocation();
		UUID worldID = location.getWorld().getUID();
		UUID internalSaveID = pBlock.getInternalSaveId();
		
		ByteStash stash = new ByteStash();
		persistent.saveData(stash);
		byte[] dataBytes = stash.getBytes();
		
		ByteBuffer buffer = ByteBuffer.allocate(BASE_BYTE_ALLOCATIONS + dataBytes.length);
		
		buffer.putLong(internalSaveID.getMostSignificantBits());
		buffer.putLong(internalSaveID.getLeastSignificantBits());
		buffer.putLong(worldID.getMostSignificantBits());
		buffer.putLong(worldID.getLeastSignificantBits());
		buffer.putDouble(location.getX());
		buffer.putDouble(location.getY());
		buffer.putDouble(location.getZ());
		buffer.putFloat(location.getPitch());
		buffer.putFloat(location.getYaw());
		buffer.putInt(dataBytes.length);
		buffer.put(dataBytes);
		
		try(FileOutputStream stream = new FileOutputStream(dataFile)){
			stream.write(buffer.array());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
