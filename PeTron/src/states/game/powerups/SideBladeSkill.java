package states.game.powerups;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import states.game.gameinterfaces.Paintable;
import states.game.motorbiker.MotorBiker;

public class SideBladeSkill implements Paintable {
	public static final Color COLOR = Color.WHITE;
	public SubSideBladeSkill sideBlade1;
	public SubSideBladeSkill sideBlade2;
	
	public SideBladeSkill(MotorBiker motorBiker) {
		sideBlade1 = new SubSideBladeSkill(SubSideBladeSkill.FIRST_ORIENTATION, motorBiker, COLOR);
		sideBlade2 = new SubSideBladeSkill(SubSideBladeSkill.SECOND_ORIENTATION, motorBiker, COLOR);
	}
	
	public void paint(Graphics2D g) {
		sideBlade1.paint(g);
		sideBlade2.paint(g);
	}
}
