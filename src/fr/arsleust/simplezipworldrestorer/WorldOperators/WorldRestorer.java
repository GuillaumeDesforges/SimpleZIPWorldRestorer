package fr.arsleust.simplezipworldrestorer.WorldOperators;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import fr.arsleust.simplezipworldrestorer.Plugin;
import fr.arsleust.simplezipworldrestorer.Exceptions.NoSuchWorldException;
import fr.arsleust.simplezipworldrestorer.Exceptions.SendableException;
import fr.arsleust.simplezipworldrestorer.Util.FileUtil;
import fr.arsleust.simplezipworldrestorer.Util.ZipFileUtil;
import net.md_5.bungee.api.ChatColor;

public class WorldRestorer implements WorldOperator {

	private final String worldName;
	private boolean jobDone = false;
	
	public WorldRestorer(String worldName) {
		this.worldName = worldName;
	}
	
	public void execute() throws SendableException {
		Bukkit.getLogger().info("[SimpleZIPWorldRestorer] Restoring world " + worldName + " : Kicking players out of the world ...");
		kickPlayers();
		Bukkit.getLogger().info("[SimpleZIPWorldRestorer] Restoring world " + worldName + " : Unloading world ...");
		unloadWorld();
		Bukkit.getLogger().info("[SimpleZIPWorldRestorer] Restoring world " + worldName + " : Cleaning world ...");
		cleanData();
		Bukkit.getLogger().info("[SimpleZIPWorldRestorer] Restoring world " + worldName + " : Restoring data ...");
		restoreData();
		Bukkit.getLogger().info("[SimpleZIPWorldRestorer] Reloading world " + worldName + " : Reloading world ...");
		reloadWorld();
		Bukkit.getLogger().info("[SimpleZIPWorldRestorer] Reloading world " + worldName + " : Done !");
		jobDone = true;
	}
	
	private void kickPlayers() throws NoSuchWorldException {
		World world = Bukkit.getWorld(worldName);
		if(world == null) {
			throw new NoSuchWorldException(worldName);
		}
		Location teleportLocation = Bukkit.getWorlds().get(0).getSpawnLocation();
		for(Player p : world.getPlayers()) {
			p.teleport(teleportLocation);
			p.sendMessage(ChatColor.BLUE + "[WorldRestorer] The world you were in was restored. You were kicked out of it.");
		}
	}

	private void unloadWorld() throws NoSuchWorldException {
		World world = Bukkit.getWorld(worldName);
		if(world == null) {
			throw new NoSuchWorldException(worldName);
		}
		// put false to ensure world isn't saved when unloaded
		Bukkit.unloadWorld(world, false);
	}

	private void cleanData() {
		FileUtil.delete(getWorldFolder());
	}
	
	private void restoreData() throws SendableException {
		try {
			ZipFileUtil.unzipFileIntoDirectory(getBackupFile(), getWorldFolder());
		} catch (IOException e) {
			e.printStackTrace();
			throw new SendableException("Error while unzipping, check console for more info.");
		}
	}
	
	private void reloadWorld() {
		WorldCreator worldCreator = new WorldCreator(worldName);
		worldCreator.createWorld();
	}
	
	private File getWorldFolder() {
		File worldContainer = Bukkit.getWorldContainer();
		File worldFolder = new File(worldContainer, worldName);
		return worldFolder;
	}
	
	private File getBackupFile() {
		File backupFolder = Plugin.getBackupFolder();
		File backupFile = new File(backupFolder, worldName + ".zip");
		return backupFile;
	}
	
	public Boolean isJobDone() {
		return this.jobDone;
	}
	
	public String getResultMessage() {
		if(isJobDone()) {
			return "Successfully restored world " + worldName;
		} else {
			return "World " + worldName + " isn't restored yet.";
		}
	}

}
