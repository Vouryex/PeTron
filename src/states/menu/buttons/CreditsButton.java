package states.menu.buttons;

import engine.components.HoverableButton;
import states.game.Game;

public class CreditsButton extends HoverableButton {
	
	private static CreditsButton creditsButtonInstance;
	private static final String buttonImageSource = "/images/states/Credits.png";
	private static final String buttonHoveredImageSource = "/images/states/Credits2.png";
	private static final int leftBound = Game.getFrameWidth()/2 - 450;
	private static final int upBound = Game.getFrameHeight()/2 - 155 + 60 + 60;
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
