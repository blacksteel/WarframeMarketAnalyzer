package enums.fields;

public enum ModFieldEnum implements IFieldEnum {
	Avg48("48HrAvg"),
	Low48("48HrLow"),
	High48("48HrHigh"),
	Num48("48HrNumSales"),
	Avg90("90DayAvg"),
	Low90("90DayLow"),
	High90("90DayHigh"),
	Num90("90DayNumSales"),
	Rank,
	MaxRank,
	Type,
	Compatibility,
	Rarity,
	IsAugment,
	IsSet,
	IsConclaveOnly,
	;

	private String defaultDisplayName;
	
	private ModFieldEnum() {
		this(null);
	}

	private ModFieldEnum(String defaultDisplayName) {
		this.defaultDisplayName = defaultDisplayName;
	}
	
	@Override
	public String getDisplayName() {
		if (defaultDisplayName != null) {
			return defaultDisplayName;
		}
		return name();
	}
	
	@Override
	public String toString() {
		return getDisplayName();
	}
}
