package customComponents;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.EmptyStackException;

import javax.swing.JPanel;

import gameEngine.stateEngine.GameStateEngine;

public class MainPanel extends JPanel {
	public GameStateEngine gameStateEngine;
	
	public MainPanel(GameStateEngine gameStateEngine) {
		this.gameStateEngine = gameStateEngine;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
							 RenderingHints.VALUE_ANTIALIAS_ON);
		
		try {
			gameStateEngine.stateStack.peek().draw(g2d);
		} catch(EmptyStackException e) {
			e.printStackTrace();
		}
	}
}