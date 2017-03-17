package states.game.powerups;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import states.game.gameinterfaces.Collideable;
import states.game.gameinterfaces.Paintable;
import utilities.Animation;

public class PowerUp implements Collideable, Paintable {
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	public int xPos;
	public int yPos;
	public Color color;
	
	public BufferedImage shield;
	public BufferedImage[] shieldSprite;
	public Animation shieldAnim;
	
	public BufferedImage sideBlade;
	public BufferedImage[] sideBladeSprite;
	public Animation sideBladeAnim;
	
	public BufferedImage reverse;
	public BufferedImage[] reverseSprite;
	public Animation reverseAnim;
	
	public Image statusImage;
	
	public PowerUp(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public PowerUp(int xPos, int yPos, Color color) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.color = color;
		try {
			shield = ImageIO.read(getClass().getResourceAsStream("/res/images/powerups/PowerUpShield Spritesheet.png"));
			sideBlade = ImageIO.read(getClass().getResourceAsStream("/res/images/powerups/PowerUpSideBlade Spritesheet.png"));
			reverse = ImageIO.read(getClass().getResourceAsStream("/res/images/powerups/PowerUpReverse Spritesheet.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		
		try {
			shieldSprite = new BufferedImage[10];
			for(int i = 0; i < shieldSprite.length; i++) {
				shieldSprite[i] = shield.getSubimage(i * WIDTH, 0, WIDTH, HEIGHT);
			}
			sideBladeSprite = new BufferedImage[10];
			for(int i = 0; i < sideBladeSprite.length; i++) {
				sideBladeSprite[i] = sideBlade.getSubimage(i * WIDTH, 0, WIDTH, HEIGHT);
			}
			reverseSprite = new BufferedImage[10];
			for(int i = 0; i < reverseSprite.length; i++) {
				reverseSprite[i] = reverse.getSubimage(i * WIDTH, 0, WIDTH, HEIGHT);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		shieldAnim = new Animation();
		shieldAnim.setFrames(shieldSprite);
		shieldAnim.setDelay(100);
		
		sideBladeAnim = new Animation();
		sideBladeAnim.setFrames(sideBladeSprite);
		sideBladeAnim.setDelay(100);
		
		reverseAnim = new Animation();
		reverseAnim.setFrames(reverseSprite);
		reverseAnim.setDelay(100);
	}

	public boolean collision(Collideable collideObj) {
		return collideObj.getBounds().intersects(getBounds());
	}

	public int getUpBound() {
		return yPos;
	}

	public int getDownBound() {
		return yPos + HEIGHT;
	}

	public int getLeftBound() {
		return xPos;
	}

	public int getRightBound() {
		return xPos + WIDTH;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(xPos, yPos, WIDTH, HEIGHT);
	}

	public void update() {
		shieldAnim.update();
		sideBladeAnim.update();
		reverseAnim.update();
	}
	
	public void paint(Graphics2D g) {
		if(this instanceof Shield) {
			g.drawImage(shieldAnim.getImage(), xPos, yPos, 10, 10, null);
			shieldAnim.update();
		} else if(this instanceof SideBlade) {
			g.drawImage(sideBladeAnim.getImage(), xPos, yPos, 10, 10, null);
			sideBladeAnim.update();
		} else {
			g.drawImage(reverseAnim.getImage(), xPos, yPos, 10, 10, null);
			reverseAnim.update();
		}
	}
	
	public void setStatusImage(Image statusImage) {
		this.statusImage = statusImage;
	}
}
