package states.pause.buttons;

import customComponents.CustomHoverableButton;
import states.game.Game;

public class ExitButton extends CustomHoverableButton {

	private static ExitButton exitButtonInstance;
	private static final String buttonImageSource = "/res/images/states/pauseExit.png"; 
	private static final String buttonHoveredImageSource = "/res/images/states/pauseExit2.png"; 
	private static final int leftBound = (Game.frameWidth / 2) - (93 / 2);
	private static final int upBound = (Game.frameHeight / 2) - (30 / 2) + 95;
	private static final int width = 93;
	private static final int height = 30;
	
	private ExitButton() {
		super(buttonImageSource, buttonHoveredImageSource, leftBound, upBound, width, height);
	}
	
	public static ExitButton getInstance() {
		if(exitButtonInstance == null) {
			exitButtonInstance = new ExitButton();
		}
		return exitButtonInstance;
	}
}
