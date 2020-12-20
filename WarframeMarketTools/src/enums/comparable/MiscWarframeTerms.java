package enums.comparable;

import enums.comparable.interfaces.ComparableByStringValueIgnoreCase;

public enum MiscWarframeTerms implements ComparableByStringValueIgnoreCase{
	PRIME("Prime"),
	SET("Set"),
	PARAZON("Parazon"),
	REQUIEM("Requiem");

	public final String value;

	private MiscWarframeTerms(String value){
		this.value = value;
	}

	@Override
	public String getValue(){
		return value;
	}
}