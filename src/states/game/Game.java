package states.game;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import engine.stateEngine.GameState;
import engine.stateEngine.GameStateEngine;
import states.Score;
import states.game.interfaces.Collideable;
import states.game.interfaces.Movable;
import states.game.interfaces.Paintable;
import states.game.motorbiker.MotorBiker;
import states.game.motorbiker.Player1;
import states.game.motorbiker.Player2;
import states.game.motorbiker.Trail;
import states.game.powerups.PowerUp;
import states.game.powerups.PowerUpEnum;
import states.game.powerups.Reverse;
import states.game.powerups.Shield;
import states.game.powerups.SideBlade;
import states.game.powerups.SubSideBladeSkill;
import states.pause.Pause;
import engine.utilities.Sound;

public class Game extends GameState {
	
	private static Game gameInstance;
	/**Dimensions**/
	// each state should have their own width and height
	// rename
	public static final int frameWidth = 1000; 
	public static final int frameHeight = 600;
	/**************/
	
	/**Intervals**/
	// rename transition interval
	public static final int KEYPRESS_INTERVAL = 160;
	public static final int POWERUP_SPAWN_INTERVAL = 5000;
	public static final int TRANSITION_INTERVAL = 3000;
	/*************/
	
	public static final int PAUSE_BUTTON = KeyEvent.VK_P; // Pause Button Code
	public KeyListener keyListener;
	public long transitionStartTime;
	public long powerUpSpawnTime;
	
	/**Lists**/
	public List<Integer> gameEventKeyList;
	public List<Collideable> collideableList;
	public List<Paintable> paintableList;
	public List<Movable> movableList;
	public List<MotorBiker> motorBikerList;
	/*********/
	
	/**Game Object Fields**/
	private Arena arena;
	public StatusBar statusBar;
	private MotorBiker motorBiker1;
	private MotorBiker motorBiker2;
	//private CountDownManager countDownManager; // TODO NEW
	//private CursorManager cursorManager; // TODO NEW
	/**********************/

	/**Countdown Images**/
	public Image one;
	public Image two;
	public Image three;
	/********************/
	
	public Image emptyCursor;
	
	
	public int player1Score = 0;
	public int player2Score = 0;
	
	//*******************************
	// Status Border should contain these fields
	public int player1ScoreOnesDigit;
	public int player1ScoreTensDigit;
	public int player2ScoreOnesDigit;
	public int player2ScoreTensDigit;
	//*******************************

	AudioClip gameMusic;
	int countNum; // rename
	
	
	
	private Game() {}
	
	public static Game getInstance() {
		if(gameInstance == null) {
			gameInstance = new Game();
		}
		return gameInstance;
	}

	public void init(GameStateEngine gameStateEngine) {
		arena  = new Arena(gameInstance);
		statusBar = new StatusBar(gameInstance);
		motorBiker1 = new Player1();
		motorBiker2 = new Player2();
		
		initializeKeyListener();
		gameStateEngine.mainPanel.addKeyListener(keyListener);
		initializeGameLists();
		addObjsToLists();
		
		try {
			loadImages();
		} catch(IOException e) {
			e.printStackTrace();
		}

		setCursorEmpty(gameStateEngine);
		
		transitionStartTime = System.currentTimeMillis();
		powerUpSpawnTime = System.currentTimeMillis();

		chooseRandMusic();
		countNum = 0;
	}
	
