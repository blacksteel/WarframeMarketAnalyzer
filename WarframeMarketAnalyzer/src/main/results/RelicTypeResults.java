package main.results;

import java.security.InvalidParameterException;
import java.util.Comparator;
import java.util.EnumMap;

import enums.ItemType;
import enums.comparable.VoidRelicEra;
import enums.fields.RelicFieldEnum;
import gui.IOptionProvider;
import main.Config;

public class RelicTypeResults extends TypeResults<RelicFieldEnum> {

	RelicTypeResults(IOptionProvider optionProvider) {
		super(RelicFieldEnum.class, RelicFieldEnum.NameEra, ItemType.VOID_RELIC, optionProvider);
	}

	@Override
	public Comparator<EnumMap<RelicFieldEnum, String>> getComparator() {
		return new RowComparator();
	}

	/**
	 * Sort results rows based on the name field
	 *
	 * TODO Need to ensure that the name field exists
	 * @author ladaa
	 *
	 */
	private class RowComparator implements Comparator<EnumMap<RelicFieldEnum, String>> {

		@Override
		public int compare(EnumMap<RelicFieldEnum, String> o1, EnumMap<RelicFieldEnum, String> o2) {
			int compareVal;
			if (o1.get(RelicFieldEnum.Era) != null && o1.get(RelicFieldEnum.Name) != null &&
					o2.get(RelicFieldEnum.Era) != null && o2.get(RelicFieldEnum.Name)  != null) {
				VoidRelicEra era1 = VoidRelicEra.getByValue(o1.get(RelicFieldEnum.Era));
				VoidRelicEra era2 = VoidRelicEra.getByValue(o2.get(RelicFieldEnum.Era));
				compareVal = era1.compareTo(era2);
				if (compareVal == 0) {
					String name1 = o1.get(RelicFieldEnum.Name);
					String name2 = o2.get(RelicFieldEnum.Name);
					compareVal = name1.compareTo(name2);
				}
			} else if (o1.get(RelicFieldEnum.NameEra) != null && o2.get(RelicFieldEnum.NameEra) != null) {
				compareVal = o1.get(RelicFieldEnum.NameEra).compareTo(o2.get(RelicFieldEnum.NameEra));
			} else {
				throw new InvalidParameterException("Relic doesn't have either NameEra or the combination of Name and Era");
			}

			return compareVal;
		}
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
