package com.gestankbratwurst.persistentblockapi;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.MoistureChangeEvent;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.block.SpongeAbsorbEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;

import com.gestankbratwurst.persistentblockapi.datapersistence.PersistentDataIO;
import com.gestankbratwurst.persistentblockapi.interactionlayers.BlockBreakReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.BlockBurnReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.BlockCanBuildReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.BlockDamageReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.BlockDispenseReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.BlockExpReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.BlockExplodeReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.BlockFadeReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.BlockFertilizeReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.BlockFormReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.BlockFromToReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.BlockGrowReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.BlockIgniteReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.BlockMultiPlaceReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.BlockPhysicsReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.BlockPistonExtendReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.BlockPistonRetractReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.BlockPlaceReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.BlockRedstoneReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.BlockSpreadReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.CauldronLevelChangeReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.EntityBlockFormReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.LeavesDecayReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.MoistureChangeReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.NotePlayReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.SignChangeReaction;
import com.gestankbratwurst.persistentblockapi.interactionlayers.SpongeAbsorbReaction;
import com.gestankbratwurst.persistentblockapi.util.UtilChunk;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * This API class is used to register your {@link PersistentBlockFactory}s,
 * and to create/remove {@link PersistentBlock}s at locations using
 * a {@link NamespacedKey}
 * @author Gestankbratwurst
 *
 */
public class PersistentBlockAPI {
	
	private static final String DATA_FOLDER_NAME = "persistentblocks";
	
	private static PersistentBlockAPI instance;
	
	/**
	 * Self initializing getter of the PersistentBlockAPI instance.
	 * @return the singleton instance
	 */
	public static PersistentBlockAPI get() {
		return instance == null ? instance = new PersistentBlockAPI(PersistentBlockPlugin.getInstance()) : instance;
	}
	
	/**
	 * Constructor.
	 * @param plugin
	 */
	private PersistentBlockAPI(final PersistentBlockPlugin plugin) {
		this.plugin = plugin;
		this.registry = new PersistentBlockRegistry();
		this.loadedBlockMapping = Maps.newHashMap();
		this.operationMap = Maps.newHashMap();
		Bukkit.getPluginManager().registerEvents(new PersistentBlockListener(this), plugin);
		this.io = new PersistentDataIO(plugin);
		Bukkit.getWorlds().forEach(world -> this.loadWorld(world));
	}
	
	private final Map<Location, PersistentBlock> operationMap;
	private final Map<UUID, Map<Long, Map<Location, PersistentBlock>>> loadedBlockMapping;
	private final PersistentBlockPlugin plugin;
	private final PersistentBlockRegistry registry;
	private final PersistentDataIO io;
	
	/**
	 * Gets the {@link PersistentBlock} at this blocks
	 * location. 
	 * 
	 * Returns null if not present.
	 * 
	 * @param block the Block
	 * 
	 * @return a {@link PersistentBlock} instance or null
	 */
	@Nullable
	public final PersistentBlock getPersistentBlock(final Block block) {
		return this.getPersistentBlock(block.getLocation());
	}
	
	/**
	 * Gets the {@link PersistentBlock} at this
	 * location. 
	 * 
	 * Returns null if not present.
	 * 
	 * @param location the Location
	 * 
	 * @return a {@link PersistentBlock} instance or null
	 */
	@Nullable
	public final PersistentBlock getPersistentBlock(final Location location) {
		return operationMap.get(location);
	}
	
	/**
	 * Creates a {@link PersistentBlock} at this
	 * location. 
	 * 
	 * The location is internally calibrated
	 * to be at the exact position of the Block at this
	 * location.
	 * 
	 * @param location the Location
	 * @param blockKey a valid, registered key
	 * 
	 * @return a {@link PersistentBlock} instance
	 */
	public final PersistentBlock createPersistentBlock(Location location, final NamespacedKey blockKey) {
		location = location.getBlock().getLocation();
		PersistentBlock pBlock = this.registry.getBlockFactory(blockKey).initPersistentBlock(location.getBlock().getLocation());
		this.loadedBlockMapping.get(location.getWorld().getUID()).get(UtilChunk.getChunkKey(location)).put(location, pBlock);
		this.operationMap.put(location, pBlock);
		pBlock.setInternalSaveId(UUID.randomUUID());
		return pBlock;
	}
	
