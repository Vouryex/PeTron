package states.game;



import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import states.game.interfaces.Paintable;
import states.game.motorbiker.MotorBiker;

public class StatusBar implements Paintable {
	private int upBound;
	private int downBound;
	private int leftBoound;
	private int rightBound;
	private int height;
	private int width;
	private int powerupWidth;
	private int powerupHeight;
	private int powerupRelativeXPos;
	private int powerupRelativeYPos;
	private Game game;
	
	public Image bar;
	public Image sideBladeStatus;
	public Image reverseStatus;
	
	public BufferedImage reverseStatusCount;
	public BufferedImage[] reverseStatusCountSheet;
	public BufferedImage shieldStatusCount;
	public BufferedImage[] shieldStatusCountSheet;
	public BufferedImage sideBladeStatusCount;
	public BufferedImage[] sideBladeStatusCountSheet;
	
	public BufferedImage player1NumberSheet;
	public BufferedImage player2NumberSheet;
	public BufferedImage[] player1Numbers;
	public BufferedImage[] player2Numbers;
	
	public StatusBar(Game game) {
		this.game = game;
		initDimensions(game);
		try {
			bar = ImageIO.read((getClass().getResourceAsStream("/images/game/StatusBarFinalFinal.png")));
			shieldStatusCount = ImageIO.read((getClass().getResourceAsStream("/images/game/ShieldStatusSheet.png")));
			sideBladeStatusCount = ImageIO.read((getClass().getResourceAsStream("/images/game/SideBladeStatusSheet.png")));
			reverseStatusCount = ImageIO.read((getClass().getResourceAsStream("/images/game/ReverseStatusSheet.png")));
			player1NumberSheet = ImageIO.read((getClass().getResourceAsStream("/images/game/Score Numbers1.png")));
			player2NumberSheet = ImageIO.read((getClass().getResourceAsStream("/images/game/Score Numbers2.png")));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		try {
			reverseStatusCountSheet = new BufferedImage[6];
			for(int i = 0; i < reverseStatusCountSheet.length; i++) {
				reverseStatusCountSheet[i] = reverseStatusCount.getSubimage(i * 50, 0, 50, 45);
			}
			shieldStatusCountSheet = new BufferedImage[6];
			for(int i = 0; i < shieldStatusCountSheet.length; i++) {
				shieldStatusCountSheet[i] = shieldStatusCount.getSubimage(i * 50, 0, 50, 45);
			}
			sideBladeStatusCountSheet = new BufferedImage[6];
			for(int i = 0; i < sideBladeStatusCountSheet.length; i++) {
				sideBladeStatusCountSheet[i] = sideBladeStatusCount.getSubimage(i * 50, 0, 50, 45);
			}
			player1Numbers = new BufferedImage[10];
			for(int i = 0; i < player1Numbers.length; i++) {
				player1Numbers[i] = player1NumberSheet.getSubimage(i * 38, 0, 38, 53);
			}
			player2Numbers = new BufferedImage[10];
			for(int i = 0; i < player2Numbers.length; i++) {
				player2Numbers[i] = player2NumberSheet.getSubimage(i * 38, 0, 38, 53);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initDimensions(Game game) {
		upBound = 0;
		downBound = (Game.frameHeight / 8) - 22;
		leftBoound = 0;
		rightBound = Game.frameWidth;
		height = downBound - upBound;
		width = rightBound - leftBoound;
		
		powerupWidth = height / 2 + 23; // 50px
		powerupHeight = height / 2 + 18; // 45px
		powerupRelativeXPos = (Game.frameWidth / 2) - 175;
		
		final Arena arena = game.getArena();
		final VirtualBorder virtualBorder = arena.getVirtualBorder();
		powerupRelativeYPos = ((height + virtualBorder.getWidth()) / 2) - (powerupHeight / 2) + 3;
	}

	public void paint(Graphics2D g) {
		/**Powerup**/
		MotorBiker motorBiker = game.getMotorBiker1();
		if(motorBiker.powerUp1 != null) {
			g.drawImage(motorBiker.powerUp1.statusImage, powerupRelativeXPos , powerupRelativeYPos, powerupWidth, powerupHeight, null);
		}
		if(motorBiker.powerUp2 != null) {
			g.drawImage(motorBiker.powerUp2.statusImage, powerupRelativeXPos - powerupWidth - (powerupWidth / 5) - 13,  powerupRelativeYPos, powerupWidth, powerupHeight, null);
			
		}
		
		motorBiker = game.getMotorBiker2();
		if(motorBiker.powerUp1 != null) {
			g.drawImage(motorBiker.powerUp1.statusImage, rightBound - powerupRelativeXPos - powerupWidth - 3 , powerupRelativeYPos, powerupWidth, powerupHeight, null);
		}
		if(motorBiker.powerUp2 != null) {
			g.drawImage(motorBiker.powerUp2.statusImage, rightBound - (powerupRelativeXPos - powerupWidth - (powerupWidth / 5)) - powerupWidth - 3 + 13,  powerupRelativeYPos, powerupWidth, powerupHeight, null);
		}
		/**********/
		
		g.drawImage(bar, 15, 5, 969, 66, null);
		
		/**Powerup status**/
		motorBiker = game.getMotorBiker1();
		if(motorBiker.sideBladeActivated) {
			long currentTime = System.currentTimeMillis();
			int index = (int) (motorBiker.statusCurrentTime - motorBiker.sideBladeActivatedTime) / 1000;
			if(index < 6) {
				g.drawImage(sideBladeStatusCountSheet[index], 110, 39, 25, 23, null);
			}
		}
		if(motorBiker.shieldActivated) {
			long currentTime = System.currentTimeMillis();
			int index = (int) (motorBiker.statusCurrentTime  - motorBiker.shieldActivatedTime) / 1000;
			if(index < 6) {
				g.drawImage(shieldStatusCountSheet[index], 150, 39, 25, 23, null);	
			}
		}

		if(motorBiker.reverseActivated) {
			long currentTime = System.currentTimeMillis();
			int index = (int) (motorBiker.statusCurrentTime  - motorBiker.reverseActivatedTime) / 1000;
			if(index < 6) {
				g.drawImage(reverseStatusCountSheet[index], 190, 39, 25, 23, null);
			}
		} 
		
		motorBiker = game.getMotorBiker2();
		if(motorBiker.sideBladeActivated) {
			long currentTime = System.currentTimeMillis();
			int index = (int) (motorBiker.statusCurrentTime  - motorBiker.sideBladeActivatedTime) / 1000;
			if(index < 6) {
				g.drawImage(sideBladeStatusCountSheet[index], 785, 39, 25, 23, null);
			}
		}
		if(motorBiker.shieldActivated) {
			long currentTime = System.currentTimeMillis();
			int index = (int) (motorBiker.statusCurrentTime  - motorBiker.shieldActivatedTime) / 1000;
			if(index < 6) {
				g.drawImage(shieldStatusCountSheet[index], 825, 39, 25, 23, null);
			}
		}
		if(motorBiker.reverseActivated) {
			long currentTime = System.currentTimeMillis();
			int index = (int) (motorBiker.statusCurrentTime  - motorBiker.reverseActivatedTime) / 1000;
			if(index < 6) {
				g.drawImage(reverseStatusCountSheet[index], 865, 39, 25, 23, null);
			}
		} 
		
		/**Score**/
		g.drawImage(player1Numbers[game.player1ScoreTensDigit], 400, 10, 38, 53, null);
		g.drawImage(player1Numbers[game.player1ScoreOnesDigit], 445 , 10, 38, 53, null);
		
		g.drawImage(player2Numbers[game.player2ScoreTensDigit], 517, 10, 38, 53, null);
		g.drawImage(player2Numbers[game.player2ScoreOnesDigit], 562 , 10, 38, 53, null);
	}
}
