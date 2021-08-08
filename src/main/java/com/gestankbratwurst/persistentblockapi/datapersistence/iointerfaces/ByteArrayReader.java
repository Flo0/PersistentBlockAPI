package com.gestankbratwurst.persistentblockapi.datapersistence.iointerfaces;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.gestankbratwurst.persistentblockapi.PersistentBlock;
import com.gestankbratwurst.persistentblockapi.PersistentBlockFactory;
import com.gestankbratwurst.persistentblockapi.PersistentBlockRegistry;
import com.gestankbratwurst.persistentblockapi.datapersistence.ByteArrayPersistent;
import com.gestankbratwurst.persistentblockapi.util.ByteStash;

public class ByteArrayReader implements PersistentDataReader {
	
	private static final ByteBuffer BUFFER_4 = ByteBuffer.allocate(4);
	private static final ByteBuffer BUFFER_8 = ByteBuffer.allocate(8);
	
	@Override
	public PersistentBlock read(PersistentBlockFactory<?> factory, PersistentBlockRegistry registry, File dataFile) {
		
		UUID internalID = null;
		UUID worldID = null;
		double x;
		double y;
		double z;
		float pitch;
		float yaw;
		int dataSize;
		byte[] data = null;
		
		try {
			
			FileInputStream stream = new FileInputStream(dataFile);
			stream.read(BUFFER_8.array());
			long msb = BUFFER_8.getLong(0);
			stream.read(BUFFER_8.array());
			long lsb = BUFFER_8.getLong(0);
			
			internalID = new UUID(msb, lsb);
			
			stream.read(BUFFER_8.array());
			msb = BUFFER_8.getLong(0);
			stream.read(BUFFER_8.array());
			lsb = BUFFER_8.getLong(0);
			
			worldID = new UUID(msb, lsb);
			
			if(worldID == null || internalID == null) {
				stream.close();
				return null;
			}
			
			stream.read(BUFFER_8.array());
			x = BUFFER_8.getDouble(0);
			stream.read(BUFFER_8.array());
			y = BUFFER_8.getDouble(0);
			stream.read(BUFFER_8.array());
			z = BUFFER_8.getDouble(0);
			
			stream.read(BUFFER_4.array());
			pitch = BUFFER_4.getFloat(0);
			stream.read(BUFFER_4.array());
			yaw = BUFFER_4.getFloat(0);
			
			stream.read(BUFFER_4.array());
			dataSize = BUFFER_4.getInt(0);
			
			if(dataSize > 0) {
				data = new byte[dataSize];
				stream.read(data);
			}
			
			if(stream.read() != -1) {
				System.out.println("§cERROR§f >>> §cUnexpected byte length.");
			}
			stream.close();
			
		} catch (IOException e) {
			System.out.println("§cERROR§f >>> §cFailed to load PersistentBlock §f:§c IOException.");
			return null;
		}
		
		World world = Bukkit.getWorld(worldID);
		if(world == null) return null;
		
		PersistentBlock pBlock = factory.initPersistentBlock(new Location(world, x, y, z, pitch, yaw));
		registry.injectID(pBlock, internalID);
		if(data != null && data.length > 0) {
			((ByteArrayPersistent)pBlock).loadData(ByteStash.ofBoxed(data));
		}
		
		return pBlock;
	}

}