	/**
	 * Removes a {@link PersistentBlock}
	 * 
	 * @param pBlock the {@link PersistentBlock} to remove.
	 */
	public final void removePersistentBlock(final PersistentBlock pBlock) {
		this.removePersistentBlock(pBlock.getLocation(), false);
	}
	
	/**
	 * Removes a {@link PersistentBlock} at a given location.
	 * 
	 * The location is internally calibrated to
	 * be at the exact position of the Block at this
	 * location.
	 * 
	 * @param location the location
	 * 
	 * @return true if a block was at this location and is now removed. False otherwise.
	 */
	public final boolean removePersistentBlock(final Location location) {
		return this.removePersistentBlock(location, false);
	}
	
	/**
	 * Removes a {@link PersistentBlock}
	 * 
	 * @param location the location
	 * @param setAir is the blocks type at this location should be set to air.
	 * 
	 * @return true if a block was at this location and is now removed. False otherwise.
	 */
	public final boolean removePersistentBlock(final PersistentBlock pBlock, final boolean setAir) {
		return this.removePersistentBlock(pBlock.getLocation(), setAir);
	}
	
	/**
	 * Removes a {@link PersistentBlock} at a given location.
	 * 
	 * The location is internally calibrated to
	 * be at the exact position of the Block at this
	 * location.
	 * 
	 * @param location the location
	 * @param setAir is the blocks type at this location should be set to air.
	 * 
	 * @return true if a block was at this location and is now removed. False otherwise.
	 */
	public final boolean removePersistentBlock(Location location, final boolean setAir) {
		location = location.getBlock().getLocation();
		Map<Location, PersistentBlock> chunkBlocks = this.loadedBlockMapping.get(location.getWorld().getUID()).get(UtilChunk.getChunkKey(location));
		if(chunkBlocks == null) return false;
		PersistentBlock pBlock = chunkBlocks.get(location);
		if(pBlock == null) return false;
		chunkBlocks.remove(location);
		this.operationMap.remove(location);
		if(setAir) location.getBlock().setType(Material.AIR);
		return true;
	}
	
	/**
	 * Registers a {@link PersistentBlockFactory}.
	 * This will trigger all chunks to be searched for
	 * {@link PersistentBlock}s with this key.
	 * 
	 * @param factory the block factory.
	 */
	public final void registerPersistentBlock(final PersistentBlockFactory<?> factory) {
		this.registry.registerBlockFactory(factory.getBlockKey(), factory);
		for(World world : Bukkit.getWorlds()) {
			for(Chunk chunk : world.getLoadedChunks()) {
				this.loadChunk(chunk);
			}
		}
	}
	
	/**
	 * Moves data from one location to another.
	 * 
	 * Will load the target chunk.
	 * 
	 * Throws {@link IllegalStateException} if the target location
	 * already inherits some persistent block data.
	 * 
	 * If the location to copy from does not inherit any block data
	 * nothin will happen.
	 * @param from the location to copy from
	 * @param to the location to copy to
	 */
	public final void movePersistentBlockData(Location from, Location to) {
		from = from.getBlock().getLocation();
		to = to.getBlock().getLocation();
		PersistentBlock fromBlock = this.getPersistentBlock(from);
		PersistentBlock toBlock = this.getPersistentBlock(to);
		if(fromBlock == null) return;
		Preconditions.checkState(toBlock == null, "Tried to move blockdata to an already occupied location.");
		
		Map<Location, PersistentBlock> fromChunk = this.loadedBlockMapping.get(from.getWorld().getUID()).get(UtilChunk.getChunkKey(from));
		Map<Location, PersistentBlock> toChunk = this.loadedBlockMapping.get(to.getWorld().getUID()).get(UtilChunk.getChunkKey(to));
		
		this.operationMap.remove(from);
		this.operationMap.put(to, fromBlock);
		
		fromChunk.remove(from);
		toChunk.put(to, fromBlock);
	}
	
