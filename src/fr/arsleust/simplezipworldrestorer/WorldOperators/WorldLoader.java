package fr.arsleust.simplezipworldrestorer.WorldOperators;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import fr.arsleust.simplezipworldrestorer.Exceptions.NoSuchWorldException;
import fr.arsleust.simplezipworldrestorer.Exceptions.SendableException;

public class WorldLoader implements WorldOperator {
	
	private final String worldName;
	private boolean jobDone = false;
	
	public WorldLoader(String worldName) {
		this.worldName = worldName;
	}
	
	public void execute() throws SendableException {
		if(exists()) {
			if(!isWorldAlreadyLoaded()) {
				createWorld();
				jobDone = true;
			} else {
				throw new SendableException("World " + worldName + " is already loaded.");
			}
		} else {
			throw new NoSuchWorldException(worldName);
		}
	}
	
	private void createWorld() {
		WorldCreator worldCreator = new WorldCreator(worldName);
		worldCreator.createWorld();
	}
	
	private boolean isWorldAlreadyLoaded() {
		World world = Bukkit.getWorld(worldName);
		if(world != null) {
			return true;
		} else {
			return false;
		}
	}

	private File getWorldFolder() {
		File worldContainer = Bukkit.getWorldContainer();
		File worldFolder = new File(worldContainer, worldName);
		return worldFolder;
	}
	
	private boolean exists() {
		File worldFolder = getWorldFolder();
		return worldFolder.isDirectory();
	}
	
	public Boolean isJobDone() {
		return this.jobDone;
	}
	
	public String getResultMessage() {
		if(isJobDone()) {
			return "Successfully loaded world " + worldName;
		} else {
			return "World " + worldName + " isn't loaded yet.";
		}
	}
	
}
