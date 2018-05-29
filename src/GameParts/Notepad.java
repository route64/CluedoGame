package GameParts;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Notepad {
	private GridPane background;
	private Clue[] clues;
	
	public Notepad(Clue[] clues, int noOfPlayers) {
		background = new GridPane();
		this.clues = clues;
		createRows();
		createColumns(noOfPlayers);
		createContents(noOfPlayers);
		background.setGridLinesVisible(true);
	}
	public GridPane getNotepad() {
		return background;
	}
	private void createRows() {
		Label clueHeader = new Label("Clues");
		int buffer=22;
		background.add(clueHeader, 0, 0);
		Text characters = new Text("Characters");
		background.add(characters, 0, 1);
		for(int i=(clues.length-1); i>=15; i=i-1) {
			Clue c = clues[i];
			Text row = new Text(c.getName());
			row.minWidth(55);
			row.maxWidth(95);
			row.setWrappingWidth(90);
			background.add(row, 0, (0-i)+buffer);
		}
		Text weapons = new Text("Weapons");
		background.add(weapons, 0, 8);
		buffer++;
		for(int i=(clues.length-7); i>=9; i=i-1) {
			Clue c = clues[i];
			Text row = new Text(c.getName());
			row.minWidth(55);
			row.maxWidth(95);
			row.setWrappingWidth(90);
			background.add(row, 0, (0-i)+buffer);
		}
		Text rooms = new Text("Rooms");
		background.add(rooms, 0, 15);
		buffer++;
		for(int i=(clues.length-13); i>=0; i=i-1) {
			Clue c = clues[i];
			Text row = new Text(c.getName());
			row.minWidth(55);
			row.maxWidth(95);
			row.setWrappingWidth(90);
			background.add(row, 0, (0-i)+buffer);
		}
	}
	private void createColumns(int noOfPlayers) {
		for(int i=0; i<noOfPlayers; i++) {
			Text player = new Text("Player"+i);
			player.minWidth(80);
			player.minHeight(40);
			player.setWrappingWidth(50);
			background.add(player, i+1, 0);
		}
	}
	private void createContents(int noOfPlayers) {
		for(int r=1; r<clues.length+4; r++) {
			if((r!=1)&&(r!=8)&&(r!=15)) {
				for(int c=1; c<noOfPlayers+1; c++) {
					Button box = new Button("?");
					box.setMinSize(30, 30);
					box.setMaxSize(100, 80);
					box.setBackground(new Background(new BackgroundFill(
							Color.ALICEBLUE, new CornerRadii(2), new Insets(2))));
					box.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							if(box.getText().equals("?")) {
								box.setText("/");
								box.setBackground(new Background(new BackgroundFill(
										Color.GREEN, new CornerRadii(2), new Insets(2))));
							}
							else if(box.getText().equals("/")){
								box.setText("~");
								box.setBackground(new Background(new BackgroundFill(
										Color.RED, new CornerRadii(2), new Insets(2))));
							}
							else if(box.getText().equals("~")) {
								box.setText("?");
								box.setBackground(new Background(new BackgroundFill(
										Color.ALICEBLUE, new CornerRadii(2), new Insets(2))));
							}
						}
					});
					background.add(box, c, r);
				}
			}	
		}	
	}

}
