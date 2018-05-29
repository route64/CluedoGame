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
		allowMovement(board);
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
	private void allowMovement(Board board) {
		ArrayList<Location> tiles = board.getTiles();
		for(int i=0; i<tiles.size(); i++) {
			Location tile = tiles.get(i);
			tile.getTile().setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					int row = board.getBoard().getRowIndex(tile.getTile());
					int col = board.getBoard().getColumnIndex(tile.getTile());
					MovePlayer move = new MovePlayer(board, player);
					if(move.canMove(diceTotal, tile)) {
						InRoom roomBox;
						if(player.inRoom()) {
							CreateAlert room;
							if(tile.isPassageway()) {
								room = new CreateAlert();
								boolean enter = room.enterRoom();
								if(enter) {
									Location end = tile.travelToLocation();
									String passageEndName = end.getName().toLowerCase();
									for(int a=0; a<tiles.size(); a++) {
										Location loc = tiles.get(a);
										String locName = loc.getName().toLowerCase();
										if(passageEndName.contains(locName) && !(locName.contains("door"))) {
											row = board.getBoard().getRowIndex(loc.getTile());
											col = board.getBoard().getColumnIndex(loc.getTile());
											a=tiles.size();
											player.enterRoom(loc);
											initialiseRoomBox();
										}
									}
								}
							}
							else {
								CreateAlert leaveRoom = new CreateAlert();
								boolean leave = leaveRoom.askToLeave();
								if(leave) {
									player.leaveRoom();
									initialiseRoomBox();
								//background.getChildren().remove(getRoomBox());
								}
							}
						}
						if(!player.inRoom()) {
							dice1.reset();
							dice2.reset();
							board.getBoard().getChildren().remove(token);
							board.getBoard().add(token, col, row);
							if(tile.getIsDoorway()) {
								CreateAlert enterRoom = new CreateAlert();
								boolean enter = enterRoom.enterRoom();
								if(enter) {
									String door = tile.getName();
									for(int a=0; a<tiles.size(); a++) {
										Location loc = tiles.get(a);
										String locName = loc.getName().toLowerCase();
										if(door.contains(locName)) {
											row = board.getBoard().getRowIndex(loc.getTile());
											col = board.getBoard().getColumnIndex(loc.getTile());
											a=tiles.size();
											player.enterRoom(loc);
											initialiseRoomBox();
										}
									}
								}
								board.getBoard().getChildren().remove(token);
								board.getBoard().add(token, col, row);
							//player.setLocation(col, row);
							}
							else if(!tile.getIsRoom()) {
								board.getBoard().getChildren().remove(token);
								board.getBoard().add(token, col, row);
							}
						}
						board.getBoard().getChildren().remove(token);
						board.getBoard().add(token, player.getCol(), player.getRow());
						/*else if(!tile.getIsRoom()) {
							board.getBoard().getChildren().remove(token);
							board.getBoard().add(token, col, row);
						}*/
						diceTotal=0;
					}
					else {
						CreateAlert cantMove = new CreateAlert();
						cantMove.cantMoveAlert();
					}
				}
			});
		}
	}
	private void initialiseRoomBox() {
		if(player.inRoom()) {
			roomBox = new InRoom(player.getRoom(), noOfAIPlayers, AIPlayers);
			background.add(roomBox.getBackground(), 22, 20, 6, 2);
			if(roomBox.getWantToLeave()) {
				player.leaveRoom();
			}
		}
		if(!player.inRoom()) {
			background.getChildren().remove(roomBox.getBackground());
		}
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
	/*private Clue getResponse(int aiInt, String[] question) {
		AIPlayer ai = (AIPlayer)AIPlayers.get(aiInt);
		Clue response;
		response = ai.revealCard(question);
		return response;
	}*/
	public static void main(String[] args) {
		launch(args);
	}
}
