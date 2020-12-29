package enums;

import enums.fields.RelicFieldEnum;

public enum VoidRelicRefinement{
	INTACT("intact", 0, 0.2533, 0.11, 0.02,
					RelicFieldEnum.HrAvgInt48,
					RelicFieldEnum.HrHighInt48,
					RelicFieldEnum.HrLowInt48,
					RelicFieldEnum.HrNumSalesInt48,
					RelicFieldEnum.DayAvgInt90,
					RelicFieldEnum.DayLowInt90,
					RelicFieldEnum.DayHighInt90,
					RelicFieldEnum.DayNumSalesInt90),
	EXCEPTIONAL("exceptional", 25, 0.2333, 0.13, 0.04,
					RelicFieldEnum.HrAvgEx48,
					RelicFieldEnum.HrHighEx48,
					RelicFieldEnum.HrLowEx48,
					RelicFieldEnum.HrNumSalesEx48,
					RelicFieldEnum.DayAvgEx90,
					RelicFieldEnum.DayLowEx90,
					RelicFieldEnum.DayHighEx90,
					RelicFieldEnum.DayNumSalesEx90),
	FLAWLESS("flawless", 50, 0.2, 0.17, 0.06,
					RelicFieldEnum.HrAvgFlaw48,
					RelicFieldEnum.HrHighFlaw48,
					RelicFieldEnum.HrLowFlaw48,
					RelicFieldEnum.HrNumSalesFlaw48,
					RelicFieldEnum.DayAvgFlaw90,
					RelicFieldEnum.DayLowFlaw90,
					RelicFieldEnum.DayHighFlaw90,
					RelicFieldEnum.DayNumSalesFlaw90),
	RADIANT("radiant", 100, 0.1667, 0.2, 0.1,
					RelicFieldEnum.HrAvgRad48,
					RelicFieldEnum.HrHighRad48,
					RelicFieldEnum.HrLowRad48,
					RelicFieldEnum.HrNumSalesRad48,
					RelicFieldEnum.DayAvgRad90,
					RelicFieldEnum.DayLowRad90,
					RelicFieldEnum.DayHighRad90,
					RelicFieldEnum.DayNumSalesRad90);
	
	public final String name;
	public final int numVoidTracesToUpgrade;
	//Note: These are the probabilities of getting a particular item of that rarity to drop
	public final double commonDropChance;
	public final double uncommonDropChance;
	public final double rareDropChance;
	
	public final RelicFieldEnum HrAvg48;
	public final RelicFieldEnum HrHigh48;
	public final RelicFieldEnum HrLow48;
	public final RelicFieldEnum HrNum48;
	public final RelicFieldEnum DayAvg90;
	public final RelicFieldEnum DayHigh90;
	public final RelicFieldEnum DayLow90;
	public final RelicFieldEnum DayNum90;
	
	private VoidRelicRefinement(String name, int numVoidTracesToUpgrade, double commonDropChance,
			double uncommonDropChance, double rareDropChance, RelicFieldEnum HrAvg48, RelicFieldEnum HrHigh48,
			RelicFieldEnum HrLow48, RelicFieldEnum HrNum48, RelicFieldEnum DayAvg90, RelicFieldEnum DayHigh90,
			RelicFieldEnum DayLow90, RelicFieldEnum DayNum90){
		this.name = name;
		this.numVoidTracesToUpgrade = numVoidTracesToUpgrade;
		this.commonDropChance = commonDropChance;
		this.uncommonDropChance = uncommonDropChance;
		this.rareDropChance = rareDropChance;

		this.HrAvg48 = HrAvg48;
		this.HrHigh48 = HrHigh48;
		this.HrLow48 = HrLow48;
		this.HrNum48 = HrNum48;
		this.DayAvg90 = DayAvg90;
		this.DayHigh90 = DayHigh90;
		this.DayLow90 = DayLow90;
		this.DayNum90 = DayNum90;
	}
}
