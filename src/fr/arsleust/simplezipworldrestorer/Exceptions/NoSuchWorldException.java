package fr.arsleust.simplezipworldrestorer.Exceptions;

public class NoSuchWorldException extends SendableException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -275832015475098241L;

	public NoSuchWorldException(String worldName) {
		super("There is no such world : " + worldName);
	}

}
