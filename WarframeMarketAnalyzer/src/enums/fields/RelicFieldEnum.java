package enums.fields;

public enum RelicFieldEnum implements IFieldEnum {
	Name,
	AvgInt48Hr("48HrAvg(Int)"),
	AvgEx48Hr("48HrAvg(Ex)"),
	AvgFlaw48Hr("48HrAvg(Flaw)"),
	AvgRad48Hr("48HrAvg(Rad)"),

	LowInt48Hr("48HrLow(Int)"),
	HighInt48Hr("48HrHigh(Int)"),

	LowEx48Hr("48HrLow(Ex)", false),
	HighEx48Hr("48HrHigh(Ex)", false),

	LowFlaw48Hr("48HrLow(Flaw)", false),
	HighFlaw48Hr("48HrHigh(Flaw)", false),

	LowRad48hr("48HrLow(Rad)"),
	HighRad48Hr("48HrHigh(Rad)"),

	NumSalesInt48Hr("48HrNumSales(Int)"),
	NumSalesEx48Hr("48HrNumSales(Ex)"),
	NumSalesFlaw48Hr("48HrNumSales(Flaw)"),
	NumSalesRad48Hr("48HrNumSales(Rad)"),

	AvgInt90Day("90DayAvg(Int)"),
	AvgEx90Day("90DayAvg(Ex)"),
	AvgFlaw90Day("90DayAvg(Flaw)"),
	AvgRad90Day("90DayAvg(Rad)"),

	LowInt90Day("90DayLow(Int)"),
	HighInt90Day("90DayHigh(Int)"),

	LowEx90Day("90DayLow(Ex)", false),
	HighEx90Day("90DayHigh(Ex)", false),

	HighFlaw90Day("90DayHigh(Flaw)", false),
	LowFlaw90Day("90DayLow(Flaw)", false),

	HighRad90Day("90DayHigh(Rad)"),
	LowRad90Day("90DayLow(Rad)"),

	NumSalesInt90Day("90DayNumSales(Int)"),
	NumSalesEx90Day("90DayNumSales(Ex)"),
	NumSalesFlaw90Day("90DayNumSales(Flaw)"),
	NumSalesRad90Day("90DayNumSales(Rad)"),

