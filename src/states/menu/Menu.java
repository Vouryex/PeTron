package states.menu;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.components.HoverableButton;
import engine.components.GameButton;
import engine.stateEngine.GameState;
import engine.stateEngine.GameStateEngine;
import states.game.Game;
import states.menu.buttons.CreditsButton;
import states.menu.buttons.OptionsButton;
import states.menu.buttons.QuitButton;
import states.menu.buttons.StartButton;
import engine.utilities.Sound;

public class Menu extends GameState {	

	private static Menu menuInstance;
	private BufferedImage backgroundImage;
	private MouseListener mouseListener;
	private GameButton startButton = StartButton.getInstance();
	private GameButton optionsButton = OptionsButton.getInstance();
	private GameButton creditsButton = CreditsButton.getInstance();
	private GameButton quitButton = QuitButton.getInstance();
	
	private Menu() {}
	
	public static Menu getInstance() {
		if(menuInstance == null) {
			menuInstance = new Menu();
		}
		
		return menuInstance;
	}
	
	public void init(GameStateEngine gameStateEngine) {
		initMousePressListener(gameStateEngine);
		loadImageBackground();
		Sound.MENU.loop(); 
	}
	
	private void initMousePressListener(GameStateEngine gameStateEngine) {
		mouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				final int mouseXPos = e.getX();
				final int mouseYPos = e.getY();
				
				if(startButton.hovered(mouseXPos, mouseYPos)) {
					pause(gameStateEngine);
					Game.getInstance().init(gameStateEngine);
					gameStateEngine.pushState(Game.getInstance());
				}
				if(optionsButton.hovered(mouseXPos, mouseYPos)) {
					// cleanup();
					// gameStateEngine.pushState(new Options(gameStateEngine));
				}
				if(creditsButton.hovered(mouseXPos, mouseYPos)) {
					// cleanup();
					// gameStateEngine.pushState(new Credits(gameStateEngine));
				}
				if(quitButton.hovered(mouseXPos, mouseYPos)) {
					System.exit(0);
				}
			}
		};
		gameStateEngine.mainPanel.addMouseListener(mouseListener);
	}
	
	private void loadImageBackground() {
		try {
			backgroundImage = ImageIO.read((getClass().getResourceAsStream("/images/states/Menu Enhanced.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void cleanup(GameStateEngine gameStateEngine) {
		gameStateEngine.mainPanel.removeMouseListener(mouseListener);
		Sound.MENU.stop();
	}
	
	public void pause(GameStateEngine gameStateEngine) {
		gameStateEngine.mainPanel.removeMouseListener(mouseListener);
		Sound.MENU.stop();
	}
	
	public void resume(GameStateEngine gameStateEngine) {
		initMousePressListener(gameStateEngine);
		Sound.MENU.loop();
	}
	
	public void update(GameStateEngine gameStateEngine) {
		checkButtonHovers(gameStateEngine);
	}
	
	private void checkButtonHovers(GameStateEngine gameStateEngine) {
		Point mousePos = getMousePos(gameStateEngine);
		int mouseXPos = getMouseXPos(mousePos);
		int mouseYPos = getMouseYPos(mousePos);
		
		checkStartButtonHover(mouseXPos, mouseYPos);
		checkOptionsButtonHover(mouseXPos, mouseYPos);
		checkCreditsButtonHover(mouseXPos, mouseYPos);
		checkQuitButtonHover(mouseXPos, mouseYPos);
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
	
	private void checkStartButtonHover(int mouseXPos, int mouseYPos) {
		if(!startButton.getWasHovered() && startButton.hovered(mouseXPos, mouseYPos)) {
			startButton.setButtonImage(((HoverableButton)startButton).getHoveredImage());
			Sound.clickhover.play();
		} else if(startButton.getWasHovered() && !startButton.hovered(mouseXPos, mouseYPos)) {
			startButton.setButtonImage(((HoverableButton)startButton).getDefaultImage());
			Sound.clickhover.stop();
		}
	}
	
	private void checkOptionsButtonHover(int mouseXPos, int mouseYPos) {
		if(!optionsButton.getWasHovered() && optionsButton.hovered(mouseXPos, mouseYPos)) {
			optionsButton.setButtonImage(((HoverableButton)optionsButton).getHoveredImage());
			Sound.clickhover.play();
		} else if(optionsButton.getWasHovered() && !optionsButton.hovered(mouseXPos, mouseYPos)) {
			optionsButton.setButtonImage(((HoverableButton)optionsButton).getDefaultImage());
			Sound.clickhover.stop();
		}
	}
	
	private void checkCreditsButtonHover(int mouseXPos, int mouseYPos) {
		if(!creditsButton.getWasHovered() && creditsButton.hovered(mouseXPos, mouseYPos)) {
			creditsButton.setButtonImage(((HoverableButton)creditsButton).getHoveredImage());
			Sound.clickhover.play();
		} else if(creditsButton.getWasHovered() && !creditsButton.hovered(mouseXPos, mouseYPos)) {
			creditsButton.setButtonImage(((HoverableButton)creditsButton).getDefaultImage());
			Sound.clickhover.stop();
		}
	}
	
	private void checkQuitButtonHover(int mouseXPos, int mouseYPos) {
		if(!quitButton.getWasHovered() && quitButton.hovered(mouseXPos, mouseYPos)) {
			quitButton.setButtonImage(((HoverableButton)quitButton).getHoveredImage());
			Sound.clickhover.play();
		} else if(quitButton.getWasHovered() && !quitButton.hovered(mouseXPos, mouseYPos)) {
			quitButton.setButtonImage(((HoverableButton)quitButton).getDefaultImage());
			Sound.clickhover.stop();
		}
	}
	
	public void draw(Graphics2D g2d) {		
		g2d.drawImage(backgroundImage, 0, 0, Game.frameWidth, Game.frameHeight, null);
		startButton.draw(g2d);
		optionsButton.draw(g2d);
		creditsButton.draw(g2d);
		quitButton.draw(g2d);
	}
}
