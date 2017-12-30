package states.game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import states.game.interfaces.Paintable;
import states.game.motorbiker.MotorBiker;

public class StatusBar implements Paintable {
	private int powerupWidth;
	private int powerupHeight;
	private int powerupRelativeXPos;
	private int powerupRelativeYPos;
	private Game game;

	private Image statusBar;
	private BufferedImage reverseStatusCount;
	private BufferedImage[] reverseStatusCountSheet;
	private BufferedImage shieldStatusCount;
	private BufferedImage[] shieldStatusCountSheet;
	private BufferedImage sideBladeStatusCount;
	private BufferedImage[] sideBladeStatusCountSheet;

	private BufferedImage player1NumberSheet;
	private BufferedImage player2NumberSheet;
	private BufferedImage[] player1Numbers;
	private BufferedImage[] player2Numbers;

	public StatusBar(Game game) {
		this.game = game;
		initDimensions();
		initImages();
		initSprites();
	}
	
	private void initDimensions() {
		int upBound = 0;
		int downBound = (Game.getFrameHeight() / 8) - 22;
		int height = downBound - upBound;
		
		powerupWidth = height / 2 + 23; // 50px
		powerupHeight = height / 2 + 18; // 45px
		powerupRelativeXPos = (Game.getFrameWidth() / 2) - 175;
		powerupRelativeYPos = ((height + VirtualBorder.getWidth()) / 2)
								- (powerupHeight / 2) + 3;
	}

