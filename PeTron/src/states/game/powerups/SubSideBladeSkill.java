package states.game.powerups;



import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import states.game.gameinterfaces.Collideable;
import states.game.gameinterfaces.Paintable;
import states.game.motorbiker.MotorBiker;

public class SubSideBladeSkill implements Collideable, Paintable {
	public static final int FIRST_ORIENTATION = 0;
	public static final int SECOND_ORIENTATION = 1;
	public static final int LENGTH = 35;
	public static final int WIDTH = 3;
	
	public MotorBiker motorBiker;
	public Color color;
	public int orientation;
	
	public SubSideBladeSkill(int orientation, MotorBiker motorBiker, Color color) {
		this.orientation = orientation;
		this.motorBiker = motorBiker;
		this.color = color;
	}
	
	public void paint(Graphics2D g) {
		g.setColor(color);
		if(orientation == FIRST_ORIENTATION) { 
			if(motorBiker.direction == MotorBiker.DIRECTION_UP) {
				g.fillRect(motorBiker.getLeftBound() - LENGTH, motorBiker.getUpBound(), LENGTH, WIDTH);
			} else if(motorBiker.direction == MotorBiker.DIRECTION_DOWN) {
				g.fillRect(motorBiker.getRightBound(), motorBiker.getDownBound() - WIDTH, LENGTH, WIDTH);
			} else if(motorBiker.direction == MotorBiker.DIRECTION_LEFT) {
				g.fillRect(motorBiker.getLeftBound(), motorBiker.getDownBound(), WIDTH, LENGTH);
			} else if(motorBiker.direction == MotorBiker.DIRECTION_RIGHT) {
				g.fillRect(motorBiker.getRightBound() - WIDTH, motorBiker.getUpBound() - LENGTH, WIDTH, LENGTH);
			}
		} else if(orientation == SECOND_ORIENTATION) {
			if(motorBiker.direction == MotorBiker.DIRECTION_UP) {
				g.fillRect(motorBiker.getRightBound(), motorBiker.getUpBound(), LENGTH, WIDTH);
			} else if(motorBiker.direction == MotorBiker.DIRECTION_DOWN) {
				g.fillRect(motorBiker.getLeftBound() - LENGTH, motorBiker.getDownBound() - WIDTH, LENGTH, WIDTH);
			} else if(motorBiker.direction == MotorBiker.DIRECTION_LEFT) {
				g.fillRect(motorBiker.getLeftBound(), motorBiker.getUpBound() - LENGTH, WIDTH, LENGTH);
			} else if(motorBiker.direction == MotorBiker.DIRECTION_RIGHT) {
				g.fillRect(motorBiker.getRightBound() - WIDTH, motorBiker.getDownBound(), WIDTH, LENGTH);
			}
		}
	}

	@Override
	public boolean collision(Collideable collideObj) {
		return collideObj.getBounds().intersects(getBounds());
	}

	@Override
	public int getUpBound() {
		return 0;
	}

	@Override
	public int getDownBound() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLeftBound() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRightBound() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Rectangle getBounds() {
		if(orientation == FIRST_ORIENTATION) { 
			if(motorBiker.direction == MotorBiker.DIRECTION_UP) {
				return new Rectangle(motorBiker.getLeftBound() - LENGTH, motorBiker.getUpBound(), LENGTH, WIDTH);
			} else if(motorBiker.direction == MotorBiker.DIRECTION_DOWN) {
				return new Rectangle(motorBiker.getRightBound(), motorBiker.getDownBound() - WIDTH, LENGTH, WIDTH);
			} else if(motorBiker.direction == MotorBiker.DIRECTION_LEFT) {
				return new Rectangle(motorBiker.getLeftBound(), motorBiker.getDownBound(), WIDTH, LENGTH);
			} else { // motorBiker.direction == MotorBiker.DIRECTION_RIGHT
				return new Rectangle(motorBiker.getRightBound() - WIDTH, motorBiker.getUpBound() - LENGTH, WIDTH, LENGTH);
			}
		} else { // orientation == SECOND_ORIENTATION
			if(motorBiker.direction == MotorBiker.DIRECTION_UP) {
				return new Rectangle(motorBiker.getRightBound(), motorBiker.getUpBound(), LENGTH, WIDTH);
			} else if(motorBiker.direction == MotorBiker.DIRECTION_DOWN) {
				return new Rectangle(motorBiker.getLeftBound() - LENGTH, motorBiker.getDownBound() - WIDTH, LENGTH, WIDTH);
			} else if(motorBiker.direction == MotorBiker.DIRECTION_LEFT) {
				return new Rectangle(motorBiker.getLeftBound(), motorBiker.getUpBound() - LENGTH, WIDTH, LENGTH);
			} else { // motorBiker.direction == MotorBiker.DIRECTION_RIGHT
				return new Rectangle(motorBiker.getRightBound() - WIDTH, motorBiker.getDownBound(), WIDTH, LENGTH);
			}
		}
	}
	
	
}
