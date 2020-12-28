package enums.fields;

public enum PrimeFieldEnum implements IFieldEnum {
	Avg48("48HrAvg"),
	Low48("48HrLow"),
	High48("48HrHigh"),
	Num48("48HrNumSales"),
	Avg90("90DayAvg"),
	Low90("90DayLow"),
	High90("90DayHigh"),
	Num90("90DayNumSales"),
	ItemName,
	PartName,
	Type,
	IsVaulted,
	IsFullSet,
	NumInFullSet,
	Ducats,
	Ducats48("DucatsPerPlat(48HrsAvgPrice)"),
	Ducats90("DucatsPerPlat(90DaysAvgPrice)"),
	;

	private String defaultDisplayName;
	
	private PrimeFieldEnum() {
		this(null);
	}

	private PrimeFieldEnum(String defaultDisplayName) {
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
