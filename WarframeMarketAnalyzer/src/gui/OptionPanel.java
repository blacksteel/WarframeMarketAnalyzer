package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import enums.fields.ModFieldEnum;
import enums.fields.PrimeFieldEnum;
import enums.fields.RelicFieldEnum;
import main.Config;

public class OptionPanel extends JPanel implements IOptionProvider {

	private static final int GRID_WIDTH=5;

	private JCheckBox processMods;
	private JCheckBox processRelics;
	private JCheckBox processPrimes;
	// TODO Add sub checkboxes for things like conclave, vaulted, sets, relic drop values

	private JCheckBox enableAdvanced;
	private FieldSelector<ModFieldEnum> modSelector;
	private FieldSelector<PrimeFieldEnum> primeSelector;
	private FieldSelector<RelicFieldEnum> relicSelector;

	private JButton runButton;

	public OptionPanel(MainPanel main) throws ClassNotFoundException {

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		int y = 0;
		y = addSourceSelection(c, y);
		y = addSeperator(c, y);
		y = addAdvanced(main, c, y);
		y = addSeperator(c, y);
		y = addInvoke(main, c, y);
	}

	public int addSeperator(GridBagConstraints c, int y) {
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = y;
		c.gridwidth = GRID_WIDTH;
		c.ipady = 5;
		add(new JSeparator(), c);

		// Next Row
		y++;

		return y;
	}

	public int addSourceSelection(GridBagConstraints c, int y) {
		c.fill = GridBagConstraints.NONE;
		c.ipady = 0;
		c.gridwidth = 1;

		processMods = new JCheckBox("Process Mods:", Config.PROCESS_MODS);
		processMods.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				syncFieldSelectorsEnabled();
			}
		});
		c.gridx = 0;
		c.gridy = y;
		add(processMods, c);

		processPrimes = new JCheckBox("Process Primes:", Config.PROCESS_PRIMES);
		processPrimes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// If the primes checkbox was just de-selected, make sure that the relics checkbox is also de-selected
				if (!processPrimes.isSelected()) {
					processRelics.setSelected(false);
				}
				syncFieldSelectorsEnabled();
			}
		});
		c.gridx = 2;
		c.gridy = y;
		add(processPrimes, c);

		processRelics = new JCheckBox("Process Relics (Requires Primes):", Config.PROCESS_RELICS);
		processRelics.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// If the relics checkbox was just selected, make sure that the primes checkbox is also enabled
				if (processRelics.isSelected()) {
					processPrimes.setSelected(true);
				}
				syncFieldSelectorsEnabled();
			}
		});
		c.gridx = 4;
		c.gridy = y;
		add(processRelics, c);

		// Next Row
		y++;

		return y;
	}

	private void syncFieldSelectorsEnabled() {
		modSelector.setEnabled(processMods.isSelected());
		primeSelector.setEnabled(processPrimes.isSelected());
		relicSelector.setEnabled(processRelics.isSelected());
	}

	private int addAdvanced(IPackListener packListener, GridBagConstraints c, int y) throws ClassNotFoundException {

		c.fill = GridBagConstraints.NONE;
		c.ipady = 0;
		c.gridwidth = 1;
		c.ipadx = 10;

		modSelector = new FieldSelector<>(ModFieldEnum.class);
		modSelector.setVisible(false);
		c.gridx = 0;
		c.gridy = y;
		add(modSelector, c);

		c.gridx = 1;
		c.gridy = y;
		add(new JSeparator(SwingConstants.VERTICAL),c);

		primeSelector = new FieldSelector<>(PrimeFieldEnum.class);
		primeSelector.setVisible(false);
		c.gridx = 2;
		c.gridy = y;
		add(primeSelector, c);

		c.gridx = 3;
		c.gridy = y;
		add(new JSeparator(SwingConstants.VERTICAL),c);

		relicSelector = new FieldSelector<>(RelicFieldEnum.class);
		relicSelector.setVisible(false);
		c.gridx = 4;
		c.gridy = y;
		add(relicSelector, c);

		// Next Row
		y++;

		enableAdvanced = new JCheckBox("Advanced");
		enableAdvanced.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modSelector.setVisible(enableAdvanced.isSelected());
				primeSelector.setVisible(enableAdvanced.isSelected());
				relicSelector.setVisible(enableAdvanced.isSelected());
				packListener.resize();
			}
		});
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridwidth = GRID_WIDTH;
		c.gridy = y;
		add(enableAdvanced, c);

		// Next Row
		y++;

		return y;
	}

	public int addInvoke(MainPanel main, GridBagConstraints c, int y) {
		runButton = new JButton("Run Me!");
		runButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.invokeAnalysis();
			}
		});

		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridwidth = GRID_WIDTH;
		c.gridy = y;
		add(runButton, c);

		// TODO
		// importButton = new JButton("Import Settings");

		return y+1;
	}

	@Override
	public boolean processMods() {
		return processMods.isSelected();
	}

	@Override
	public List<ModFieldEnum> getModFields() {
		return modSelector.getFields();
	}

	@Override
	public boolean processRelics() {
		return processRelics.isSelected();
	}

	@Override
	public List<RelicFieldEnum> getRelicFields() {
		return relicSelector.getFields();
	}

	@Override
	public boolean processPrimes() {
		return processPrimes.isSelected();
	}

	@Override
	public List<PrimeFieldEnum> getPrimeFields() {
		return primeSelector.getFields();
	}
}
