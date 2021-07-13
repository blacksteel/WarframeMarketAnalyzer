package dataSourceHandlers;

import static enums.PrimePartType.WARFRAME;
import static enums.PrimePartType.WEAPON;
import static enums.comparable.MiscWarframeTerms.KAVASA;
import static enums.comparable.MiscWarframeTerms.PRIME;
import static enums.comparable.MiscWarframeTerms.SET;
import static enums.comparable.UniqueNameSnippet.FLAWED_MOD_UNIQUE_NAME_SNIPPET;
import static enums.jsonProps.WarframeStatusPropName.COMPONENTS;
import static enums.jsonProps.WarframeStatusPropName.DUCATS;
import static enums.jsonProps.WarframeStatusPropName.NUM_IN_SET;
import static enums.jsonProps.WarframeStatusPropName.SIMPLE_NAME;
import static enums.jsonProps.WarframeStatusPropName.UNIQUE_NAME;
import static enums.jsonProps.WarframeStatusPropName.VAULTED;
import static main.MainRunner.getGson;
import static utils.JSONUtils.getBoolProp;
import static utils.JSONUtils.getIntProp;
import static utils.JSONUtils.getJsonArray;
import static utils.JSONUtils.getStrProp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import enums.PrimePartType;
import enums.fields.ModFieldEnum;
import enums.fields.PrimeFieldEnum;
import enums.hopefullyTemporaryPrimeEnums.PrimeArchwing;
import enums.hopefullyTemporaryPrimeEnums.PrimeCompanion;
import enums.hopefullyTemporaryPrimeEnums.PrimeSpecialCase;
import enums.hopefullyTemporaryPrimeEnums.interfaces.PrimeItemEnum;
import items.Mod;
import items.PrimePart;
import items.WarframeItem;
import main.results.TypeResults;

public class WarframeStatusHandler extends DataSourceHandler{
	private static final String WF_STATUS_BASE_URL = "https://api.warframestat.us";
	private static final String WF_STATUS_URL_MODS_SUFFIX = "/mods";
	private static final String WF_STATUS_URL_WEAPONS_SUFFIX = "/weapons";
	private static final String WF_STATUS_URL_FRAMES_SUFFIX = "/warframes";
	private static final String WF_STATUS_URL_ITEM_LOOKUP_BASE_SUFFIX = "/items/";
	
	private static final ItemComparator comparator = new ItemComparator();
	
	public WarframeStatusHandler(){
		super(WF_STATUS_BASE_URL);
	}
	
	public List<Mod> handleMods(TypeResults<ModFieldEnum> typeResults) throws IOException, InterruptedException{
		String modsPayload = getFromDataSource(WF_STATUS_URL_MODS_SUFFIX, new String[]{}, new String[]{});
		JsonArray modsArray = getGson().fromJson(modsPayload, JsonArray.class);
		List<Mod> tradableModsList = new ArrayList<>();
		
		for(JsonElement element: modsArray){
			if(!FLAWED_MOD_UNIQUE_NAME_SNIPPET.containsValue(getStrProp(element, UNIQUE_NAME)) &&
					WarframeMarketHandler.isTradableItem(getStrProp(element, SIMPLE_NAME))){
				tradableModsList.add(new Mod((JsonObject)element, typeResults));
			}
		}

		tradableModsList.sort(comparator);
		
		return tradableModsList;
	}

	public List<PrimePart> handlePrimes(TypeResults<PrimeFieldEnum> typeResults) throws IOException, InterruptedException{
		String framesPayload = getFromDataSource(WF_STATUS_URL_FRAMES_SUFFIX, new String[]{}, new String[]{});
		JsonArray framesArray = getGson().fromJson(framesPayload, JsonArray.class);

		List<PrimePart> primePartsList = new ArrayList<>();

		for(JsonElement element: framesArray){
			if(PRIME.endsWithValue(getStrProp(element, SIMPLE_NAME))){
				//Prime Warframe
				primePartsList.addAll(getPrimeParts(element, WARFRAME, typeResults));
			}
		}

		String weaponsPayload = getFromDataSource(WF_STATUS_URL_WEAPONS_SUFFIX, new String[]{}, new String[]{});
		JsonArray weaponList = getGson().fromJson(weaponsPayload, JsonArray.class);

		for(JsonElement element: weaponList){
			if(PRIME.endsWithValue(getStrProp(element, SIMPLE_NAME))){
				//Prime Weapon
				primePartsList.addAll(getPrimeParts(element, WEAPON, typeResults));
			}
		}

		List<PrimeItemEnum> otherPrimes = new ArrayList<>();
		otherPrimes.addAll(Arrays.asList(PrimeArchwing.values()));
		otherPrimes.addAll(Arrays.asList(PrimeCompanion.values()));
		otherPrimes.addAll(Arrays.asList(PrimeSpecialCase.values()));

		for(PrimeItemEnum item: otherPrimes){
			String payload = getFromDataSource(getWFStatusURLItemLookupSuffix(item.getItemName()), new String[]{}, new String[]{});
			JsonObject jsonObject = getGson().fromJson(payload, JsonObject.class);
			primePartsList.addAll(getPrimeParts(jsonObject, item.getType(), typeResults));
		}

		primePartsList.sort(comparator);
		
		return primePartsList;
	}

	private static List<PrimePart> getPrimeParts(JsonElement primeItemObjectElement, PrimePartType type, TypeResults<PrimeFieldEnum> typeResults){
		return getPrimeParts((JsonObject)primeItemObjectElement, type, typeResults);
	}

	private static List<PrimePart> getPrimeParts(JsonObject primeItemObject, PrimePartType type, TypeResults<PrimeFieldEnum> typeResults){
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
					
					//Special case
					if(KAVASA.containsValue(partName)){
						partName = partName.replace("Kavasa Prime", "").trim();
					}
					
					int numInFullSet = getIntProp(componentObject, NUM_IN_SET);
					int ducats = getIntProp(componentObject, DUCATS);

					primeParts.add(new PrimePart(itemName, partName, type.value, isVaulted, false, numInFullSet, ducats, typeResults));

					fullSetDucatVal += numInFullSet*ducats;
				}
			}

			//Add the entire set as an item to check
			primeParts.add(new PrimePart(itemName, SET.value, type.value, isVaulted, true, null, fullSetDucatVal, typeResults));
		}

		return primeParts;
	}
	
	private static String getWFStatusURLItemLookupSuffix(String itemName){
		itemName = itemName.trim().toLowerCase().replace(" ", "%20");
		return WF_STATUS_URL_ITEM_LOOKUP_BASE_SUFFIX + itemName;
	}
	
	private static class ItemComparator implements Comparator<WarframeItem>{
		@Override
		public int compare(WarframeItem item1, WarframeItem item2){
			return item1.getName().compareTo(item2.getName());
		}
	}
}
