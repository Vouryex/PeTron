package states.game;



import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import states.game.interfaces.Collideable;
import states.game.interfaces.Paintable;

public class VirtualSubBorder implements Collideable, Paintable {
	private int xPos;
	private int yPos;
	private int width;
	private int height;
	private Color color;
	
	public VirtualSubBorder(int xPos, int yPos, int width, int height, Color color) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	public boolean collision(Collideable c) {
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
		//g.fillRect(xPos, yPos, width, height);
	}
}

