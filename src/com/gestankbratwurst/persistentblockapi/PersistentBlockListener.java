package com.gestankbratwurst.persistentblockapi;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
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
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
/**
 * Internal event listener
 * @author Gestankbratwurst
 *
 */
public class PersistentBlockListener implements Listener {
	
	protected PersistentBlockListener(PersistentBlockAPI api) {
		this.api = api;
	}
	
	private final PersistentBlockAPI api;
	
	@EventHandler
	public void onWorlLoad(WorldLoadEvent event) {
		this.api.loadWorld(event.getWorld());
	}
	
	@EventHandler
	public void onWorlUnload(WorldUnloadEvent event) {
		this.api.unloadWorld(event.getWorld());
	}
	
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent event) {
		this.api.loadChunk(event.getChunk());
	}
	
	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent event) {
		this.api.unloadChunk(event.getChunk());
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(BlockBreakEvent event){
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(BlockBurnEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(BlockCanBuildEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(BlockDamageEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(BlockDispenseEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(BlockExpEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(BlockExplodeEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(EntityExplodeEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(BlockFadeEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(BlockFertilizeEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(BlockFormEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(BlockFromToEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(BlockGrowEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(BlockIgniteEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(BlockMultiPlaceEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(BlockPhysicsEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(BlockPistonExtendEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(BlockPistonRetractEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(BlockPlaceEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(BlockRedstoneEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(BlockSpreadEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(CauldronLevelChangeEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(EntityBlockFormEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(LeavesDecayEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(MoistureChangeEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(NotePlayEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(SignChangeEvent event) {
		this.api.handleEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void handleEvent(SpongeAbsorbEvent event) {
		this.api.handleEvent(event);
	}
	
}