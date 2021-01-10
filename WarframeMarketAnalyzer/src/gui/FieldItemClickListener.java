package gui;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import enums.fields.IFieldEnum;

public class FieldItemClickListener<T extends Enum<T> & IFieldEnum> extends MouseAdapter {
	private FieldList<T> fieldList;
	private Frame parent;

	public FieldItemClickListener(Frame parent, FieldList<T> fieldList) {
		this.fieldList = fieldList;
		this.parent = parent;
	}

    @Override
	public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger())
            doPop(e);
    }

    @Override
	public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e) {
        if ( SwingUtilities.isRightMouseButton(e) )
        {
        	int selectedIdx = fieldList.getList().locationToIndex(e.getPoint());
        	FieldItem<T> field = fieldList.getList().getModel().getElementAt(selectedIdx);
        	FieldContectMenu menu = new FieldContectMenu(e.getComponent(), field);
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

	private class FieldContectMenu extends JPopupMenu {
	    private JMenuItem anItem;
	    public FieldContectMenu(Component component, FieldItem<T> field) {
	    	anItem = new JMenuItem("Rename");
	    	anItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					RenameDialog<T> dialog = new RenameDialog<T>(parent, fieldList, field);
					dialog.setLocationRelativeTo(parent);
					dialog.setVisible(true);
				}
			});

	        add(anItem);
	    }
	}
}