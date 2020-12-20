package main;

import static enums.PrimePartType.*;
import static enums.comparable.MiscWarframeTerms.*;
import static enums.comparable.UniqueNameSnippet.*;
import static enums.jsonProps.DataSourcePropName.*;
import static utils.JSONUtils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import enums.PrimePartType;
import enums.hopefullyTemporaryPrimeEnums.PrimeArchwing;
import enums.hopefullyTemporaryPrimeEnums.PrimeCompanion;
import enums.hopefullyTemporaryPrimeEnums.PrimeSpecialCase;
import enums.hopefullyTemporaryPrimeEnums.interfaces.PrimeItemEnum;
import items.Mod;
import items.PrimePart;
import items.WarframeItem;
import utils.MiscUtils;

public class DataSourceHandler{
	private static final String DATA_SOURCE_BASE_URL = "https://api.warframestat.us/";
	private static final String DATA_SOURCE_MODS_URL = DATA_SOURCE_BASE_URL + "mods";
	private static final String DATA_SOURCE_WEAPONS_URL = DATA_SOURCE_BASE_URL + "weapons";
	private static final String DATA_SOURCE_FRAMES_URL = DATA_SOURCE_BASE_URL + "warframes";
	private static final String DATA_SOURCE_ITEM_LOOKUP_URL = DATA_SOURCE_BASE_URL + "items/";
	
	private static final ItemComparator comparator = new ItemComparator();
	
	protected static List<Mod> handleMods(Gson gson) throws IOException, InterruptedException{
		String modsPayload = MiscUtils.getFromServer(DATA_SOURCE_MODS_URL, new String[]{}, new String[]{});
		JsonArray modList = gson.fromJson(modsPayload, JsonArray.class);
		List<Mod> tradableMods = new ArrayList<>();

		//Unforunately we have to do this hacky workaround because our data source's "tradable" property
		//has proven to be unreliable
		List<String> tradableItemNames = MarketHandler.getTradableItemNamesList();
		
		for(JsonElement element: modList){
			if(!FLAWED_MOD_UNIQUE_NAME_SNIPPET.containsValue(getStrProp(element, UNIQUE_NAME)) &&
					tradableItemNames.contains(getStrProp(element, SIMPLE_NAME))){
				tradableMods.add(new Mod(element));
			}
		}

		tradableMods.sort(comparator);
		
		return tradableMods;
	}

	protected static List<PrimePart> handlePrimes(Gson gson) throws IOException, InterruptedException{
		String framesPayload = MiscUtils.getFromServer(DATA_SOURCE_FRAMES_URL, new String[]{}, new String[]{});
		JsonArray frameList = gson.fromJson(framesPayload, JsonArray.class);

		List<PrimePart> tradablePrimeParts = new ArrayList<>();

		for(JsonElement element: frameList){
			if(PRIME.endsWithValue(getStrProp(element, SIMPLE_NAME))){
				//Prime Warframe
				tradablePrimeParts.addAll(getPrimeParts(element, WARFRAME));
			}
		}

		String weaponsPayload = MiscUtils.getFromServer(DATA_SOURCE_WEAPONS_URL, new String[]{}, new String[]{});
		JsonArray weaponList = gson.fromJson(weaponsPayload, JsonArray.class);

		for(JsonElement element: weaponList){
			if(PRIME.endsWithValue(getStrProp(element, SIMPLE_NAME))){
				//Prime Weapon
				tradablePrimeParts.addAll(getPrimeParts(element, WEAPON));
			}
		}

		List<PrimeItemEnum> otherPrimes = new ArrayList<>();
		otherPrimes.addAll(Arrays.asList(PrimeArchwing.values()));
		otherPrimes.addAll(Arrays.asList(PrimeCompanion.values()));
		otherPrimes.addAll(Arrays.asList(PrimeSpecialCase.values()));

		for(PrimeItemEnum item: otherPrimes){
			String payload = MiscUtils.getFromServer(getFinalDataSourceLookupURL(item.getItemName()), new String[]{}, new String[]{});
			JsonObject jsonObject = gson.fromJson(payload, JsonObject.class);
			tradablePrimeParts.addAll(getPrimeParts(jsonObject, item.getType()));
		}

		tradablePrimeParts.sort(comparator);
		
		return tradablePrimeParts;
	}

	private static List<PrimePart> getPrimeParts(JsonElement primeItemObjectElement, PrimePartType type){
		return getPrimeParts((JsonObject)primeItemObjectElement, type);
	}

	private static List<PrimePart> getPrimeParts(JsonObject primeItemObject, PrimePartType type){
		List<PrimePart> primeParts = new ArrayList<>();

		JsonArray componentsArray = getJsonArray(primeItemObject, COMPONENTS);

		if(componentsArray == null){
			//Prime item that is not built from Prime parts. Excalibur Prime, Sweeper & Deconstructor Prime, etc
			//Do nothing for now
		}
		else{
			String itemName = getStrProp(primeItemObject, SIMPLE_NAME);

			boolean isVaulted = primeItemObject.has(VAULTED.value) ? getBoolProp(primeItemObject, VAULTED) : false;

			int fullSetDucatVal = 0;

			for(JsonElement component: componentsArray){
				JsonObject componentObject = (JsonObject)component;
				
				if(componentObject.has(DUCATS.value)) {
					//Prime part
					String partName = getStrProp(componentObject, SIMPLE_NAME);
					int numInFullSet = getIntProp(componentObject, NUM_IN_SET);
					int ducats = getIntProp(componentObject, DUCATS);

					primeParts.add(new PrimePart(itemName, partName, type.value, isVaulted, false, numInFullSet, ducats));

					fullSetDucatVal += numInFullSet*ducats;
				}
			}

			//Add the entire set as an item to check
			primeParts.add(new PrimePart(itemName, SET.value, type.value, isVaulted, true, null, fullSetDucatVal));
		}

		return primeParts;
	}
	
	private static String getFinalDataSourceLookupURL(String itemName){
		itemName = itemName.trim().toLowerCase().replace(" ", "%20");
		return DATA_SOURCE_ITEM_LOOKUP_URL + itemName;
	}
	
	private static class ItemComparator implements Comparator<WarframeItem>{
		@Override
		public int compare(WarframeItem item1, WarframeItem item2){
			return item1.name.compareTo(item2.name);
		}
	}
}
