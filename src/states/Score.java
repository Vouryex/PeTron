package states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import engine.stateEngine.GameState;
import engine.stateEngine.GameStateEngine;
import engine.utilities.KeypressControl;
import states.game.Game;

public class Score extends GameState{
	
	private static Score scoreInstance;
	private static final int PLAY_AGAIN_BUTTON = KeyEvent.VK_SPACE;
	private GameState prevGameState;
	private KeypressControl keypressControlInstance = new KeypressControl();
	private String message;
	
	private Score() {}
	
	public static Score getInstance() {
		if(scoreInstance == null) {
			scoreInstance = new Score();
		}
		
		return scoreInstance;
	}
	
	public void setPrevGameState(GameState prevGameState) {
		this.prevGameState = prevGameState;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void init(GameStateEngine gameStateEngine) {
		final KeyListener keyListener = keypressControlInstance.getKeyListener();
		gameStateEngine.mainPanel.addKeyListener(keyListener);
	}
	
	public void cleanup(GameStateEngine gameStateEngine) {
		keypressControlInstance.removeKey(PLAY_AGAIN_BUTTON); // should clear all keys
		final KeyListener keyListener = keypressControlInstance.getKeyListener();
		gameStateEngine.mainPanel.removeKeyListener(keyListener);
	}
	
	public void pause(GameStateEngine gameStateEngine) {
		
	}
	
	public void resume(GameStateEngine gameStateEngine) {
		
	}
	
	public void update(GameStateEngine gameStateEngine) {
		checkPlayAgain(gameStateEngine);
	}
	
	public void draw(Graphics2D g2d) {
		prevGameState.draw(g2d);
		//g2d.drawString("Press Space Bar to Continue", 400, 300);
	}
	
	public void checkPlayAgain(GameStateEngine gameStateEngine) {
		if(keypressControlInstance.keyExists(PLAY_AGAIN_BUTTON)) {
			cleanup(gameStateEngine); 
			Game.getInstance().init(gameStateEngine);
			gameStateEngine.popState();
		}
	}
}