	CommonDrop1("CommonDrop1"),
	CommonDrop2("CommonDrop2"),
	CommonDrop3("CommonDrop3"),
	UncommonDrop1("UncommonDrop1"),
	UncommonDrop2("UncommonDrop2"),
	RareDrop("RareDrop"),
	CommonDrop1DucatVal("CommonDrop1DucatVal"),
	CommonDrop1AvgPlatVal48Hrs("CommonDrop1AvgPlatVal48Hrs"),
	CommonDrop1AvgPlatVal90Days("CommonDrop1AvgPlatVal90Days"),
	CommonDrop2DucatVal("CommonDrop2DucatVal"),
	CommonDrop2AvgPlatVal48Hrs("CommonDrop2AvgPlatVal48Hrs"),
	CommonDrop2AvgPlatVal90Days("CommonDrop2AvgPlatVal90Days"),
	CommonDrop3DucatVal("CommonDrop3DucatVal"),
	CommonDrop3AvgPlatVal48Hrs("CommonDrop3AvgPlatVal48Hrs"),
	CommonDrop3AvgPlatVal90Days("CommonDrop3AvgPlatVal90Days"),
	UncommonDrop1DucatVal("UncommonDrop1DucatVal"),
	UncommonDrop1AvgPlatVal48Hrs("UncommonDrop1AvgPlatVal48Hrs"),
	UncommonDrop1AvgPlatVal90Days("UncommonDrop1AvgPlatVal90Days"),
	UncommonDrop2DucatVal("UncommonDrop2DucatVal"),
	UncommonDrop2AvgPlatVal48Hrs("UncommonDrop2AvgPlatVal48Hrs"),
	UncommonDrop2AvgPlatVal90Days("UncommonDrop2AvgPlatVal90Days"),
	RareDropDucatVal("RareDropDucatVal"),
	RareDropAvgPlatVal48Hrs("RareDropAvgPlatVal48Hrs"),
	RareDropAvgPlatVal90Days("RareDropAvgPlatVal90Days"),
	CommonDrop1ItemName("CommonDrop1ItemName"),
	CommonDrop1PartName("CommonDrop1PartName"),
	CommonDrop2ItemName("CommonDrop2ItemName"),
	CommonDrop2PartName("CommonDrop2PartName"),
	CommonDrop3ItemName("CommonDrop3ItemName"),
	CommonDrop3PartName("CommonDrop3PartName"),
	UncommonDrop1ItemName("UncommonDrop1ItemName"),
	UncommonDrop1PartName("UncommonDrop1PartName"),
	UncommonDrop2ItemName("UncommonDrop2ItemName"),
	UncommonDrop2PartName("UncommonDrop2PartName"),
	RareDropItemName("RareDropItemName"),
	RareDropPartName("RareDropPartName"),
	DropsForma("DropsForma"),
	DropsFormaCommon("DropsFormaCommon"),
	DropsFormaUncommon("DropsFormaUncommon"),
	IsVaulted("IsVaulted"),
	IsBaroExclusive("IsBaroExclusive"),
	AvgDucatValInt("AvgDucatVal(Int)"),
	AvgDucatValEx("AvgDucatVal(Ex)"),
	AvgDucatValFlaw("AvgDucatVal(Flaw)"),
	AvgDucatValRad("AvgDucatVal(Rad)"),
	AvgPlatVal48HrsInt("AvgPlatVal48Hrs(Int)"),
	AvgPlatVal48HrsEx("AvgPlatVal48Hrs(Ex)"),
	AvgPlatVal48HrsFlaw("AvgPlatVal48Hrs(Flaw)"),
	AvgPlatVal48HrsRad("AvgPlatVal48Hrs(Rad)"),
	AvgPlatVal90DaysInt("AvgPlatVal90Days(Int)"),
	AvgPlatVal90DaysEx("AvgPlatVal90Days(Ex)"),
	AvgPlatVal90DaysFlaw("AvgPlatVal90Days(Flaw)"),
	AvgPlatVal90DaysRad("AvgPlatVal90Days(Rad)"),
	BestAvgDucatRefinement("BestAvgDucatRefinement"),
	BestAvgPlatRefinement48Hrs("BestAvgPlatRefinement48Hrs"),
	BestAvgPlatRefinement90Days("BestAvgPlatRefinement90Days"),
	AvgDucatValChangeIntEx("AvgDucatValChange(Int=>Ex)"),
	AvgDucatValChangeIntFlaw("AvgDucatValChange(Int=>Flaw)"),
	AvgDucatValChangeIntRad("AvgDucatValChange(Int=>Rad)"),
	AvgDucatValChangeExFlaw("AvgDucatValChange(Ex=>Flaw)"),
	AvgDucatValChangeExRad("AvgDucatValChange(Ex=>Rad)"),
	AvgDucatValChangeFlawRad("AvgDucatValChange(Flaw=>Rad)"),
	AvgPlatVal48HrsChangeIntEx("AvgPlatVal48HrsChange(Int=>Ex)"),
	AvgPlatVal48HrsChangeIntFlaw("AvgPlatVal48HrsChange(Int=>Flaw)"),
	AvgPlatVal48HrsChangeIntRad("AvgPlatVal48HrsChange(Int=>Rad)"),
	AvgPlatVal48HrsChangeExFlaw("AvgPlatVal48HrsChange(Ex=>Flaw)"),
	AvgPlatVal48HrsChangeExRad("AvgPlatVal48HrsChange(Ex=>Rad)"),
	AvgPlatVal48HrsChangeFlawRad("AvgPlatVal48HrsChange(Flaw=>Rad)"),
	AvgPlatVal90DaysChangeIntEx("AvgPlatVal90DaysChange(Int=>Ex)"),
	AvgPlatVal90DaysChangeIntFlaw("AvgPlatVal90DaysChange(Int=>Flaw)"),
	AvgPlatVal90DaysChangeIntRad("AvgPlatVal90DaysChange(Int=>Rad)"),
	AvgPlatVal90DaysChangeExFlaw("AvgPlatVal90DaysChange(Ex=>Flaw)"),
	AvgPlatVal90DaysChangeExRad("AvgPlatVal90DaysChange(Ex=>Rad)"),
	AvgPlatVal90DaysChangeFlawRad("AvgPlatVal90DaysChange(Flaw=>Rad)"),
	AvgDucatValChangePerTraceIntEx("AvgDucatValChangePerTrace(Int=>Ex)"),
	AvgDucatValChangePerTraceIntFlaw("AvgDucatValChangePerTrace(Int=>Flaw)"),
	AvgDucatValChangePerTraceIntRad("AvgDucatValChangePerTrace(Int=>Rad)"),
	AvgDucatValChangePerTraceExFlaw("AvgDucatValChangePerTrace(Ex=>Flaw)"),
	AvgDucatValChangePerTraceExRad("AvgDucatValChangePerTrace(Ex=>Rad)"),
	AvgDucatValChangePerTraceFlawRad("AvgDucatValChangePerTrace(Flaw=>Rad)"),
	AvgPlatVal48HrsChangePerTraceIntEx("AvgPlatVal48HrsChangePerTrace(Int=>Ex)"),
	AvgPlatVal48HrsChangePerTraceIntFlaw("AvgPlatVal48HrsChangePerTrace(Int=>Flaw)"),
	AvgPlatVal48HrsChangePerTraceIntRad("AvgPlatVal48HrsChangePerTrace(Int=>Rad)"),
	AvgPlatVal48HrsChangePerTraceExFlaw("AvgPlatVal48HrsChangePerTrace(Ex=>Flaw)"),
	AvgPlatVal48HrsChangePerTraceExRad("AvgPlatVal48HrsChangePerTrace(Ex=>Rad)"),
	AvgPlatVal48HrsChangePerTraceFlawRad("AvgPlatVal48HrsChangePerTrace(Flaw=>Rad)"),
	AvgPlatVal90DaysChangePerTraceIntEx("AvgPlatVal90DaysChangePerTrace(Int=>Ex)"),
	AvgPlatVal90DaysChangePerTraceIntFlaw("AvgPlatVal90DaysChangePerTrace(Int=>Flaw)"),
	AvgPlatVal90DaysChangePerTraceIntRad("AvgPlatVal90DaysChangePerTrace(Int=>Rad)"),
	AvgPlatVal90DaysChangePerTraceExFlaw("AvgPlatVal90DaysChangePerTrace(Ex=>Flaw)"),
	AvgPlatVal90DaysChangePerTraceExRad("AvgPlatVal90DaysChangePerTrace(Ex=>Rad)"),
	AvgPlatVal90DaysChangePerTraceFlawRad("AvgPlatVal90DaysChangePerTrace(Flaw=>Rad)"),
	IntShareAvgDucatVal("IntShareAvgDucatVal"),
	IntShareAvgPlatVal48Hrs("IntShareAvgPlatVal48Hrs"),
	IntShareAvgPlatVal90Days("IntShareAvgPlatVal90Days"),
	RadShareAvgDucatVal("RadShareAvgDucatVal"),
	RadShareAvgPlatValue48Hrs("RadShareAvgPlatValue48Hrs"),
	RadShareAvgPlatValue90Days("RadShareAvgPlatValue90Days"),
	;

