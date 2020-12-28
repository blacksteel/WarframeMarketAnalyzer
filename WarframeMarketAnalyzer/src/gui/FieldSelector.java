package gui;

import java.util.EnumSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import enums.fields.IFieldEnum;

public class FieldSelector<T extends Enum<T> & IFieldEnum> extends JPanel {
	
	private FieldList<T> unusedList;
	private JPanel buttonPanel;
	private JButton removeAll;
	private JButton removeOne;
	private JButton addOne;
	private JButton addAll;
	private FieldList<T> usedList;
	
	public FieldSelector(Class<T> enumClass) {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		unusedList = new FieldList<T>(enumClass, EnumSet.allOf(enumClass));
		add(unusedList);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		removeAll = new JButton("<<");
		buttonPanel.add(removeAll);
		removeOne = new JButton("<");
		buttonPanel.add(removeOne);
		addOne = new JButton(">");
		buttonPanel.add(addOne);
		addAll = new JButton(">>");
		buttonPanel.add(addAll);
		add(buttonPanel);
		
		usedList = new FieldList<T>(enumClass, EnumSet.noneOf(enumClass));
		add(usedList);
	}

}
