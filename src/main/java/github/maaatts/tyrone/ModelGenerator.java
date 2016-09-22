package github.maaatts.tyrone;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import github.maaatts.tyrone.io.JarParser;
import github.maaatts.tyrone.model.Instruction;
import github.maaatts.tyrone.model.MethodModel;
import github.maaatts.tyrone.model.ParentModel;
import github.maaatts.tyrone.model.ParentModelType;

public class ModelGenerator {
	private JarParser parser;
	private Map<String, ParentModel> packages;

	public ModelGenerator(JarParser parser) {
		this.parser = parser;
		this.packages = new HashMap<String, ParentModel>();
	}

	public ParentModel parse() {
		ParentModel root = new ParentModel(parser.getName(), ParentModelType.JAR);

		for (String className : parser.getClasses().keySet()) {
			String packageName = getPackageName(className);

			ParentModel pkg = null;

			if (packages.containsKey(packageName)) {
				pkg = this.packages.get(packageName);
			} else {
				pkg = new ParentModel(packageName, ParentModelType.PACKAGE);
				root.add(pkg);
				this.packages.put(packageName, pkg);
			}

			ParentModel cpm = new ParentModel(getOnlyClassName(className), ParentModelType.CLASS);
			ClassNode cn = parser.getClasses().get(className);

			for (Object o : cn.methods) {
				MethodNode mn = (MethodNode) o;
				StringBuilder sb = new StringBuilder();
				sb.append(mn.name);
				sb.append(' ');
				sb.append(mn.desc);

				MethodModel mm = new MethodModel(sb.toString());

				// whyyyyy
				for (int i = 0; i < mn.instructions.size(); i++) {
					AbstractInsnNode ain = (AbstractInsnNode) mn.instructions.get(i);
					Instruction in = InstructionParser.toInstruction(i, ain, mn.instructions);
					if (in != null) {
						mm.getInstructions().addElement(in);
					}
				}

				cpm.add(mm);
			}

			pkg.add(cpm);
		}

		return root;
	}

	private static String getPackageName(String className) {
		return className.substring(0, className.lastIndexOf('/') + 1);
	}

	private static String getOnlyClassName(String className) {
		return className.substring(className.lastIndexOf('/') + 1, className.length());
	}
}
