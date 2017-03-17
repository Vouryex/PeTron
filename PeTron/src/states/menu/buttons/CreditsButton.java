package states.menu.buttons;

import customComponents.CustomHoverableButton;
import states.game.Game;

public class CreditsButton extends CustomHoverableButton {
	
	private static CreditsButton creditsButtonInstance;
	private static final String buttonImageSource = "/res/images/states/Credits.png";
	private static final String buttonHoveredImageSource = "/res/images/states/Credits2.png";
	private static final int leftBound = Game.frameWidth/2 - 450;
	private static final int upBound = Game.frameHeight/2 - 155 + 60 + 60;
	private static final int width = 182;
	private static final int height = 30;
	
	private CreditsButton() {
		super(buttonImageSource, buttonHoveredImageSource, leftBound, upBound, width, height);
	}
	
	public static CreditsButton getInstance() {
		if(creditsButtonInstance == null) {
			creditsButtonInstance = new CreditsButton();
		}
		return creditsButtonInstance;
	}
}
