package enums.jsonProps;

import enums.jsonProps.interfaces.JSONPropName;

public enum WarframeWikiPropName implements JSONPropName{
	DATA("data"), //JSONObject
	RELICS("relics"), //JSONArray
	RELIC_ERA("Tier"), //String
	RELIC_NAME("name"), //String
	RELIC_DROPS("drops"), //JSONArray
	IS_VAULTED("isVaulted"), //Int
	DROPPED_ITEM_NAME("item"), //String
	DROPPED_ITEM_PART("part"), //String
	DROPPED_ITEM_RARITY("rarity"); //String
	
	public final String value;
	
	private WarframeWikiPropName(String value){
		this.value = value;
	}
	
	@Override
	public String getValue(){
		return value;
	}
}
