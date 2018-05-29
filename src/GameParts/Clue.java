package GameParts;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Clue {
	private boolean guilty;
	private GridPane card;
	private String title;
	private Color color;
	
	public Clue(Color color, String title) {
		this.color = color;
		guilty=false;
		card = new GridPane();
		card.setGridLinesVisible(true);
		this.title = title;
		makeCard();
	}
	public void makeGuilty() {
		guilty=true;
	}
	public boolean getIsGuilty() {
		return guilty;
	}
	private void makeCard() {
		Label label = new Label();
		Label header = new Label(title);
		//header.setText(title);
		header.setAlignment(Pos.CENTER);
		label.setBackground(new Background(new BackgroundFill(color, new CornerRadii(2), new Insets(2))));
		label.setMinSize(120, 40);
		label.setMaxSize(120, 40);
		header.setMinSize(120, 40);
		header.setMaxSize(120,  40);
		card.add(header, 0, 0);
		card.add(label, 0, 1);
	}
	public GridPane getCard() {
		return card;
	}
	public String getName() {
		return title;
	}
}
