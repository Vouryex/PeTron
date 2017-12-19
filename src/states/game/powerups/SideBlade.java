package states.game.powerups;


import java.awt.Color;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SideBlade extends PowerUp {
	public static final Color COLOR = Color.ORANGE;
	public static final String NAME = "Side Blade";
	public static final int DURATION = 6000;
	
	public SideBlade(int xPos, int yPos) {
		super(xPos, yPos, COLOR);
		try {
			statusImage = ImageIO.read(getClass().getResourceAsStream("/images/powerups/SideBladeStatus.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		super.setStatusImage(statusImage);
	}
	
	public static String getName() {
		return NAME;
	}

}
