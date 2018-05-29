package GameParts;

import java.util.ArrayList;

import Practical.Location;
import Visual.CreateAlert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class InRoom {
	private GridPane background;
	private Location room;
	private String[] question;
	private boolean wantToLeave;
	private int who;
	private int noOfPlayers;
	private ArrayList aiPlayers;
	public InRoom(Location room, int noOfPlayers, ArrayList players) {
		background = new GridPane();
		this.room = room;
		this.noOfPlayers = noOfPlayers;
		aiPlayers = players;
		createRoomDescription();
		createButtons();
	}
	public GridPane getBackground() {
		return background;
	}
	private void createButtons() {
		Button tunnel = new Button("Use Secret Passageway");
		Button leave = new Button("Leave");
		Button ask = new Button("Ask");
		leave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				CreateAlert leaving = new CreateAlert();
				leaving.askToLeave();
			}});
		ask.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				CreateAlert ask = new CreateAlert();
				who = ask.whoToAsk(noOfPlayers);
				question = ask.askQuestion(room.getName().toLowerCase());
				Clue response = getResponse(who, question);
				CreateAlert answer = new CreateAlert();
				answer.getAnswer(response, who);
			}});
		background.add(leave, 0, 1);
		background.add(ask, 2, 1);
	}
	private void createRoomDescription() {
		Label roomName = new Label(room.getName());
		background.add(roomName, 0, 0);
	}
	private Clue getResponse(int aiInt, String[] question) {
		AIPlayer ai = (AIPlayer)aiPlayers.get(aiInt);
		Clue response;
		response = ai.revealCard(question);
		return response;
	}
	public int getWhoToAsk() {
		return who;
	}
	public String[] getQuesstion() {
		return question;
	}
	public boolean getWantToLeave() {
		return wantToLeave;
	}
}