	/**
	 * Unlaods all worlds.
	 */
	protected final void flush() {
		for(World world : Bukkit.getWorlds()) {
			this.unloadWorld(world);
		}
	}
	
	/**
	 * Loads a world
	 * @param world
	 */
	protected final void loadWorld(final World world) {
		this.loadedBlockMapping.put(world.getUID(), Maps.newHashMap());
		for(final Chunk chunk : world.getLoadedChunks()) {
			this.loadChunk(chunk);
		}
	}
	
	/**
	 * Loads a chunk
	 * @param chunk
	 */
	protected final void loadChunk(final Chunk chunk) {
		final long chunkKey = UtilChunk.getChunkKey(chunk);
		Map<Location, PersistentBlock> chunkMap = Maps.newHashMap();
		this.loadedBlockMapping.get(chunk.getWorld().getUID()).put(chunkKey, chunkMap);
		final File chunkFolder = new File(chunk.getWorld().getWorldFolder() + File.separator + DATA_FOLDER_NAME + File.separator + chunkKey);
		for(PersistentBlock pBlock : this.loadChunkFolderData(chunkFolder)) {
			chunkMap.put(pBlock.getLocation(), pBlock);
			this.operationMap.put(pBlock.getLocation(), pBlock);
			pBlock.onLoad();
		}
	}
	
	/**
	 * Unloads a world
	 * @param world
	 */
	protected final void unloadWorld(final World world) {
		for(Chunk chunk : world.getLoadedChunks()) {
			this.unloadChunk(chunk);
		}
		this.loadedBlockMapping.remove(world.getUID());
	}
	
	/**
	 * Unloads a chunk
	 * @param chunk
	 */
	protected final void unloadChunk(final Chunk chunk) {
		UUID worldID = chunk.getWorld().getUID();
		long chunkKey = UtilChunk.getChunkKey(chunk);
		this.saveChunkToFile(chunkKey, chunk.getWorld());
		this.loadedBlockMapping.get(worldID).remove(chunkKey);
	}
	
	/**
	 * Triggers chunk save to disk.
	 * @param chunkKey
	 * @param world
	 */
	private final void saveChunkToFile(long chunkKey, World world) {
		File chunkFolder = new File(world.getWorldFolder() + File.separator + DATA_FOLDER_NAME + File.separator + chunkKey);
		Collection<PersistentBlock> chunkBlocks = this.loadedBlockMapping.get(world.getUID()).get(chunkKey).values();
		if(chunkBlocks.isEmpty()) {
			if(chunkFolder.exists()) {
				this.io.removeDirectory(chunkFolder);
			}
			return;
		}
		if(!chunkFolder.exists()) chunkFolder.mkdirs();
		Set<File> existingFolders = Sets.newHashSet();
		for(PersistentBlock pBlock : chunkBlocks) {
			NamespacedKey blockKey = pBlock.getBlockKey();
			File dataFolder = new File(chunkFolder + File.separator + blockKey.getNamespace() + File.separator + blockKey.getKey());
			if(!existingFolders.contains(dataFolder)) {
				if(dataFolder.exists()) this.io.removeDirectory(dataFolder);
				dataFolder.mkdirs();
				existingFolders.add(dataFolder);
			}else if(!dataFolder.exists()) {
				dataFolder.mkdirs();
			}
			pBlock.onUnload();
			this.io.saveBlock(this.registry.getBlockFactory(blockKey), pBlock, dataFolder);
		}
		
		for(File blockFolder : existingFolders) {
			if(blockFolder.list().length == 0) {
				blockFolder.delete();
				File pluginFolder = blockFolder.getParentFile();
				if(pluginFolder.list().length == 0) {
					pluginFolder.delete();
				}
			}
		}
		if(chunkFolder.list().length == 0) {
			chunkFolder.delete();
		}
	}
	
