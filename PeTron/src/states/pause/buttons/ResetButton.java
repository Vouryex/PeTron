package states.pause.buttons;

import customComponents.CustomHoverableButton;
import states.game.Game;

public class ResetButton extends CustomHoverableButton {

	private static ResetButton resetButtonInstance;
	private static final String buttonImageSource = "/res/images/states/pauseReset.png"; 
	private static final String buttonHoveredImageSource = "/res/images/states/pauseReset2.png"; 
	private static final int leftBound = (Game.frameWidth / 2) - (148 / 2);
	private static final int upBound = (Game.frameHeight / 2) - (30 / 2) + 30;
	private static final int width = 148;
	private static final int height = 30;
	
	private ResetButton() {
		super(buttonImageSource, buttonHoveredImageSource, leftBound, upBound, width, height);
	}
	
	public static ResetButton getInstance() {
		if(resetButtonInstance == null) {
			resetButtonInstance = new ResetButton();
		}
		return resetButtonInstance;
	}
}
