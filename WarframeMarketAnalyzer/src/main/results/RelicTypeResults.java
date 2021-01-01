package main.results;

import enums.ItemType;
import enums.fields.RelicFieldEnum;
import gui.IOptionProvider;

public class RelicTypeResults extends TypeResults<RelicFieldEnum> {

	RelicTypeResults(IOptionProvider optionProvider) {
		super(RelicFieldEnum.class, RelicFieldEnum.Name, ItemType.VOID_RELIC, optionProvider);
	}

//	@Override
//	public List<RelicFieldEnum> getTradeStatsHeaderFields48Hrs() {
//		return RelicFieldEnum.get48HrFields();
//	}
//
//	@Override
//	public List<RelicFieldEnum> getTradeStatsHeaderFields90Days() {
//		return RelicFieldEnum.get90DayFields();
//	}
//
//	@Override
//	public List<RelicFieldEnum> getDataHeaderFields() {
//		return RelicFieldEnum.getDataFields();
//	}

}