	/**
	 * Triggers chunk load from disk.
	 * @param chunkFolder
	 * @return
	 */
	private final Set<PersistentBlock> loadChunkFolderData(final File chunkFolder) {
		Set<PersistentBlock> blockSet = Sets.newHashSet();
		if(!chunkFolder.exists()) return blockSet;
		
		for(File nameFolder : chunkFolder.listFiles()) {
			Plugin pluginHost = Bukkit.getPluginManager().getPlugin(nameFolder.getName());
			if(pluginHost == null) continue;
			PluginBlockLoop:
			for(File dataFolder : nameFolder.listFiles()) {
				NamespacedKey dataKey = new NamespacedKey(pluginHost, dataFolder.getName());
				PersistentBlockFactory<?> blockFactory = this.registry.getBlockFactory(dataKey);
				if(blockFactory == null) {
					Bukkit.getScheduler().runTask(this.plugin, () -> this.io.removeDirectory(dataFolder));
					break PluginBlockLoop;
				}
				for(File dataFile : dataFolder.listFiles()) {
					PersistentBlock pBlock = this.io.loadBlock(blockFactory, this.registry, dataFile);
					if(pBlock != null) {
						blockSet.add(pBlock);
					}
				}
			}
		}
		
		return blockSet;
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(BlockBreakEvent event){
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock == null) return;
		if(pBlock instanceof BlockBreakReaction) {
			((BlockBreakReaction)pBlock).handle(event);
		}
		if(!event.isCancelled()) pBlock.remove();
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(BlockBurnEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock == null) return;
		if(pBlock instanceof BlockBurnReaction) {
			((BlockBurnReaction)pBlock).handle(event);
		}
		if(!event.isCancelled()) pBlock.remove();
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(BlockCanBuildEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock == null) return;
		if(pBlock instanceof BlockCanBuildReaction) {
			((BlockCanBuildReaction)pBlock).handle(event);
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(BlockDamageEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock == null) return;
		if(pBlock instanceof BlockDamageReaction) {
			((BlockDamageReaction)pBlock).handle(event);
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(BlockDispenseEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock == null) return;
		if(pBlock instanceof BlockDispenseReaction) {
			((BlockDispenseReaction)pBlock).handle(event);
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(BlockExpEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock == null) return;
		if(pBlock instanceof BlockExpReaction) {
			((BlockExpReaction)pBlock).handle(event);
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(BlockExplodeEvent event) {
		for(Block block : event.blockList()) {
			PersistentBlock pBlock = this.getPersistentBlock(block);
			if(pBlock != null && pBlock instanceof BlockExplodeReaction) {
				((BlockExplodeReaction)pBlock).handleBlockExplosion(event);
			}
		}
		
		if(event.isCancelled()) return;
		
		PersistentBlock mainBlock = this.getPersistentBlock(event.getBlock());
		if(mainBlock != null) mainBlock.remove();
		
		for(Block block : event.blockList()) {
			PersistentBlock pBlock = this.getPersistentBlock(block);
			if(pBlock != null) pBlock.remove();
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(EntityExplodeEvent event) {
		for(Block block : event.blockList()) {
			PersistentBlock pBlock = this.getPersistentBlock(block);
			if(pBlock != null && pBlock instanceof BlockExplodeReaction) {
				((BlockExplodeReaction)pBlock).handleEntityExplosion(event);
			}
		}
		
		if(event.isCancelled()) return;
		
		for(Block block : event.blockList()) {
			PersistentBlock pBlock = this.getPersistentBlock(block);
			if(pBlock != null) pBlock.remove();
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(BlockFadeEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock == null) return;
		if(pBlock instanceof BlockFadeReaction) {
			((BlockFadeReaction)pBlock).handle(event);
		}
		if(!event.isCancelled()) pBlock.remove();
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(BlockFertilizeEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock != null && pBlock instanceof BlockFertilizeReaction) {
			((BlockFertilizeReaction)pBlock).handle(event);
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(BlockFormEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock != null && pBlock instanceof BlockFormReaction) {
			((BlockFormReaction)pBlock).handle(event);
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(BlockFromToEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock != null && pBlock instanceof BlockFromToReaction) {
			((BlockFromToReaction)pBlock).handle(event);
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(BlockGrowEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock != null && pBlock instanceof BlockGrowReaction) {
			((BlockGrowReaction)pBlock).handle(event);
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(BlockIgniteEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock != null && pBlock instanceof BlockIgniteReaction) {
			((BlockIgniteReaction)pBlock).handle(event);
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(BlockMultiPlaceEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock != null && pBlock instanceof BlockMultiPlaceReaction) {
			((BlockMultiPlaceReaction)pBlock).handle(event);
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(BlockPhysicsEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock != null && pBlock instanceof BlockPhysicsReaction) {
			((BlockPhysicsReaction)pBlock).handle(event);
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(BlockPistonExtendEvent event) {
		boolean containsPersistent = false;
		
		for(Block block : event.getBlocks()) {
			PersistentBlock pBlock = this.getPersistentBlock(block);
			if(pBlock != null) {
				containsPersistent = true;
				if(pBlock instanceof BlockPistonExtendReaction) {
					((BlockPistonExtendReaction)pBlock).handle(event);
				}
			}
		}
		
		if(!event.isCancelled() && containsPersistent) {
			List<Block> blocks = event.getBlocks();
			for(int i = blocks.size() - 1; i >= 0; i--) {
				Block block = blocks.get(i);
				PersistentBlock pBlock = this.getPersistentBlock(block);
				if(pBlock != null) {
					this.movePersistentBlockData(pBlock.getLocation(), pBlock.getLocation().getBlock().getRelative(event.getDirection()).getLocation());
				}
			}
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(BlockPistonRetractEvent event) {
		boolean containsPersistent = false;
		
		for(Block block : event.getBlocks()) {
			PersistentBlock pBlock = this.getPersistentBlock(block);
			if(pBlock != null) {
				containsPersistent = true;
				if(pBlock instanceof BlockPistonRetractReaction) {
					((BlockPistonRetractReaction)pBlock).handle(event);
				}
			}
		}
		
		if(!event.isCancelled() && containsPersistent) {
			List<Block> blocks = event.getBlocks();
			for(int i = blocks.size() - 1; i >= 0; i--) {
				Block block = blocks.get(i);
				PersistentBlock pBlock = this.getPersistentBlock(block);
				if(pBlock != null) {
					this.movePersistentBlockData(pBlock.getLocation(), pBlock.getLocation().getBlock().getRelative(event.getDirection()).getLocation());
				}
			}
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(BlockPlaceEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock != null && pBlock instanceof BlockPlaceReaction) {
			((BlockPlaceReaction)pBlock).handle(event);
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(BlockRedstoneEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock != null && pBlock instanceof BlockRedstoneReaction) {
			((BlockRedstoneReaction)pBlock).handle(event);
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(BlockSpreadEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock != null && pBlock instanceof BlockSpreadReaction) {
			((BlockSpreadReaction)pBlock).handle(event);
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(CauldronLevelChangeEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock != null && pBlock instanceof CauldronLevelChangeReaction) {
			((CauldronLevelChangeReaction)pBlock).handle(event);
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(EntityBlockFormEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock != null && pBlock instanceof EntityBlockFormReaction) {
			((EntityBlockFormReaction)pBlock).handle(event);
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(LeavesDecayEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock == null) return;
		if(pBlock instanceof LeavesDecayReaction) {
			((LeavesDecayReaction)pBlock).handle(event);
		}
		if(!event.isCancelled()) pBlock.remove();
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(MoistureChangeEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock != null && pBlock instanceof MoistureChangeReaction) {
			((MoistureChangeReaction)pBlock).handle(event);
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(NotePlayEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock != null && pBlock instanceof NotePlayReaction) {
			((NotePlayReaction)pBlock).handle(event);
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(SignChangeEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock != null && pBlock instanceof SignChangeReaction) {
			((SignChangeReaction)pBlock).handle(event);
		}
	}
	
	/**
	 * Internal event handler
	 * @param event
	 */
	protected void handleEvent(SpongeAbsorbEvent event) {
		PersistentBlock pBlock = this.getPersistentBlock(event.getBlock());
		if(pBlock != null && pBlock instanceof SpongeAbsorbReaction) {
			((SpongeAbsorbReaction)pBlock).handle(event);
		}
	}
	
}
