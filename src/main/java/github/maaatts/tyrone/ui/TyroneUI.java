package github.maaatts.tyrone.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import github.maaatts.tyrone.Tyrone;
import github.maaatts.tyrone.model.Instruction;
import github.maaatts.tyrone.model.MethodModel;
import github.maaatts.tyrone.model.ParentModel;

public class TyroneUI extends JFrame {
	private static final long serialVersionUID = -7241474821921484228L;

	private Tyrone tyrone;

	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem openItem;
	private JMenuItem settingsItem;
	private JMenuItem quitItem;
	private JMenu searchMenu;
	private JMenuItem stringSearchItem;

	private JToolBar toolbar;
	private JButton toolbarOpenButton;
	private JButton toolbarSearchButton;

	private JPanel outlineArea;
	private JScrollPane outlineScroller;
	private JTree outlineTree;

	private JList<Instruction> insnList;

	private JPanel contentPanel;

	private JSplitPane splitter;

	private DefaultTreeModel displayModel;

	public TyroneUI(Tyrone tyrone) {
		this.tyrone = tyrone;

		this.menuBar = new JMenuBar();
		this.fileMenu = new JMenu("File");
		this.openItem = new JMenuItem("Open");
		this.settingsItem = new JMenuItem("Settings");
		this.quitItem = new JMenuItem("Quit");
		this.searchMenu = new JMenu("Search");
		this.stringSearchItem = new JMenuItem("String");

		this.toolbar = new JToolBar();
		this.toolbarOpenButton = new JButton();
		this.toolbarSearchButton = new JButton();

		this.outlineArea = new JPanel();
		this.outlineTree = new JTree();
		this.outlineScroller = new JScrollPane(this.outlineTree);

		this.contentPanel = new JPanel();

		this.splitter = new JSplitPane();

		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		this.setJMenuBar(this.menuBar);
		this.menuBar.add(this.fileMenu);
		this.fileMenu.add(this.openItem);
		this.fileMenu.add(this.settingsItem);
		this.fileMenu.add(this.quitItem);
		this.menuBar.add(this.searchMenu);
		this.searchMenu.add(this.stringSearchItem);

		this.openItem.setMnemonic('o');
		this.settingsItem.setMnemonic('s');
		this.quitItem.setMnemonic('q');
		this.fileMenu.setMnemonic('f');
		this.searchMenu.setMnemonic('s');
		this.stringSearchItem.setMnemonic('s');

		this.toolbar.setFloatable(false);
		this.toolbar.setBorder(new MatteBorder(0, 0, 1, 0, new Color(150, 150, 150)));

		this.toolbar.add(this.toolbarOpenButton);
		this.toolbarOpenButton.setIcon(IconLoader.getImage("folder.png"));
		this.toolbarOpenButton.setBorder(new EmptyBorder(3, 3, 3, 3));
		this.toolbarOpenButton.setToolTipText("Open File");
		this.toolbarOpenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tyrone.showFilePickerDialog();
			}
		});
		this.toolbar.addSeparator();
		this.toolbar.add(this.toolbarSearchButton);
		this.toolbarSearchButton.setIcon(IconLoader.getImage("magnifier.png"));
		this.toolbarSearchButton.setBorder(new EmptyBorder(3, 3, 3, 3));
		this.toolbarSearchButton.setToolTipText("Search for String");

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		this.add(this.toolbar, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		this.add(this.splitter, gbc);

		{
			this.outlineArea.setLayout(new GridBagLayout());

			// gbc.gridx = 0;
			// gbc.gridy = 0;
			// gbc.weightx = 1;
			// gbc.weighty = 0;
			// gbc.gridwidth = 1;
			// gbc.gridheight = 1;
			// gbc.insets = new Insets(0, 0, 0, 0);
			// this.outlineArea.add(this.outlineFilterField, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.weightx = 1;
			gbc.weighty = 1;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			gbc.insets = new Insets(0, 0, 0, 0);
			this.outlineArea.add(this.outlineScroller, gbc);
		}

		this.contentPanel.setLayout(new BorderLayout());

		this.splitter.setLeftComponent(this.outlineArea);
		this.splitter.setRightComponent(this.contentPanel);
		this.splitter.setBorder(null);

		this.outlineScroller.setBorder(null);

		this.outlineTree.setExpandsSelectedPaths(true);
		this.outlineTree.setCellRenderer(new OutlineTreeCellRenderer());
		this.outlineTree.setRowHeight(18);
		this.outlineTree.setModel(null);
		this.outlineTree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				TyroneUI.this.treeSelectUpdate();
			}
		});

		this.openItem.setIcon(IconLoader.getImage("folder.png"));
		this.openItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tyrone.showFilePickerDialog();
			}
		});

		this.settingsItem.setIcon(IconLoader.getImage("cog.png"));
		//

		this.quitItem.setIcon(IconLoader.getImage("cancel.png"));
		this.quitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tyrone.quit();
			}
		});

		this.stringSearchItem.setIcon(IconLoader.getImage("magnifier.png"));
		//

		this.setIconImage(IconLoader.getImage("box.png").getImage());
		this.setPreferredSize(new Dimension(1280, 720));
		this.splitter.setDividerLocation(300);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setTitle("Tyrone");
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				tyrone.quit();
			}
		});
	}

	public void treeSelectUpdate() {
		TreePath path = this.outlineTree.getSelectionPath();
		if (path == null)
			return;

		Object o = path.getLastPathComponent();

		if (o instanceof MethodModel) {
			this.contentPanel.removeAll();
			MethodModel cm = (MethodModel) o;

			JLabel nameLabel = new JLabel(cm.getParent().toString() + "/" + cm.toString());
			nameLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
			this.contentPanel.add(nameLabel, BorderLayout.NORTH);

			this.insnList = new JList<Instruction>();
			this.insnList.setCellRenderer(new CodeCellRenderer());
			insnList.setModel(cm.getInstructions());

			JScrollPane scroller = new JScrollPane(this.insnList);
			scroller.setBorder(null);
			this.contentPanel.add(scroller, BorderLayout.CENTER);

			this.splitter.validate();
			this.splitter.repaint();
		}
	}

	public void displayModel(ParentModel model) {
		this.contentPanel.removeAll();
		this.displayModel = new DefaultTreeModel(model);
		this.outlineTree.setModel(this.displayModel);
	}
}
