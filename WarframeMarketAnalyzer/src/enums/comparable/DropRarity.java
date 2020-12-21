package enums.comparable;

import enums.comparable.interfaces.ComparableByStringValueIgnoreCase;

public enum DropRarity implements ComparableByStringValueIgnoreCase{
	COMMON("Common"),
	UNCOMMON("Uncommon"),
	RARE("Rare");
	
	public final String value;

	private DropRarity(String value){
		this.value = value;
	}

	@Override
	public String getValue(){
		return value;
	}

	public static DropRarity getByValue(String testValue){
		for(DropRarity dropRarity: DropRarity.values()){
			if(dropRarity.valueEquals(testValue)) return dropRarity;
		}
		
		return null;
	}
}