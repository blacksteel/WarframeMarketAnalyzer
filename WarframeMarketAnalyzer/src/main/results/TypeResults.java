package main.results;

import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;

import enums.ItemType;
import enums.fields.IFieldEnum;
import gui.IOptionProvider;
import utils.FieldUtils;
import utils.TokenList;

public abstract class TypeResults <T extends Enum<T> & IFieldEnum> {

	private Class<T> enumClass;
	private ItemType type;
	private T nameField;
	private List<T> fieldList;

	private List<EnumMap<T, String>> results = new ArrayList<>();

	@SuppressWarnings("unchecked")
	TypeResults(Class<T> enumClass, T nameField, ItemType type, IOptionProvider optionProvider) {
		this.enumClass = enumClass;
		this.nameField = nameField;
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

//	public boolean hasField(String fieldName) {
//		return true;
//	}

	public boolean hasField(T field) {
		return fieldList.contains(field);
	}

	public int startNewResultRow() {
		int newIndex = results.size();
		results.add(new EnumMap<T,String>(enumClass));
		return newIndex;
	}

	public void setResult(String value, int index, T field) {
		EnumMap<T,String> resultsRow = results.get(index);
		if (resultsRow != null) {
			resultsRow.put(field, value);
		} else {
			throw new InvalidParameterException("Attempted to set results in a row before initializing it: type: "
					+ type + " index:" + index);
		}
	}

//	public String getTradeStatsHeader48Hrs() {
//		List<T> allFields = getTradeStatsHeaderFields48Hrs();
//		List<T> includedFields = getFilterIncluded(allFields);
//		return FieldUtils.fieldListToHeader(includedFields);
//	}
//
//	public String getTradeStatsHeader90Days() {
//		List<T> allFields = getTradeStatsHeaderFields90Days();
//		List<T> includedFields = getFilterIncluded(allFields);
//		return FieldUtils.fieldListToHeader(includedFields);
//	}
//
//	public String getDataHeader() {
//		List<T> allFields = getDataHeaderFields();
//		List<T> includedFields = getFilterIncluded(allFields);
//		return FieldUtils.fieldListToHeader(includedFields);
//	}

//	public abstract List<T> getTradeStatsHeaderFields48Hrs();
//
//	public abstract List<T> getTradeStatsHeaderFields90Days();
//
//	public abstract List<T> getDataHeaderFields();

	public String getHeaderFields() {
		return FieldUtils.fieldListToHeader(fieldList);
	}

	public String getName(int index) {
		return results.get(index).get(nameField);
	}

	public void outputResults(PrintWriter outputWriter) {
		// Order the results
		results.sort(new RowComparator());

		TokenList outputTokens = new TokenList();
		for (T field : fieldList) {
			outputTokens.add(field.getDisplayName());
		}
		outputWriter.println(outputTokens.toCSV());

		for (EnumMap<T, String> lineValues : results) {
			printLine(lineValues, outputWriter);
		}
	}

	private void printLine(EnumMap<T, String> lineValues, PrintWriter outputWriter) {
		TokenList outputTokens = new TokenList();
		for (T fieldName : fieldList) {
			String value = lineValues.get(fieldName);
			if (value != null) {
				outputTokens.add(value);
			} else {
				outputTokens.add("MISSING");
			}
		}
		outputWriter.println(outputTokens.toCSV());
	}

	private List<T> getFilterIncluded(List<T> fieldSet) {
		List<T> includedFields = new ArrayList<T>();
		for (T field : fieldSet) {
			if (fieldList.contains(field)) {
				includedFields.add(field);
			}
		}
		return includedFields;
	}

	/**
	 * Sort results rows based on the name field
	 *
	 * TODO Need to ensure that the name field exists
	 * @author ladaa
	 *
	 */
	private class RowComparator implements Comparator<EnumMap<T, String>> {

		@Override
		public int compare(EnumMap<T, String> o1, EnumMap<T, String> o2) {
			return o1.get(nameField).compareTo(o2.get(nameField));
		}


	}
}
