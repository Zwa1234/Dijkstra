package io.gameoftrades.student21;

import io.gameoftrades.model.Handelaar;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.ui.MainGui;
import io.gameoftrades.ui.TileSet;

/**
 * Toont de visuele gebruikersinterface.
 * 
 * Let op: dit werkt alleen als je de WereldLader hebt geimplementeerd (Anders krijg je een NullPointerException).
 */
public class StudentUI {

	public static void main(String[] args) {
		SnelstePadAlgoritme astar = AlgoritmeFactory.getA("astar");
		SnelstePadAlgoritme dijkstra = AlgoritmeFactory.getA("dijkstra");
		//Dijkstra d = Dijkstra.getInstance();
		MainGui.builder()
		       .add(new HandelaarImpl()).add(astar).add(dijkstra)
		       // Je kan hier eventueel extra algoritmen toevoegen dmv extra 'add' aanroepen.
		       .toon(TileSet.T32, "/kaarten/voorbeeld-kaart.txt");
	}
	
}
