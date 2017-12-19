package states.game.powerups;


import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Shield extends PowerUp {
	public static final Color COLOR = Color.GREEN;
	public static final String NAME = "Shield";
	public static int DURATION = 6000;
	public static Image statusImage;
	
	public Shield(int xPos, int yPos) {
		super(xPos, yPos, COLOR);
		try {
			statusImage = ImageIO.read(getClass().getResourceAsStream("/images/powerups/ShieldStatus.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		super.setStatusImage(statusImage);
	}
	
	public static String getName() {
		return NAME;
	}
	
}
