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
	private static int WIDTH = 8;
	private static int HEIGHT = 15;
	private static final int SPEED_INITIAL = 1;
	private static final int SPEED_STOP = 0;
	public  static final int DIRECTION_UP = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_LEFT = 3;
	public static final int DIRECTION_RIGHT = 4;

	private int xPos;
	private int yPos;
	private int speed;
	private int xUpdate;
	private int yUpdate;
	private int direction;

	private int upButton;
	private int downButton;
	private int leftButton;
	private int rightButton;
	private int powerUp1Button;
	private int powerUp2Button;
	private long keyPressTime;

	private PowerUp powerUp1;
	private PowerUp powerUp2;
	private boolean shieldActivated;
	private boolean sideBladeActivated;
	private boolean reverseActivated;
	private long shieldActivatedTime;
	private long sideBladeActivatedTime;
	private long reverseActivatedTime;
	private SideBladeSkill sideBladeSkill;
	private ShieldEffect shieldEffectSkill;

	private BufferedImage leftImage;
	private BufferedImage rightImage;
	private BufferedImage upImage;
	private BufferedImage downImage;
	private BufferedImage[] leftImageFrames;
	private BufferedImage[] rightImageFrames;
	private BufferedImage[] upImageFrames;
	private BufferedImage[] downImageFrames;

	private BufferedImage explodeHorizontal;
	private BufferedImage explodeVertical;
	private BufferedImage[] explodeHorizontalFrames; // direction is left
	private BufferedImage[] explodeHorizontalFrames2; // direction is right
	private BufferedImage[] explodeVerticalFrames; // direction is up
	private BufferedImage[] explodeVerticalFrames2; // direction is down

	private Animation showLeftAnim;
	private Animation showRightAnim;
	private Animation showUpAnim;
	private Animation showDownAnim;

	public List<Integer> pendingKeyList;
	public List<Integer> keyPressedList;
	
	public Color color;
	public Game game = Game.getInstance();
	public long statusCurrentTime;
	
	public MotorBiker(int xPos, int yPos, int direction, Color color, 
					  int upButton, int downButton, int leftButton,
					  int rightButton, int powerUp1Button, int powerUp2Button) {
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
		
		initImages();
		initSprites();
		initAnimation();

		pendingKeyList = new ArrayList<>();
		keyPressedList = new ArrayList<>();
	}

	private void initImages() {
		motorbikerImages();
		explosionImages();
	}

	private void motorbikerImages() {
		try {
			if(color == Color.YELLOW) {
				leftImage = ImageIO.read((getClass().getResourceAsStream(
						"/images/motorbiker/MotorBiker_left.png")));
				rightImage = ImageIO.read((getClass().getResourceAsStream(
						"/images/motorbiker/MotorBiker_right.png")));
				upImage = ImageIO.read((getClass().getResourceAsStream(
						"/images/motorbiker/MotorBiker_up.png")));
				downImage = ImageIO.read((getClass().getResourceAsStream(
						"/images/motorbiker/MotorBiker_down.png")));
			} else if(color == Color.MAGENTA) {
				leftImage = ImageIO.read((getClass().getResourceAsStream(
						"/images/motorbiker/MotorBiker2_left.png")));
				rightImage = ImageIO.read((getClass().getResourceAsStream(
						"/images/motorbiker/MotorBiker2_right.png")));
				upImage = ImageIO.read((getClass().getResourceAsStream(
						"/images/motorbiker/MotorBiker2_up.png")));
				downImage = ImageIO.read((getClass().getResourceAsStream(
						"/images/motorbiker/MotorBiker2_down.png")));
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void explosionImages() {
		try {
			explodeHorizontal = ImageIO.read((getClass().getResourceAsStream(
					"/images/motorbiker/motorbiker explode1.png")));
			explodeVertical = ImageIO.read((getClass().getResourceAsStream(
					"/images/motorbiker/motorbiker explode2.png")));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void initSprites() {
		motorbikerSprites();
		explodeSprites();
	}

	private void motorbikerSprites() {
		try {
			leftImageFrames = new BufferedImage[1];
			leftImageFrames[0] = leftImage;
			rightImageFrames = new BufferedImage[1];
			rightImageFrames[0] = rightImage;
			upImageFrames = new BufferedImage[1];
			upImageFrames[0] = upImage;
			downImageFrames = new BufferedImage[1];
			downImageFrames[0] = downImage;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void explodeSprites() {
		try {
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
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void initAnimation() {
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
			activateShield();
		} else if(powerUp instanceof SideBlade) {
			activateSideBlade();
		} else if(powerUp instanceof Reverse) {
			activateReverse();
		}
	}

	private void activateShield() {
		Sound.shieldactivate.play();
		shieldActivated = true;
		shieldActivatedTime = System.currentTimeMillis();
		if(shieldEffectSkill != null) {
			game.paintableList.remove(shieldEffectSkill);
		}
		shieldEffectSkill = new ShieldEffect(this);
		game.paintableList.add(shieldEffectSkill);
	}

	private void activateSideBlade() {
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
	}

	private void activateReverse() {
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

	public boolean collision(Collideable collideObj) {
		if(direction == DIRECTION_UP) {
			return yPos < collideObj.getDownBound()
					&& yPos > collideObj.getUpBound()
					&& (xPos < collideObj.getRightBound()
					&& xPos >= collideObj.getLeftBound()
					|| xPos + WIDTH > collideObj.getLeftBound()
					&& xPos + WIDTH <= collideObj.getRightBound());
		} else if(direction == DIRECTION_DOWN) {
			return yPos + HEIGHT > collideObj.getUpBound()
					&& yPos + HEIGHT < collideObj.getDownBound()
					&& (xPos < collideObj.getRightBound()
					&& xPos >= collideObj.getLeftBound()
					|| xPos + WIDTH > collideObj.getLeftBound()
					&& xPos + WIDTH <= collideObj.getRightBound());
		} else if(direction == DIRECTION_LEFT) {
			return xPos > collideObj.getLeftBound()
					&& xPos < collideObj.getRightBound()
					&& (yPos >= collideObj.getUpBound()
					&& yPos < collideObj.getDownBound()
					|| yPos + HEIGHT > collideObj.getUpBound()
					&& yPos + HEIGHT <= collideObj.getDownBound());
		} else { 
			return xPos + WIDTH > collideObj.getLeftBound()
					&& xPos + WIDTH < collideObj.getRightBound()
					&& (yPos >= collideObj.getUpBound()
					&& yPos < collideObj.getDownBound()
					|| yPos + HEIGHT > collideObj.getUpBound()
					&& yPos + HEIGHT <= collideObj.getDownBound());
		}
	}
	
	public int getUpBound() {
		if(direction == DIRECTION_LEFT || direction == DIRECTION_RIGHT
				|| direction == DIRECTION_DOWN) {
			return yPos;	
		} else {
			return yPos - (HEIGHT - WIDTH);
		}
		
	}
	
	public int getDownBound() {
		if(direction == DIRECTION_LEFT || direction == DIRECTION_RIGHT
				|| direction == DIRECTION_UP) {
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
	
	public void move() {
		updateDirection();
		createTrail();
		updatePosition();
		statusCurrentTime = System.currentTimeMillis();
	}

	private void updateDirection() {
		if(direction != DIRECTION_RIGHT
				&& game.keyExists(keyPressedList, leftButton)) {
			xUpdate = -speed;
			yUpdate = SPEED_STOP;
			direction = DIRECTION_LEFT;
		}
		if(direction != DIRECTION_LEFT
				&& game.keyExists(keyPressedList,rightButton)) {
			xUpdate = speed;
			yUpdate = SPEED_STOP;
			direction = DIRECTION_RIGHT;
		}
		if(direction != DIRECTION_DOWN
				&& game.keyExists(keyPressedList, upButton)) {
			xUpdate = SPEED_STOP;
			yUpdate = -speed;
			direction = DIRECTION_UP;
		}
		if(direction != DIRECTION_UP
				&& game.keyExists(keyPressedList, downButton)) {
			xUpdate = SPEED_STOP;
			yUpdate = speed;
			direction = DIRECTION_DOWN;
		}
	}

	private void createTrail() {
		Trail newTrail;
		if(direction == DIRECTION_LEFT) {
			newTrail = new Trail((4*(WIDTH/16))+xPos,
					((4*(WIDTH)/16))+yPos, WIDTH/2, WIDTH/2, color);
		} else if(direction == DIRECTION_RIGHT) {
			newTrail = new Trail((4*(WIDTH/16))+xPos,
					((4*(WIDTH)/16))+yPos, WIDTH/2, WIDTH/2, color);
		} else if(direction == DIRECTION_UP) {
			newTrail = new Trail((4*(WIDTH/16))+xPos,
					((4*(WIDTH)/16))+yPos, WIDTH/2, WIDTH/2, color);
		} else {
			newTrail = new Trail((4*(WIDTH/16))+xPos,
					((4*(WIDTH)/16))+yPos, WIDTH/2, WIDTH/2, color);
		}
		game.collideableList.add(newTrail);
		game.paintableList.add(newTrail);
	}

	private void updatePosition() {
		xPos += xUpdate;
		yPos += yUpdate;
	}
	
	public void paint(Graphics2D g) {
		if(direction == DIRECTION_LEFT) {
			g.drawImage(showLeftAnim.getImage(), xPos - (HEIGHT - WIDTH),
					yPos, HEIGHT, WIDTH, null);
			showLeftAnim.update();
		} else if(direction == DIRECTION_RIGHT) {
			g.drawImage(showRightAnim.getImage(), xPos, yPos,
					HEIGHT, WIDTH, null);
			showRightAnim.update();
		} else if(direction == DIRECTION_UP) {
			g.drawImage(showUpAnim.getImage(), xPos - (WIDTH/4),
					yPos - (HEIGHT - WIDTH), WIDTH, HEIGHT, null);
			showUpAnim.update();
		} else {
			g.drawImage(showDownAnim.getImage(), xPos - (WIDTH/4),
					yPos, WIDTH, HEIGHT, null);
			showDownAnim.update();
		}
	}
	
	private int initXUpdate(int direction) {
		if(direction == DIRECTION_UP || direction == DIRECTION_DOWN) {
			return SPEED_STOP;
		} else if(direction == DIRECTION_LEFT){
			return -speed;
		} else { 
			return speed;
		}
	}
	
	private int initYUpdate(int direction) {
		if(direction == DIRECTION_LEFT || direction == DIRECTION_RIGHT) {
			return SPEED_STOP;
		} else if(direction == DIRECTION_UP){
			return -speed;
		} else {
			return speed;
		}
	}
	
	public boolean hasKey(int keyCode) {
		return keyCode == upButton || keyCode == downButton
				|| keyCode == leftButton || keyCode == rightButton
				|| keyCode == powerUp1Button || keyCode == powerUp2Button;
	}

	public static int getWIDTH() {
		return WIDTH;
	}

	public static void setWIDTH(int WIDTH) {
		MotorBiker.WIDTH = WIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}

	public static void setHEIGHT(int HEIGHT) {
		MotorBiker.HEIGHT = HEIGHT;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getUpButton() {
		return upButton;
	}

	public void setUpButton(int upButton) {
		this.upButton = upButton;
	}

	public int getDownButton() {
		return downButton;
	}

	public void setDownButton(int downButton) {
		this.downButton = downButton;
	}

	public int getLeftButton() {
		return leftButton;
	}

	public void setLeftButton(int leftButton) {
		this.leftButton = leftButton;
	}

	public int getRightButton() {
		return rightButton;
	}

	public void setRightButton(int rightButton) {
		this.rightButton = rightButton;
	}

	public int getPowerUp1Button() {
		return powerUp1Button;
	}

	public void setPowerUp1Button(int powerUp1Button) {
		this.powerUp1Button = powerUp1Button;
	}

	public int getPowerUp2Button() {
		return powerUp2Button;
	}

	public void setPowerUp2Button(int powerUp2Button) {
		this.powerUp2Button = powerUp2Button;
	}

	public long getKeyPressTime() {
		return keyPressTime;
	}

	public void setKeyPressTime(long keyPressTime) {
		this.keyPressTime = keyPressTime;
	}

	public PowerUp getPowerUp1() {
		return powerUp1;
	}

	public void setPowerUp1(PowerUp powerUp1) {
		this.powerUp1 = powerUp1;
	}

	public PowerUp getPowerUp2() {
		return powerUp2;
	}

	public void setPowerUp2(PowerUp powerUp2) {
		this.powerUp2 = powerUp2;
	}

	public boolean isShieldActivated() {
		return shieldActivated;
	}

	public void setShieldActivated(boolean shieldActivated) {
		this.shieldActivated = shieldActivated;
	}

	public boolean isSideBladeActivated() {
		return sideBladeActivated;
	}

	public void setSideBladeActivated(boolean sideBladeActivated) {
		this.sideBladeActivated = sideBladeActivated;
	}

	public boolean isReverseActivated() {
		return reverseActivated;
	}

	public void setReverseActivated(boolean reverseActivated) {
		this.reverseActivated = reverseActivated;
	}

	public long getShieldActivatedTime() {
		return shieldActivatedTime;
	}

	public void setShieldActivatedTime(long shieldActivatedTime) {
		this.shieldActivatedTime = shieldActivatedTime;
	}

	public long getSideBladeActivatedTime() {
		return sideBladeActivatedTime;
	}

	public void setSideBladeActivatedTime(long sideBladeActivatedTime) {
		this.sideBladeActivatedTime = sideBladeActivatedTime;
	}

	public long getReverseActivatedTime() {
		return reverseActivatedTime;
	}

	public void setReverseActivatedTime(long reverseActivatedTime) {
		this.reverseActivatedTime = reverseActivatedTime;
	}

	public SideBladeSkill getSideBladeSkill() {
		return sideBladeSkill;
	}

	public void setSideBladeSkill(SideBladeSkill sideBladeSkill) {
		this.sideBladeSkill = sideBladeSkill;
	}

	public ShieldEffect getShieldEffectSkill() {
		return shieldEffectSkill;
	}

	public void setShieldEffectSkill(ShieldEffect shieldEffectSkill) {
		this.shieldEffectSkill = shieldEffectSkill;
	}
}
