package github.maaatts.tyrone.io;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

public class JarParser {
	private final String name;
	private final Map<String, ClassNode> classes;

	public JarParser(JarFile j) throws IOException {
		this.name = j.getName();
		this.classes = new HashMap<String, ClassNode>();

		try {
			Enumeration<JarEntry> entries = j.entries();
			while (entries.hasMoreElements()) {
				JarEntry en = entries.nextElement();
				if (en.getName().endsWith(".class")) {
					ClassReader cr = new ClassReader(j.getInputStream(en));
					ClassNode cn = new ClassNode();
					cr.accept(cn, ClassReader.SKIP_FRAMES);
					classes.put(cn.name, cn);
				}

				// TODO: handle resource files in some way
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, ClassNode> getClasses() {
		return classes;
	}

	public String getName() {
		return name;
	}
}
