package Main;

import Visual.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;

import GameParts.AIPlayer;
import GameParts.Clue;
import GameParts.InRoom;
import GameParts.Notepad;
import Practical.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
	private GridPane background;
	private Dice dice1;
	private Dice dice2;
	private int diceTotal;
	private Label token;
	private AssignClues assClues;
	private int noOfAIPlayers;
	private ArrayList AIPlayers;
	private Clue[] allClues;
	private Player player;
	private boolean inRoom;
	private InRoom roomBox;
	private boolean roomBoxExists;
	private AllowMovement allowMovement;

	public Main() {
		inRoom=false;
		background = new GridPane();
		player = new Player();
		token = player.createToken();
	}
	@Override
	public void start(final Stage primaryStage) {
		CreateAlert alert = new CreateAlert();
		noOfAIPlayers = alert.getNumberOfPlayers(primaryStage);
		if(noOfAIPlayers>0) {
			play(primaryStage);
		}
	}

	private void play(Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root, 1500, 700);
		stage.setScene(scene);
		stage.setTitle("Cluedo");
		setUpBoard(stage);

		root.getChildren().add(background);
		stage.show();
	}
	private void setUpBoard(Stage stage) {
		for (int r = 0; r < 35; r++) {
			//RowConstraints row = new RowConstraints(35);
			ColumnConstraints col = new ColumnConstraints(35);
			//background.getRowConstraints().add(row);
			background.getColumnConstraints().add(col);
		}
		Board board = new Board(stage);
		GridPane boardGrid = board.getBoard();
		//add the player's token to the board
		boardGrid.add(token, 4, 0);
		player.setLocation(4, 0);
		background.add(boardGrid, 0, 1, 20, 20);
		setUpDice();
		background.add(dice1.getDice(), 22, 0, 2, 2);
		background.add(dice2.getDice(), 25, 0, 2, 2);
		Button rollBtn = createRollButton();
		background.add(rollBtn, 24, 2, 3, 1);
		Button guessBtn = createGuessBtn(stage);
		background.add(guessBtn, 24, 20, 4, 1);
		createClues();
		createAIPlayers();
		GridPane notepad = new Notepad(allClues, noOfAIPlayers).getNotepad();
		background.add(notepad, 30, 0, 10, 22);
		allowMovement = new AllowMovement(player, diceTotal, background, noOfAIPlayers, AIPlayers, board);
		allowMovement.move(dice1, dice2, token);
		background = allowMovement.updateBackground();
	}
	private Button createRollButton() {
		Button rollBtn = new Button("roll");
		rollBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				background.getChildren().remove(dice1.getDice());
				background.getChildren().remove(dice2.getDice());
				dice1.roll();
				int dice1Num = dice1.getDiceNum();
				dice2.roll();
				int dice2Num = dice2.getDiceNum();
				diceTotal = dice1Num + dice2Num;
				background.add(dice1.getDice(), 22, 0,2,2);
				background.add(dice2.getDice(), 25, 0,2,2);
				allowMovement.updateDiceTotal(diceTotal);
			}
		});
		return rollBtn;
	}
	private Button createGuessBtn(Stage stage) {
		Button guessBtn = new Button("Guess");
		guessBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				CreateAlert guessAlert = new CreateAlert();
				String[] guess = guessAlert.makeGuess();
				CreateAlert guessResponse = new CreateAlert();
				guessResponse.getResponse(guess, assClues.guess(), stage);
			}
		});
		return guessBtn;
	}
	private void setUpDice() {
		//Dice di = new Dice();
		dice1= new Dice();//di.createDice(di.roll());
		dice2= new Dice();//di.createDice(di.roll());
	}
	private void createClues() {
		assClues = new AssignClues(noOfAIPlayers);
		VBox playerClues = assClues.getPlayersHand();
		background.add(playerClues, 22, 5, 6, 15);
		allClues = assClues.getAllClues();
	}
	private void createAIPlayers() {
		AIPlayers = new ArrayList<AIPlayer>();
		ArrayList aiHands = assClues.getAIHands();
		for(int a=0; a<noOfAIPlayers; a++) { 
			ArrayList aiHand = (ArrayList)aiHands.get(a);
			AIPlayer ai = new AIPlayer(aiHand);
			AIPlayers.add(ai);
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
