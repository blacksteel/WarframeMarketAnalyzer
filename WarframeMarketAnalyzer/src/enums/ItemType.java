package enums;

import items.Mod;
import items.PrimePart;
import items.VoidRelic;
import items.WarframeItem;

public enum ItemType{
	MOD("MODS", Mod.class),
	PRIME_PART("PRIMES", PrimePart.class),
	VOID_RELIC("RELICS", VoidRelic.class);

	public final String outputFileNameSuffix;
	public final Class<? extends WarframeItem> itemClass;

	private ItemType(String outputFileNameSuffix, Class<? extends WarframeItem> itemClass){
		this.outputFileNameSuffix = outputFileNameSuffix;
		this.itemClass = itemClass;
	}
}