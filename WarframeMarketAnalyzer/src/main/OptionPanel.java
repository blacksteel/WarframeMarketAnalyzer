package main;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

class OptionPanel extends JPanel implements IOptionProvider {
	
	private JCheckBox processMods;
	private JCheckBox processRelics;
	private JCheckBox processPrimes;

	private JButton runButton;
//	private JButton exitButton;
	
	public OptionPanel(JPanel contentPanel) {
		runButton = new JButton("Run Me!");
		// TODO Is this needed?
//		exitButton = new JButton("No Thanks");
		
		processMods = new JCheckBox("Process Mods:", Config.PROCESS_MODS);
		processPrimes = new JCheckBox("Process Primes:", Config.PROCESS_PRIMES);
		processRelics = new JCheckBox("Process Relics (Requires Primes):", Config.PROCESS_RELICS);

		this.add(runButton);
//		this.add(exitButton);
		this.add(processMods);
		this.add(processPrimes);
		this.add(processRelics);

		runButton.addActionListener(new AnalysisInvoker(contentPanel, this));
	}

	@Override
	public boolean processMods() {
		return processMods.isSelected();
	}

	@Override
	public boolean processRelics() {
		return processRelics.isSelected();
	}

	@Override
	public boolean processPrimes() {
		return processPrimes.isSelected();
	}
}
