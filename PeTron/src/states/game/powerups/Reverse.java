package states.game.powerups;


import java.awt.Color;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Reverse extends PowerUp {
	public static final Color COLOR = Color.RED;
	public static final String NAME = "Reverse";
	public static int DURATION = 6000;
	
	public Reverse(int xPos, int yPos) {
		super(xPos, yPos, COLOR);
		try {
			statusImage = ImageIO.read(getClass().getResourceAsStream("/res/images/powerups/ReverseStatus.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		super.setStatusImage(statusImage);
	}
	
	public static String getName() {
		return NAME;
	}
}
