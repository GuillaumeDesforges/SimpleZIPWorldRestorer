package fr.arsleust.simplezipworldrestorer.WorldOperators;

import fr.arsleust.simplezipworldrestorer.Exceptions.SendableException;

public interface WorldOperator {
	
	public void execute() throws SendableException;
	
	public Boolean isJobDone();

	public String getResultMessage();
	
}
