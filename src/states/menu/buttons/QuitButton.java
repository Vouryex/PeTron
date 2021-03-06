package states.menu.buttons;

import engine.components.HoverableButton;
import states.game.Game;

public class QuitButton extends HoverableButton {
	
	private static QuitButton quitButtonInstance;
	private static final String buttonImageSource = "/images/states/Quit.png";
	private static final String buttonHoveredImageSource = "/images/states/Quit2.png";
	private static final int leftBound = Game.getFrameWidth()/2 - 450;
	private static final int upBound = Game.getFrameHeight()/2 - 155 + 90 + 90;
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
