package fr.arsleust.simplezipworldrestorer.WorldOperators;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;

import fr.arsleust.simplezipworldrestorer.Plugin;
import fr.arsleust.simplezipworldrestorer.Exceptions.NoSuchWorldException;
import fr.arsleust.simplezipworldrestorer.Exceptions.SendableException;
import fr.arsleust.simplezipworldrestorer.Util.ZipFileUtil;

public class WorldZipper implements WorldOperator {
	
	private final String worldName;
	private boolean jobDone = false;
	
	public WorldZipper(String worldName) {
		this.worldName = worldName;
	}
	
	public void execute() throws SendableException {
		if(exists()) {
			try {
				zipWorldFolder();
			} catch (IOException e) {
				e.printStackTrace();
				throw new SendableException("Error while zipping : check server console for more info.");
			}
			jobDone = true;
		} else {
			throw new NoSuchWorldException(worldName);
		}
	}
	
	private void zipWorldFolder() throws IOException {
		File worldFolder = getWorldFolder();
		File backupFile = getBackupFile();
		try {
			ZipFileUtil.zipDirectory(worldFolder, backupFile);
		} catch (IOException e) {
			throw e;
		}
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
	
	private boolean exists() {
		File worldFolder = getWorldFolder();
		return worldFolder.isDirectory();
	}
	
	public Boolean isJobDone() {
		return this.jobDone;
	}
	
	public String getResultMessage() {
		if(isJobDone()) {
			return "Successfully zipped world " + worldName;
		} else {
			return "World " + worldName + " isn't zipped yet.";
		}
	}
	
}