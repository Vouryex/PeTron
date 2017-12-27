package states.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import states.game.interfaces.Collideable;
import states.game.interfaces.Paintable;

public class VirtualSubBorder implements Collideable, Paintable {
	private int xPos;
	private int yPos;
	private int horizontalLength;
	private int verticalLength;
	private Color color;
	
	public VirtualSubBorder(int xPos, int yPos, int horizontalLength,
							int verticalLength, Color color)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.horizontalLength = horizontalLength;
		this.verticalLength = verticalLength;
		this.color = color;
	}

	public boolean collision(Collideable c) {
		return false;
	}

	public int getUpBound() {
		return yPos;
	}

	public int getDownBound() {
		return yPos + verticalLength;
	}

	public int getLeftBound() {
		return xPos;
	}

	public int getRightBound() {
		return xPos + horizontalLength;
	}

	public Rectangle getBounds() {
		return new Rectangle(xPos, yPos, horizontalLength, verticalLength);
	}

	public void paint(Graphics2D g) {
		g.setColor(color);
		//g.fillRect(xPos, yPos, horizontalLength, verticalLength);
	}
}

