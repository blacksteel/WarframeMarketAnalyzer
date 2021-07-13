package main.results;

import java.security.InvalidParameterException;

import enums.ItemType;
import enums.fields.IFieldEnum;
import enums.fields.ModFieldEnum;
import enums.fields.PrimeFieldEnum;
import enums.fields.RelicFieldEnum;
import gui.IOptionProvider;

public class AnalysisResults {

	private ModTypeResults modResults;
	private PrimeTypeResults primeResults;
	private RelicTypeResults relicResults;

	public AnalysisResults(IOptionProvider optionProvider) {
		if (optionProvider.processMods()) {
			modResults = new ModTypeResults(optionProvider);
		} else {
			modResults = null;
		}
		
		if (optionProvider.processPrimes()) {
			primeResults = new PrimeTypeResults(optionProvider);
		} else {
			primeResults = null;
		}
		
		if (optionProvider.processRelics()) {
			relicResults = new RelicTypeResults(optionProvider);
		} else {
			relicResults = null;
		}
	}
	
	// TODO This is a bit kludgy
	public <T extends Enum<T> & IFieldEnum> TypeResults<T> getTypeResults(ItemType type) {
		switch (type) {
		case MOD:
			return (TypeResults<T>)modResults;
		case PRIME_PART:
			return (TypeResults<T>)primeResults;
		case VOID_RELIC:
			return (TypeResults<T>)relicResults;
		default:
			throw new InvalidParameterException("Invalid item type: "+type);
		}
	}

	private TypeResults<? extends Enum<?>> getTypeResults(Enum field) {
		if (field instanceof ModFieldEnum) {
			return modResults;
		} else if (field instanceof PrimeFieldEnum) {
			return primeResults;
		} else if (field instanceof RelicFieldEnum) {
			return relicResults;
		} else {
			throw new InvalidParameterException("Invalid item type: "+field.getClass().getCanonicalName());
		}
	}
	
//	public boolean hasField(ItemType type, String fieldName) {
//		TypeResults<? extends Enum<?>> typeResult = getTypeResults(type);
//		boolean hasField;
//		if (typeResult != null) {
//			hasField = typeResult.hasField(fieldName);
//		} else {
//			hasField = false;
//		}
//		return hasField;
//	}
	
	public int startNewResultRow(ItemType type) {
		TypeResults<? extends Enum<?>> typeResult = getTypeResults(type);
		int newIndex;
		if (typeResult != null) {
			newIndex = typeResult.startNewResultRow();
		} else {
			newIndex = -1;
		}
		return newIndex;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Enum<T> & IFieldEnum> void addResult(String value, int index, T field) {
		TypeResults<T> typeResults = (TypeResults<T>)getTypeResults(field);
		if (typeResults != null) {
			typeResults.setResult(value, index, field);
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends Enum<T> & IFieldEnum> boolean hasField(T field) {
		TypeResults<T> typeResults = (TypeResults<T>)getTypeResults(field);
		boolean hasField = false;
		if (typeResults != null) {
			typeResults.hasField(field);
		}
		return hasField;
	}
}
