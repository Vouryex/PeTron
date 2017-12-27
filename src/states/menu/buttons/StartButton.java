package states.menu.buttons;

import engine.components.HoverableButton;
import states.game.Game;

public class StartButton extends HoverableButton {
	
	private static StartButton startButtonInstance;
	private static final String buttonImageSource = "/images/states/Start.png";
	private static final String buttonHoveredImageSource = "/images/states/Start2.png";
	private static final int leftBound = Game.getFrameWidth()/2 - 450;
	private static final int upBound = Game.getFrameHeight()/2 - 155;
	private static final int width = 151;
	private static final int height = 30;
	
	private StartButton() {
		super(buttonImageSource, buttonHoveredImageSource, leftBound, upBound, width, height);
	}
	
	public static StartButton getInstance() {
		if(startButtonInstance == null) {
			startButtonInstance = new StartButton();
		}
		return startButtonInstance;
	}
}
