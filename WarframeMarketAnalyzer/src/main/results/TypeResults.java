package main.results;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import enums.ItemType;
import enums.fields.IFieldEnum;
import gui.IOptionProvider;
import utils.FieldUtils;

public abstract class TypeResults <T extends Enum<T> & IFieldEnum> {
	
	private Class<T> enumClass;
	private ItemType type;
	private List<T> fieldList;

	private List<EnumMap<T, String>> results = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	TypeResults(Class<T> enumClass, ItemType type, IOptionProvider optionProvider) {
		this.enumClass = enumClass;
		this.type = type;
		switch (type) {
		case MOD:
			fieldList = (List<T>)optionProvider.getModFields();
			break;
		case PRIME_PART:
			fieldList = (List<T>)optionProvider.getPrimeFields();
			break;
		case VOID_RELIC:
			fieldList = (List<T>)optionProvider.getRelicFields();
			break;
		default:
			throw new InvalidParameterException("Invalid item type: "+type);
		}
	}
	
	public ItemType getType() {
		return type;
	}
	
	public boolean hasField(String fieldName) {
		// TODO find field
		return true;
	}
	
	public boolean hasField(T field) {
		return fieldList.contains(field);
	}
	
	public int startNewResultRow() {
		int newIndex = results.size();
		results.add(new EnumMap<T,String>(enumClass));
		return newIndex;
	}
	
	public void addResult(String value, int index, T field) {
		EnumMap<T,String> resultsRow = results.get(index);
		if (resultsRow != null) {
			resultsRow.put(field, value);
		} else {
			throw new InvalidParameterException("Attempted to set results in a row before initializing it: type: "
					+ type + " index:" + index);
		}
	}
	
	public String getTradeStatsHeader48Hrs() {
		List<T> allFields = getTradeStatsHeaderFields48Hrs();
		List<T> includedFields = getFilterIncluded(allFields);
		return FieldUtils.fieldListToHeader(includedFields);
	}
	
	public String getTradeStatsHeader90Days() {
		List<T> allFields = getTradeStatsHeaderFields90Days();
		List<T> includedFields = getFilterIncluded(allFields);
		return FieldUtils.fieldListToHeader(includedFields);
	}
	
	public String getDataHeader() {
		List<T> allFields = getDataHeaderFields();
		List<T> includedFields = getFilterIncluded(allFields);
		return FieldUtils.fieldListToHeader(includedFields);
	}
	
	public abstract List<T> getTradeStatsHeaderFields48Hrs();
	
	public abstract List<T> getTradeStatsHeaderFields90Days();
	
	public abstract List<T> getDataHeaderFields();
	
	private List<T> getFilterIncluded(List<T> fieldSet) {
		List<T> includedFields = new ArrayList<T>();
		for (T field : fieldSet) {
			if (fieldList.contains(field)) {
				includedFields.add(field);
			}
		}
		return includedFields;
	}
}
