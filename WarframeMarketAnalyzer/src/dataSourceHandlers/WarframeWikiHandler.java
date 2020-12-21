package dataSourceHandlers;

import static enums.jsonProps.WarframeWikiPropName.*;
import static utils.JSONUtils.*;
import static main.MainRunner.getGson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import items.PrimePart;
import items.VoidRelic;

public class WarframeWikiHandler extends DataSourceHandler{

	private static final String WF_WIKI_BASE_URL = "https://wf.snekw.com";
	private static final String WF_WIKI_URL_VOID_RELICS_SUFFIX = "/void-wiki";
	
	public WarframeWikiHandler(){
		super(WF_WIKI_BASE_URL);
	}

	public List<VoidRelic> handleVoidRelics(List<PrimePart> primePartsList) throws IOException{
		String relicsPayload = getFromServer(WF_WIKI_URL_VOID_RELICS_SUFFIX, new String[]{}, new String[]{});
		JsonObject relicsObject = getGson().fromJson(relicsPayload, JsonObject.class);
		JsonArray relicsArray = getJsonArray(getJsonObj(relicsObject, DATA), RELICS);
		
		List<VoidRelic> relicsList = new ArrayList<>();
		
		Map<String, PrimePart> primePartNamesToPartsMap = new HashMap<>();
		for(PrimePart part: primePartsList){
			primePartNamesToPartsMap.put(part.name, part);
		}
		
		for(JsonElement element: relicsArray){
			relicsList.add(new VoidRelic(element, primePartNamesToPartsMap));
		}
		
		return relicsList;
	}
}