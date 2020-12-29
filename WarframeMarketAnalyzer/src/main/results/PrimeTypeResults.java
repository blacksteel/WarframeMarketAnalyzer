package main.results;

import java.util.List;

import enums.ItemType;
import enums.fields.PrimeFieldEnum;
import gui.IOptionProvider;

public class PrimeTypeResults extends TypeResults<PrimeFieldEnum> {

	PrimeTypeResults(IOptionProvider optionProvider) {
		super(PrimeFieldEnum.class, ItemType.PRIME_PART, optionProvider);
	}

	@Override
	public List<PrimeFieldEnum> getTradeStatsHeaderFields48Hrs() {
		return PrimeFieldEnum.get48HrFields();
	}

	@Override
	public List<PrimeFieldEnum> getTradeStatsHeaderFields90Days() {
		return PrimeFieldEnum.get90DayFields();
	}

	@Override
	public List<PrimeFieldEnum> getDataHeaderFields() {
		return PrimeFieldEnum.getDataFields();
	}

}
