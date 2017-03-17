package states;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameEngine.stateEngine.GameState;
import gameEngine.stateEngine.GameStateEngine;
import states.game.Game;
import states.menu.Menu;
import utilities.Sound;

public class Credits extends GameState {
	
	private static Credits creditsInstance;
	
	/**Images**/
	public Image insideCredits;
	public Image back;
	public Image back2;
	public Image backShow;
	/**********/
	
	/**Image Dimensions**/
	public int backButton_height = 30;
	public int backButton_width = 107;
	public int backButton_leftBound = (Game.frameWidth / 2) - (backButton_width / 2);
	public int backButton_rightBound = backButton_leftBound + backButton_width;
	public int backButton_upBound = 558;
	public int backButton_downBound = backButton_upBound + backButton_height;
	/********************/
	
	/**Mouse Fields**/
	MouseListener mouseListener;
	Point mousePos;
	int mouseX;
	int mouseY;
	/****************/
	boolean hasClickSound;
	
	public static Credits getInstance() {
		if(creditsInstance == null) {
			creditsInstance = new Credits();
		}
		
		return creditsInstance;
	}
	
	public void init(GameStateEngine gameStateEngine) {
		/**MouseListener**/
		mouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(e.getX() > backButton_leftBound && e.getX() < backButton_rightBound &&
				   e.getY() > backButton_upBound && e.getY() < backButton_downBound) {
					cleanup(gameStateEngine);
					Menu.getInstance().init(gameStateEngine);
					gameStateEngine.popState();
				} 
			}
		};
		gameStateEngine.mainPanel.addMouseListener(mouseListener);
		/*****************/
		/**Load images**/
		try {
			insideCredits = ImageIO.read((getClass().getResourceAsStream("/res/images/states/InsideCredits.png")));
			back = ImageIO.read((getClass().getResourceAsStream("/res/images/states/Back.png")));
			back2 = ImageIO.read((getClass().getResourceAsStream("/res/images/states/Back2.png")));
		} catch(IOException e) {
			e.printStackTrace();
		}
		/***************/
	}
	
	public void cleanup(GameStateEngine gameStateEngine) {
		
	}
	
	public void pause(GameStateEngine gameStateEngine) {
		
	}
	
	public void resume(GameStateEngine gameStateEngine) {
		
	}
	
	public void update(GameStateEngine gameStateEngine) {
		/**Check for Button Hovers**/
		try {
			mousePos = gameStateEngine.mainPanel.getMousePosition();
			
			try {
				mouseX = mousePos.x;
				mouseY = mousePos.y;
			} catch(NullPointerException e) {
				//e.printStackTrace();
			}
			
			boolean outSideBack = false;
			if(mouseX > backButton_leftBound && mouseX < backButton_rightBound &&
			   mouseY > backButton_upBound && mouseY < backButton_downBound) {
				backShow = back2;
				if(!hasClickSound) {
					Sound.clickhover.play();
					hasClickSound = true;
				}
			} else {
				outSideBack = true;
				backShow = back;
			}
			
			if(outSideBack) {
				hasClickSound = false;
			}
			
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
		/**********************/
	}
	
	public void draw(Graphics2D g2d) {
		g2d.drawImage(insideCredits, 0, 0, Game.frameWidth, Game.frameHeight, null);
		g2d.drawImage(backShow, backButton_leftBound, backButton_upBound, backButton_width, backButton_height, null);
	}
}
