package states.pause;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import customComponents.CustomHoverableButton;
import gameEngine.gameComponents.GameButton;
import gameEngine.gameComponents.GamePanel;
import gameEngine.stateEngine.GameState;
import gameEngine.stateEngine.GameStateEngine;
import keys.KeypressControl;
import states.game.Game;
import states.menu.Menu;
import states.pause.buttons.ExitButton;
import states.pause.buttons.ResetButton;
import states.pause.buttons.RestartButton;
import states.pause.buttons.ResumeButton;
import states.pause.panels.PausePanel;
import utilities.Sound;

public class Pause extends GameState {
	
	private static Pause pauseInstance;
	private static final int PAUSE_BUTTON = Game.PAUSE_BUTTON;
	private MouseListener mouseListener;
	private KeypressControl keypressControl = new KeypressControl();
	private GamePanel pausePanel = PausePanel.getInstance();
	private GameButton restartButton = RestartButton.getInstance();
	private GameButton resumeButton = ResumeButton.getInstance();
	private GameButton resetButton = ResetButton.getInstance();
	private GameButton exitButton = ExitButton.getInstance();
	private GameState previousState; 

	private Pause() {}
	
	public static Pause getInstance() {
		if(pauseInstance == null) {
			pauseInstance = new Pause();
		}
		
		return pauseInstance;
	}
	
	public void setPreviousState(GameState previousState) {
		this.previousState = previousState;
	}
	
	public void init(GameStateEngine gameStateEngine) {
		initMouseListener(gameStateEngine);
		gameStateEngine.mainPanel.addKeyListener(keypressControl.getKeyListener());
		gameStateEngine.mainPanel.setCursor(Cursor.getDefaultCursor());
	}
	
	private void initMouseListener(GameStateEngine gameStateEngine) {
		mouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				final int mouseXPos = e.getX();
				final int mouseYPos = e.getY();
				
				if(restartButton.hovered(mouseXPos, mouseYPos)) {
					cleanup(gameStateEngine);
					previousState.init(gameStateEngine);
					gameStateEngine.popState();
				}
				if(resumeButton.hovered(mouseXPos, mouseYPos)) {
					cleanup(gameStateEngine);
					previousState.resume(gameStateEngine);
					gameStateEngine.popState();
				}
				if(resetButton.hovered(mouseXPos, mouseYPos)) {
					cleanup(gameStateEngine);
					previousState.init(gameStateEngine);
					((Game)previousState).resetScores();
					gameStateEngine.popState();
				}
				if(exitButton.hovered(mouseXPos, mouseYPos)) {
					cleanup(gameStateEngine);
					previousState.cleanup(gameStateEngine);
					gameStateEngine.popState();
					Menu.getInstance().resume(gameStateEngine);
					gameStateEngine.popState();
				}
			}
		};
		gameStateEngine.mainPanel.addMouseListener(mouseListener);
	}
	
	public void cleanup(GameStateEngine gameStateEngine) {
		gameStateEngine.mainPanel.removeMouseListener(mouseListener);
		// .... clear keypressControl
		gameStateEngine.mainPanel.removeKeyListener(keypressControl.getKeyListener());
	}
	
	public void pause(GameStateEngine gameStateEngine) {
		
	}
	
	public void resume(GameStateEngine gameStateEngine) {
		
	}
	
	public void update(GameStateEngine gameStateEngine) {
		checkUnpause(gameStateEngine);
		checkButtonHovers(gameStateEngine);
	}
	
	private void checkUnpause(GameStateEngine gameStateEngine) {
		if(keypressControl.keyExists(PAUSE_BUTTON)) {
			cleanup(gameStateEngine);
			previousState.resume(gameStateEngine);
			gameStateEngine.popState();
		}
	}
	
	private void checkButtonHovers(GameStateEngine gameStateEngine) {
		Point mousePos = getMousePos(gameStateEngine);
		int mouseXPos = getMouseXPos(mousePos);
		int mouseYPos = getMouseYPos(mousePos);
		
		checkRestartButtonHover(mouseXPos, mouseYPos);
		checkResumeButtonHover(mouseXPos, mouseYPos);
		checkResetButtonHover(mouseXPos, mouseYPos);
		checkExitButtonHover(mouseXPos, mouseYPos);
	}

	private Point getMousePos(GameStateEngine gameStateEngine) {
		try {
			return gameStateEngine.mainPanel.getMousePosition();
		} catch(NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private int getMouseXPos(Point mousePos) {
		try {
			return mousePos.x;
		} catch(NullPointerException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	private int getMouseYPos(Point mousePos) {
		try {
			return mousePos.y;
		} catch(NullPointerException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	private void checkRestartButtonHover(int mouseXPos, int mouseYPos) {
		if(!restartButton.getWasHovered() && restartButton.hovered(mouseXPos, mouseYPos)) {
			restartButton.setButtonImage(((CustomHoverableButton)restartButton).getHoveredImage());
			Sound.clickhover.play();
		} else if(restartButton.getWasHovered() && !restartButton.hovered(mouseXPos, mouseYPos)) {
			restartButton.setButtonImage(((CustomHoverableButton)restartButton).getDefaultImage());
			Sound.clickhover.stop();
		}
	}
	
	private void checkResumeButtonHover(int mouseXPos, int mouseYPos) {
		if(!resumeButton.getWasHovered() && resumeButton.hovered(mouseXPos, mouseYPos)) {
			resumeButton.setButtonImage(((CustomHoverableButton)resumeButton).getHoveredImage());
			Sound.clickhover.play();
		} else if(resumeButton.getWasHovered() && !resumeButton.hovered(mouseXPos, mouseYPos)) {
			resumeButton.setButtonImage(((CustomHoverableButton)resumeButton).getDefaultImage());
			Sound.clickhover.stop();
		}
	}
	
	private void checkResetButtonHover(int mouseXPos, int mouseYPos) {
		if(!resetButton.getWasHovered() && resetButton.hovered(mouseXPos, mouseYPos)) {
			resetButton.setButtonImage(((CustomHoverableButton)resetButton).getHoveredImage());
			Sound.clickhover.play();
		} else if(resetButton.getWasHovered() && !resetButton.hovered(mouseXPos, mouseYPos)) {
			resetButton.setButtonImage(((CustomHoverableButton)resetButton).getDefaultImage());
			Sound.clickhover.stop();
		}
	}
	
	private void checkExitButtonHover(int mouseXPos, int mouseYPos) {
		if(!exitButton.getWasHovered() && exitButton.hovered(mouseXPos, mouseYPos)) {
			exitButton.setButtonImage(((CustomHoverableButton)exitButton).getHoveredImage());
			Sound.clickhover.play();
		} else if(exitButton.getWasHovered() && !exitButton.hovered(mouseXPos, mouseYPos)) {
			exitButton.setButtonImage(((CustomHoverableButton)exitButton).getDefaultImage());
			Sound.clickhover.stop();
		}
	}
	
	public void draw(Graphics2D g2d) {
		previousState.draw(g2d);
		pausePanel.draw(g2d);
		restartButton.draw(g2d);
		resumeButton.draw(g2d);
		resetButton.draw(g2d);
		exitButton.draw(g2d);
	}
}
