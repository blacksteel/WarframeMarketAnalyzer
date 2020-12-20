package enums;

public enum PrimePartType{
	WARFRAME("Warframe"),
	WEAPON("Weapon"),
	ARCHWING("Archwing"),
	COMPANION("Companion");
	
	public final String value;
	
	private PrimePartType(String value){
		this.value = value;
	}
}