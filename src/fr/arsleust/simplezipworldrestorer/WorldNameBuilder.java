package fr.arsleust.simplezipworldrestorer;

public class WorldNameBuilder {
	
	private final String[] args;
	private final int firstArg;
	private String worldname;
	
	/**
	 * 
	 * @param args directly put all arguments received with onCommand
	 */
	public WorldNameBuilder(String[] args) {
		this.args = args;
		this.firstArg = 0;
	}
	/**
	 * 
	 * @param args directly put all arguments received with onCommand
	 * @param firstArg id (form 0) of the starting point
	 */
	public WorldNameBuilder(String[] args, int firstArg) {
		this.args = args;
		this.firstArg = firstArg;
	}
	
	public void buildWorldName() {
		worldname = "";
		for(int i = firstArg; i < args.length; i++) {
			worldname += args[i];
			if(i < args.length - 1) {
				worldname += " ";
			}
		}
	}
	
	public String getWorldName() {
		return this.worldname;
	}
	
}
