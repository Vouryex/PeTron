package states.game.motorbiker;



import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import states.game.interfaces.Collideable;
import states.game.interfaces.Paintable;

public class Trail implements Collideable, Paintable {
	public int xPos;
	public int yPos;
	public int width;
	public int height;
	public Color color;
	
	public Trail(int xPos, int yPos, int width, int height, Color color) {
		this.xPos= xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public boolean collision(Collideable collideObj) {
		return false;
	}
	
	public int getUpBound() {
		return yPos;
	}
	public int getDownBound() {
		return yPos + height;
	}
	public int getLeftBound() {
		return xPos;
	}
	public int getRightBound() {
		return xPos + width;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(xPos, yPos, width, height);
	}
	
	public void paint(Graphics2D g) {
		g.setColor(color);
		g.fillRect(xPos, yPos, width, height);
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
}
