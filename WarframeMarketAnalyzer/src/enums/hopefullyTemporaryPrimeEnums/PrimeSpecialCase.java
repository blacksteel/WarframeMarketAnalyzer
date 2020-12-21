package enums.hopefullyTemporaryPrimeEnums;

import enums.PrimePartType;
import enums.hopefullyTemporaryPrimeEnums.interfaces.PrimeItemEnum;

public enum PrimeSpecialCase implements PrimeItemEnum{
	KAVASA("Kavasa Prime Kubrow Collar", PrimePartType.COMPANION);

	String name;
	PrimePartType type;
	
	private PrimeSpecialCase(String name, PrimePartType type){
		this.name = name;
		this.type = type;
	}
	
	//This works differently because it's the only Prime item that uses a different name format
	@Override
	public String getItemName(){
		return name;
	}
	
	@Override
	public PrimePartType getType(){
		return type;
	}
}