package states.game.motorbiker;

import java.awt.Color;
import com.sun.glass.events.KeyEvent;
import states.game.Game;

public class Player1 extends MotorBiker {
	
	private static Player1 player1Instance;
	private static final int XPOS = Game.getInstance().getArena().
			getHorizontalCenter()
			- (Game.getInstance().getArena().getHorizontalCenter() / 2);
	private static final int YPOS = Game.getInstance().
			getArena().getVerticalCenter();
	private static final int DIRECTION = MotorBiker.DIRECTION_RIGHT;
	private static final Color COLOR = Color.YELLOW;
	private static final int UP_BUTTON = KeyEvent.VK_W;
	private static final int DOWN_BUTTON = KeyEvent.VK_S;
	private static final int LEFT_BUTTON = KeyEvent.VK_A;
	private static final int RIGHT_BUTTON = KeyEvent.VK_D;
	private static final int POWERUP_BUTTON_1 = KeyEvent.VK_E;
	private static final int POWERUP_BUTTON_2 = KeyEvent.VK_D;

	public Player1() {
		super(XPOS, YPOS, DIRECTION, COLOR, UP_BUTTON, DOWN_BUTTON, LEFT_BUTTON,
				RIGHT_BUTTON, POWERUP_BUTTON_1, POWERUP_BUTTON_2);
	}
	
	public static Player1 getInstance() {
		if(player1Instance == null) {
			player1Instance = new Player1();
		}
		return player1Instance;
	}
}
