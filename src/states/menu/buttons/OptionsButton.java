package states.menu.buttons;

import engine.components.HoverableButton;
import states.game.Game;

public class OptionsButton extends HoverableButton {

	private static OptionsButton optionsButtonInstance;
	private static final String buttonImageSource = "/images/states/Options.png";
	private static final String buttonHoveredImageSource = "/images/states/Options2.png";
	private static final int leftBound = Game.getFrameWidth()/2 - 450;
	private static final int upBound = Game.getFrameHeight()/2 - 155 + 30 + 30;
	private static final int width = 193;
	private static final int height = 30;
	
	private OptionsButton() {
		super(buttonImageSource, buttonHoveredImageSource, leftBound, upBound, width, height);
	}
	
	public static OptionsButton getInstance() {
		if(optionsButtonInstance == null) {
			optionsButtonInstance = new OptionsButton();
		}
		return optionsButtonInstance;
	}
}
