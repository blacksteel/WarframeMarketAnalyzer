package enums.hopefullyTemporaryPrimeEnums.interfaces;

import enums.PrimePartType;

/*
 * TODO: I couldn't find an easy way to pull the info for these classes of items from the data source.
 * Since there's only a few, I'm hard-coding it for now and will revisit it later
 */

public interface PrimeItemEnum{
	public abstract String getItemName();
	public abstract PrimePartType getType();
}
