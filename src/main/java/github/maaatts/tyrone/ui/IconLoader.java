package github.maaatts.tyrone.ui;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class IconLoader {
	private static Map<String, ImageIcon> cache = new HashMap<String, ImageIcon>();

	public static ImageIcon getImage(String name) {
		ImageIcon cached = cache.get(name);
		if (cached != null)
			return cached;

		ImageIcon imageIcon = new ImageIcon();

		try {
			imageIcon = new ImageIcon(IconLoader.class.getResource("/icons/" + name));
		} catch (Exception e) {
			System.err.printf("Failed to load icon: %s\n", name);
			e.printStackTrace();
		}

		cache.put(name, imageIcon);

		return imageIcon;
	}
}
