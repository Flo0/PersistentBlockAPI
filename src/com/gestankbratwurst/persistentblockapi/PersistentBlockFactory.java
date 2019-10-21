package com.gestankbratwurst.persistentblockapi;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;

import com.gestankbratwurst.persistentblockapi.datapersistence.PersistentDataFormat;

/**
 * This class needs to be registered at
 * the {@link PersistentBlockAPI} in order to
 * create/load blocks with this namespace
 * 
 * @author Gestankbratwurst
 *
 * @param <T> the PersistentBlock class type you want to initialized
 */
public interface PersistentBlockFactory<T extends PersistentBlock> {
	
	/**
	 * The {@link NamespacedKey} that should be associated with
	 * this block.
	 * @return
	 */
	public abstract NamespacedKey getBlockKey();
	/**
	 * Has to return a valid instance of <T>
	 * @param location the location the block is beeing initialized at.
	 * @return instance of <T>
	 */
	public abstract T initPersistentBlock(final Location location);
	/**
	 * Choose a data format to save the block data.
	 * Your <T> has to implement the following interface
	 * to its corresponding {@link PersistentDataFormat}
	 * 
	 * YML >> {@link YmlPersistent}
	 * JSON >> {@link JsonPersistent}
	 * BYTE_ARRAY >> {@link ByteArrayPersistent}
	 * 
	 * @return the chosen data format
	 */
	public abstract PersistentDataFormat getDataFormat();
	
}