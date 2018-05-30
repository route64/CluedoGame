package Practical;

import java.util.ArrayList;

import GameParts.AIPlayer;
import GameParts.Clue;
import GameParts.InRoom;
import Visual.Board;
import Visual.CreateAlert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class AllowMovement {
	private Player player;
	private int diceTotal;
	private InRoom roomBox;
	private boolean roomBoxExists;
	private GridPane background;
	private int noOfAIPlayers;
	private ArrayList AIPlayers;
	private Board board;
	private ArrayList<Location> tiles;

	public AllowMovement(Player player, int diceTotal, GridPane background, int noOfAIPlayers, ArrayList AIPlayers, Board board) {
		this.diceTotal = diceTotal;
		this.player = player;
		this.background = background;
		this.noOfAIPlayers = noOfAIPlayers;
		this.AIPlayers = AIPlayers;
		this.board = board;
		tiles = board.getTiles();
	}

	public void move(Dice dice1, Dice dice2, Label token) {
		//ArrayList<Location> tiles = board.getTiles();
		for(int i=0; i<tiles.size(); i++) {
			Location tile = tiles.get(i);
			tile.getTile().setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					int row = board.getBoard().getRowIndex(tile.getTile());
					int col = board.getBoard().getColumnIndex(tile.getTile());
					MovePlayer move = new MovePlayer(board, player);
					if(player.inRoom()) {
						CreateAlert roomAlert = new CreateAlert();
						roomAlert.mustLeave();
						dice1.reset();
						dice2.reset();
					}
					else if(move.canMove(diceTotal, tile)) {
						InRoom roomBox;
						if(!player.inRoom()) {
							dice1.reset();
							dice2.reset();
							board.getBoard().getChildren().remove(token);
							board.getBoard().add(token, col, row);
							if(tile.getIsDoorway()) {
								CreateAlert enterRoom = new CreateAlert();
								//boolean enter = enterRoom.enterRoom(tile.getName());
								//if(enter) {
								String door = tile.getName();
								for(int a=0; a<tiles.size(); a++) {
									Location loc = tiles.get(a);
									String locName = loc.getName().toLowerCase();
									if(door.contains(locName)) {
										boolean enter = enterRoom.enterRoom(loc.getName());
										if(enter) {
											row = board.getBoard().getRowIndex(loc.getTile());
											col = board.getBoard().getColumnIndex(loc.getTile());
											a=tiles.size();
											player.enterRoom(loc);
											initialiseRoomBox();
										}
									}
								}
								//}
								board.getBoard().getChildren().remove(token);
								board.getBoard().add(token, col, row);
								//player.setLocation(col, row);
							}
							else if(!tile.getIsRoom()) {
								board.getBoard().getChildren().remove(token);
								board.getBoard().add(token, col, row);
							}
						}
						/*board.getBoard().getChildren().remove(token);
						board.getBoard().add(token, player.getCol(), player.getRow());*/
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
			if(roomBoxExists) {
				background.getChildren().remove(roomBox.getBackground());
			}
			Button tunnel = new Button("Use Secret Passageway");
			Button leave = new Button("Leave");
			//Button ask = new Button("Ask");
			tunnel.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					usePassage();
				}
			});
			leave.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					leaveRoom();
					player.leaveRoom();
					initialiseRoomBox();
					board.getBoard().getChildren().remove(player.getToken());
					board.getBoard().add(player.getToken(), player.getCol(), player.getRow());
				}
			});
			roomBox = new InRoom(player.getRoom(), noOfAIPlayers, AIPlayers, leave, tunnel);
			background.add(roomBox.getBackground(), 22, 20, 6, 3);
			roomBoxExists=true;
			if(roomBox.getWantToLeave()) {
				player.leaveRoom();
			}
		}
		if(!player.inRoom()) {
			background.getChildren().remove(roomBox.getBackground());
			roomBoxExists=false;
		}
	}
	public GridPane updateBackground() {
		return background;
	}
	public void updateDiceTotal(int diceTotal) {
		this.diceTotal = diceTotal;
	}
	private void leaveRoom() {
		int row;
		int col;
		CreateAlert leaveRoom = new CreateAlert();
		Location room = player.getRoom();
		for(int a=0; a<tiles.size(); a++) {
			Location loc = tiles.get(a);
			String locName = loc.getName().toLowerCase();
			if(locName.contains(room.getName().toLowerCase()) && locName.toLowerCase().contains("door")) {
				boolean leave = leaveRoom.askToLeave();
				if(leave) {
					row = board.getBoard().getRowIndex(loc.getTile());
					col = board.getBoard().getColumnIndex(loc.getTile());
					a=tiles.size();
					player.leaveRoom();
					initialiseRoomBox();
					player.setLocation(col, row);
				}
			}
		}
	}
	private void usePassage() {
		int row;
		int col;
		Location currentRoom = player.getRoom();
		Location passage = currentRoom.getPassage();
		if(passage != null) {
			Location end = passage.travelToLocation();
			row=end.getRow();
			col=end.getCol();
			player.leaveRoom();
			initialiseRoomBox();
			player.enterRoom(end);
			initialiseRoomBox();
			board.getBoard().getChildren().remove(player.getToken());
			board.getBoard().add(player.getToken(), player.getCol(), player.getRow());
		}
	}
}
