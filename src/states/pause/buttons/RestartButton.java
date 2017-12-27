package states.pause.buttons;

import engine.components.HoverableButton;
import states.game.Game;

public class RestartButton extends HoverableButton {
	
	private static RestartButton restartButtonInstance;
	private static final String buttonImageSource = "/images/states/pauseRestart.png";
	private static final String buttonHoveredImageSource = "/images/states/pauseRestart2.png";
	private static final int leftBound = (Game.getFrameWidth() / 2) - (207 / 2);
	private static final int upBound = (Game.getFrameHeight() / 2) - (30 / 2) - 100;
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
