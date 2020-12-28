package gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class OptionPanel extends JPanel implements IOptionProvider {
	
//	private JButton exitButton;
	private SourceSelectionPanel sourceSelection;
	private AdvancedPanel advancedPanel;
	private InvokePanel invokePanel;
	
	public OptionPanel(MainPanel main) {

		sourceSelection = new SourceSelectionPanel();
		advancedPanel = new AdvancedPanel(sourceSelection);
		invokePanel = new InvokePanel(main);

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(sourceSelection);
		add(advancedPanel);
		add(invokePanel);
	}

	@Override
	public boolean processMods() {
		return sourceSelection.processMods();
	}

	@Override
	public boolean processRelics() {
		return sourceSelection.processRelics();
	}

	@Override
	public boolean processPrimes() {
		return sourceSelection.processPrimes();
	}
}
