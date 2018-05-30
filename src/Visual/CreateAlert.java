package Visual;

import java.util.ArrayList;
import java.util.Optional;

import GameParts.Clue;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class CreateAlert extends Application {
	private String character;
	private String weapon;
	private String room;
	private int playerNumbers;
	//private Alert alert;

	public CreateAlert() {

	}
	@Override
	public void start(Stage primaryStage) {
		Group rootGroup = new Group();
		//alert=new Alert(AlertType.NONE);
		/*
		 * Editing the DialogPane
		 *
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(
		   getClass().getResource("MyDialogs.css").toExternalForm());
		dialogPane.getStyleClass().add("MyDialog");*/
	}
	//To start the game we need to know how many players we have
	public int getNumberOfPlayers(Stage stage) {
		Alert alert = new Alert(AlertType.NONE);
		alert.setTitle("NUMBER OF PLAYERS");

		Text dialog = new Text("Choose how many AI players you want to go up against:");
		dialog.setFont(Font.font ("Arial", 20));
		dialog.setFill(Color.BLUE);
		dialog.setTextAlignment(TextAlignment.CENTER);
		dialog.setWrappingWidth(300);
		
		GridPane background = new GridPane();
		background.getColumnConstraints().add(new ColumnConstraints(30));
		background.add(dialog, 0, 0, 6, 1);
		HBox btnBox = new HBox();
		Button two = new Button("Two");
		two.setBackground(new Background(new BackgroundFill(
				Color.BISQUE, new CornerRadii(2), new Insets(2))));
		two.setMinWidth(50);
		two.setMinHeight(30);
		Button three = new Button("Three");
		three.setBackground(new Background(new BackgroundFill(
				Color.BISQUE, new CornerRadii(2), new Insets(2))));
		three.setMinWidth(50);
		three.setMinHeight(30);
		two.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				playerNumbers=2;
				two.setBackground(new Background(new BackgroundFill(
						Color.DARKGOLDENROD, new CornerRadii(2), new Insets(2))));
				three.setBackground(new Background(new BackgroundFill(
						Color.ALICEBLUE, new CornerRadii(2), new Insets(2))));
			}
		});
		three.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				playerNumbers=3;
				three.setBackground(new Background(new BackgroundFill(
						Color.DARKGOLDENROD, new CornerRadii(2), new Insets(2))));
				two.setBackground(new Background(new BackgroundFill(
						Color.ALICEBLUE, new CornerRadii(2), new Insets(2))));
			}
		});
		btnBox.setSpacing(60);
		btnBox.getChildren().addAll(two, three);
		background.add(btnBox, 1, 1);
		alert.getDialogPane().setContent(background);
		alert.getButtonTypes().clear();
		alert.getButtonTypes().clear();
		ButtonType play = new ButtonType("Play");
		ButtonType quit = new ButtonType("Quit");
		alert.getButtonTypes().addAll(play, quit);
		Optional<ButtonType> option = alert.showAndWait();
		if(option.get()==play) {
			while(!(playerNumbers > 0)) {
				option = alert.showAndWait();
			}
			if(playerNumbers>0) {
				alert.close();
			}
		}
		if(option.get() == quit) {
			stage.close();
			alert.close();
		}
		return playerNumbers;
	}
	public boolean enterRoom(String roomName) {
		boolean enter = false;
		Alert alert = new Alert(AlertType.NONE);
		alert.setTitle("ENTER ROOM");
		alert.setContentText("Do you want to enter the "+roomName+"?");
		alert.getButtonTypes().clear();
		ButtonType yes = new ButtonType("Yes");
		ButtonType no = new ButtonType("No");
		alert.getButtonTypes().addAll(yes, no);
		Optional<ButtonType> option = alert.showAndWait();
		if(option.get()==yes) {
			enter = true;
		}
		if(option.get()==no){
			enter = false;
		}
		alert.close();
		return enter;
	}
	public String[] askQuestion(String currentRoom) {
		String[] question = new String[3];
		character="";
		weapon="";
		room=currentRoom;
		Alert alert = new Alert(AlertType.NONE);
		Text dialog = new Text("Choose a Character, a Weapon and a Room");
		dialog.setFont(Font.font ("Arial", 20));
		dialog.setFill(Color.BLUE);
		dialog.setTextAlignment(TextAlignment.CENTER);
		dialog.setWrappingWidth(600);
		//alert.setTitle("Choose a Character, a Weapon and a Room");
		GridPane background = new GridPane();
		GridPane guessOptions = getGuessOptions(room);
		background.add(dialog, 0, 0);
		background.add(guessOptions, 0, 1);
		alert.getDialogPane().setContent(background);
		alert.getButtonTypes().clear();
		ButtonType ask = new ButtonType("Ask");
		alert.getButtonTypes().add(ask);
		Optional<ButtonType> option = alert.showAndWait();
		if(option.get()==ask) {
			question[0]=character;
			question[1]=weapon;
			question[2]=room;
			alert.close();
		}
		return question;
	}
	public String[] makeGuess() {
		String[] guess = new String[3];
		character="";
		weapon="";
		room="";
		Alert alert = new Alert(AlertType.NONE);
		alert.setTitle("MAKE A GUESS");
		Text dialog = new Text("Have you solved the murder?");
		dialog.setFont(Font.font ("Arial", 20));
		dialog.setFill(Color.DARKGREEN);
		dialog.setTextAlignment(TextAlignment.CENTER);
		dialog.setWrappingWidth(600);
		GridPane background = new GridPane();
		GridPane guessOptions = getGuessOptions("");
		background.add(dialog, 0, 0);
		background.add(guessOptions, 0, 1);
		alert.getDialogPane().setContent(background);
		alert.getButtonTypes().clear();
		ButtonType ask = new ButtonType("Guess");
		alert.getButtonTypes().add(ask);
		Optional<ButtonType> option = alert.showAndWait();
		if(option.get()==ask) {
			guess[0]=character;
			guess[1]=weapon;
			guess[2]=room;
			alert.close();
		}
		return guess;
	}
	private GridPane getGuessOptions(String currentRoom) {
		Button mrsWhite = new Button("Mrs White");
		Button mrsPeacock = new Button("Mrs Peacock");
		Button missScarlet = new Button("Miss Scarlet");
		Button colMustard = new Button("Col. Mustard");
		Button revGreen = new Button("Rev. Green");
		Button profPlum = new Button("Prof. Plum");
		Button dagger = new Button("Dagger");
		Button rope = new Button("Rope");
		Button revolver = new Button("Revolver");
		Button candlestick = new Button("candlestick");
		Button leadPipe = new Button("Lead Piping");
		Button spanner = new Button("Spanner");
		Button study = new Button("Study");
		Button billiard = new Button("Billiard Room");
		Button library = new Button("Library");
		Button lounge = new Button("Lounge");
		Button ballroom = new Button("Ballroom");
		Button dinning = new Button("Dinning Room");
		Button conservatory = new Button("Conservatory");
		Button hall = new Button("Hall");
		Button kitchen = new Button("Kitchen");
		GridPane guessOptions = new GridPane();
		Button[] clueOptions = new Button[21];
		guessOptions.add(mrsWhite, 0, 0);
		clueOptions[0]=mrsWhite;
		guessOptions.add(mrsPeacock, 1, 0);
		clueOptions[1]=mrsPeacock;
		guessOptions.add(missScarlet, 2, 0);
		clueOptions[2]=missScarlet;
		guessOptions.add(colMustard, 3, 0);
		clueOptions[3]=colMustard;
		guessOptions.add(revGreen, 4, 0);
		clueOptions[4]=revGreen;
		guessOptions.add(profPlum, 5, 0);
		clueOptions[5]=profPlum;
		guessOptions.add(dagger, 0, 1);
		clueOptions[6]=dagger;
		guessOptions.add(revolver, 1, 1);
		clueOptions[7]=revolver;
		guessOptions.add(rope, 2, 1);
		clueOptions[8]=rope;
		guessOptions.add(leadPipe, 3, 1);
		clueOptions[9]=leadPipe;
		guessOptions.add(candlestick, 4, 1);
		clueOptions[10]=candlestick;
		guessOptions.add(spanner, 5, 1);
		clueOptions[11]=spanner;
		guessOptions.add(study, 0, 2);
		clueOptions[12]=study;
		guessOptions.add(billiard, 1, 2);
		clueOptions[13]=billiard;
		guessOptions.add(library, 2, 2);
		clueOptions[14]=library;
		guessOptions.add(lounge, 3, 2);
		clueOptions[15]=lounge;
		guessOptions.add(ballroom, 4, 2);
		clueOptions[16]=ballroom;
		guessOptions.add(dinning, 5, 2);
		clueOptions[17]=dinning;
		guessOptions.add(conservatory, 0, 3);
		clueOptions[18]=conservatory;
		guessOptions.add(hall, 1, 3);
		clueOptions[19]=hall;
		guessOptions.add(kitchen, 2, 3);
		clueOptions[20]=kitchen;
		//add the event listener for the character buttons
		for(int c=0; c<6; c++) {
			Button ch = clueOptions[c];
			ch.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					character = ch.getText();
					for(int a=0; a<6; a++) {
						Button allchar = clueOptions[a];
						allchar.setBackground(new Background(new BackgroundFill(
								Color.ALICEBLUE, new CornerRadii(2), new Insets(2))));
					}
					ch.setBackground(new Background(new BackgroundFill(
							Color.CHARTREUSE, new CornerRadii(2), new Insets(2))));
				}
			});
		}
		//add the event listener for the weapon buttons
		for(int w=6; w<12; w++) {
			Button wea = clueOptions[w];
			wea.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					weapon = wea.getText();
					for(int a=6; a<12; a++) {
						Button allweapons = clueOptions[a];
						allweapons.setBackground(new Background(new BackgroundFill(
								Color.ALICEBLUE, new CornerRadii(2), new Insets(2))));
					}
					wea.setBackground(new Background(new BackgroundFill(
							Color.CHARTREUSE, new CornerRadii(2), new Insets(2))));
				}
			});
		}
		//add event listener for the room buttons
		for(int r=12; r<21; r++) {
			Button roo = clueOptions[r];
			room=currentRoom;
			if(!(currentRoom.equals(""))) {
				if(roo.getText().toLowerCase().equals(currentRoom)) {
					roo.setBackground(new Background(new BackgroundFill(
							Color.CHARTREUSE, new CornerRadii(2), new Insets(2))));
				}
				else {
					roo.setBackground(new Background(new BackgroundFill(
							Color.ALICEBLUE, new CornerRadii(2), new Insets(2))));
				}
			}
			roo.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if(currentRoom.equals("")) {
						room = roo.getText();
						for(int a=12; a<21;a++) {
							Button otherRoo = clueOptions[a];
							otherRoo.setBackground(new Background(new BackgroundFill(
									Color.ALICEBLUE, new CornerRadii(2), new Insets(2))));
						}
						roo.setBackground(new Background(new BackgroundFill(
								Color.CHARTREUSE, new CornerRadii(2), new Insets(2))));
					}
				}
			});
		}
		return guessOptions;
	}
	public int whoToAsk(int noOfAIPlayers) {
		int who=0;
		Alert alert = new Alert(AlertType.NONE);
		Text dialog = new Text();
		alert.setTitle("WHO TO ASK");
		dialog.setText("WHO DO YOU WANT TO ASK: ");
		dialog.setFont(Font.font ("Arial", 20));
		dialog.setFill(Color.BLUE);
		dialog.setTextAlignment(TextAlignment.CENTER);
		dialog.setWrappingWidth(600);
		alert.getDialogPane().setContent(dialog);
		alert.getButtonTypes().clear();
		ButtonType ai1 = new ButtonType("AI Player 1");
		ButtonType ai2 = new ButtonType("AI Player 2");
		ButtonType ai3 = new ButtonType("AI Player 3");
		if(noOfAIPlayers==2) {
			alert.getButtonTypes().addAll(ai1, ai2);
		}
		if(noOfAIPlayers==3) {
			alert.getButtonTypes().addAll(ai1, ai2, ai3);
		}
		
		Optional<ButtonType> option = alert.showAndWait();
		int numOfPlayers=0;
		if(option.get()==ai1) {
			//alert.close();
			who = 0;
		}
		else if(option.get()==ai2) {
			//alert.close();
			who = 1;
		}
		else if(option.get()==ai3) {
			who = 2;
		}
		alert.close();
		return who;
	}
	public void getAnswer(Clue clue, int aiPlayer) {
		Alert alert = new Alert(AlertType.NONE);
		alert.setTitle("RESPONSE");
		Text dialog;
		if(clue==null) {	
			dialog = new Text("Player"+aiPlayer+" did not have any of the cards you were looking for");
			dialog.setFill(Color.BROWN);
		}
		else {
			alert.setTitle("RESPONSE");
			dialog = new Text("Player"+aiPlayer+" has this card: "+ clue.getName());
			dialog.setFill(Color.DARKGREEN);
		}
		dialog.setFont(Font.font ("Arial", 20));
		dialog.setTextAlignment(TextAlignment.CENTER);
		dialog.setWrappingWidth(400);
		alert.getDialogPane().setContent(dialog);
		alert.getButtonTypes().clear();
		ButtonType cont = new ButtonType("Continue");
		alert.getButtonTypes().add(cont);
		Optional<ButtonType> option = alert.showAndWait();
		if(option.get()==cont) {
			alert.close();
		}
		
	}
	public void getResponse(String[] guess, Clue[] guiltyCards, Stage stage) {
		Alert alert = new Alert(AlertType.NONE);
		ArrayList correctCards = new ArrayList<String>();
		Text dialog;
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(guess[i].toLowerCase().equals(guiltyCards[j].getName().toLowerCase())) {
					correctCards.add(guess[i]);
				}
			}
		}
		if(correctCards.size()==3) {
			dialog = new Text("CONGRATULATIONS! YOU WON!");
			dialog.setFill(Color.DARKGREEN);
		}
		else {
			dialog = new Text("YOUR GUESS WAS INCORRECT");
			dialog.setFill(Color.BROWN);
		}
		dialog.setFont(Font.font ("Arial", 20));
		dialog.setTextAlignment(TextAlignment.CENTER);
		dialog.setWrappingWidth(400);
		alert.getDialogPane().setContent(dialog);
		alert.getButtonTypes().clear();
		ButtonType quit = new ButtonType("Exit Game");
		ButtonType cont = new ButtonType("Continue Playing");
		alert.getButtonTypes().addAll(quit, cont);
		Optional<ButtonType> option = alert.showAndWait();
		if(option.get()==quit) {
			stage.close();
			alert.close();
		}
		else if(option.get()==cont) {
			alert.close();
		}
	}
	public void cantMoveAlert() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Insufficient Roll");
		alert.setContentText("You have not rolled enough to reach this tile");
		Optional<ButtonType>option = alert.showAndWait();
		if(option.get()==ButtonType.OK) {
			alert.close();
		}
	}
	public void mustLeave() {
		Alert alert = new Alert(AlertType.WARNING);
		Text dialog = new Text("You must leave the room before you can travel");
		dialog.setFont(Font.font ("Arial", 20));
		dialog.setTextAlignment(TextAlignment.CENTER);
		dialog.setWrappingWidth(400);
		alert.getDialogPane().setContent(dialog);
		Optional<ButtonType>option = alert.showAndWait();
		if(option.get()==ButtonType.OK) {
			alert.close();
		}
	}
	public boolean askToLeave() {
		Alert alert = new Alert(AlertType.NONE);
		Text dialog = new Text("Do you want to leave this room?");
		dialog.setFont(Font.font ("Arial", 20));
		dialog.setTextAlignment(TextAlignment.CENTER);
		dialog.setWrappingWidth(400);
		alert.setTitle("Leave Room");
		alert.getDialogPane().setContent(dialog);
		alert.getButtonTypes().clear();
		ButtonType yes = new ButtonType("Yes");
		ButtonType no = new ButtonType("No");
		alert.getButtonTypes().addAll(yes, no);
		Optional<ButtonType> option = alert.showAndWait();
		if(option.get() == yes) {
			alert.close();
			return true;
		}
		if(option.get() == no) {
			alert.close();
			return false;
		}
		return false;
	}
}