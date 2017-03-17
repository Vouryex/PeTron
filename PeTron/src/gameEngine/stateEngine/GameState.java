package gameEngine.stateEngine;

import java.awt.Graphics2D;

public abstract class GameState {
	
	public abstract void init(GameStateEngine gameStateEngine);
	public abstract void cleanup(GameStateEngine gameStateEngine);
	
	public abstract void pause(GameStateEngine gameStateEngine);
	public abstract void resume(GameStateEngine gameStateEngine);
	
	public abstract void update(GameStateEngine gameStateEngine);
	public abstract void draw(Graphics2D g2d);
}
