package dataSourceHandlers;

import static enums.jsonProps.WarframeWikiPropName.DATA;
import static enums.jsonProps.WarframeWikiPropName.RELICS;
import static main.MainRunner.getGson;
import static utils.JSONUtils.getJsonArray;
import static utils.JSONUtils.getJsonObj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import enums.VoidRelicRefinement;
import enums.fields.RelicFieldEnum;
import items.PrimePart;
import items.VoidRelic;
import main.Config;
import main.results.TypeResults;

public class WarframeWikiHandler extends DataSourceHandler{

	private static final String WF_WIKI_BASE_URL = "https://wf.snekw.com";
	private static final String WF_WIKI_URL_VOID_RELICS_SUFFIX = "/void-wiki";

	public WarframeWikiHandler(){
		super(WF_WIKI_BASE_URL);
	}

	public List<VoidRelic> handleVoidRelics(List<PrimePart> primePartsList, TypeResults<RelicFieldEnum> typeResults) throws IOException{
		String relicsPayload = getFromDataSource(WF_WIKI_URL_VOID_RELICS_SUFFIX, new String[]{}, new String[]{});
		JsonObject relicsObject = getGson().fromJson(relicsPayload, JsonObject.class);
		JsonArray relicsArray = getJsonArray(getJsonObj(relicsObject, DATA), RELICS);

		List<VoidRelic> relicsList = new ArrayList<>();

		Map<String, PrimePart> primePartNamesToPartsMap = new HashMap<>();
		for(PrimePart part: primePartsList){
			primePartNamesToPartsMap.put(part.getName().toUpperCase(), part);
		}

		for(JsonElement element: relicsArray){
			String relicName = VoidRelic.getName(element);

			if(WarframeMarketHandler.isTradableItem(relicName + " " + VoidRelicRefinement.INTACT.name)){
				relicsList.add(new VoidRelic((JsonObject)element, primePartNamesToPartsMap, typeResults));
			}
			else if(Config.WRITE_DEBUG_INFO_TO_CONSOLE){
				System.out.println("Failed to find tradable Void Relic: " + relicName);
			}
		}

		return relicsList;
	}
}