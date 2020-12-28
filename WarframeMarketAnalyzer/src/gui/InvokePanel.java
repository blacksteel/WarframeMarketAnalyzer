package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class InvokePanel extends JPanel {

	private JButton runButton;

	public InvokePanel(MainPanel main) {
		runButton = new JButton("Run Me!");
		this.add(runButton);

		runButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				main.invokeAnalysis();
			}
		});
	}
}
