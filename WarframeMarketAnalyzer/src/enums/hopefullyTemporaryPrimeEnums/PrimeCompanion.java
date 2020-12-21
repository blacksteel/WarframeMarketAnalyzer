package enums.hopefullyTemporaryPrimeEnums;

import enums.PrimePartType;
import enums.comparable.MiscWarframeTerms;
import enums.hopefullyTemporaryPrimeEnums.interfaces.PrimeItemEnum;

public enum PrimeCompanion implements PrimeItemEnum{
	CARRIER("Carrier"),
	DETHCUBE("Dethcube"),
	HELIOS("Helios"),
	WYRM("Wyrm");

	String name;
	
	private PrimeCompanion(String name){
		this.name = name;
	}
	
	@Override
	public String getItemName(){
		return name + " " + MiscWarframeTerms.PRIME;
	}
	
	@Override
	public PrimePartType getType(){
		return PrimePartType.COMPANION;
	}
}