package states.game.motorbiker;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import states.game.Game;
import states.game.interfaces.Collideable;
import states.game.interfaces.Movable;
import states.game.interfaces.Paintable;
import states.game.powerups.PowerUp;
import states.game.powerups.Reverse;
import states.game.powerups.Shield;
import states.game.powerups.ShieldEffect;
import states.game.powerups.SideBlade;
import states.game.powerups.SideBladeSkill;
import engine.utilities.Animation;
import engine.utilities.Sound;

public class MotorBiker implements Collideable, Paintable, Movable {
	public static int WIDTH = 8;
	public static int HEIGHT = 15;
	public static final int SPEED_INITIAL = 1;
	public static final int SPEED_STOP = 0;
	public static final int DIRECTION_UP = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_LEFT = 3;
	public static final int DIRECTION_RIGHT = 4;
	
	/**Movement Fields**/
	public int xPos;
	public int yPos;
	public int speed;
	public int xUpdate;
	public int yUpdate;
	public int direction;
	/*******************/
	
	/**Key Press Fields**/
	public int upButton;
	public int downButton;
	public int leftButton;
	public int rightButton;
	public int powerUp1Button;
	public int powerUp2Button;
	public long keyPressTime;
	/****************/
	
	/**Power Up Fields*/
	public PowerUp powerUp1;
	public PowerUp powerUp2;
	public boolean shieldActivated;
	public boolean sideBladeActivated;
	public boolean reverseActivated;
	public long shieldActivatedTime;
	public long sideBladeActivatedTime;
	public long reverseActivatedTime;
	public SideBladeSkill sideBladeSkill;
	public ShieldEffect shieldEffectSkill;
	/******************/
	
	/**Motor biker images**/
	public BufferedImage leftImage;
	public BufferedImage rightImage;
	public BufferedImage upImage;
	public BufferedImage downImage;
	public BufferedImage[] leftImageFrames;
	public BufferedImage[] rightImageFrames;
	public BufferedImage[] upImageFrames;
	public BufferedImage[] downImageFrames;
	/**********************/
	
	/**Motor biker explode**/
	public BufferedImage explodeHorizontal; // direction is left
	public BufferedImage explodeHorizontal2; // direction is right
	public BufferedImage explodeVertical; // direction is up
	public BufferedImage explodeVertical2; // direction is down
	public BufferedImage[] explodeHorizontalFrames; // direction is left
	public BufferedImage[] explodeHorizontalFrames2; // direction is right
	public BufferedImage[] explodeVerticalFrames; // direction is up
	public BufferedImage[] explodeVerticalFrames2; // direction is down
	/***********************/
	
	/**Motor biker game appearance animation**/
	public Animation showLeftAnim;
	public Animation showRightAnim;
	public Animation showUpAnim;
	public Animation showDownAnim;
	/*****************************************/
	
	/**Key Lists**/
	public List<Integer> pendingKeyList;
	public List<Integer> keyPressedList;
	/*************/
	
	public Color color;
	public Game game = Game.getInstance();
	public long statusCurrentTime;
	
	public MotorBiker(int xPos, int yPos, int direction, Color color, 
					  int upButton, int downButton, int leftButton, int rightButton,
					  int powerUp1Button, int powerUp2Button) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.direction = direction;
		speed = SPEED_INITIAL;
		xUpdate = initXUpdate(direction);
		yUpdate = initYUpdate(direction);
		this.color = color;
		this.upButton = upButton;
		this.downButton = downButton;
		this.leftButton = leftButton;
		this.rightButton = rightButton;
		this.powerUp1Button = powerUp1Button;
		this.powerUp2Button = powerUp2Button;
		keyPressTime = 0;
		powerUp1 = null;
		powerUp2 = null;
		shieldActivated = false;
		sideBladeActivated = false;
		
