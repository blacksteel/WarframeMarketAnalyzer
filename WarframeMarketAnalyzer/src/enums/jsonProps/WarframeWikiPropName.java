package enums.jsonProps;

import enums.jsonProps.interfaces.JSONPropName;

public enum WarframeWikiPropName implements JSONPropName{
	DATA("data"), //JSONObject
	RELICS("Relics"), //JSONArray
	RELIC_ERA("Tier"), //String
	RELIC_NAME("Name"), //String
	RELIC_DROPS("Drops"), //JSONArray
	IS_VAULTED("IsVaulted"), //Int
	IS_BARO_EXCLUSIVE("IsBaro"), //Int
	DROPPED_ITEM_NAME("Item"), //String
	DROPPED_ITEM_PART("Part"), //String
	DROPPED_ITEM_RARITY("Rarity"); //String
	
	public final String value;
	
	private WarframeWikiPropName(String value){
		this.value = value;
	}
	
	@Override
	public String getValue(){
		return value;
	}
}
