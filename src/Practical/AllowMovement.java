package Practical;

import java.util.ArrayList;

import GameParts.InRoom;
import Visual.Board;
import Visual.CreateAlert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	
	public AllowMovement(Player player, int diceTotal, GridPane background, int noOfAIPlayers, ArrayList AIPlayers) {
		this.diceTotal = diceTotal;
		this.player = player;
		this.background = background;
		this.noOfAIPlayers = noOfAIPlayers;
		this.AIPlayers = AIPlayers;
	}
	
	public void move(Board board, Dice dice1, Dice dice2, Label token) {
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
			if(roomBoxExists) {
				background.getChildren().remove(roomBox.getBackground());
			}
			roomBox = new InRoom(player.getRoom(), noOfAIPlayers, AIPlayers);
			background.add(roomBox.getBackground(), 22, 20, 6, 2);
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
}
