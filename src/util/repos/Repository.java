package util.repos;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import util.PropertiesManager;
import util.folders.Folders;

public class Repository {

	public static boolean cloneRepos() {

		String jsonPath = PropertiesManager.getProperty("url.list.json");

		JSONParser parser = new JSONParser();
		JSONArray jsonArray = null;

		try {
			jsonArray = (JSONArray) parser.parse(new FileReader(jsonPath));
		} catch (Exception e) {
			System.out.println("error to get json file");
			e.printStackTrace();
			return false;
		}

		for (Object object : jsonArray) {
			JSONObject jsonObject = (JSONObject) object;
			String url = (String) jsonObject.get("url");
			File folder = new File(Folders.REPOS_FOLDER + File.separator
					+ url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")));
			if (!folder.exists() && folder.mkdirs()) {
				try {
					Git.cloneRepository().setURI(url).setDirectory(folder).call();
				} catch (Exception e) {
					try {
						FileUtils.deleteDirectory(folder);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
		}
		
		return true;
	}
}