	private void initializeKeyListener() {
		keyListener = new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				for(MotorBiker motorBikerObj : motorBikerList) {
					removeKey(motorBikerObj.keyPressedList, e.getKeyCode());
				}
			}

			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == PAUSE_BUTTON) {
					gameEventKeyList.add(e.getKeyCode());
				}
				if((e.getKeyCode() != KeyEvent.VK_SHIFT && e.getKeyCode() != KeyEvent.VK_CONTROL) || e.getKeyLocation() != KeyEvent.KEY_LOCATION_LEFT) {
					long currentTime = System.currentTimeMillis();
					
					for(MotorBiker motorBikerObj : motorBikerList) {
						if(motorBikerObj.hasKey(e.getKeyCode())) {
							if(!(motorBikerObj.direction == MotorBiker.DIRECTION_LEFT && motorBikerObj.leftButton == e.getKeyCode() ||
							     motorBikerObj.direction == MotorBiker.DIRECTION_RIGHT && motorBikerObj.rightButton == e.getKeyCode() ||
							     motorBikerObj.direction == MotorBiker.DIRECTION_UP && motorBikerObj.upButton == e.getKeyCode() ||
							     motorBikerObj.direction == MotorBiker.DIRECTION_DOWN && motorBikerObj.downButton == e.getKeyCode())) 
							{
								if(currentTime > motorBikerObj.keyPressTime + KEYPRESS_INTERVAL) {
									motorBikerObj.keyPressedList.clear();
									motorBikerObj.keyPressedList.add(e.getKeyCode());
									motorBikerObj.keyPressTime = System.currentTimeMillis();
								} else {
									motorBikerObj.pendingKeyList.clear();
									motorBikerObj.pendingKeyList.add(e.getKeyCode());
								}
								break;
							}
						}
					}
				}
		 	}
		};
	}
	
	private void initializeGameLists() {
		gameEventKeyList = new ArrayList<Integer>();
		collideableList = new ArrayList<Collideable>();
		paintableList = new ArrayList<Paintable>();
		movableList = new ArrayList<Movable>();
		motorBikerList = new ArrayList<MotorBiker>();
	}
	
	private void initializeGameObjects() {
		
		
	}
	
	private void addObjsToLists() {
		collideableList.add(motorBiker1);
		collideableList.add(motorBiker2);
		collideableList.add(arena.getVirtualBorder().getLeftBorder());
		collideableList.add(arena.getVirtualBorder().getRightBorder());
		collideableList.add(arena.getVirtualBorder().getUpBorder());
		collideableList.add(arena.getVirtualBorder().getDownBorder());
		
		paintableList.add(motorBiker1);
		paintableList.add(motorBiker2);
		paintableList.add(arena);
		paintableList.add(statusBar);
		
		movableList.add(motorBiker1);
		movableList.add(motorBiker2);
		
		motorBikerList.add(motorBiker1);
		motorBikerList.add(motorBiker2);
	}
	
	private void loadImages() throws IOException {
		loadCountdownImages();
		emptyCursor = ImageIO.read((getClass().getResourceAsStream("/images/game/Blank Cursor.png")));
	}
	
	private void loadCountdownImages() throws IOException {
		one = ImageIO.read((getClass().getResourceAsStream("/images/game/1.png")));
		two = ImageIO.read((getClass().getResourceAsStream("/images/game/2.png")));
		three = ImageIO.read((getClass().getResourceAsStream("/images/game/3.png")));
	}

	private void setCursorEmpty(GameStateEngine gameStateEngine) {
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(emptyCursor, new Point(0, 0), "blank cursor");
		gameStateEngine.mainPanel.setCursor(blankCursor);
	}
	
	public void cleanup(GameStateEngine gameStateEngine) {
		gameStateEngine.mainPanel.removeKeyListener(keyListener); 
		gameMusic.stop();
	}
	
	public void pause(GameStateEngine gameStateEngine) {
		gameStateEngine.mainPanel.removeKeyListener(keyListener); 
	}
	
	public void resume(GameStateEngine gameStateEngine) {
		gameEventKeyList.clear();
		initializeKeyListener();
		gameStateEngine.mainPanel.addKeyListener(keyListener);
		
		setCursorEmpty(gameStateEngine);
		
		transitionStartTime = System.currentTimeMillis(); 
		
		countNum = 0;
	}
	
	public void resetScores() {
		player1Score = player2Score = 0;
	}
	
	public void chooseRandMusic() {
		if(gameMusic != null) {
			gameMusic.stop();
		}
		int musicIndex = randInt(1, 8);
		if(musicIndex == 1) {
			gameMusic = Sound.game1;
		} else if(musicIndex == 2) { 
			gameMusic = Sound.game2;
		} else if(musicIndex == 3) {
			gameMusic = Sound.game3;
		} else if(musicIndex == 4) {
			gameMusic = Sound.game4;
		} else if(musicIndex == 5) {
			gameMusic = Sound.game5;
		} else if(musicIndex == 6) {
			gameMusic = Sound.game6;
		} else if(musicIndex == 7) {
			gameMusic = Sound.game7;
		} else if(musicIndex == 8) {
			gameMusic = Sound.game8;
		} else {
			gameMusic = Sound.game9;
		}
		gameMusic.loop();
	}
	
	void updateScore() {
		player1ScoreOnesDigit = player1Score % 10;
		player1ScoreTensDigit = player1Score / 10;
		player2ScoreOnesDigit = player2Score % 10;
		player2ScoreTensDigit = player2Score / 10;
	}
	
	public void update(GameStateEngine gameStateEngine) {
		checkPause(gameStateEngine);
		
		if(countDownFinished()) {
			initiatePendingKeys();
			checkCollisions(gameStateEngine);
			move();
			spawnPowerUp();
			deactivatePowerUp();
			activatePowerUp();
		} else {
			countDown();
		}
		
		updateScore();
	}
	
	private boolean countDownFinished() {
		long currentTime = System.currentTimeMillis();
		return currentTime > transitionStartTime + TRANSITION_INTERVAL;
	}
	
	private void countDown() {
		long currentTime = System.currentTimeMillis();
		if(currentTime - transitionStartTime > 0 && countNum == 0) {
			Sound.count.stop();
			Sound.count.play();
			countNum++;
		} else if(currentTime - transitionStartTime > 1000 && countNum == 1) {
			Sound.count.stop();
			Sound.count.play();
			countNum++;
		} else if(currentTime - transitionStartTime > 2000 && countNum == 2) {
			Sound.count.stop();
			Sound.count.play();
			countNum++;
		}
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
							 RenderingHints.VALUE_ANTIALIAS_ON);
		
		setMotorBikersLastToPaint();
		drawPaintables(g2d);
		drawCountdown(g2d);
	}
	
	private void setMotorBikersLastToPaint() {
		for(MotorBiker motorBiker:motorBikerList) {
			paintableList.remove(motorBiker);
		}
		
		for(MotorBiker motorBiker:motorBikerList) {
			paintableList.add(motorBiker);
		}
	}
	
	private void drawPaintables(Graphics2D g2d) {
		for(int i = 0; i < paintableList.size(); i++) {
			Paintable paintableObj = paintableList.get(i);
			paintableObj.paint(g2d);
		}
	}
	
	private void drawCountdown(Graphics2D g2d) {
		long currentTime = System.currentTimeMillis();
		
		if(currentTime < transitionStartTime + TRANSITION_INTERVAL) {
			if(currentTime - transitionStartTime <= 1000) {
				g2d.drawImage(three, (frameWidth / 2) - (146 / 2), 
							 (frameHeight / 2) - (150 / 2), 146, 150, null);
			} else if(currentTime - transitionStartTime <= 2000) {
				g2d.drawImage(two, (frameWidth / 2) - (157 / 2), 
						 (frameHeight / 2) - (150 / 2), 157, 150, null);
			} else {
				g2d.drawImage(one, (frameWidth / 2) - (55 / 2), 
						 (frameHeight / 2) - (150 / 2), 55, 150, null);
			}
		}
	}
	
	public void checkPause(GameStateEngine gameStateEngine) {
		if(keyExists(gameEventKeyList, PAUSE_BUTTON)) {
			pause(gameStateEngine);
			Pause.getInstance().init(gameStateEngine);
			Pause.getInstance().setPreviousState(this);
			gameStateEngine.pushState(Pause.getInstance());
		}
	}
	
	public void spawnPowerUp() {
		
		if(timeToSpawnPowerUp()) {
			int xPos = 0;
			int yPos = 0;
			boolean invalidPosition = true;
			
			while(invalidPosition) {
				xPos = randInt(arena.getLeftBound(), arena.getRightBound());
				yPos = randInt(arena.getUpBound(), arena.getDownBound());
				for(Collideable collideableObj : collideableList) {
					if(new PowerUp(xPos, yPos).collision(collideableObj)) {
						invalidPosition = true;
						break;
					}
					invalidPosition = false;
				}
			}

			PowerUp powerup = null;
			int powerUpID = randInt(0, PowerUpEnum.values().length - 1);
			if(PowerUpEnum.values()[powerUpID].getName() == Shield.getName()) {
				powerup = new Shield(xPos, yPos);	
			} else if(PowerUpEnum.values()[powerUpID].getName() == SideBlade.getName()) {
				powerup = new SideBlade(xPos, yPos);
			} else if(PowerUpEnum.values()[powerUpID].getName() == Reverse.getName()) {
				powerup = new Reverse(xPos, yPos);
			}
			long currentPowerUpSpawnTime = System.currentTimeMillis();
			
			paintableList.add(powerup);
			collideableList.add(powerup);
			powerUpSpawnTime = currentPowerUpSpawnTime;
		}
	}
	
	private boolean timeToSpawnPowerUp() {
		long currentTime = System.currentTimeMillis();
		
		return currentTime > powerUpSpawnTime + POWERUP_SPAWN_INTERVAL;
	}
	
	public void checkCollisions(GameStateEngine gameStateEngine) {
		List<Collideable> removeCollideables = new ArrayList<Collideable>();
		
		boolean roundTerminate = false;
		for(MotorBiker motorBiker : motorBikerList) {
			for(Collideable collideObj : collideableList) {
				if(motorBiker.collision(collideObj) || collideObj.collision(motorBiker)) {
					// if instance of trail
					if(collideObj instanceof Trail && !collideObj.collision(motorBiker)) {
						if(!motorBiker.shieldActivated) {
							Sound.motorhittrail.play();
							((Trail)collideObj).setColor(Color.WHITE);
							if(motorBiker == motorBiker1) player2Score++;
							else						  player1Score++;
							motorBiker.imageExplode();
							pause(gameStateEngine);
							Score.getInstance().init(gameStateEngine);
							Score.getInstance().setPrevGameState(this);
							Score.getInstance().setMessage("Hit by Trail");
							gameStateEngine.pushState(Score.getInstance());
							break;
						} 
					} else if(collideObj instanceof PowerUp) {
						// if instance of power up
						removeCollideables.add(collideObj);
						paintableList.remove((Paintable) collideObj);
						motorBiker.addPowerUp((PowerUp)collideObj);
						if(((PowerUp)collideObj) instanceof Reverse) {
							Sound.reverseget.play();
						} else {
							Sound.powerupget.play();
						}
					} else if(collideObj instanceof VirtualSubBorder) {
						// if instance of subBorders
						if(motorBiker.shieldActivated) {
							Sound.shieldhitwall.play();
						} else {
							Sound.motorhitwall.play();
						}
						if(motorBiker == motorBiker1) player2Score++;
						else						  player1Score++;
						motorBiker.imageExplode();
						pause(gameStateEngine);
						Score.getInstance().init(gameStateEngine);
						Score.getInstance().setPrevGameState(this);
						Score.getInstance().setMessage("Hit by border");
						gameStateEngine.pushState(Score.getInstance());
						break;
					} else if(collideObj instanceof SubSideBladeSkill) {
						if(motorBiker.shieldActivated) {
							if((motorBiker.sideBladeSkill != null && (motorBiker.sideBladeSkill.sideBlade1 != (SubSideBladeSkill) collideObj &&
							    motorBiker.sideBladeSkill.sideBlade2 != (SubSideBladeSkill) collideObj)) || motorBiker.sideBladeSkill == null) 
							{
								Sound.shieldhitsideblade.play();
								removeCollideables.add(collideObj);
								paintableList.remove((Paintable)collideObj);
							}
						} else {
							if((motorBiker.sideBladeSkill != null && (motorBiker.sideBladeSkill.sideBlade1 != (SubSideBladeSkill) collideObj &&
							    motorBiker.sideBladeSkill.sideBlade2 != (SubSideBladeSkill) collideObj)) || motorBiker.sideBladeSkill == null) 
							{
								if(motorBiker == motorBiker1) player2Score++;
								else						  player1Score++;
								Sound.sidebladehitmotor.play();
								motorBiker.imageExplode();
								pause(gameStateEngine);
								Score.getInstance().init(gameStateEngine);
								Score.getInstance().setPrevGameState(this);
								Score.getInstance().setMessage("Hit by sideblade");
								gameStateEngine.pushState(Score.getInstance());
								break;
							}
						}
					} else if(collideObj instanceof MotorBiker && collideObj != motorBiker){
						// if instance of motor biker
						if(motorBiker.shieldActivated && ((MotorBiker)collideObj).shieldActivated) {
							Sound.shieldshieldmotor.play();
							motorBiker.imageExplode();
							((MotorBiker)collideObj).imageExplode();
							pause(gameStateEngine);
							Score.getInstance().init(gameStateEngine);
							Score.getInstance().setPrevGameState(this);
							Score.getInstance().setMessage("Hit by enemy, draw");
							gameStateEngine.pushState(Score.getInstance());
							break;
						} else if(motorBiker.shieldActivated && !((MotorBiker)collideObj).shieldActivated) {
							if(motorBiker == motorBiker1) player1Score++;
							else						  player2Score++;
							Sound.shieldshieldmotor.play();
							((MotorBiker)collideObj).imageExplode();
							pause(gameStateEngine);
							Score.getInstance().init(gameStateEngine);
							Score.getInstance().setPrevGameState(this);
							Score.getInstance().setMessage("Hit by enemy, hit by shield");
							gameStateEngine.pushState(Score.getInstance());
						    roundTerminate = true;
							break;
						} else if(!motorBiker.shieldActivated && ((MotorBiker)collideObj).shieldActivated) {
							if(motorBiker == motorBiker1) player2Score++;
							else						  player1Score++;
							Sound.shieldshieldmotor.play();
							motorBiker.imageExplode();
							pause(gameStateEngine);
							Score.getInstance().init(gameStateEngine);
							Score.getInstance().setPrevGameState(this);
							Score.getInstance().setMessage("Hit by enemy, hit by shield");	
							gameStateEngine.pushState(Score.getInstance());
							roundTerminate = true;
							break;
						} else {
							Sound.motorhitmotor.play();
							motorBiker.imageExplode();
							((MotorBiker)collideObj).imageExplode();
							pause(gameStateEngine);
							Score.getInstance().init(gameStateEngine);
							Score.getInstance().setPrevGameState(this);
							Score.getInstance().setMessage("Hit by enemy, draw");
							gameStateEngine.pushState(Score.getInstance());
							roundTerminate = true;
							break;
						}
						
					}
				}
			}
			if(roundTerminate) {
				break;
			}
		}
		
		// Remove Collideables
		for(Collideable removeCollideable : removeCollideables) {
			collideableList.remove(removeCollideable);
		}
	}
	
	public void initiatePendingKeys() {
		long currentTime = System.currentTimeMillis();
		for(MotorBiker motorBikerObj:motorBikerList) {
			if(!motorBikerObj.pendingKeyList.isEmpty() && currentTime > motorBikerObj.keyPressTime + KEYPRESS_INTERVAL) {
				motorBikerObj.keyPressedList.clear();
				motorBikerObj.keyPressedList.add(motorBikerObj.pendingKeyList.get(motorBikerObj.pendingKeyList.size()-1));
				motorBikerObj.pendingKeyList.clear();
				motorBikerObj.keyPressTime = currentTime;
			}
		}
		
	}
	
	public void move() {
		for(Movable movableObj : movableList) {
			movableObj.move();
		}
	}
	
	public void deactivatePowerUp() {
		long currentTime = System.currentTimeMillis();
		
		for(MotorBiker motorBiker : motorBikerList) {
			if(motorBiker.shieldActivated == true && currentTime > motorBiker.shieldActivatedTime + Shield.DURATION) {
				motorBiker.shieldActivated = false;
				paintableList.remove(motorBiker.shieldEffectSkill);
			}
			if(motorBiker.sideBladeActivated == true && currentTime > motorBiker.sideBladeActivatedTime + SideBlade.DURATION) {
				motorBiker.sideBladeActivated = false;
				collideableList.remove(motorBiker.sideBladeSkill.sideBlade1);
				collideableList.remove(motorBiker.sideBladeSkill.sideBlade2);
				paintableList.remove(motorBiker.sideBladeSkill.sideBlade1);
				paintableList.remove(motorBiker.sideBladeSkill.sideBlade2);
			}
			if(motorBiker.reverseActivated == true && currentTime > motorBiker.reverseActivatedTime + Reverse.DURATION) {
				motorBiker.reverseActivated = false;
				int tmp = motorBiker.leftButton;
				motorBiker.leftButton = motorBiker.rightButton;
				motorBiker.rightButton = tmp;
				tmp = motorBiker.upButton;
				motorBiker.upButton = motorBiker.downButton;
				motorBiker.downButton = tmp;
			}
		}
	}
	
	public void activatePowerUp() {
		for(MotorBiker motorBiker : motorBikerList) {
			if(keyExists(motorBiker.keyPressedList, motorBiker.powerUp1Button)) {
				motorBiker.activatePowerUp(motorBiker.powerUp1);
				motorBiker.powerUp1 = null;
			}
			if(keyExists(motorBiker.keyPressedList, motorBiker.powerUp2Button)) {
				motorBiker.activatePowerUp(motorBiker.powerUp2);
				motorBiker.powerUp2 = null;
			}
			if(motorBiker.powerUp1 instanceof Reverse) {
				motorBiker.activatePowerUp(motorBiker.powerUp1);
				motorBiker.powerUp1 = null;
			}
			if(motorBiker.powerUp2 instanceof Reverse) {
				motorBiker.activatePowerUp(motorBiker.powerUp2);
				motorBiker.powerUp2 = null;
			}
		}
	}
	
	public void removeKey(List<Integer> keyPressedList, int keyCode) {
		int index = 0;
		while (keyExists(keyPressedList, keyCode)) {
			if(keyPressedList.get(index) == keyCode) {
				keyPressedList.remove(keyPressedList.get(index));
			} else {
				index++;
			}
		}
	}

	public boolean keyExists(List<Integer> keyPressedList, int keyCode) {
		for(int key : keyPressedList) {
			if(key == keyCode) {
				return true;
			}
		}
		return false;
	}
	
    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
    
	private static Random rand = new Random();
    public static int randInt(int min, int max) {
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    
    public Arena getArena() {
    	return arena;
    }
    
    public MotorBiker getMotorBiker1() {
    	return motorBiker1;
    }
    
    public MotorBiker getMotorBiker2() {
    	return motorBiker2;
    }
}
