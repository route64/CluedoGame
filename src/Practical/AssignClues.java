package Practical;

import GameParts.Clue;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class AssignClues {
	private VBox playersHand;
	private Clue[] clueList;
	private Clue[] guiltyCards;
	private Clue[] givenClues;
	private ArrayList<ArrayList> AIHands;
	
	public AssignClues(int noOfAIPlayers) {
		playersHand = new VBox();
		clueList = new Clue[21];
		guiltyCards = new Clue[3];
		createRoomClues();
		createWeaponClues();
		createCharacterClues();
		setGuiltyCards();
		//System.out.println(guiltyCards[0].getName() + guiltyCards[1].getName() + guiltyCards[2].getName());
		createPlayersHand();
		AIHands = new ArrayList<ArrayList>();
		createComputerHands(noOfAIPlayers);
	}
	private void createPlayersHand() {
		Random rand = new Random();
		//add a random assortment of clue cards to the players hand
		givenClues = new Clue[5];
		for(int n=0; n<5; n++) {
			//Get a random clue
			Clue clue = clueList[rand.nextInt(21)];
			//Check that this clue is not the same as one that has already been given
			while(clue == givenClues[0] || clue==givenClues[1] || clue==givenClues[2] || 
					clue==givenClues[3] || clue==givenClues[4] || clue==guiltyCards[0] ||
					clue==guiltyCards[1] || clue==guiltyCards[2]) {
				clue = clueList[rand.nextInt(21)];
			}
			//Add this clue to the list of given clues
			givenClues[n]=clue;
			//Add this clue to the players hand that they will see
			playersHand.getChildren().add(0, clue.getCard());
		}
	}
	private void createComputerHands(int noOfAIPlayers) {
		int remainingClues = 21-(givenClues.length+3);
		for(int a=0; a<noOfAIPlayers; a++) {
			ArrayList AI = new ArrayList<Clue>();
			AIHands.add(AI);
		}
		int a=0;
		while(a<remainingClues) {
			for(int b=0; b<noOfAIPlayers; b++) {
				if(a==remainingClues) {
					break;
				}
				else {
					Random rand = new Random();
					Clue clue = clueList[rand.nextInt(21)];
					while(!checkClueNotUsed(clue)) {
						clue = clueList[rand.nextInt(21)];
					}
					ArrayList AI = AIHands.get(b);
					AIHands.remove(b);
					AI.add(clue);
					AIHands.add(b, AI);
					a++;
				}
			}
		}
	}
	private boolean checkClueNotUsed(Clue checkClue) {
		Boolean notUsed=true;
		for(int i=0; i<AIHands.size(); i++) {
			ArrayList AIhand = AIHands.get(i);
			for(int a=0; a<AIhand.size(); a++) {
				Clue c = (Clue)AIhand.get(a);
				if(c==checkClue) {
					notUsed=false;
				}
			}
		}
		for(int j=0; j<givenClues.length; j++) {
			Clue gc = givenClues[j];
			if(checkClue==gc) {
				notUsed=false;
			}
		}
		for(int a=0; a<guiltyCards.length; a++) {
			Clue gc = guiltyCards[a];
			if(checkClue==gc) {
				notUsed=false;
			}
		}
		return notUsed;
	}
	private void setGuiltyCards() {
		Random rand = new Random();
		Clue room = clueList[rand.nextInt(9)];
		room.makeGuilty();
		int weaponNo = rand.nextInt(15);
		while(weaponNo<9) {
			weaponNo = rand.nextInt(15);
		}
		Clue weapon = clueList[weaponNo];
		weapon.makeGuilty();
		int charNo = rand.nextInt(21);
		while(charNo<15) {
			charNo = rand.nextInt(21);
		}
		Clue character = clueList[charNo];
		character.makeGuilty();
		guiltyCards[0]=room;
		guiltyCards[1]=weapon;
		guiltyCards[2]=character;
	}
	//clueList 0-8 are room cards
	private void createRoomClues() {
		Clue bilRoom = new Clue(Color.BROWN, "Billiard Room");
		clueList[0] = bilRoom;
		Clue study = new Clue(Color.MAROON, "Study");
		clueList[1]=study;
		Clue library = new Clue(Color.CHARTREUSE, "Library");
		clueList[2]=library;
		Clue lounge = new Clue(Color.DARKOLIVEGREEN, "Lounge");
		clueList[3] = lounge;
		Clue ballroom = new Clue(Color.BISQUE, "Ballroom");
		clueList[4] = ballroom;
		Clue dinningRoom = new Clue(Color.AQUA, "Dinning Room");
		clueList[5] = dinningRoom;
		Clue kitchen = new Clue(Color.CADETBLUE, "Kitchen");
		clueList[6] = kitchen;
		Clue hall = new Clue(Color.CORNFLOWERBLUE, "Hall");
		clueList[7]= hall;
		Clue conservatory = new Clue(Color.CORAL, "Conservatory");
		clueList[8]=conservatory;
		
	}
	//clueList 9-14 are weapons
	private void createWeaponClues() {
		Clue dagger = new Clue(Color.BEIGE, "Dagger");
		clueList[9]=dagger;
		Clue rope = new Clue(Color.BEIGE, "Rope");
		clueList[10]=rope;
		Clue revolver = new Clue(Color.BEIGE, "Revolver");
		clueList[11]= revolver;
		//Clue candlestick = new Clue(Color.BEIGE, "Candlestick");
		clueList[12] = new Clue(Color.BEIGE, "Candlestick");
		//Clue spanner = new Clue(Color.BEIGE, "Spanner");
		clueList[13]= new Clue(Color.BEIGE, "Spanner");
		clueList[14] =  new Clue(Color.BEIGE, "Lead Pipe");
	}
	//clueList 15-20 are characters
	private void createCharacterClues() {
		clueList[15] = new Clue(Color.CYAN, "Mrs White");
		clueList[16] = new Clue(Color.CYAN, "Mrs Peacock");
		clueList[17] = new Clue(Color.CYAN, "Miss Scarlet");
		clueList[18] = new Clue(Color.CYAN, "Prof. Plum");
		clueList[19] = new Clue(Color.CYAN, "Rev. Green");
		clueList[20] = new Clue(Color.CYAN, "Col. Mustard");
	}
	public VBox getPlayersHand() {
		return playersHand;
	}
	public Clue[] getPlayersHandList() {
		return givenClues;
	}
	public ArrayList getAIHands() {
		return AIHands;
	}
	public Clue[] guess() {
		return guiltyCards;
	}
	public Clue[] getAllClues() {
		return clueList;
	}
}
