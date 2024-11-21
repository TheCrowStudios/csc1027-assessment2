package part01;

import java.util.ArrayList;

public class Helper {
	public static <T extends IdNameClass> IdNameClass findById(ArrayList<? extends IdNameClass> list, int id) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == id) {
				return list.get(i);
			}
		}
		
		return null;
	}

	public static <T extends IdNameClass> ArrayList<? extends IdNameClass> findByName(ArrayList<? extends IdNameClass> list, String name) {
		ArrayList<T> searchResults = new ArrayList<T>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().contains(name)) {
				searchResults.add((T) list.get(i));
			}
		}
		
		return (ArrayList<? extends IdNameClass>) searchResults;
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
