package enums.fields;

public enum PrimeFieldEnum implements IFieldEnum {
	Name,
	Avg48Hr("48HrAvg"),
	Low48Hr("48HrLow"),
	High48Hr("48HrHigh"),
	Num48Hr("48HrNumSales"),
	Avg90Day("90DayAvg"),
	Low90Day("90DayLow"),
	High90Day("90DayHigh"),
	Num90Day("90DayNumSales"),
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
	private final boolean includedByDefault;

	private PrimeFieldEnum() {
		this(null);
	}

	private PrimeFieldEnum(String defaultDisplayName) {
		this(defaultDisplayName, true);
	}

	private PrimeFieldEnum(String defaultDisplayName, boolean includedByDefault) {
		this.defaultDisplayName = defaultDisplayName;
		this.includedByDefault = includedByDefault;
	}

	@Override
	public String getDisplayName() {
		if (defaultDisplayName != null) {
			return defaultDisplayName;
		}
		return name();
	}

	@Override
	public boolean isIncudedByDefault() {
		return includedByDefault;
	}

	@Override
	public String toString() {
		return getDisplayName();
	}

//	public static List<PrimeFieldEnum> get48HrFields() {
//		List<PrimeFieldEnum> fields = new ArrayList<>();
//		fields.add(Avg48Hr);
//		fields.add(Low48Hr);
//		fields.add(High48Hr);
//		fields.add(Num48Hr);
//		return fields;
//	}
//
//	public static List<PrimeFieldEnum> get90DayFields() {
//		List<PrimeFieldEnum> fields = new ArrayList<>();
//		fields.add(Avg90Day);
//		fields.add(Low90Day);
//		fields.add(High90Day);
//		fields.add(Num90Day);
//		return fields;
//	}
//
//	public static List<PrimeFieldEnum> getDataFields() {
//		List<PrimeFieldEnum> fields = new ArrayList<>();
//		fields.add(ItemName);
//		fields.add(PartName);
//		fields.add(Type);
//		fields.add(IsVaulted);
//		fields.add(IsFullSet);
//		fields.add(NumInFullSet);
//		fields.add(Ducats);
//		fields.add(Ducats48);
//		fields.add(Ducats90);
//		return fields;
//	}
}
