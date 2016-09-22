package github.maaatts.tyrone.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeCellRenderer;

import github.maaatts.tyrone.model.MethodModel;
import github.maaatts.tyrone.model.ParentModel;

public class OutlineTreeCellRenderer extends DefaultTreeCellRenderer {
	private static final long serialVersionUID = 3784315634732926158L;

	private static final Icon PACKAGE = IconLoader.getImage("bricks.png");
	private static final Icon CLASS = IconLoader.getImage("brick.png");
	private static final Icon METHOD = IconLoader.getImage("bullet_blue.png");

	private JPanel panel;
	private JLabel label;

	public OutlineTreeCellRenderer() {
		this.panel = new JPanel();
		this.label = new JLabel();

		this.panel.setLayout(new BorderLayout());
		this.panel.add(this.label, BorderLayout.CENTER);
		this.label.setIconTextGap(3);
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		this.label.setText(value.toString());
		if (value instanceof ParentModel) {
			ParentModel pm = (ParentModel) value;
			switch (pm.getType()) {
			case CLASS:
				this.label.setIcon(CLASS);
				break;
			case PACKAGE:
			case JAR:
				this.label.setIcon(PACKAGE);
				break;
			}
		} else if (value instanceof MethodModel) {
			this.label.setIcon(METHOD);
		}
		
		this.panel.setBackground(Color.WHITE);
		this.label.setForeground(Color.BLACK);
		this.label.setSize(this.label.getPreferredSize());

		if (hasFocus) {
			this.label.setBorder(new CompoundBorder(BorderFactory.createDashedBorder(Color.DARK_GRAY, 1, 1),
					new EmptyBorder(0, 2, 2, 2)));
		} else {
			this.label.setBorder(new EmptyBorder(1, 3, 3, 3));
		}

		return this.panel;
	}
}
