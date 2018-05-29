package Visual;

import java.util.ArrayList;

import Practical.Dice;
import Practical.Location;
import Practical.Player;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Board{ //extends Application{
	private GridPane boardGrid;
	private int rowHeight;
	private int colWidth;
	private int noOfRows;
	private int noOfCols;
	private ArrayList boardTiles;

	public Board(Stage stage) {
		rowHeight = 35;
		colWidth=35;
		noOfRows = 20;
		noOfCols = 20;
		//Create an arraylist to store all the button tiles of the board
		boardTiles = new ArrayList<Location>();

        boardGrid = new GridPane();
        for (int i = 0; i < noOfRows; i++) {
            RowConstraints row = new RowConstraints(rowHeight);
            ColumnConstraints col = new ColumnConstraints(colWidth);
            boardGrid.getRowConstraints().add(row);
            boardGrid.getColumnConstraints().add(col);
            boardGrid.setGridLinesVisible(true);
        }
        buildBoard();
        
	}
	public GridPane getBoard() {
		return boardGrid;
	}
	
	public void buildBoard() {
		//Build the rooms
		Location study = new Location(true, false, 0, 0);
		study.buildRoom(colWidth*3, rowHeight*4, Color.MAROON, "Study");
		boardGrid.add(study.getTile(), 0, 0, 3, 4);
		boardTiles.add(study);
		Location billiardRoom = new Location(true, false, 6, 0);
		billiardRoom.buildRoom(colWidth*3, rowHeight*3, Color.BROWN, "Billiard Room");
		boardGrid.add(billiardRoom.getTile(), 6, 0, 3, 3);
		boardTiles.add(billiardRoom);
		Location library = new Location(true, false, 9, 0);
		library.buildRoom(colWidth*3, rowHeight*3, Color.ANTIQUEWHITE, "Library");
		boardGrid.add(library.getTile(), 9, 0, 3, 3);
		boardTiles.add(library);
		Location lounge = new Location(true, false, 15, 0);
		lounge.buildRoom(colWidth*5, rowHeight*5, Color.CRIMSON, "Lounge");
		boardGrid.add(lounge.getTile(), 15, 0, 5, 5);
		boardTiles.add(lounge);
		Location ballRoom = new Location(true, false, 0, 8);
		ballRoom.buildRoom(colWidth*6, rowHeight*5, Color.BISQUE, "Ballroom");
		boardGrid.add(ballRoom.getTile(), 0, 8, 6, 5);
		boardTiles.add(ballRoom);
		Location dinningRoom = new Location(true, false,  14, 8);
		dinningRoom.buildRoom(colWidth*6, rowHeight*4, Color.AQUA, "Dinning Room");
		boardGrid.add(dinningRoom.getTile(), 14, 8, 6, 4);
		boardTiles.add(dinningRoom);
		Location kitchen = new Location(true, false, 0, 16);
		kitchen.buildRoom(colWidth*4, rowHeight*5, Color.CADETBLUE, "Kitchen");
		boardGrid.add(kitchen.getTile(), 0, 16, 4, 5);
		boardTiles.add(kitchen);
		Location hall = new Location(true, false,  7, 15);
		hall.buildRoom(colWidth*5, rowHeight*6, Color.CORNFLOWERBLUE, "Hall");
		boardTiles.add(hall);
		boardGrid.add(hall.getTile(), 7, 15, 5, 6);
		Location conservatory = new Location(true, false,  16, 16);
		conservatory.buildRoom(colWidth*4, rowHeight*4, Color.CORAL, "Conservatory");
		boardTiles.add(conservatory);
		boardGrid.add(conservatory.getTile(), 16, 16, 4, 4);
		//Add the doorways
		Location studyDoor = new Location(false, true, 3, 1);
		studyDoor.buildRoom(colWidth, rowHeight, Color.BISQUE, "study door");
		boardGrid.add(studyDoor.getTile(), 3, 1);
		boardTiles.add(studyDoor);
		Location billiardDoor = new Location(false, true,  7, 3);
		billiardDoor.buildRoom(colWidth, rowHeight, Color.BISQUE, "billiard room door");
		boardGrid.add(billiardDoor.getTile(), 7, 3);
		boardTiles.add(billiardDoor);
		Location libraryDoor = new Location(false, true,  9, 3);
		libraryDoor.buildRoom(colWidth, rowHeight, Color.BISQUE, "library door");
		boardGrid.add(libraryDoor.getTile(), 9, 3);
		boardTiles.add(libraryDoor);
		Location loungeDoor = new Location(false, true,  18, 5);
		loungeDoor.buildRoom(colWidth, rowHeight, Color.BISQUE, "lounge door");
		boardGrid.add(loungeDoor.getTile(), 18, 5);
		boardTiles.add(loungeDoor);
		Location ballDoor = new Location(false, true,  6, 9);
		ballDoor.buildRoom(colWidth, rowHeight, Color.BISQUE, "ballroom door");
		boardGrid.add(ballDoor.getTile(), 6, 9);
		boardTiles.add(ballDoor);
		Location dinningDoor = new Location(false, true,  13, 9);
		dinningDoor.buildRoom(colWidth, rowHeight, Color.BISQUE, "dinning room door");
		boardGrid.add(dinningDoor.getTile(), 13, 9);
		boardTiles.add(dinningDoor);
		Location kitchenDoor = new Location(false, true,  1, 15);
		kitchenDoor.buildRoom(colWidth, rowHeight, Color.BISQUE, "kitchen door");
		boardGrid.add(kitchenDoor.getTile(), 1, 15);
		boardTiles.add(kitchenDoor);
		Location hallDoor = new Location(false, true, 9, 14);
		hallDoor.buildRoom(colWidth, rowHeight, Color.BISQUE, "hall door");
		boardGrid.add(hallDoor.getTile(), 9, 14);
		boardTiles.add(hallDoor);
		Location conservatoryDoor = new Location(false, true, 17, 15);
		conservatoryDoor.buildRoom(colWidth, rowHeight, Color.BISQUE, "conservatory door");
		boardGrid.add(conservatoryDoor.getTile(), 17, 15);
		boardTiles.add(conservatoryDoor);
		//Every remaining cell in the boardGrid that does not contain something wants to
		//just be a floor Location
		for(int row=0; row<noOfRows; row++) {
			for(int col=0; col<noOfCols; col++) {
				if((row<4 && col>2 && col<=5 &&!(col==3 && row==1)) ||
						(col>=12 && col<=14 && row<=3)||
						(row==3 && (col==6 || col==8 || col==10 || col==11))||
						(row>=4 && row<8 && col<15)||
						(row>4 && row<8 &&!(row==5 && col==18))||
						(col>=6 && col<=13 && row>=8 && row<14 && !(row==9 && col==6)&&!(row==9 && col==13))||
						((row>=13 && row<=15 && (col<7 || col>11) &&!(col==1 && row==15) && !(row==13 && col==9) &&!(col==17 && row==15)) ||
						(row==14 && col>=7 && col<=11 && col!=9))||
						(row==12 && col>13)||
						(row>15 &&((col>=4 && col<=6)||(col>=12 && col<=15)))){
					Location floor = new Location(false, false, col, row);
					floor.buildRoom(colWidth, rowHeight, Color.ALICEBLUE, "");
					boardGrid.add(floor.getTile(), col, row);
					boardTiles.add(floor);
				}
			}
		}
		//Now we add the secret passageways
		Location spassage = new Location(false, false, 0, 1);
		Location kpassage = new Location(false, false, 1, 17);
		spassage.buildRoom(colWidth, rowHeight, Color.BLACK, "Study Passage");
		spassage.makeSecretPassage(kpassage);
		kpassage.buildRoom(colWidth, rowHeight, Color.BLACK, "kitchen passage");
		kpassage.makeSecretPassage(spassage);
		boardGrid.add(spassage.getTile(), 0, 1);
		boardGrid.add(kpassage.getTile(), 1, 17);
		boardTiles.add(spassage);
		boardTiles.add(kpassage);
		
	}
	public ArrayList<Location> getTiles(){
		return boardTiles;
	}
	public void setUpDice() {
		Dice dice = new Dice();
	}
}
