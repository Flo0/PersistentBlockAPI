package com.gestankbratwurst.persistentblockapi;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.AccessLevel;
import lombok.Getter;

public class PersistentBlockPlugin extends JavaPlugin {
	
	@Getter(value = AccessLevel.PROTECTED)
	private static PersistentBlockPlugin instance;
	
	@Override
	public void onEnable() {
		instance = this;
	}
	
	@Override
	public void onDisable() {
		PersistentBlockAPI.get().flush();
	}
	
}
