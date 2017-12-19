package states.pause.buttons;

import engine.components.HoverableButton;
import states.game.Game;

public class ResumeButton extends HoverableButton {

	private static ResumeButton resumeButtonInstance;
	private static final String buttonImageSource = "/images/states/pauseResume.png";
	private static final String buttonHoveredImageSource = "/images/states/pauseResume2.png";
	private static final int leftBound = (Game.frameWidth / 2) - (170 / 2);
	private static final int upBound = (Game.frameHeight / 2) - (30 / 2) - 35;
	private static final int width = 170;
	private static final int height = 30;
	
	private ResumeButton() {
		super(buttonImageSource, buttonHoveredImageSource, leftBound, upBound, width, height);
	}
	
	public static ResumeButton getInstance() {
		if(resumeButtonInstance == null) {
			resumeButtonInstance = new ResumeButton();
		}
		return resumeButtonInstance;
	}
}
