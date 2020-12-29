package enums.fields;

public enum ModFieldEnum implements IFieldEnum {
	Avg48Hr("48HrAvg"),
	Low48Hr("48HrLow"),
	High48Hr("48HrHigh"),
	Num48Hr("48HrNumSales"),
	Avg90Day("90DayAvg"),
	Low90Day("90DayLow"),
	High90Day("90DayHigh"),
	Num90Day("90DayNumSales"),
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
