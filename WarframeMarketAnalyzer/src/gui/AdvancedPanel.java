package gui;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class AdvancedPanel extends JPanel {

	private SourceSelectionPanel sourceSelection;
	private JCheckBox enableAdvanced;

	public AdvancedPanel(SourceSelectionPanel sourceSelection) {
		this.sourceSelection = sourceSelection;
		
		enableAdvanced = new JCheckBox("Advanced");
		
		add(enableAdvanced);
	}
}
