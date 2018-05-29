package GameParts;
import java.util.ArrayList;
import java.util.Random;

import GameParts.Clue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class AIPlayer {
	private ArrayList playersHand;
	
	public AIPlayer(ArrayList hand) {
		playersHand = hand;
	}
	public Label createToken() {
		Label token = new Label();
		token.setBackground(new Background(new BackgroundFill(Color.DARKORCHID, new CornerRadii(2), new Insets(2))));
		token.setMinSize(35, 35);
		token.setMaxSize(35, 35);
		return token;
	}
	public Clue revealCard(String[] question) {
		Random rand = new Random();
		Clue returnedClue=null;
		ArrayList matchingClues = new ArrayList<Clue>();
		for(int a=0; a<playersHand.size(); a++) {
			Clue checkClue = (Clue)playersHand.get(a);
			for(int b=0; b<question.length; b++) {
				String checkClueName = checkClue.getName().toLowerCase();
				String askingClueName = question[b].toLowerCase();
				if(checkClueName.equals(askingClueName)) {
					matchingClues.add(checkClue);
				}
			}
		}
		if(matchingClues.size()>1) {
			returnedClue = (Clue)matchingClues.get((rand.nextInt(matchingClues.size()))-1);
		}
		else if(matchingClues.size()==1) {
			returnedClue = (Clue)matchingClues.get(0);
		}
		return returnedClue;
	}
}
