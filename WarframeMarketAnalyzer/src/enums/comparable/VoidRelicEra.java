package enums.comparable;

import enums.comparable.interfaces.ComparableByStringValueIgnoreCase;

public enum VoidRelicEra implements ComparableByStringValueIgnoreCase{
	LITH("Lith"),
	MESO("Meso"),
	NEO("Neo"),
	AXI("Axi");
	
	public final String value;

	private VoidRelicEra(String value){
		this.value = value;
	}

	@Override
	public String getValue(){
		return value;
	}
	
	public static VoidRelicEra getByValue(String testValue){
		for(VoidRelicEra voidRelicEra: VoidRelicEra.values()){
			if(voidRelicEra.valueEquals(testValue)) return voidRelicEra;
		}
		
		return null;
	}
}