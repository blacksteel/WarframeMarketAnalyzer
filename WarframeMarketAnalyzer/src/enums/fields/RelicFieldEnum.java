package enums.fields;

import java.util.ArrayList;
import java.util.List;

public enum RelicFieldEnum implements IFieldEnum {
	HrAvgInt48("48HrAvg(Int)"),
	HrAvgEx48("48HrAvg(Ex)"),
	HrAvgFlaw48("48HrAvg(Flaw)"),
	HrAvgRad48("48HrAvg(Rad)"),
	HrLowInt48("48HrLow(Int)"),
	HrHighInt48("48HrHigh(Int)"),
	HrLowRad48("48HrLow(Rad)"),
	HrHighRad48("48HrHigh(Rad)"),
	HrNumSalesInt48("48HrNumSales(Int)"),
	HrNumSalesEx48("48HrNumSales(Ex)"),
	HrNumSalesFlaw48("48HrNumSales(Flaw)"),
	HrNumSalesRad48("48HrNumSales(Rad)"),
	DayAvgInt90("90DayAvg(Int)"),
	DayAvgEx90("90DayAvg(Ex)"),
	DayAvgFlaw90("90DayAvg(Flaw)"),
	DayAvgRad90("90DayAvg(Rad)"),
	DayLowInt90("90DayLow(Int)"),
	DayHighInt90("90DayHigh(Int)"),
	DayLowRad90("90DayLow(Rad)"),
	DayHighRad90("90DayHigh(Rad)"),
	DayNumSalesInt90("90DayNumSales(Int)"),
	DayNumSalesEx90("90DayNumSales(Ex)"),
	DayNumSalesFlaw90("90DayNumSales(Flaw)"),
	DayNumSalesRad90("90DayNumSales(Rad)"),
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

	private String defaultDisplayName;
	
	private RelicFieldEnum() {
		this(null);
	}

