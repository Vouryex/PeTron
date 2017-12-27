package states.game.interfaces;


import java.awt.Rectangle;

public interface Collideable {
	
	public boolean collision(Collideable c);
	public int getUpBound();
	public int getDownBound();
	public int getLeftBound();
	public int getRightBound();
	public Rectangle getBounds();
}
