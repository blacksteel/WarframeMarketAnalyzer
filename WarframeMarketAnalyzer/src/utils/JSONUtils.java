package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import enums.jsonProps.interfaces.JSONPropName;

public class JSONUtils{
	public static boolean getBoolProp(JsonElement objectElement, JSONPropName propName){
		return getBoolProp((JsonObject)objectElement, propName);
	}
	
	public static boolean getBoolProp(JsonObject object, JSONPropName propName){
		return object.get(propName.getValue()).getAsBoolean();
	}
	
	public static double getDblProp(JsonElement objectElement, JSONPropName propName){
		return getDblProp((JsonObject)objectElement, propName);
	}
	
	public static double getDblProp(JsonObject object, JSONPropName propName){
		return object.get(propName.getValue()).getAsDouble();
	}
	
	public static int getIntProp(JsonElement objectElement, JSONPropName propName){
		return getIntProp((JsonObject)objectElement, propName);
	}
	
	public static int getIntProp(JsonObject object, JSONPropName propName){
		return object.get(propName.getValue()).getAsInt();
	}
	
	public static String getStrProp(JsonElement objectElement, JSONPropName propName){
		return getStrProp((JsonObject)objectElement, propName);
	}
	
	public static String getStrProp(JsonObject object, JSONPropName propName){
		return object.get(propName.getValue()).getAsString();
	}
	
	public static JsonArray getJsonArray(JsonElement objectElement, JSONPropName propName){
		return getJsonArray((JsonObject)objectElement, propName);
	}
	
	public static JsonArray getJsonArray(JsonObject object, JSONPropName propName){
		return object.getAsJsonArray(propName.getValue());
	}
	
	public static JsonObject getJsonObj(JsonElement objectElement, JSONPropName propName){
		return getJsonObj((JsonObject)objectElement, propName);
	}
	
	public static JsonObject getJsonObj(JsonObject object, JSONPropName propName){
		return object.getAsJsonObject(propName.getValue());
	}
}