	private RelicFieldEnum(String defaultDisplayName) {
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
	
	public static List<RelicFieldEnum> get48HrFields() {
		List<RelicFieldEnum> fields = new ArrayList<>();
		fields.add(HrAvgInt48);
		fields.add(HrAvgEx48);
		fields.add(HrAvgFlaw48);
		fields.add(HrAvgRad48);
		fields.add(HrLowInt48);
		fields.add(HrHighInt48);
		fields.add(HrLowRad48);
		fields.add(HrHighRad48);
		fields.add(HrNumSalesInt48);
		fields.add(HrNumSalesEx48);
		fields.add(HrNumSalesFlaw48);
		fields.add(HrNumSalesRad48);
		return fields;
	}
	
	public static List<RelicFieldEnum> get90DayFields() {
		List<RelicFieldEnum> fields = new ArrayList<>();
		fields.add(DayAvgInt90);
		fields.add(DayAvgEx90);
		fields.add(DayAvgFlaw90);
		fields.add(DayAvgRad90);
		fields.add(DayLowInt90);
		fields.add(DayHighInt90);
		fields.add(DayLowRad90);
		fields.add(DayHighRad90);
		fields.add(DayNumSalesInt90);
		fields.add(DayNumSalesEx90);
		fields.add(DayNumSalesFlaw90);
		fields.add(DayNumSalesRad90);
		return fields;
	}
	
	public static List<RelicFieldEnum> getDataFields() {
		List<RelicFieldEnum> fields = new ArrayList<>();
		fields.add(CommonDrop1);
		fields.add(CommonDrop2);
		fields.add(CommonDrop3);
		fields.add(UncommonDrop1);
		fields.add(UncommonDrop2);
		fields.add(RareDrop);
		fields.add(CommonDrop1DucatVal);
		fields.add(CommonDrop1AvgPlatVal48Hrs);
		fields.add(CommonDrop1AvgPlatVal90Days);
		fields.add(CommonDrop2DucatVal);
		fields.add(CommonDrop2AvgPlatVal48Hrs);
		fields.add(CommonDrop2AvgPlatVal90Days);
		fields.add(CommonDrop3DucatVal);
		fields.add(CommonDrop3AvgPlatVal48Hrs);
		fields.add(CommonDrop3AvgPlatVal90Days);
		fields.add(UncommonDrop1DucatVal);
		fields.add(UncommonDrop1AvgPlatVal48Hrs);
		fields.add(UncommonDrop1AvgPlatVal90Days);
		fields.add(UncommonDrop2DucatVal);
		fields.add(UncommonDrop2AvgPlatVal48Hrs);
		fields.add(UncommonDrop2AvgPlatVal90Days);
		fields.add(RareDropDucatVal);
		fields.add(RareDropAvgPlatVal48Hrs);
		fields.add(RareDropAvgPlatVal90Days);
		fields.add(CommonDrop1ItemName);
		fields.add(CommonDrop1PartName);
		fields.add(CommonDrop2ItemName);
		fields.add(CommonDrop2PartName);
		fields.add(CommonDrop3ItemName);
		fields.add(CommonDrop3PartName);
		fields.add(UncommonDrop1ItemName);
		fields.add(UncommonDrop1PartName);
		fields.add(UncommonDrop2ItemName);
		fields.add(UncommonDrop2PartName);
		fields.add(RareDropItemName);
		fields.add(RareDropPartName);
		fields.add(DropsForma);
		fields.add(DropsFormaCommon);
		fields.add(DropsFormaUncommon);
		fields.add(IsVaulted);
		fields.add(IsBaroExclusive);
		fields.add(AvgDucatValInt);
		fields.add(AvgDucatValEx);
		fields.add(AvgDucatValFlaw);
		fields.add(AvgDucatValRad);
		fields.add(AvgPlatVal48HrsInt);
		fields.add(AvgPlatVal48HrsEx);
		fields.add(AvgPlatVal48HrsFlaw);
		fields.add(AvgPlatVal48HrsRad);
		fields.add(AvgPlatVal90DaysInt);
		fields.add(AvgPlatVal90DaysEx);
		fields.add(AvgPlatVal90DaysFlaw);
		fields.add(AvgPlatVal90DaysRad);
		fields.add(BestAvgDucatRefinement);
		fields.add(BestAvgPlatRefinement48Hrs);
		fields.add(BestAvgPlatRefinement90Days);
		fields.add(AvgDucatValChangeIntEx);
		fields.add(AvgDucatValChangeIntFlaw);
		fields.add(AvgDucatValChangeIntRad);
		fields.add(AvgDucatValChangeExFlaw);
		fields.add(AvgDucatValChangeExRad);
		fields.add(AvgDucatValChangeFlawRad);
		fields.add(AvgPlatVal48HrsChangeIntEx);
		fields.add(AvgPlatVal48HrsChangeIntFlaw);
		fields.add(AvgPlatVal48HrsChangeIntRad);
		fields.add(AvgPlatVal48HrsChangeExFlaw);
		fields.add(AvgPlatVal48HrsChangeExRad);
		fields.add(AvgPlatVal48HrsChangeFlawRad);
		fields.add(AvgPlatVal90DaysChangeIntEx);
		fields.add(AvgPlatVal90DaysChangeIntFlaw);
		fields.add(AvgPlatVal90DaysChangeIntRad);
		fields.add(AvgPlatVal90DaysChangeExFlaw);
		fields.add(AvgPlatVal90DaysChangeExRad);
		fields.add(AvgPlatVal90DaysChangeFlawRad);
		fields.add(AvgDucatValChangePerTraceIntEx);
		fields.add(AvgDucatValChangePerTraceIntFlaw);
		fields.add(AvgDucatValChangePerTraceIntRad);
		fields.add(AvgDucatValChangePerTraceExFlaw);
		fields.add(AvgDucatValChangePerTraceExRad);
		fields.add(AvgDucatValChangePerTraceFlawRad);
		fields.add(AvgPlatVal48HrsChangePerTraceIntEx);
		fields.add(AvgPlatVal48HrsChangePerTraceIntFlaw);
		fields.add(AvgPlatVal48HrsChangePerTraceIntRad);
		fields.add(AvgPlatVal48HrsChangePerTraceExFlaw);
		fields.add(AvgPlatVal48HrsChangePerTraceExRad);
		fields.add(AvgPlatVal48HrsChangePerTraceFlawRad);
		fields.add(AvgPlatVal90DaysChangePerTraceIntEx);
		fields.add(AvgPlatVal90DaysChangePerTraceIntFlaw);
		fields.add(AvgPlatVal90DaysChangePerTraceIntRad);
		fields.add(AvgPlatVal90DaysChangePerTraceExFlaw);
		fields.add(AvgPlatVal90DaysChangePerTraceExRad);
		fields.add(AvgPlatVal90DaysChangePerTraceFlawRad);
		fields.add(IntShareAvgDucatVal);
		fields.add(IntShareAvgPlatVal48Hrs);
		fields.add(IntShareAvgPlatVal90Days);
		fields.add(RadShareAvgDucatVal);
		fields.add(RadShareAvgPlatValue48Hrs);
		fields.add(RadShareAvgPlatValue90Days);
		return fields;
	}
}
