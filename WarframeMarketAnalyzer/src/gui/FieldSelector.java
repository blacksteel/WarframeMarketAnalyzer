package gui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	public FieldSelector(Frame parent, Class<T> enumClass) throws ClassNotFoundException {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(constructListPanel(parent, enumClass));
		add(constructFieldButtonPanel(enumClass));
	}

	private JPanel constructListPanel(Frame parent, Class<T> enumClass) throws ClassNotFoundException {
		JPanel listPanel = new JPanel();

		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.LINE_AXIS));

		unusedList = new FieldList<T>(parent, "Unused", enumClass, FieldUtils.nonDefaultFields(enumClass));
		listPanel.add(unusedList);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		removeAll = new JButton("<<");
		removeAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<FieldItem<T>> allFields = usedList.removeAllFields();
				unusedList.addFields(allFields);

			}
		});
		buttonPanel.add(removeAll);

		removeSelected = new JButton("<");
		removeSelected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<FieldItem<T>> allFields = usedList.removeSelectedFields();
				unusedList.addFields(allFields);
			}
		});
		buttonPanel.add(removeSelected);

		addSelected = new JButton(">");
		addSelected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<FieldItem<T>> allFields = unusedList.removeSelectedFields();
				usedList.addFields(allFields);
			}
		});
		buttonPanel.add(addSelected);

		addAll = new JButton(">>");
		addAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<FieldItem<T>> allFields = unusedList.removeAllFields();
				usedList.addFields(allFields);
			}
		});
		buttonPanel.add(addAll);
		listPanel.add(buttonPanel);

		usedList = new FieldList<T>(parent, "Used", enumClass, FieldUtils.defaultFields(enumClass));
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

	public List<FieldItem<T>> getFields() {
		return usedList.getIncludedFields();
	}

}
