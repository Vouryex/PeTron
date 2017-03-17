package states.menu.buttons;

import customComponents.CustomHoverableButton;
import states.game.Game;

public class StartButton extends CustomHoverableButton {
	
	private static StartButton startButtonInstance;
	private static final String buttonImageSource = "/res/images/states/Start.png";
	private static final String buttonHoveredImageSource = "/res/images/states/Start2.png";
	private static final int leftBound = Game.frameWidth/2 - 450;
	private static final int upBound = Game.frameHeight/2 - 155;
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
