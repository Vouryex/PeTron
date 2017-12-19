package states.game.motorbiker;

import java.awt.Color;

import com.sun.glass.events.KeyEvent;

import states.game.Game;

public class Player2 extends MotorBiker {
	
	private static Player2 Player2Instance;
	private static final int XPOS = Game.getInstance().getArena().getHorizontalCenter() + (Game.getInstance().getArena().getHorizontalCenter() / 2);
	private static final int YPOS = Game.getInstance().getArena().getVerticalCenter();
	private static final int DIRECTION = MotorBiker.DIRECTION_LEFT;
	private static final Color COLOR = Color.MAGENTA;
	private static final int UP_BUTTON = KeyEvent.VK_I;
	private static final int DOWN_BUTTON = KeyEvent.VK_K;
	private static final int LEFT_BUTTON = KeyEvent.VK_J;
	private static final int RIGHT_BUTTON = KeyEvent.VK_L;
	private static final int POWERUP_BUTTON_1 = KeyEvent.VK_U;
	private static final int POWERUP_BUTTON_2 = KeyEvent.VK_O;

	public Player2() {
		super(XPOS, YPOS, DIRECTION, COLOR, UP_BUTTON, DOWN_BUTTON, LEFT_BUTTON, RIGHT_BUTTON, POWERUP_BUTTON_1,
				POWERUP_BUTTON_2);
	}
	
	public static Player2 getInstance() {
		if(Player2Instance == null) {
			Player2Instance = new Player2();
		}
		return Player2Instance;
	}
}
