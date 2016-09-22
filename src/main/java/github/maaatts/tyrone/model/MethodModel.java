package github.maaatts.tyrone.model;

import javax.swing.DefaultListModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class MethodModel extends DefaultMutableTreeNode {
	private static final long serialVersionUID = -5101126648726003695L;

	private String name;
	private DefaultListModel<Instruction> instructions;
	
	public MethodModel(String name) {
		this.name = name;
		this.instructions = new DefaultListModel<Instruction>();
		setAllowsChildren(false);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public DefaultListModel<Instruction> getInstructions() {
		return instructions;
	}
}
