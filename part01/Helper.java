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

	public static <T extends IdNameClass> ArrayList<? extends IdNameClass> sortByName(ArrayList<? extends IdNameClass> list) {
		ArrayList<T> sorted = new ArrayList<T>();

		for (int i = 0; i < list.size(); i++) {
			sorted.add((T) list.get(i)); // fill new list
		}

		Boolean swapped = true;

		while (swapped) {
			swapped = false;

			for (int i = 0; i < sorted.size() - 1; i++) {
				if (sorted.get(i).getName().compareTo(sorted.get(i + 1).getName()) > 0) {
					T object = sorted.get(i);
					sorted.set(i, sorted.get(i + 1));
					sorted.set(i + 1, object);
					swapped = true;
				}
			}
		}

		return sorted;
	}

	public static void generateArtifacts(Museum museum) {
		museum.artifacts.add(new Artifact("Delaware Landscape", ArtifactType.PAINTING, 1));
		museum.artifacts.add(new Artifact("Acropolis Statues", ArtifactType.SCULPTURE, 5));
		museum.artifacts.add(new Artifact("Incas Interactive", ArtifactType.DIGITAL, 10));
		museum.artifacts.add(new Artifact("TouchIt", ArtifactType.TACTILE, 8));
		museum.artifacts.add(new Artifact("1KG Carbon Statue", ArtifactType.SCULPTURE, 8));
		museum.artifacts.add(new Artifact("Dinosaur Statue", ArtifactType.ARTIFACT, 8));
		museum.artifacts.add(new Artifact("Tar Pit Miniature", ArtifactType.SCULPTURE, 8));
		museum.artifacts.add(new Artifact("Coal Miner Portrait", ArtifactType.PAINTING, 8));
		museum.artifacts.add(new Artifact("Home Carbon Footprint Miniature", ArtifactType.SCULPTURE, 8));
	}

	public static void generateExhibits(Museum museum) {
		museum.exhibits.add(new Exhibit("Global Warming"));
		museum.exhibits.add(new Exhibit("Egypt"));
		museum.exhibits.add(new Exhibit("Architecture"));
		museum.exhibits.add(new Exhibit("Vikings"));
		museum.exhibits.add(new Exhibit("History of AI"));
		museum.exhibits.add(new Exhibit("Ship Building"));
		museum.exhibits.add(new Exhibit("Henry VIII"));
		museum.exhibits.add(new Exhibit("Geology"));
		museum.exhibits.add(new Exhibit("Beasts of the Sea"));
	}

	public static void generateAnnualPlan(Museum museum) {
		museum.annualPlans.add(new AnnualPlan("Exhibit Hall 1"));
	}

	public static Museum generateMuseum() {
		Museum museum = new Museum();
		generateArtifacts(museum);
		generateExhibits(museum);
		generateAnnualPlan(museum);

		for (int i = 0; i < museum.exhibits.size(); i++) {
			
		}

		return museum;
	}
}
