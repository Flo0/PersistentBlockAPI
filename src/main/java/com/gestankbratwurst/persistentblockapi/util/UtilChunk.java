package com.gestankbratwurst.persistentblockapi.util;

import org.apache.commons.lang.Validate;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.World;

public class UtilChunk {
	
	public static int[] getChunkCoords(long chunkKey) {
		int x = ((int)chunkKey) >> 32;
		int z = (int)(chunkKey >> 32) >> 32;
		return new int[] {x, z};
	}
	
	public static long getChunkKey(int x, int z) {
		return (long) x & 0xffffffffL | ((long) z & 0xffffffffL) << 32;
	}

	public static long getChunkKey(Chunk chunk) {
		return (long) chunk.getX() & 0xffffffffL | ((long) chunk.getZ() & 0xffffffffL) << 32;
	}

	public static Chunk keyToChunk(World world, long chunkID) {
		Validate.notNull(world, "World cannot be null");
		return world.getChunkAt((int) chunkID, (int) (chunkID >> 32));
	}

	public static boolean isChunkLoaded(Location loc) {
		int chunkX = loc.getBlockX() >> 4;
		int chunkZ = loc.getBlockZ() >> 4;
		return loc.getWorld().isChunkLoaded(chunkX, chunkZ);
	}

	public static long getChunkKey(Location loc) {
		return getChunkKey(loc.getBlockX() >> 4, loc.getBlockZ() >> 4);
	}
	
	public static long getChunkKey(ChunkSnapshot chunk) {
		return (long) chunk.getX() & 0xffffffffL | ((long) chunk.getZ() & 0xffffffffL) << 32;
	}
	
}
