package Practical;
import java.util.Random;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class Dice {
	private Random rand;
	private int num;
	private Label image;
	
	public Dice() {
		rand = new Random();
		//startNum = roll();
		image = createDice();
	}
	//return a number between 1 and 12 (like rolling 2 dice)
	public void roll() {
		num = rand.nextInt(7);
		while(num==0) {
			num = rand.nextInt(7);
		}
		image.setText(""+num);
	}
	public Label getDice() {
		return image;
	}
	public Label createDice() {
		image = new Label();
		image.setText("Roll");
		image.setBackground(new Background(new BackgroundFill(Color.BLUE, new CornerRadii(2), new Insets(2))));
		//image.setMaxWidth(40);
		image.setMinWidth(70);
		image.setMinHeight(70);
		//image.setMaxHeight(40);
		image.setAlignment(Pos.CENTER);
		return image;
	}
	public void reset() {
		image.setText("Roll");
	}
	public int getDiceNum() {
		return num;
	}

}
