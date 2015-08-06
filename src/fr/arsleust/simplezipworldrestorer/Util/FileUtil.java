package fr.arsleust.simplezipworldrestorer.Util;

import java.io.File;

public class FileUtil {
	
	public static void delete(File file) {
		if(file.isDirectory()) {
			for(File subfile : file.listFiles()) {
				delete(subfile);
			}
		} else {
			file.delete();
		}
	}
	
}