	private final String defaultDisplayName;
	private final boolean includedByDefault;

	private RelicFieldEnum() {
		this(null);
	}

	private RelicFieldEnum(String defaultDisplayName) {
		this(defaultDisplayName, true);
	}

	private RelicFieldEnum(String defaultDisplayName, boolean includedByDefault) {
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

//	public static List<RelicFieldEnum> get48HrFields() {
//		List<RelicFieldEnum> fields = new ArrayList<>();
//		fields.add(AvgInt48Hr);
//		fields.add(AvgEx48Hr);
//		fields.add(AvgFlaw48Hr);
//		fields.add(AvgRad48Hr);
//		fields.add(LowInt48Hr);
//		fields.add(HighInt48Hr);
//		fields.add(LowRad48hr);
//		fields.add(HighRad48Hr);
//		fields.add(NumSalesInt48Hr);
//		fields.add(NumSalesEx48Hr);
//		fields.add(NumSalesFlaw48Hr);
//		fields.add(NumSalesRad48Hr);
//		return fields;
//	}
//
//	public static List<RelicFieldEnum> get90DayFields() {
//		List<RelicFieldEnum> fields = new ArrayList<>();
//		fields.add(AvgInt90Day);
//		fields.add(AvgEx90Day);
//		fields.add(AvgFlaw90Day);
//		fields.add(AvgRad90Day);
//		fields.add(LowInt90Day);
//		fields.add(HighInt90Day);
//		fields.add(LowRad90Day);
//		fields.add(HighRad90Day);
//		fields.add(NumSalesInt90Day);
//		fields.add(NumSalesEx90Day);
//		fields.add(NumSalesFlaw90Day);
//		fields.add(NumSalesRad90Day);
//		return fields;
//	}
//
//	public static List<RelicFieldEnum> getDataFields() {
//		List<RelicFieldEnum> fields = new ArrayList<>();
//		fields.add(CommonDrop1);
//		fields.add(CommonDrop2);
//		fields.add(CommonDrop3);
//		fields.add(UncommonDrop1);
//		fields.add(UncommonDrop2);
//		fields.add(RareDrop);
//		fields.add(CommonDrop1DucatVal);
//		fields.add(CommonDrop1AvgPlatVal48Hrs);
//		fields.add(CommonDrop1AvgPlatVal90Days);
//		fields.add(CommonDrop2DucatVal);
//		fields.add(CommonDrop2AvgPlatVal48Hrs);
//		fields.add(CommonDrop2AvgPlatVal90Days);
//		fields.add(CommonDrop3DucatVal);
//		fields.add(CommonDrop3AvgPlatVal48Hrs);
//		fields.add(CommonDrop3AvgPlatVal90Days);
//		fields.add(UncommonDrop1DucatVal);
//		fields.add(UncommonDrop1AvgPlatVal48Hrs);
//		fields.add(UncommonDrop1AvgPlatVal90Days);
//		fields.add(UncommonDrop2DucatVal);
//		fields.add(UncommonDrop2AvgPlatVal48Hrs);
//		fields.add(UncommonDrop2AvgPlatVal90Days);
//		fields.add(RareDropDucatVal);
//		fields.add(RareDropAvgPlatVal48Hrs);
//		fields.add(RareDropAvgPlatVal90Days);
//		fields.add(CommonDrop1ItemName);
//		fields.add(CommonDrop1PartName);
//		fields.add(CommonDrop2ItemName);
//		fields.add(CommonDrop2PartName);
//		fields.add(CommonDrop3ItemName);
//		fields.add(CommonDrop3PartName);
//		fields.add(UncommonDrop1ItemName);
//		fields.add(UncommonDrop1PartName);
//		fields.add(UncommonDrop2ItemName);
//		fields.add(UncommonDrop2PartName);
//		fields.add(RareDropItemName);
//		fields.add(RareDropPartName);
//		fields.add(DropsForma);
//		fields.add(DropsFormaCommon);
//		fields.add(DropsFormaUncommon);
//		fields.add(IsVaulted);
//		fields.add(IsBaroExclusive);
//		fields.add(AvgDucatValInt);
//		fields.add(AvgDucatValEx);
//		fields.add(AvgDucatValFlaw);
//		fields.add(AvgDucatValRad);
//		fields.add(AvgPlatVal48HrsInt);
//		fields.add(AvgPlatVal48HrsEx);
//		fields.add(AvgPlatVal48HrsFlaw);
//		fields.add(AvgPlatVal48HrsRad);
//		fields.add(AvgPlatVal90DaysInt);
//		fields.add(AvgPlatVal90DaysEx);
//		fields.add(AvgPlatVal90DaysFlaw);
//		fields.add(AvgPlatVal90DaysRad);
//		fields.add(BestAvgDucatRefinement);
//		fields.add(BestAvgPlatRefinement48Hrs);
//		fields.add(BestAvgPlatRefinement90Days);
//		fields.add(AvgDucatValChangeIntEx);
//		fields.add(AvgDucatValChangeIntFlaw);
//		fields.add(AvgDucatValChangeIntRad);
//		fields.add(AvgDucatValChangeExFlaw);
//		fields.add(AvgDucatValChangeExRad);
//		fields.add(AvgDucatValChangeFlawRad);
//		fields.add(AvgPlatVal48HrsChangeIntEx);
//		fields.add(AvgPlatVal48HrsChangeIntFlaw);
//		fields.add(AvgPlatVal48HrsChangeIntRad);
//		fields.add(AvgPlatVal48HrsChangeExFlaw);
//		fields.add(AvgPlatVal48HrsChangeExRad);
//		fields.add(AvgPlatVal48HrsChangeFlawRad);
//		fields.add(AvgPlatVal90DaysChangeIntEx);
//		fields.add(AvgPlatVal90DaysChangeIntFlaw);
//		fields.add(AvgPlatVal90DaysChangeIntRad);
//		fields.add(AvgPlatVal90DaysChangeExFlaw);
//		fields.add(AvgPlatVal90DaysChangeExRad);
//		fields.add(AvgPlatVal90DaysChangeFlawRad);
//		fields.add(AvgDucatValChangePerTraceIntEx);
//		fields.add(AvgDucatValChangePerTraceIntFlaw);
//		fields.add(AvgDucatValChangePerTraceIntRad);
//		fields.add(AvgDucatValChangePerTraceExFlaw);
//		fields.add(AvgDucatValChangePerTraceExRad);
//		fields.add(AvgDucatValChangePerTraceFlawRad);
//		fields.add(AvgPlatVal48HrsChangePerTraceIntEx);
//		fields.add(AvgPlatVal48HrsChangePerTraceIntFlaw);
//		fields.add(AvgPlatVal48HrsChangePerTraceIntRad);
//		fields.add(AvgPlatVal48HrsChangePerTraceExFlaw);
//		fields.add(AvgPlatVal48HrsChangePerTraceExRad);
//		fields.add(AvgPlatVal48HrsChangePerTraceFlawRad);
//		fields.add(AvgPlatVal90DaysChangePerTraceIntEx);
//		fields.add(AvgPlatVal90DaysChangePerTraceIntFlaw);
//		fields.add(AvgPlatVal90DaysChangePerTraceIntRad);
//		fields.add(AvgPlatVal90DaysChangePerTraceExFlaw);
//		fields.add(AvgPlatVal90DaysChangePerTraceExRad);
//		fields.add(AvgPlatVal90DaysChangePerTraceFlawRad);
//		fields.add(IntShareAvgDucatVal);
//		fields.add(IntShareAvgPlatVal48Hrs);
//		fields.add(IntShareAvgPlatVal90Days);
//		fields.add(RadShareAvgDucatVal);
//		fields.add(RadShareAvgPlatValue48Hrs);
//		fields.add(RadShareAvgPlatValue90Days);
//		return fields;
//	}
}
