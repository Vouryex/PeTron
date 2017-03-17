package states.pause.panels;

import gameEngine.gameComponents.GamePanel;
import states.game.Game;

public class PausePanel extends GamePanel {
	
	private static PausePanel pausePanelInstance;
	private static final String panelImageSource = "/res/images/states/Pause.png";
	private static final int width = 340;
	private static final int height = 310;
	private static final int leftBound = (Game.frameWidth / 2) - (width / 2);
	private static final int upBound = (Game.frameHeight / 2) - (height / 2);
	
	private PausePanel() {
		super(panelImageSource, leftBound, upBound, width, height);
	}
	
	public static PausePanel getInstance() {
		if(pausePanelInstance == null) {
			pausePanelInstance = new PausePanel();
		}
		return pausePanelInstance;
	}
}
