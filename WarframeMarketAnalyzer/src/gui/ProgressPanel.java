package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressPanel extends JPanel implements ActionListener {

	private JButton cancelButton;
	private JProgressBar progressBar;
	
	public ProgressPanel() {
		cancelButton = new JButton("Oh God Make It Stop");
		cancelButton.addActionListener(this);
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);

		setLayout(new GridLayout(2, 1, 5, 5));
		add(cancelButton);
		add(progressBar);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		System.exit(0);
	}
}
