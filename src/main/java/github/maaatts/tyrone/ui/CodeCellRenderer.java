package github.maaatts.tyrone.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import github.maaatts.tyrone.model.Instruction;

public class CodeCellRenderer implements ListCellRenderer<Instruction> {
	private static final Color SELECTED_BACKGROUND = new Color(55, 155, 255);
	private static final Color OPCODE_SELECTED = new Color(240, 240, 255);
	private static final Color OPCODE_UNSELECTED = new Color(100, 100, 120);
	private static final Font FONT = new Font(Font.MONOSPACED, Font.PLAIN, 12);
	private static final Font FONT_BOLD = new Font(Font.MONOSPACED, Font.BOLD, 12);

	private JPanel panel;
	private JLabel addressLabel;
	private JLabel opcodeLabel;
	private JLabel instructionLabel;

	public CodeCellRenderer() {
		this.panel = new JPanel();

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		this.panel.setLayout(new GridBagLayout());

		this.addressLabel = new JLabel();
		this.addressLabel.setFont(FONT_BOLD);
		this.addressLabel.setIconTextGap(3);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		this.addressLabel.setPreferredSize(new Dimension(80, 0));
		this.panel.add(this.addressLabel, gbc);

		this.opcodeLabel = new JLabel();
		this.opcodeLabel.setFont(FONT);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		this.opcodeLabel.setPreferredSize(new Dimension(150, 0));
		this.panel.add(this.opcodeLabel, gbc);

		this.instructionLabel = new JLabel();
		this.instructionLabel.setFont(FONT);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0;
		this.panel.add(this.instructionLabel, gbc);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Instruction> list, Instruction value, int index,
			boolean selected, boolean cellHasFocus) {
		String address = Long.toString(value.index);
		while (address.length() < 8) {
			address = "0" + address;
		}
		
		// TODO: temp
//		selected = false;
//		cellHasFocus = false;

		this.addressLabel.setText(address);
		this.opcodeLabel.setText(value.opcode);
		this.instructionLabel.setText(value.params);
		
		this.addressLabel.setSize(this.addressLabel.getPreferredSize());
		this.opcodeLabel.setSize(this.opcodeLabel.getPreferredSize());
		this.instructionLabel.setSize(this.instructionLabel.getPreferredSize());

		this.panel.setBackground(selected ? SELECTED_BACKGROUND : Color.WHITE);
		this.addressLabel.setForeground(selected ? Color.WHITE : Color.BLACK);
		this.opcodeLabel.setForeground(selected ? OPCODE_SELECTED : OPCODE_UNSELECTED);

		Color selectedColor = Color.WHITE;
		Color unselectedColor = Color.BLACK;
		this.instructionLabel.setForeground(selected ? selectedColor : unselectedColor);

		if (cellHasFocus) {
			this.panel.setBorder(new CompoundBorder(BorderFactory.createDashedBorder(Color.DARK_GRAY, 1, 1),
					new EmptyBorder(2, 2, 0, 2)));
		} else {
			this.panel.setBorder(new EmptyBorder(3, 3, 1, 3));
		}

		this.panel.invalidate();

		return this.panel;
	}
}
