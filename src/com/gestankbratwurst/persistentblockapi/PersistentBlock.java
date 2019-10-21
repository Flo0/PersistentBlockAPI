package com.gestankbratwurst.persistentblockapi;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
/**
 * This class has to be extended by your new persistent block.
 * Has to implement one of those interfaces. 
 * (Has to be compatible with the chosen {@link PersistentDataFormat} int your {@link PersistentBlockFactory})
 * @author Gestankbratwurst
 *
 */
public abstract class PersistentBlock {
	
	/**
	 * Constructor
	 * @param location the location of the called block.
	 * @param blockKey the namespaced key of this block.
	 */
	public PersistentBlock(Location location, NamespacedKey blockKey) {
		this.location = location;
		this.blockKey = blockKey;
	}
	
	@Getter @Setter(value = AccessLevel.PROTECTED)
	private UUID internalSaveId;
	private final Location location;
	@Getter
	private final NamespacedKey blockKey;
	private final PersistentBlockAPI api = PersistentBlockAPI.get();
	
	/**
	 * Gets called after all data is loaded.
	 */
	protected abstract void onLoad();
	/**
	 * Gets called before any data is saved.
	 */
	protected abstract void onUnload();
	
	/**
	 * Does the same as PersistentBlockAPI.get().remove(this);
	 */
	public void remove() {
		api.removePersistentBlock(this);
	}
	
	/**
	 * The blocks location.
	 * @return A copy of this blocks location.
	 */
	public Location getLocation() {
		return this.location.clone();
	}
	
}