package enums.comparable;

import enums.comparable.interfaces.ComparableByStringValueIgnoreCase;

public enum UniqueNameSnippet implements ComparableByStringValueIgnoreCase {
	FLAWED_MOD_UNIQUE_NAME_SNIPPET("/beginner/"),
	REQUIEM_MOD_UNIQUE_NAME_SNIPPET("/mods/immortal/"),
	CONCLAVE_MOD_UNIQUE_NAME_SNIPPET("/pvpmods/");

	public final String value;

	private UniqueNameSnippet(String value){
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}
	
	public static UniqueNameSnippet getByValue(String testValue){
		for(UniqueNameSnippet uniqueNameSnippet: UniqueNameSnippet.values()){
			if(uniqueNameSnippet.valueEquals(testValue)) return uniqueNameSnippet;
		}
		
		return null;
	}
}
