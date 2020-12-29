package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumSet;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import enums.fields.IFieldEnum;

public class FieldSelector<T extends Enum<T> & IFieldEnum> extends JPanel {
	
	private FieldList<T> unusedList;
	private JPanel buttonPanel;
	private JButton removeAll;
	private JButton removeSelected;
	private JButton addSelected;
	private JButton addAll;
	private FieldList<T> usedList;
	
	public FieldSelector(Class<T> enumClass) {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		unusedList = new FieldList<T>("Unused", enumClass, EnumSet.noneOf(enumClass));
		add(unusedList);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		removeAll = new JButton("<<");
		removeAll.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				unusedList.addFields(EnumSet.allOf(enumClass));
				usedList.removeFields(EnumSet.allOf(enumClass));
			}
		});
		buttonPanel.add(removeAll);

		removeSelected = new JButton("<");
		removeSelected.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				EnumSet<T> selected = usedList.getSelectedFields();
				unusedList.addFields(selected);
				usedList.removeFields(selected);
			}
		});
		buttonPanel.add(removeSelected);

		addSelected = new JButton(">");
		addSelected.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				EnumSet<T> selected = unusedList.getSelectedFields();
				unusedList.removeFields(selected);
				usedList.addFields(selected);
			}
		});
		buttonPanel.add(addSelected);

		addAll = new JButton(">>");
		addAll.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				unusedList.removeFields(EnumSet.allOf(enumClass));
				usedList.addFields(EnumSet.allOf(enumClass));
			}
		});
		buttonPanel.add(addAll);
		add(buttonPanel);
		
		usedList = new FieldList<T>("Used", enumClass, EnumSet.allOf(enumClass));
		add(usedList);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		unusedList.setEnabled(enabled);
		removeAll.setEnabled(enabled);
		removeSelected.setEnabled(enabled);
		addSelected.setEnabled(enabled);
		addAll.setEnabled(enabled);
		usedList.setEnabled(enabled);
	}
	
	public List<T> getFields() {
		return usedList.getIncludedFields();
	}

}
