package main.results;

import java.util.List;

import enums.ItemType;
import enums.fields.ModFieldEnum;
import gui.IOptionProvider;

public class ModTypeResults extends TypeResults<ModFieldEnum> {

	ModTypeResults(IOptionProvider optionProvider) {
		super(ModFieldEnum.class, ItemType.MOD, optionProvider);
	}

	@Override
	public List<ModFieldEnum> getTradeStatsHeaderFields48Hrs() {
		return ModFieldEnum.get48HrFields();
	}

	@Override
	public List<ModFieldEnum> getTradeStatsHeaderFields90Days() {
		return ModFieldEnum.get90DayFields();
	}

	@Override
	public List<ModFieldEnum> getDataHeaderFields() {
		return ModFieldEnum.getDataFields();
	}

}
