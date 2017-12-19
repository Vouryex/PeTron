package states.game;
import java.awt.Color;
import java.awt.Graphics2D;

import states.game.interfaces.Paintable;

public class VirtualBorder implements Paintable {
	private static final Color COLOR = Color.MAGENTA;	
	private static final int WIDTH = 18;
	private VirtualSubBorder leftBorder;
	private VirtualSubBorder rightBorder;
	private VirtualSubBorder upBorder;
	private VirtualSubBorder downBorder;
	
	public VirtualBorder(Arena arena) {
		leftBorder = new VirtualSubBorder(arena.getLeftBound(), arena.getUpBound(), WIDTH, arena.getHeight(), COLOR);
		rightBorder = new VirtualSubBorder(arena.getRightBound() - WIDTH, arena.getUpBound(), WIDTH, arena.getHeight(), COLOR);
		upBorder = new VirtualSubBorder(arena.getLeftBound(), arena.getUpBound(), arena.getWidth(), WIDTH, COLOR);
		downBorder = new VirtualSubBorder(arena.getLeftBound(), arena.getDownBound() - (WIDTH + 11), arena.getWidth(), WIDTH + 11, COLOR);
	}
	
	public void paint(Graphics2D g) {
		g.setColor(COLOR);
		leftBorder.paint(g);
		rightBorder.paint(g);
		upBorder.paint(g);
		downBorder.paint(g);
	}
	
	public static int getWidth() {
		return WIDTH;
	}
	
	public VirtualSubBorder getLeftBorder() {
		return leftBorder;
	}
	
	public VirtualSubBorder getRightBorder() {
		return rightBorder;
	}
	
	public VirtualSubBorder getUpBorder() {
		return upBorder;
	}

	public VirtualSubBorder getDownBorder() {
		return downBorder;
	}
}
