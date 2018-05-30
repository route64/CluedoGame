package Practical;
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

public class Player {
	private int col;
	private int row;
	private boolean inRoom;
	private Location room;
	private Label token;
	
	public Player() {
		inRoom=false;
		createToken();
	}
	private void createToken() {
		token = new Label();
		token.setBackground(new Background(new BackgroundFill(Color.DARKORCHID, new CornerRadii(2), new Insets(2))));
		token.setMinSize(35, 35);
		token.setMaxSize(35, 35);
		//return token;
	}
	public Label getToken() {
		return token;
	}
	public void setLocation(int col, int row) {
		this.col = col;
		this.row = row;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public void enterRoom(Location loc) {
		inRoom=true;
		setLocation(loc.getCol(), loc.getRow());
		room = loc;
	}
	public void leaveRoom() {
		inRoom=false;
	}
	public boolean inRoom() {
		return inRoom;
	}
	public Location getRoom() {
		return room;
	}
}