	private void initImages() {
		try {
			statusBar = ImageIO.read((getClass().getResourceAsStream(
					"/images/game/StatusBarFinalFinal.png")));
			shieldStatusCount = ImageIO.read((getClass().getResourceAsStream(
					"/images/game/ShieldStatusSheet.png")));
			sideBladeStatusCount = ImageIO.read((getClass().getResourceAsStream(
					"/images/game/SideBladeStatusSheet.png")));
			reverseStatusCount = ImageIO.read((getClass().getResourceAsStream(
					"/images/game/ReverseStatusSheet.png")));
			player1NumberSheet = ImageIO.read((getClass().getResourceAsStream(
					"/images/game/Score Numbers1.png")));
			player2NumberSheet = ImageIO.read((getClass().getResourceAsStream(
					"/images/game/Score Numbers2.png")));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void initSprites() {
		try {
			reverseStatusCountSheet = new BufferedImage[6];
			for(int i = 0; i < reverseStatusCountSheet.length; i++) {
				reverseStatusCountSheet[i] = reverseStatusCount.getSubimage(
						i * 50, 0, 50, 45);
			}
			shieldStatusCountSheet = new BufferedImage[6];
			for(int i = 0; i < shieldStatusCountSheet.length; i++) {
				shieldStatusCountSheet[i] = shieldStatusCount.getSubimage(
						i * 50, 0, 50, 45);
			}
			sideBladeStatusCountSheet = new BufferedImage[6];
			for(int i = 0; i < sideBladeStatusCountSheet.length; i++) {
				sideBladeStatusCountSheet[i] = sideBladeStatusCount.getSubimage(
						i * 50, 0, 50, 45);
			}
			player1Numbers = new BufferedImage[10];
			for(int i = 0; i < player1Numbers.length; i++) {
				player1Numbers[i] = player1NumberSheet.getSubimage(
						i * 38, 0, 38, 53);
			}
			player2Numbers = new BufferedImage[10];
			for(int i = 0; i < player2Numbers.length; i++) {
				player2Numbers[i] = player2NumberSheet.getSubimage(
						i * 38, 0, 38, 53);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void paint(Graphics2D g) {
		paintPowerupStack(g);
		paintPowerupActivated(g);
		paintScores(g);
		g.drawImage(statusBar, 15, 5, 969, 66, null);
	}

	private void paintPowerupStack(Graphics2D g) {
		MotorBiker motorBiker = game.getMotorBiker1();
		if(motorBiker.getPowerUp1() != null) {
			g.drawImage(motorBiker.getPowerUp1().statusImage, powerupRelativeXPos ,
					powerupRelativeYPos, powerupWidth, powerupHeight, null);
		}
		if(motorBiker.getPowerUp2() != null) {
			g.drawImage(motorBiker.getPowerUp2().statusImage,
					powerupRelativeXPos - powerupWidth
							- (powerupWidth / 5) - 13,  powerupRelativeYPos,
					powerupWidth, powerupHeight, null);

		}

		motorBiker = game.getMotorBiker2();
		if(motorBiker.getPowerUp1() != null) {
			g.drawImage(motorBiker.getPowerUp1().statusImage,
					Game.getFrameWidth() - powerupRelativeXPos
							- powerupWidth - 3 ,
					powerupRelativeYPos, powerupWidth, powerupHeight, null);
		}
		if(motorBiker.getPowerUp2() != null) {
			g.drawImage(motorBiker.getPowerUp2().statusImage,
					Game.getFrameWidth() - (powerupRelativeXPos - powerupWidth
							- (powerupWidth / 5)) - powerupWidth - 3 + 13,
					powerupRelativeYPos, powerupWidth, powerupHeight, null);
		}
	}

	private void paintPowerupActivated(Graphics2D g) {
		MotorBiker motorBiker = game.getMotorBiker1();
		if(motorBiker.isSideBladeActivated()) {
			int index = (int) (motorBiker.statusCurrentTime
					- motorBiker.getSideBladeActivatedTime()) / 1000;
			if(index < 6) {
				g.drawImage(sideBladeStatusCountSheet[index],
						110, 39, 25, 23, null);
			}
		}
		if(motorBiker.isShieldActivated()) {
			int index = (int) (motorBiker.statusCurrentTime
					- motorBiker.getShieldActivatedTime()) / 1000;
			if(index < 6) {
				g.drawImage(shieldStatusCountSheet[index],
						150, 39, 25, 23, null);
			}
		}

		if(motorBiker.isReverseActivated()) {
			int index = (int) (motorBiker.statusCurrentTime
					- motorBiker.getReverseActivatedTime()) / 1000;
			if(index < 6) {
				g.drawImage(reverseStatusCountSheet[index],
						190, 39, 25, 23, null);
			}
		}

		motorBiker = game.getMotorBiker2();
		if(motorBiker.isSideBladeActivated()) {
			int index = (int) (motorBiker.statusCurrentTime
					- motorBiker.getSideBladeActivatedTime()) / 1000;
			if(index < 6) {
				g.drawImage(sideBladeStatusCountSheet[index],
						785, 39, 25, 23, null);
			}
		}
		if(motorBiker.isShieldActivated()) {
			int index = (int) (motorBiker.statusCurrentTime
					- motorBiker.getShieldActivatedTime()) / 1000;
			if(index < 6) {
				g.drawImage(shieldStatusCountSheet[index],
						825, 39, 25, 23, null);
			}
		}
		if(motorBiker.isReverseActivated()) {
			int index = (int) (motorBiker.statusCurrentTime
					- motorBiker.getReverseActivatedTime()) / 1000;
			if(index < 6) {
				g.drawImage(reverseStatusCountSheet[index],
						865, 39, 25, 23, null);
			}
		}
	}

	private void paintScores(Graphics2D g) {
		g.drawImage(player1Numbers[game.player1ScoreTensDigit],
				400, 10, 38, 53, null);
		g.drawImage(player1Numbers[game.player1ScoreOnesDigit],
				445 , 10, 38, 53, null);

		g.drawImage(player2Numbers[game.player2ScoreTensDigit],
				517, 10, 38, 53, null);
		g.drawImage(player2Numbers[game.player2ScoreOnesDigit],
				562 , 10, 38, 53, null);
	}
}
