package states.menu.buttons;

import customComponents.CustomHoverableButton;
import states.game.Game;

public class OptionsButton extends CustomHoverableButton {

	private static OptionsButton optionsButtonInstance;
	private static final String buttonImageSource = "/res/images/states/Options.png";
	private static final String buttonHoveredImageSource = "/res/images/states/Options2.png";
	private static final int leftBound = Game.frameWidth/2 - 450;
	private static final int upBound = Game.frameHeight/2 - 155 + 30 + 30;
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
