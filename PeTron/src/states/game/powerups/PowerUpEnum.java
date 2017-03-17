package states.game.powerups;



public enum PowerUpEnum {
	SHIELD(Shield.getName()), SIDE_BLADE(SideBlade.getName()), REVERSE(Reverse.getName());
	
	public final String name;
	
	private PowerUpEnum(String name) {
		this.name= name;
	}
	
	public String getName() {
		return name;
	}
}
