package engine.components;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.components.GameButton;

public class HoverableButton extends GameButton {
	
	private BufferedImage buttonDefaultImage;
	private BufferedImage buttonHoveredImage;

	public HoverableButton(String buttonImageSource, String buttonHoveredImageSource, int leftBound, int upBound, int width, int height) {
		super(leftBound, upBound, width, height);
		try {
			buttonDefaultImage = ImageIO.read((getClass().getResourceAsStream(buttonImageSource)));
			buttonHoveredImage = ImageIO.read((getClass().getResourceAsStream(buttonHoveredImageSource)));
		} catch(IOException e) {
			e.printStackTrace();
		}
		setButtonImage(buttonDefaultImage);
	}

	public BufferedImage getDefaultImage() {
		return buttonDefaultImage;
	}
	
	public BufferedImage getHoveredImage() {
		return buttonHoveredImage; 
	}
}
