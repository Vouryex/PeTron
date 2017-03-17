package states.pause.buttons;

import customComponents.CustomHoverableButton;
import states.game.Game;

public class RestartButton extends CustomHoverableButton {
	
	private static RestartButton restartButtonInstance;
	private static final String buttonImageSource = "/res/images/states/pauseRestart.png"; 
	private static final String buttonHoveredImageSource = "/res/images/states/pauseRestart2.png"; 
	private static final int leftBound = (Game.frameWidth / 2) - (207 / 2);
	private static final int upBound = (Game.frameHeight / 2) - (30 / 2) - 100;
	private static final int width = 207;
	private static final int height = 30;
	
	private RestartButton() {
		super(buttonImageSource, buttonHoveredImageSource, leftBound, upBound, width, height);
	}
	
	public static RestartButton getInstance() {
		if(restartButtonInstance == null) {
			restartButtonInstance = new RestartButton();
		}
		return restartButtonInstance;
	}
	
}
