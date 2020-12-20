package enums;

public enum ItemType{
	MOD("MODS"),
	PRIME_PARTS("PRIMES");

	public final String outputFileNameSuffix;

	private ItemType(String outputFileNameSuffix){
		this.outputFileNameSuffix = outputFileNameSuffix;
	}
}