		/**Load Images**/
		try {
			/**Motor Biker**/
			if(color == Color.YELLOW) {
				leftImage = ImageIO.read((getClass().getResourceAsStream("/images/motorbiker/MotorBiker_left.png")));
				rightImage = ImageIO.read((getClass().getResourceAsStream("/images/motorbiker/MotorBiker_right.png")));
				upImage = ImageIO.read((getClass().getResourceAsStream("/images/motorbiker/MotorBiker_up.png")));
				downImage = ImageIO.read((getClass().getResourceAsStream("/images/motorbiker/MotorBiker_down.png")));
			} else if(color == Color.MAGENTA) {
				leftImage = ImageIO.read((getClass().getResourceAsStream("/images/motorbiker/MotorBiker2_left.png")));
				rightImage = ImageIO.read((getClass().getResourceAsStream("/images/motorbiker/MotorBiker2_right.png")));
				upImage = ImageIO.read((getClass().getResourceAsStream("/images/motorbiker/MotorBiker2_up.png")));
				downImage = ImageIO.read((getClass().getResourceAsStream("/images/motorbiker/MotorBiker2_down.png")));
			}
			/***************/
			
			/**Explosions**/
			explodeHorizontal = ImageIO.read((getClass().getResourceAsStream("/images/motorbiker/motorbiker explode1.png")));
			explodeHorizontal2 = ImageIO.read((getClass().getResourceAsStream("/images/motorbiker/motorbiker explode1.png")));
			explodeVertical = ImageIO.read((getClass().getResourceAsStream("/images/motorbiker/motorbiker explode2.png")));
			explodeVertical2 = ImageIO.read((getClass().getResourceAsStream("/images/motorbiker/motorbiker explode2.png")));
			/**************/
		} catch(IOException e) {
			e.printStackTrace();
		}
		/**************/
		
		/**Load Animation**/
		try {
			/**Load motor biker image sprite**/
			leftImageFrames = new BufferedImage[1];
			leftImageFrames[0] = leftImage;
			rightImageFrames = new BufferedImage[1];
			rightImageFrames[0] = rightImage;
			upImageFrames = new BufferedImage[1];
			upImageFrames[0] = upImage;
			downImageFrames = new BufferedImage[1];
			downImageFrames[0] = downImage;	
			/*********************************/
			
			/**Load motor biker explode image sprite**/
			explodeHorizontalFrames = new BufferedImage[2];
			explodeHorizontalFrames[0] = leftImage;
			explodeHorizontalFrames[1] = explodeHorizontal;
			
			explodeHorizontalFrames2 = new BufferedImage[2];
			explodeHorizontalFrames2[0] = rightImage;
			explodeHorizontalFrames2[1] = explodeHorizontal;
			
			explodeVerticalFrames = new BufferedImage[2];
			explodeVerticalFrames[0] = upImage;
			explodeVerticalFrames[1] = explodeVertical;
			
			explodeVerticalFrames2 = new BufferedImage[2];
			explodeVerticalFrames2[0] = downImage;
			explodeVerticalFrames2[1] = explodeVertical;
			/*****************************************/
		} catch(Exception e) {
			e.printStackTrace();
		}
		/**Initialize motor biker animation**/
		showLeftAnim = new Animation();
		showLeftAnim.setFrames(leftImageFrames);
		showLeftAnim.setDelay(-1);
		
		showRightAnim = new Animation();
		showRightAnim.setFrames(rightImageFrames);
		showRightAnim.setDelay(-1);
		
		showUpAnim = new Animation();
		showUpAnim.setFrames(upImageFrames);
		showUpAnim.setDelay(-1);
		
		showDownAnim = new Animation();
		showDownAnim.setFrames(downImageFrames);
		showDownAnim.setDelay(-1);
		/************************************/
		
		
		/******************/
		
