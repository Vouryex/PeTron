package engine.stateEngine;

import java.awt.Color;
import java.util.Stack;

import javax.swing.JFrame;

import engine.components.MainPanel;
import states.game.Game;
import states.menu.Menu;

public class GameStateEngine {
	private static GameStateEngine gameStateEngineInstance;
	public MainPanel mainPanel = new MainPanel(this);
	public Stack<GameState> stateStack = new Stack<GameState>();
	
	private GameStateEngine() {
		mainPanel.setBackground(Color.BLACK);
		mainPanel.setFocusable(true);
		
		JFrame frame = new JFrame("Menu");
		frame.add(mainPanel);
		frame.setSize(Game.frameWidth, Game.frameHeight);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Game.centreWindow(frame);
	}
	
	public static GameStateEngine getInstance() {
		if(gameStateEngineInstance == null) {
			gameStateEngineInstance = new GameStateEngine();
		}
		
		return gameStateEngineInstance;
	}
	
	public void pushState(GameState gameState) {
		stateStack.push(gameState);
	}
	
	public void popState() {
		stateStack.pop();
	}

	public void update() {
		stateStack.peek().update(gameStateEngineInstance);	
	}
	
	public void draw() {
		mainPanel.repaint();
	}
	
	public static void main(String args[]) throws InterruptedException {
		GameStateEngine gameStateEngine = GameStateEngine.getInstance();
		Menu.getInstance().init(gameStateEngine);
		gameStateEngine.pushState(Menu.getInstance());
		
		
		while(true) {
			gameStateEngine.update();
			gameStateEngine.draw();
			Thread.sleep(10);
		}
	}
}


