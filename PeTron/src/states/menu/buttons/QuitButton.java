package states.menu.buttons;

import customComponents.CustomHoverableButton;
import states.game.Game;

public class QuitButton extends CustomHoverableButton {
	
	private static QuitButton quitButtonInstance;
	private static final String buttonImageSource = "/res/images/states/Quit.png";
	private static final String buttonHoveredImageSource = "/res/images/states/Quit2.png";
	private static final int leftBound = Game.frameWidth/2 - 450;
	private static final int upBound = Game.frameHeight/2 - 155 + 90 + 90;
	private static final int width = 98;
	private static final int height = 30;
	
	private QuitButton() {
		super(buttonImageSource, buttonHoveredImageSource, leftBound, upBound, width, height);
	}
	
	public static QuitButton getInstance() {
		if(quitButtonInstance == null) {
			quitButtonInstance = new QuitButton();
		}
		return quitButtonInstance;
	}
}
