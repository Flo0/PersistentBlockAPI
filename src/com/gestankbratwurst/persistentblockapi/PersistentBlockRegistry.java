package com.gestankbratwurst.persistentblockapi;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.bukkit.NamespacedKey;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
/**
 * Registry for PersistentBlockFactorys
 * @author Gestankbratwurst
 *
 */
public class PersistentBlockRegistry implements Iterable<NamespacedKey>{
	
	protected PersistentBlockRegistry() {
		this.blockFactory = Maps.newHashMap();
	}
	
	private final Map<NamespacedKey, PersistentBlockFactory<? extends PersistentBlock>> blockFactory;
	
	public final void injectID(PersistentBlock pBlock, UUID blockID) {
		pBlock.setInternalSaveId(blockID);
	}
	
	protected final void registerBlockFactory(final NamespacedKey blockKey, final PersistentBlockFactory<?> factory) {
		Preconditions.checkArgument(!blockFactory.containsKey(blockKey), "This Namespace is already registered.");
		this.blockFactory.put(blockKey, factory);
	}
	
	protected final PersistentBlockFactory<?> getBlockFactory(final NamespacedKey blockKey) {
		Preconditions.checkArgument(blockFactory.containsKey(blockKey), "This Namespace is not registered.");
		return this.blockFactory.get(blockKey);
	}
	
	@Override
	public Iterator<NamespacedKey> iterator() {
		return this.blockFactory.keySet().iterator();
	}
	
}