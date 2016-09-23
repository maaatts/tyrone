package github.maaatts.tyrone;

import java.io.File;
import java.util.jar.JarFile;

import javax.swing.JFileChooser;
import javax.swing.UIManager;

import github.maaatts.tyrone.io.JarParser;
import github.maaatts.tyrone.ui.TyroneUI;

public class Tyrone {
	private TyroneUI ui;

	private void run() {
		this.ui = new TyroneUI(this);
		this.ui.setVisible(true);
	}

	public void showFilePickerDialog() {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(ui);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();

			// TODO: Check if file is .class or .jar
			
			JarParser parser = null;

			try {
				parser = new JarParser(new JarFile(file));
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}

			ModelGenerator mg = new ModelGenerator(parser);
			this.ui.displayModel(mg.parse());
		}
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

		new Tyrone().run();
	}
}
