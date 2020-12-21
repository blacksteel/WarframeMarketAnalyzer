package enums.jsonProps;

import enums.jsonProps.interfaces.JSONPropName;

public enum WarframeMarketPropName implements JSONPropName{
	ITEMS("items"), //JsonArray
	ITEM_NAME("item_name"), //Str
	MIN_PRICE("min_price"), //Int
	MAX_PRICE("max_price"), //Int
	AVG_PRICE("avg_price"), //Int
	NUM_SALES("volume"), //Int
	RANK("mod_rank"), //Int
	TIME_PERIOD_90DAYS("90days"), //JsonArray
	TIME_PERIOD_48HRS("48hours"), //JsonArray
	PAYLOAD("payload"), //JsonObject
	CLOSED_TRADES_STATS("statistics_closed"); //JsonObject
	
	public final String value;
	
	private WarframeMarketPropName(String value){
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}
}