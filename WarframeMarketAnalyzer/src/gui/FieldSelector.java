package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import enums.fields.IFieldEnum;
import utils.FieldUtils;

public class FieldSelector<T extends Enum<T> & IFieldEnum> extends JPanel {

	private FieldList<T> unusedList;
	private JPanel buttonPanel;
	private JButton removeAll;
	private JButton removeSelected;
	private JButton addSelected;
	private JButton addAll;
	private FieldList<T> usedList;
	private JButton resetButton;

	public FieldSelector(Class<T> enumClass) throws ClassNotFoundException {

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(constructListPanel(enumClass));
		add(constructFieldButtonPanel(enumClass));
	}

	private JPanel constructListPanel(Class<T> enumClass) throws ClassNotFoundException {
		JPanel listPanel = new JPanel();

		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.LINE_AXIS));

		unusedList = new FieldList<T>("Unused", enumClass, FieldUtils.nonDefaultFields(enumClass));
		listPanel.add(unusedList);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		removeAll = new JButton("<<");
		removeAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				unusedList.addFields(Arrays.asList(enumClass.getEnumConstants()));
				usedList.removeFields(EnumSet.allOf(enumClass));
			}
		});
		buttonPanel.add(removeAll);

		removeSelected = new JButton("<");
		removeSelected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EnumSet<T> selected = usedList.getSelectedFields();
				unusedList.addFields(new ArrayList<>(selected));
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
				usedList.addFields(new ArrayList<>(selected));
			}
		});
		buttonPanel.add(addSelected);

		addAll = new JButton(">>");
		addAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				unusedList.removeFields(EnumSet.allOf(enumClass));
				usedList.addFields(Arrays.asList(enumClass.getEnumConstants()));
			}
		});
		buttonPanel.add(addAll);
		listPanel.add(buttonPanel);

		usedList = new FieldList<T>("Used", enumClass, FieldUtils.defaultFields(enumClass));
		listPanel.add(usedList);

		return listPanel;
	}

	private JPanel constructFieldButtonPanel(Class<T> enumClass) {
		JPanel fieldButtonPanel = new JPanel();
		fieldButtonPanel.setLayout(new BoxLayout(fieldButtonPanel, BoxLayout.LINE_AXIS));

		resetButton = new JButton("Reset to defaults");
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				unusedList.setFields(FieldUtils.nonDefaultFields(enumClass));
				usedList.setFields(FieldUtils.defaultFields(enumClass));
			}
		});
		fieldButtonPanel.add(resetButton);

		return fieldButtonPanel;
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
