package com.gestankbratwurst.persistentblockapi.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import com.google.gson.JsonObject;

public class UtilLoc {
	
	public static void toSection(Location location, ConfigurationSection section) {
		section.set("World", location.getWorld().getUID().toString());
		section.set("X", location.getX());
		section.set("Y", location.getY());
		section.set("Z", location.getZ());
		section.set("Pitch", location.getPitch());
		section.set("Yaw", location.getYaw());
	}
	
	public static Location fromSection(ConfigurationSection section) {
		World world = Bukkit.getWorld(UUID.fromString(section.getString("World")));
		if(world == null) return null;
		return new Location(world, section.getDouble("X"), section.getDouble("Y"), section.getDouble("Z"), (float)section.getDouble("Pitch"), (float)section.getDouble("X"));
	}
	
	public static JsonObject toJson(Location location) {
		JsonObject json = new JsonObject();
		json.addProperty("World", location.getWorld().getUID().toString());
		json.addProperty("X", location.getX());
		json.addProperty("Y", location.getY());
		json.addProperty("Z", location.getZ());
		json.addProperty("Pitch", location.getPitch());
		json.addProperty("Yaw", location.getYaw());
		return json;
	}
	
	public static Location fromJson(JsonObject json) {
		World world = Bukkit.getWorld(UUID.fromString(json.get("World").getAsString()));
		if(world == null) return null;
		return new Location(world, json.get("X").getAsDouble(), json.get("Y").getAsDouble(), json.get("Z").getAsDouble(), json.get("Pitch").getAsFloat(), json.get("Yaw").getAsFloat());
	}
	
}