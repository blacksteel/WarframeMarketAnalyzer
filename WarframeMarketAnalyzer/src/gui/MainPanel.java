package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.AnalysisInvoker;

public class MainPanel extends JFrame implements IPackListener{

	private JPanel contentPanel;
	private OptionPanel optionPanel;

	public MainPanel(AnalysisInvoker invoker) {
		super("WarframeMarketAnalyzer");
		contentPanel = new JPanel();
		optionPanel = new OptionPanel(this);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new GridLayout(1, 2, 5, 5));
		contentPanel.add(optionPanel);

		getContentPane().add(contentPanel);

		pack();

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2 - getSize().width/2, dim.height/2 - getSize().height/2);
	}

	public void invokeAnalysis() {

		contentPanel.removeAll();
		contentPanel.add(new ProgressPanel());

		pack();
		setLocationRelativeTo(null);

		// TODO Clean this up
		new AnalysisInvoker().invokeAnalysis(optionPanel);
	}

	@Override
	public void resize() {
		pack();
		setLocationRelativeTo(null);
	}
}
