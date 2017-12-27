package states.pause.buttons;

import engine.components.HoverableButton;
import states.game.Game;

public class ExitButton extends HoverableButton {

	private static ExitButton exitButtonInstance;
	private static final String buttonImageSource = "/images/states/pauseExit.png";
	private static final String buttonHoveredImageSource = "/images/states/pauseExit2.png";
	private static final int leftBound = (Game.getFrameWidth() / 2) - (93 / 2);
	private static final int upBound = (Game.getFrameHeight() / 2) - (30 / 2) + 95;
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
