package Practical;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

public class Location {
	private boolean isRoom;
	private boolean isDoorway;
	private Button tile;
	private String name;
	private int row;
	private int col;
	private Location travelTo;
	private boolean isPassageWay;
	
	public Location(boolean isRoom, boolean isDoorway, int col, int row) {
		this.isRoom = isRoom;
		this.isDoorway = isDoorway;
		tile = new Button();
		this.row = row;
		this.col = col;
		isPassageWay=false;
	}
	public void buildRoom(int width, int height, Color color, String name) {
		tile.setMinWidth(width);
		tile.setMaxWidth(width);
		tile.setMinHeight(height);
		tile.setMaxHeight(height);
		this.name = name;
		if(isRoom) {
			tile.setText(name);
		}
		tile.setAlignment(Pos.CENTER);
		tile.setBackground(new Background(new BackgroundFill(color, new CornerRadii(2), new Insets(2))));
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	//Getter methods for room and doorway
	public boolean getIsRoom() {
		return isRoom;
	}
	public String getName() {
		return name;
	}
	public boolean getIsDoorway() {
		return isDoorway;
	}
	//Get the location tile
	public Button getTile() {
		return tile;
	}
	public void makeSecretPassage(Location travelTo) {
		this.travelTo = travelTo;
		isPassageWay = true;
	}
	public Location travelToLocation() {
		return travelTo;
	}
	public boolean isPassageway() {
		return isPassageWay;
	}
}
