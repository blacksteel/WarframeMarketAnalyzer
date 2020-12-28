package gui;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import main.Config;

public class SourceSelectionPanel extends JPanel {

	private JCheckBox processMods;
	private JCheckBox processRelics;
	private JCheckBox processPrimes;

	public SourceSelectionPanel() {
		processMods = new JCheckBox("Process Mods:", Config.PROCESS_MODS);
		processPrimes = new JCheckBox("Process Primes:", Config.PROCESS_PRIMES);
		processRelics = new JCheckBox("Process Relics (Requires Primes):", Config.PROCESS_RELICS);

		this.add(processMods);
		this.add(processPrimes);
		this.add(processRelics);
	}

	public boolean processMods() {
		return processMods.isSelected();
	}

	public boolean processRelics() {
		return processRelics.isSelected();
	}

	public boolean processPrimes() {
		return processPrimes.isSelected();
	}
}
