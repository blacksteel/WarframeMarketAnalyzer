package enums.jsonProps;

import enums.jsonProps.interfaces.JSONPropName;

public enum WarframeStatusPropName implements JSONPropName{
	MAX_RANK("fusionLimit"), //Int
	MOD_SET("modSet"), //Str
	AUGMENT("isAugment"), //Bool
	RARITY("rarity"), //Str
	TYPE("type"), //Str, name of item type mod is for
	COMPAT_NAME("compatName"), //Str, name of item type mod is for - needed for some Parazon stuff to work properly
	NUM_IN_SET("itemCount"), //Int
	DUCATS("ducats"), //Int
	SIMPLE_NAME("name"), //String: In-game name
	UNIQUE_NAME("uniqueName"), //String: Item UID
	TRADABLE("tradable"), //Bool, unreliable, do not use for now
	COMPONENTS("components"), //JsonArray
	VAULTED("vaulted"); //Bool
	
	public final String value;
	
	private WarframeStatusPropName(String value){
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}
}