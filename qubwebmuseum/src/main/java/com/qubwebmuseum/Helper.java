package com.qubwebmuseum;

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
		museum.artifacts.add(new Artifact("1KG Carbon Statue", ArtifactType.SCULPTURE, 8));
        museum.artifacts.add(new Artifact("Home Carbon Footprint Miniature", ArtifactType.SCULPTURE, 8));
        museum.artifacts.add(new Artifact("Climate Change Interactive Map", ArtifactType.DIGITAL, 9));
        museum.artifacts.add(new Artifact("Polar Ice Cap Model", ArtifactType.TACTILE, 7));

        // Egypt Exhibit Artifacts
        museum.artifacts.add(new Artifact("Pharaoh Statue", ArtifactType.SCULPTURE, 10));
        museum.artifacts.add(new Artifact("Hieroglyphic Tablet", ArtifactType.HISTORICAL, 9));
        museum.artifacts.add(new Artifact("Mummy Reconstruction", ArtifactType.ARTIFACT, 8));
        museum.artifacts.add(new Artifact("Ancient Egyptian Painting", ArtifactType.PAINTING, 7));

        // Architecture Exhibit Artifacts
        museum.artifacts.add(new Artifact("Gothic Cathedral Model", ArtifactType.SCULPTURE, 9));
        museum.artifacts.add(new Artifact("Frank Lloyd Wright Sketch", ArtifactType.PAINTING, 8));
        museum.artifacts.add(new Artifact("Modern City Interactive", ArtifactType.DIGITAL, 10));
        museum.artifacts.add(new Artifact("Ancient Roman Pillar", ArtifactType.HISTORICAL, 7));

        // Vikings Exhibit Artifacts
        museum.artifacts.add(new Artifact("Viking Longship Model", ArtifactType.SCULPTURE, 9));
        museum.artifacts.add(new Artifact("Norse Mythology Tapestry", ArtifactType.PAINTING, 8));
        museum.artifacts.add(new Artifact("Viking Weapons Display", ArtifactType.ARTIFACT, 10));
        museum.artifacts.add(new Artifact("Viking Settlement Miniature", ArtifactType.TACTILE, 7));

        // History of AI Exhibit Artifacts
        museum.artifacts.add(new Artifact("First Computer Model", ArtifactType.SCULPTURE, 10));
        museum.artifacts.add(new Artifact("Alan Turing Portrait", ArtifactType.PAINTING, 9));
        museum.artifacts.add(new Artifact("AI Development Timeline", ArtifactType.DIGITAL, 8));
        museum.artifacts.add(new Artifact("Machine Learning Interactive", ArtifactType.INTERACTIVE, 9));

        // Ship Building Exhibit Artifacts
        museum.artifacts.add(new Artifact("Historical Ship Model", ArtifactType.SCULPTURE, 9));
        museum.artifacts.add(new Artifact("Shipyard Worker Painting", ArtifactType.PAINTING, 8));
        museum.artifacts.add(new Artifact("Nautical Navigation Tools", ArtifactType.ARTIFACT, 7));
        museum.artifacts.add(new Artifact("Ship Construction Interactive", ArtifactType.DIGITAL, 10));

        // Henry VIII Exhibit Artifacts
        museum.artifacts.add(new Artifact("Royal Throne Replica", ArtifactType.SCULPTURE, 10));
        museum.artifacts.add(new Artifact("Henry VIII Portrait", ArtifactType.PAINTING, 9));
        museum.artifacts.add(new Artifact("Tudor Era Artifacts", ArtifactType.HISTORICAL, 8));
        museum.artifacts.add(new Artifact("Royal Court Interactive", ArtifactType.DIGITAL, 7));

        // Geology Exhibit Artifacts
        museum.artifacts.add(new Artifact("Volcanic Rock Collection", ArtifactType.ARTIFACT, 9));
        museum.artifacts.add(new Artifact("Geological Layers Model", ArtifactType.TACTILE, 8));
        museum.artifacts.add(new Artifact("Mineral Composition Chart", ArtifactType.PAINTING, 7));
        museum.artifacts.add(new Artifact("Earth Formation Interactive", ArtifactType.DIGITAL, 10));

        // Beasts of the Sea Exhibit Artifacts
        museum.artifacts.add(new Artifact("Blue Whale Skeleton", ArtifactType.SCULPTURE, 10));
        museum.artifacts.add(new Artifact("Marine Ecosystem Painting", ArtifactType.PAINTING, 9));
        museum.artifacts.add(new Artifact("Deep Sea Creature Models", ArtifactType.ARTIFACT, 8));
        museum.artifacts.add(new Artifact("Ocean Life Interactive", ArtifactType.DIGITAL, 7));
	}

	public static void generateExhibits(Museum museum) {
		Exhibit globalWarming = new Exhibit("Global Warming");
        Exhibit egypt = new Exhibit("Egypt");
        Exhibit architecture = new Exhibit("Architecture");
        Exhibit vikings = new Exhibit("Vikings");
        Exhibit historyOfAI = new Exhibit("History of AI");
        Exhibit shipBuilding = new Exhibit("Ship Building");
        Exhibit henryVIII = new Exhibit("Henry VIII");
        Exhibit geology = new Exhibit("Geology");
        Exhibit beastsOfTheSea = new Exhibit("Beasts of the Sea");

        // Add Artifacts to Exhibits
        for (Artifact artifact : museum.artifacts) {
            switch (artifact.getName()) {
                case "1KG Carbon Statue":
                case "Home Carbon Footprint Miniature":
                case "Climate Change Interactive Map":
                case "Polar Ice Cap Model":
                    globalWarming.addArtifact(artifact);
                    break;
                case "Pharaoh Statue":
                case "Hieroglyphic Tablet":
                case "Mummy Reconstruction":
                case "Ancient Egyptian Painting":
                    egypt.addArtifact(artifact);
                    break;
                // Continue with similar logic for other exhibits
            }
        }

        // Add Exhibits to Museum
        museum.exhibits.add(globalWarming);
        museum.exhibits.add(egypt);
        museum.exhibits.add(architecture);
        museum.exhibits.add(vikings);
        museum.exhibits.add(historyOfAI);
        museum.exhibits.add(shipBuilding);
        museum.exhibits.add(henryVIII);
        museum.exhibits.add(geology);
        museum.exhibits.add(beastsOfTheSea);
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
