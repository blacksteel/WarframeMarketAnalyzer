package enums;

import java.lang.reflect.InvocationTargetException;

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

	public String getTradeStatsHeader48Hrs() throws IllegalAccessException, IllegalArgumentException,
	InvocationTargetException, NoSuchMethodException, SecurityException{
		return (String)itemClass.getMethod("getTradeStatsHeader48Hrs").invoke(null);
	}

	public String getTradeStatsHeader90Days() throws IllegalAccessException, IllegalArgumentException,
	InvocationTargetException, NoSuchMethodException, SecurityException{
		return (String)itemClass.getMethod("getTradeStatsHeader90Days").invoke(null);
	}

	public String getHeaderSuffix() throws IllegalAccessException, IllegalArgumentException,
	InvocationTargetException, NoSuchMethodException, SecurityException{
		return (String)itemClass.getMethod("getHeaderSuffix").invoke(null);
	}
}