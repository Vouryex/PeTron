package states.game.powerups;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import states.game.interfaces.Paintable;
import states.game.motorbiker.MotorBiker;
import engine.utilities.Animation;

public class ShieldEffect implements Paintable {
	public int width;
	public int length;

	public MotorBiker motorBiker;
	public BufferedImage shieldEffectSheetHorizontal;
	public BufferedImage[] shieldEffectSpriteHorizontal;
	public Animation shieldEffectAnimHorizontal;
	
	public BufferedImage shieldEffectSheetVertical;
	public BufferedImage[] shieldEffectSpriteVertical;
	public Animation shieldEffectAnimVertical;
	
	public ShieldEffect(MotorBiker motorBiker) {
		this.motorBiker = motorBiker;
		width = 17;
		length = 22;
		
		/*1*/
		try {
			shieldEffectSheetHorizontal = ImageIO.read((getClass().getResourceAsStream("/images/powerups/ShieldEffect Spritesheet.png")));
			shieldEffectSheetVertical = ImageIO.read((getClass().getResourceAsStream("/images/powerups/ShieldEffect2 Sprite Sheet.png")));
		} catch(IOException e) {
			e.printStackTrace();
		}
		/**/
		
		
		/**Shield Animation**/
		/*2*/
		try {
			shieldEffectSpriteHorizontal = new BufferedImage[17];
			shieldEffectSpriteVertical = new BufferedImage[17];
			for(int i = 0; i < shieldEffectSpriteHorizontal.length; i++) {
				shieldEffectSpriteHorizontal[i] = shieldEffectSheetHorizontal.getSubimage(i * 22, 0, 22, 17);
			}
			for(int i = 0; i < shieldEffectSpriteVertical.length; i++) {
				shieldEffectSpriteVertical[i] = shieldEffectSheetVertical.getSubimage(i * 17, 0, 17, 22);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		shieldEffectAnimHorizontal = new Animation();
		shieldEffectAnimHorizontal.setFrames(shieldEffectSpriteHorizontal);
		shieldEffectAnimHorizontal.setDelay(100);
		
		shieldEffectAnimVertical = new Animation();
		shieldEffectAnimVertical.setFrames(shieldEffectSpriteVertical);
		shieldEffectAnimVertical.setDelay(100);
		/**/
		/*******************************/
	}
	
	@Override
	public void paint(Graphics2D g) {
		/*Paint Shield*/
		/*3*/
		if(motorBiker.getDirection() == MotorBiker.DIRECTION_LEFT) {
			g.drawImage(shieldEffectAnimHorizontal.getImage(), motorBiker.getxPos() - (MotorBiker.getHEIGHT() - MotorBiker.getWIDTH()) - (length / 2) + (MotorBiker.getWIDTH()), motorBiker.getyPos() - (width / 2) + (MotorBiker.getWIDTH() / 2), length, width, null);
			shieldEffectAnimHorizontal.update();
		} else if(motorBiker.getDirection() == MotorBiker.DIRECTION_RIGHT) {
			g.drawImage(shieldEffectAnimHorizontal.getImage(), motorBiker.getxPos() - (length / 2) + (MotorBiker.getWIDTH()), motorBiker.getyPos() - (width / 2) + (MotorBiker.getWIDTH() / 2), length, width, null);
			shieldEffectAnimHorizontal.update();
		} else if(motorBiker.getDirection() == MotorBiker.DIRECTION_UP) {
			g.drawImage(shieldEffectAnimVertical.getImage(), motorBiker.getxPos() - (MotorBiker.getWIDTH()/4) - (width / 2) + (motorBiker.getWIDTH()/2), motorBiker.getyPos() - (MotorBiker.getHEIGHT() - MotorBiker.getWIDTH()) - (width / 2) + (MotorBiker.getWIDTH() / 2), width, length, null);
			shieldEffectAnimVertical.update();
		} else {
			g.drawImage(shieldEffectAnimVertical.getImage(), motorBiker.getxPos() - (MotorBiker.getWIDTH()/4) - (width / 2) + (motorBiker.getWIDTH()/2), motorBiker.getyPos() - (width / 2) + (MotorBiker.getWIDTH() / 2), width, length, null);
			shieldEffectAnimVertical.update();
		}
		/**/
	}
}
