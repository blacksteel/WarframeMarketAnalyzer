package enums.hopefullyTemporaryPrimeEnums;

import enums.PrimePartType;
import enums.comparable.MiscWarframeTerms;
import enums.hopefullyTemporaryPrimeEnums.interfaces.PrimeItemEnum;

public enum PrimeArchwing implements PrimeItemEnum{
	ODONATA("Odonata");

	String name;
	
	private PrimeArchwing(String name){
		this.name = name;
	}
	
	@Override
	public String getItemName(){
		return name + " " + MiscWarframeTerms.PRIME;
	}

	@Override
	public PrimePartType getType(){
		return PrimePartType.ARCHWING;
	}
}