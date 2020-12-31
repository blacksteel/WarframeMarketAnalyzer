package enums;

import enums.fields.RelicFieldEnum;

public enum VoidRelicRefinement{
	INTACT("intact", 0, 0.2533, 0.11, 0.02,
					RelicFieldEnum.AvgInt48Hr,
					RelicFieldEnum.HighInt48Hr,
					RelicFieldEnum.LowInt48Hr,
					RelicFieldEnum.NumSalesInt48Hr,
					RelicFieldEnum.AvgInt90Day,
					RelicFieldEnum.LowInt90Day,
					RelicFieldEnum.HighInt90Day,
					RelicFieldEnum.NumSalesInt90Day),
	EXCEPTIONAL("exceptional", 25, 0.2333, 0.13, 0.04,
					RelicFieldEnum.AvgEx48Hr,
					RelicFieldEnum.HighEx48Hr,
					RelicFieldEnum.LowEx48Hr,
					RelicFieldEnum.NumSalesEx48Hr,
					RelicFieldEnum.AvgEx90Day,
					RelicFieldEnum.LowEx90Day,
					RelicFieldEnum.HighEx90Day,
					RelicFieldEnum.NumSalesEx90Day),
	FLAWLESS("flawless", 50, 0.2, 0.17, 0.06,
					RelicFieldEnum.AvgFlaw48Hr,
					RelicFieldEnum.HighFlaw48Hr,
					RelicFieldEnum.LowFlaw48Hr,
					RelicFieldEnum.NumSalesFlaw48Hr,
					RelicFieldEnum.AvgFlaw90Day,
					RelicFieldEnum.LowFlaw90Day,
					RelicFieldEnum.HighFlaw90Day,
					RelicFieldEnum.NumSalesFlaw90Day),
	RADIANT("radiant", 100, 0.1667, 0.2, 0.1,
					RelicFieldEnum.AvgRad48Hr,
					RelicFieldEnum.HighRad48Hr,
					RelicFieldEnum.LowRad48hr,
					RelicFieldEnum.NumSalesRad48Hr,
					RelicFieldEnum.AvgRad90Day,
					RelicFieldEnum.LowRad90Day,
					RelicFieldEnum.HighRad90Day,
					RelicFieldEnum.NumSalesRad90Day);
	
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
