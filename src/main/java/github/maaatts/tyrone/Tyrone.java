package github.maaatts.tyrone;

import java.io.File;
import java.util.jar.JarFile;

import javax.swing.UIManager;

import github.maaatts.tyrone.io.JarParser;
import github.maaatts.tyrone.ui.TyroneUI;

public class Tyrone {
	private TyroneUI ui;

	private void run(String file) {
		this.ui = new TyroneUI(this);
		this.ui.setVisible(true);

		JarParser parser = null;

		try {
			parser = new JarParser(new JarFile(new File(file)));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		ModelGenerator mg = new ModelGenerator(parser);
		this.ui.displayModel(mg.parse());
	}

	public void quit() {
		System.exit(0);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		new Tyrone().run(args[0]);
	}
}
