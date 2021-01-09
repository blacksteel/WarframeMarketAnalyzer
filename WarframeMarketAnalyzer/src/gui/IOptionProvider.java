package gui;

import java.util.List;

import enums.fields.ModFieldEnum;
import enums.fields.PrimeFieldEnum;
import enums.fields.RelicFieldEnum;

public interface IOptionProvider {
	public boolean processMods();

	public List<FieldItem<ModFieldEnum>> getModFields();

	public boolean processRelics();

	public List<FieldItem<RelicFieldEnum>> getRelicFields();

	public boolean processPrimes();

	public List<FieldItem<PrimeFieldEnum>> getPrimeFields();
}
