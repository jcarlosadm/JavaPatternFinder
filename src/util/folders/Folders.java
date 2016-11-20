package util.folders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Folders {

	public static final String REPOS_FOLDER = "repos";
	public static final String OUTPUTS_FOLDER = "outputs";
	public static final String LOGS_FOLDER = OUTPUTS_FOLDER + File.separator + "logs";

	public static boolean makeFolders() {
		
		List<File> folders = new ArrayList<>();
		// add folders here!
		folders.add(new File(REPOS_FOLDER));
		folders.add(new File(OUTPUTS_FOLDER));
		folders.add(new File(LOGS_FOLDER));
		
		for (File folder : folders) {
			if (!createFolder(folder)) {
				return false;
			}
		}
		
		return true;
	}
	
	private static boolean createFolder(File folder) {
		if (!folder.exists() && !folder.mkdirs()) {
			return false;
		}
		
		return true;
	}
}
