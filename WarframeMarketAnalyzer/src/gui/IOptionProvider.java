package gui;

import java.util.List;

import enums.fields.ModFieldEnum;
import enums.fields.PrimeFieldEnum;
import enums.fields.RelicFieldEnum;

public interface IOptionProvider {
	public boolean processMods();
	
	public List<ModFieldEnum> getModFields();

	public boolean processRelics();
	
	public List<RelicFieldEnum> getRelicFields();

	public boolean processPrimes();
	
	public List<PrimeFieldEnum> getPrimeFields();
}
