package gameEngine.gameComponents;



import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameButton {
	private BufferedImage buttonImage;
	private int leftBound;
	private int upBound;
	private int rightBound;
	private int downBound;
	private int width;
	private int height;
	private boolean wasHovered;
	
	public GameButton(int leftBound, int upBound, int width, int height) {
		this.leftBound = leftBound;
		this.upBound = upBound;
		this.width = width;
		this.height = height;
		rightBound = leftBound + width;
		downBound = upBound + height;
	}
	
	public GameButton(BufferedImage buttonImage, int leftBound, int upBound, int width, int height) {
		this(leftBound, upBound, width, height);
		this.buttonImage = buttonImage;
	}
	
	public GameButton(String buttonImageSource, int leftBound, int upBound, int width, int height) {
		this(leftBound, upBound, width, height);
		try {
			buttonImage = ImageIO.read((getClass().getResourceAsStream(buttonImageSource)));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2d) {
		g2d.drawImage(buttonImage, leftBound, upBound, width, height, null);
	}
		
	public boolean hovered(int mouseXPos, int mouseYPos) {
		final boolean isHovered = mouseXPos > leftBound && mouseXPos < rightBound 
				   				  && 
				   				  mouseYPos > upBound && mouseYPos < downBound;
		wasHovered = isHovered;
		return isHovered;
	}
	
	public void setButtonImage(BufferedImage buttonImage) {
		this.buttonImage = buttonImage;
	}
	
	public boolean getWasHovered() {
		return wasHovered;
	}
}
