package gameEngine.gameComponents;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GamePanel {

	private BufferedImage panelImage;
	private int leftBound;
	private int upBound;
	private int width;
	private int height;
	
	public GamePanel(int leftBound, int upBound, int width, int height) {
		this.leftBound = leftBound;
		this.upBound = upBound;
		this.width = width;
		this.height = height;
	}
	
	public GamePanel(BufferedImage panelImage, int leftBound, int upBound, int width, int height) {
		this(leftBound, upBound, width, height);
		this.panelImage = panelImage;
	}
	
	public GamePanel(String panelImageSource, int leftBound, int upBound, int width, int height) {
		this(leftBound, upBound, width, height);
		try {
			panelImage = ImageIO.read((getClass().getResourceAsStream(panelImageSource)));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2d) {
		g2d.drawImage(panelImage, leftBound, upBound, width, height, null);
	}
}
