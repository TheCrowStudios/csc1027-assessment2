package part01;

import java.util.ArrayList;

public class Helper {
	public static IdNameClass findById(ArrayList<IdNameClass> list, int id) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == id) {
				return list.get(i);
			}
		}
		
		return null;
	}

	public static ArrayList<IdNameClass> findByName(ArrayList<IdNameClass> list, String name) {
		ArrayList<IdNameClass> searchResults = new ArrayList<IdNameClass>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().contains(name)) {
				searchResults.add(list.get(i));
			}
		}
		
		return searchResults;
	}

	public static Exhibit findExhibitById(ArrayList<Exhibit> exhibits, int id) {
		for (int i = 0; i < exhibits.size(); i++) {
			if (exhibits.get(i).getId() == id) {
				return exhibits.get(i);
			}
		}
		
		return null;
	}

	public static ArrayList<Exhibit> findExhibitsByName(ArrayList<Exhibit> exhibits, String name) {
		ArrayList<Exhibit> searchResults = new ArrayList<Exhibit>();
		for (int i = 0; i < exhibits.size(); i++) {
			if (exhibits.get(i).getName().contains(name)) {
				searchResults.add(exhibits.get(i));
			}
		}
		
		return searchResults;
	}

	public static Artifact findArtifactById(ArrayList<Artifact> artifacts, int id) {
		for (int i = 0; i < artifacts.size(); i++) {
			if (artifacts.get(i).getId() == id) {
				return artifacts.get(i);
			}
		}
		
		return null;
	}

	public static ArrayList<Artifact> findArtifactsByName(ArrayList<Artifact> artifacts, String name) {
		ArrayList<Artifact> searchResults = new ArrayList<Artifact>();
		for (int i = 0; i < artifacts.size(); i++) {
			if (artifacts.get(i).getName().contains(name)) {
				searchResults.add(artifacts.get(i));
			}
		}
		
		return searchResults;
	}

}
