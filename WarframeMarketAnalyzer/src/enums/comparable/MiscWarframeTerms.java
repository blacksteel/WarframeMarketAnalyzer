package enums.comparable;

import enums.comparable.interfaces.ComparableByStringValueIgnoreCase;

public enum MiscWarframeTerms implements ComparableByStringValueIgnoreCase{
	PRIME("Prime"),
	SET("Set"),
	PARAZON("Parazon"),
	REQUIEM("Requiem"),
	FORMA("Forma"),
	BLUEPRINT("Blueprint"),
	KAVASA("Kavasa"),
	COLLAR("Collar");

	public final String value;

	private MiscWarframeTerms(String value){
		this.value = value;
	}

	@Override
	public String getValue(){
		return value;
	}
	
	public static MiscWarframeTerms getByValue(String testValue){
		for(MiscWarframeTerms miscWarframeTerms: MiscWarframeTerms.values()){
			if(miscWarframeTerms.valueEquals(testValue)) return miscWarframeTerms;
		}
		
		return null;
	}
}