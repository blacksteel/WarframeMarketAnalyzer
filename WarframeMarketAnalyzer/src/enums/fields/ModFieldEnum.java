package enums.fields;

import java.util.ArrayList;
import java.util.List;

public enum ModFieldEnum implements IFieldEnum {
	Name,
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
	
	public static List<ModFieldEnum> get48HrFields() {
		List<ModFieldEnum> fields = new ArrayList<>();
		fields.add(Avg48Hr);
		fields.add(Low48Hr);
		fields.add(High48Hr);
		fields.add(Num48Hr);
		return fields;
	}
	
	public static List<ModFieldEnum> get90DayFields() {
		List<ModFieldEnum> fields = new ArrayList<>();
		fields.add(Avg90Day);
		fields.add(Low90Day);
		fields.add(High90Day);
		fields.add(Num90Day);
		return fields;
	}
	
	public static List<ModFieldEnum> getDataFields() {
		List<ModFieldEnum> fields = new ArrayList<>();
		fields.add(Rank);
		fields.add(MaxRank);
		fields.add(Type);
		fields.add(Compatibility);
		fields.add(Rarity);
		fields.add(IsAugment);
		fields.add(IsSet);
		fields.add(IsConclaveOnly);
		return fields;
	}
}
