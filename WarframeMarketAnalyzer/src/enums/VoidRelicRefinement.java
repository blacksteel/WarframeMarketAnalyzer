package enums;

public enum VoidRelicRefinement{
	INTACT("intact", 0, 0.2533, 0.11, 0.02),
	EXCEPTIONAL("exceptional", 25, 0.2333, 0.13, 0.04),
	FLAWLESS("flawless", 50, 0.2, 0.17, 0.06),
	RADIANT("radiant", 100, 0.1667, 0.2, 0.1);
	
	public final String name;
	public final int numVoidTracesToUpgrade;
	//Note: These are the probabilities of getting a particular item of that rarity to drop
	public final double commonDropChance;
	public final double uncommonDropChance;
	public final double rareDropChance;
	
	private VoidRelicRefinement(String name, int numVoidTracesToUpgrade, double commonDropChance,
			double uncommonDropChance, double rareDropChance){
		this.name = name;
		this.numVoidTracesToUpgrade = numVoidTracesToUpgrade;
		this.commonDropChance = commonDropChance;
		this.uncommonDropChance = uncommonDropChance;
		this.rareDropChance = rareDropChance;
	}
}