		/**Initialize Lists**/
		pendingKeyList = new ArrayList<Integer>();
		keyPressedList = new ArrayList<Integer>();
		/*******************/
	}
	
	public void addPowerUp(PowerUp powerUp) {
		if(powerUp1 == null) {
			powerUp1 = powerUp;
		} else if(powerUp2 == null) {
			powerUp2 = powerUp;
		} else {
			powerUp1 = powerUp2;
			powerUp2 = powerUp;
		}
	}
	
	public void activatePowerUp(PowerUp powerUp) {
		if(powerUp instanceof Shield) {
			Sound.shieldactivate.play();
			shieldActivated = true;
			shieldActivatedTime = System.currentTimeMillis();
			if(shieldEffectSkill != null) {
				game.paintableList.remove(shieldEffectSkill);
			}
			shieldEffectSkill = new ShieldEffect(this);
			game.paintableList.add(shieldEffectSkill);
		} else if(powerUp instanceof SideBlade) {
			Sound.sidebladeactivate.play();
			sideBladeActivated = true;
			sideBladeActivatedTime = System.currentTimeMillis();
			if(sideBladeSkill != null) {
				game.paintableList.remove(sideBladeSkill.sideBlade1);
				game.paintableList.remove(sideBladeSkill.sideBlade2);
				game.collideableList.remove(sideBladeSkill.sideBlade1);
				game.collideableList.remove(sideBladeSkill.sideBlade2);
				sideBladeSkill = null;
			}
			sideBladeSkill = new SideBladeSkill(this);
			game.paintableList.add(sideBladeSkill.sideBlade1);
			game.paintableList.add(sideBladeSkill.sideBlade2);
			game.collideableList.add(sideBladeSkill.sideBlade1);
			game.collideableList.add(sideBladeSkill.sideBlade2);
		} else if(powerUp instanceof Reverse) {
			if(!reverseActivated) {
				int tmp = leftButton;
				leftButton = rightButton;
				rightButton = tmp;
				tmp = upButton;
				upButton = downButton;
				downButton = tmp;
			}
			reverseActivated = true;
			reverseActivatedTime = System.currentTimeMillis();
		}
	}
	
	public void imageExplode() {
		if(direction == DIRECTION_LEFT) {
			showLeftAnim.setFrames(explodeHorizontalFrames);
			showLeftAnim.setDelay(500);
		} else if(direction == DIRECTION_RIGHT) {
			showRightAnim.setFrames(explodeHorizontalFrames2);
			showRightAnim.setDelay(500);
		} else if(direction == DIRECTION_UP) {
			showUpAnim.setFrames(explodeVerticalFrames);
			showUpAnim.setDelay(500);
		} else {
			showDownAnim.setFrames(explodeVerticalFrames2);
			showDownAnim.setDelay(500);
		}
	}
	
	/**Collideable Methods**/
	public boolean collision(Collideable collideObj) {
		if(direction == DIRECTION_UP) {
			return yPos < collideObj.getDownBound() && yPos > collideObj.getUpBound() &&
				  (xPos < collideObj.getRightBound() && xPos >= collideObj.getLeftBound() ||
				   xPos + WIDTH > collideObj.getLeftBound() && xPos + WIDTH <= collideObj.getRightBound());
		} else if(direction == DIRECTION_DOWN) {
			return yPos + HEIGHT > collideObj.getUpBound() && yPos + HEIGHT < collideObj.getDownBound() &&
				  (xPos < collideObj.getRightBound() && xPos >= collideObj.getLeftBound() ||
				   xPos + WIDTH > collideObj.getLeftBound() && xPos + WIDTH <= collideObj.getRightBound());
		} else if(direction == DIRECTION_LEFT) {
			return xPos > collideObj.getLeftBound() && xPos < collideObj.getRightBound() &&
				  (yPos >= collideObj.getUpBound() && yPos < collideObj.getDownBound() ||
				   yPos + HEIGHT > collideObj.getUpBound() && yPos + HEIGHT <= collideObj.getDownBound());
		} else { 
			return xPos + WIDTH > collideObj.getLeftBound() && xPos + WIDTH < collideObj.getRightBound() &&
				  (yPos >= collideObj.getUpBound() && yPos < collideObj.getDownBound() ||
				   yPos + HEIGHT > collideObj.getUpBound() && yPos + HEIGHT <= collideObj.getDownBound());
		}
	}
	
	public int getUpBound() {
		if(direction == DIRECTION_LEFT || direction == DIRECTION_RIGHT || direction == DIRECTION_DOWN) {
			return yPos;	
		} else {
			return yPos - (HEIGHT - WIDTH);
		}
		
	}
	
	public int getDownBound() {
		if(direction == DIRECTION_LEFT || direction == DIRECTION_RIGHT || direction == DIRECTION_UP) {
			return yPos + WIDTH;	
		} else {
			return yPos + HEIGHT;
		}
	}
	
	public int getLeftBound() {
		if(direction == DIRECTION_UP || direction == DIRECTION_DOWN) {
			return xPos - (WIDTH/4);	
		} else if(direction == DIRECTION_RIGHT) {
			return xPos;
		} else {
			return xPos - (HEIGHT - WIDTH);
		}
	}
	
	public int getRightBound() {
		if(direction == DIRECTION_UP || direction == DIRECTION_DOWN) {
			return xPos - (WIDTH/4) + WIDTH;	
		} else if(direction == DIRECTION_LEFT) {
			return xPos + WIDTH;
		} else {
			return xPos + HEIGHT;
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(xPos, yPos, WIDTH, HEIGHT);
	}
	/***********************/
	
	public void move() {
		/*Update Direction*/
		if(direction != DIRECTION_RIGHT && game.keyExists(keyPressedList, leftButton)) {
			xUpdate = -speed;
			yUpdate = SPEED_STOP;
			direction = DIRECTION_LEFT;
		}
		if(direction != DIRECTION_LEFT && game.keyExists(keyPressedList,rightButton)) {
			xUpdate = speed;
			yUpdate = SPEED_STOP;
			direction = DIRECTION_RIGHT;
		}
		if(direction != DIRECTION_DOWN && game.keyExists(keyPressedList, upButton)) {
			xUpdate = SPEED_STOP;
			yUpdate = -speed;
			direction = DIRECTION_UP;
		}
		if(direction != DIRECTION_UP && game.keyExists(keyPressedList, downButton)) {
			xUpdate = SPEED_STOP;
			yUpdate = speed;
			direction = DIRECTION_DOWN;
		}
		/******************/
		
		/**Create Trail**/
		Trail newTrail;
		if(direction == DIRECTION_LEFT) {
			newTrail = new Trail((4*(WIDTH/16))+xPos, ((4*(WIDTH)/16))+yPos, WIDTH/2, WIDTH/2, color);
		} else if(direction == DIRECTION_RIGHT) {
			newTrail = new Trail((4*(WIDTH/16))+xPos, ((4*(WIDTH)/16))+yPos, WIDTH/2, WIDTH/2, color);
		} else if(direction == DIRECTION_UP) {
			newTrail = new Trail((4*(WIDTH/16))+xPos, ((4*(WIDTH)/16))+yPos, WIDTH/2, WIDTH/2, color);
		} else {
			newTrail = new Trail((4*(WIDTH/16))+xPos, ((4*(WIDTH)/16))+yPos, WIDTH/2, WIDTH/2, color);
		}
		game.collideableList.add(newTrail);
		game.paintableList.add(newTrail);
		/***************/
		
		xPos += xUpdate;
		yPos += yUpdate;
		
		statusCurrentTime = System.currentTimeMillis();
	}
	
	public void paint(Graphics2D g) {
		/*Paint Motor biker*/
		if(direction == DIRECTION_LEFT) {
			g.drawImage(showLeftAnim.getImage(), xPos - (HEIGHT - WIDTH), yPos, HEIGHT, WIDTH, null);
			showLeftAnim.update();
		} else if(direction == DIRECTION_RIGHT) {
			g.drawImage(showRightAnim.getImage(), xPos, yPos, HEIGHT, WIDTH, null);
			showRightAnim.update();
		} else if(direction == DIRECTION_UP) {
			g.drawImage(showUpAnim.getImage(), xPos - (WIDTH/4), yPos - (HEIGHT - WIDTH), WIDTH, HEIGHT, null);
			showUpAnim.update();
		} else {
			g.drawImage(showDownAnim.getImage(), xPos - (WIDTH/4), yPos, WIDTH, HEIGHT, null);
			showDownAnim.update();
		}
		/******************/
	}
	
	public int initXUpdate(int direction) {
		if(direction == DIRECTION_UP || direction == DIRECTION_DOWN) {
			return SPEED_STOP;
		} else if(direction == DIRECTION_LEFT){
			return -speed;
		} else { 
			return speed;
		}
	}
	
	public int initYUpdate(int direction) {
		if(direction == DIRECTION_LEFT || direction == DIRECTION_RIGHT) {
			return SPEED_STOP;
		} else if(direction == DIRECTION_UP){
			return -speed;
		} else {
			return speed;
		}
	}
	
	public boolean hasKey(int keyCode) {
		return keyCode == upButton || keyCode == downButton || keyCode == leftButton || 
			   keyCode == rightButton || keyCode == powerUp1Button || keyCode == powerUp2Button;
	}
}
