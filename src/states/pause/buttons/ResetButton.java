package states.pause.buttons;

import engine.components.HoverableButton;
import states.game.Game;

public class ResetButton extends HoverableButton {

	private static ResetButton resetButtonInstance;
	private static final String buttonImageSource = "/images/states/pauseReset.png";
	private static final String buttonHoveredImageSource = "/images/states/pauseReset2.png";
	private static final int leftBound = (Game.getFrameWidth() / 2) - (148 / 2);
	private static final int upBound = (Game.getFrameHeight() / 2) - (30 / 2) + 30;
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
