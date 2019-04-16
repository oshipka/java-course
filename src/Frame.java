import com.sun.org.apache.bcel.internal.generic.ObjectType;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Frame extends JFrame implements ComponentListener {
	private int prevWidth;
	private int prevHeight;
	
	private JMenu menu;
	private JMenuItem i1, i2, i3;
	
	private MyTable table;
	
	private Container contentPane = getContentPane();
	
	//private double zoom = 20;
	
	Frame() {
		super("Строфоїда");
		
		
		contentPane.addComponentListener(this);
		
		
		createMenu();
		table = new MyTable(contentPane);
		createToolbar();
		createStatusBar();
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(500, 570);
	}
	
	
	private void createMenu() {
		JMenuBar mb = new JMenuBar();
		menu = new JMenu("File");
		i1 = new JMenuItem(new AbstractAction("New") {
			public void actionPerformed(ActionEvent ae) {
				table.model.setRowCount(1);
				table.model.setColumnCount(1);
				
				for (int i = 0; i < 5; i++) {
					table.model.addColumn(table.table.getColumnCount());
				}
				for (int i = 0; i < 10; i++) {
					table.model.addRow(new Object[]{i});
				}
			}
		});
		i1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		i2 = new JMenuItem(new AbstractAction("Save") {
			public void actionPerformed(ActionEvent ae) {
			
			}
		});
		i2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		i3 = new JMenuItem(new AbstractAction("Open") {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fc = new JFileChooser();
				int i = fc.showOpenDialog(contentPane);
				if (i == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					String filepath = f.getPath();
					try {
						BufferedReader br = new BufferedReader(new FileReader(filepath));
						String s1 = "", s2 = "";
						while ((s1 = br.readLine()) != null) {
							s2 += s1 + "\n";
						}
						System.out.println(s2);
						br.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				
			}
		});
		i3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		menu.add(i1);
		menu.add(i2);
		menu.add(i3);
		mb.add(menu);
		this.setJMenuBar(mb);
		this.setVisible(true);
	}
	
	private void createToolbar() {
		JToolBar tb = new JToolBar();
		tb.setRollover(true);
		JButton b1 = new JButton("copy");
		b1.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("copied");
				StringSelection selection = new StringSelection(table.currSelectedContent());
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(selection, selection);
			}
		});
		tb.add(b1);
		JButton b2 = new JButton("paste");
		b2.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
				System.out.println("pasted");
				
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();
				
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					table.table.setValueAt(clipboard.getData(DataFlavor.stringFlavor), row, column);
				} catch (UnsupportedFlavorException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		tb.add(b2);
		JButton b3 = new JButton("cut");
		b3.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("cut");
				
				StringSelection selection = new StringSelection(table.currSelectedContent());
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(selection, selection);
				
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();
				table.table.setValueAt("", row, column);
			}
		});
		tb.add(b3);
		tb.addSeparator();
		JButton b4 = new JButton("L");
		b4.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("alignment left");
			}
		});
		tb.add(b4);
		JButton b5 = new JButton("M");
		b5.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("alignment middle");
			}
		});
		tb.add(b5);
		JButton b6 = new JButton("R");
		b6.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("alignment right");
			}
		});
		tb.add(b6);
		tb.addSeparator();
		JButton b7 = new JButton("R-");
		b7.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("subtract row");
				int last_row = table.table.getRowCount() - 1;
				if (last_row != 0) {
					table.model.removeRow(last_row);
				}
			}
		});
		tb.add(b7);
		JButton b8 = new JButton("R+");
		b8.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("add row");
				table.model.addRow(new Object[]{table.table.getRowCount()});
			}
		});
		tb.add(b8);
		JButton b9 = new JButton("C-");
		b9.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("subtract column");
				
				int last_col = table.table.getColumnCount() - 1;
				if (last_col != 0) {
					table.model.setColumnCount(last_col);
				}
			}
		});
		tb.add(b9);
		JButton b10 = new JButton("C+");
		b10.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("add column");
				int last_col = table.table.getColumnCount();
				table.model.addColumn(last_col);
			}
		});
		tb.add(b10);
		this.contentPane.add(tb, BorderLayout.NORTH);
	}
	
	private void createStatusBar() {
		JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		statusBar.setBorder(new CompoundBorder(new LineBorder(Color.DARK_GRAY),
											   new EmptyBorder(4, 4, 4, 4)
		));
		final JLabel status = new JLabel();
		statusBar.add(status);
		
		add(statusBar, BorderLayout.SOUTH);
		table.table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				status.setText("row:" + table.table.getSelectedRow() + "column: " + table.table.getSelectedColumn());
			}
		});
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
	}
	
	@Override
	public void componentMoved(ComponentEvent e) {
	
	}
	
	@Override
	public void componentShown(ComponentEvent e) {
	
	}
	
	@Override
	public void componentHidden(ComponentEvent e) {
	
	}
	
	
	class MyTable extends JTable {
		
		JScrollPane sp;
		JTable table = new JTable();
		DefaultTableModel model;
		TextField tf = new TextField();
		
		MyTable(Container container) {
			
			model = (DefaultTableModel) table.getModel();
			
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			for (int i = 0; i < 5; i++) {
				model.addColumn(table.getColumnCount());
			}
			for (int i = 0; i < 10; i++) {
				model.addRow(new Object[]{i});
			}
			sp = new JScrollPane(table);
			sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			
			JPanel jp = new JPanel();
			jp.setLayout(new BorderLayout());
			jp.add(tf, BorderLayout.NORTH);
			jp.add(sp, BorderLayout.CENTER);
			container.add(jp, BorderLayout.CENTER);
			new TableCellListener(table, new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("cell edited");
				}
			});
		}
		
		public String currSelectedContent() {
			int row = table.getSelectedRow();
			int column = table.getSelectedColumn();
			return (String) table.getValueAt(row, column);
		}
	}
	
}
