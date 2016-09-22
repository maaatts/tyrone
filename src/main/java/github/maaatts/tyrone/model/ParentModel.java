package github.maaatts.tyrone.model;

import javax.swing.tree.DefaultMutableTreeNode;

public class ParentModel extends DefaultMutableTreeNode {
	private static final long serialVersionUID = 5169773836018675702L;

	private String name;
	private ParentModelType type;

	public ParentModel(String name, ParentModelType type) {
		this.name = name;
		this.type = type;
		setAllowsChildren(true);
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	public ParentModelType getType() {
		return this.type;
	}
}