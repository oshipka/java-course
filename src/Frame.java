import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Frame extends JFrame implements ComponentListener {
	private int prevWidth;
	private int prevHeight;
	
	JMenu menu;
	JMenuItem i1, i2, i3;
	
	MyTable table;
	
	Container contentPane = getContentPane();
	
	private double zoom = 20;
	
	Frame() {
		super("Строфоїда");
		
		
		contentPane.addComponentListener(this);
		
		
		createMenu();
		createToolbar();
//		createTable();
		
		new MyTable(contentPane);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(500, 570);
	}
	
	private void createTable() {
		String data[][] = {{"101", "Amit", "670000"},
				{"102", "Jai", "780000"},
				{"101", "Sachin", "700000"}};
		String column[] = {"ID", "NAME", "SALARY"};
		final JTable jt = new JTable(data, column);
		jt.setCellSelectionEnabled(true);
		ListSelectionModel select = jt.getSelectionModel();
		select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		select.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String Data = null;
				int[] row = jt.getSelectedRows();
				int[] columns = jt.getSelectedColumns();
				for (int i = 0; i < row.length; i++) {
					for (int j = 0; j < columns.length; j++) {
						Data = (String) jt.getValueAt(row[i], columns[j]);
					}
				}
			}
		});
		JScrollPane sp = new JScrollPane(jt);
		this.contentPane.add(sp, BorderLayout.CENTER);
	}
	
	private void createMenu() {
		JMenuBar mb = new JMenuBar();
		menu = new JMenu("File");
		i1 = new JMenuItem(new AbstractAction("New") {
			public void actionPerformed(ActionEvent ae) {
				table.model.setRowCount(0);
				
				for (int i = 0; i < 5; i++) {
					table.model.addColumn(i);
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
			}
		});
		tb.add(b1);
		JButton b2 = new JButton("paste");
		b2.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("pasted");
			}
		});
		tb.add(b2);
		JButton b3 = new JButton("cut");
		b3.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("cut");
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
			}
		});
		tb.add(b7);
		JButton b8 = new JButton("R+");
		b8.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("add row");
			}
		});
		tb.add(b8);
		JButton b9 = new JButton("C-");
		b9.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("subtract column");
			}
		});
		tb.add(b9);
		JButton b10 = new JButton("C+");
		b10.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("add column");
			}
		});
		tb.add(b10);
		this.contentPane.add(tb, BorderLayout.NORTH);
		JTextArea textArea = new JTextArea();
		JScrollPane pane = new JScrollPane(textArea);
		contentPane.add(pane, BorderLayout.CENTER);
	}
	
	
	@Override
	public void componentResized(ComponentEvent e) {
		
		double longerDelta = getDelta(this.getWidth(), (prevWidth * 1.0), this.getHeight(), (prevHeight * 1.0));
		
		zoom *= longerDelta;
		System.out.println("Zoom: " + zoom);
		
		prevHeight = this.getHeight();
		prevWidth = this.getWidth();
		
	}
	
	private double getDelta(int width, double v, int height, double v1) {
		double longer = Math.max(Math.abs(width - v), Math.abs(height - v1));
		if (longer == Math.abs(width - v)) {
			return width / v;
		} else {
			return height / v1;
		}
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
	
	
	class MyTable extends  JTable{
		
		JScrollPane sp;
		JTable table = new JTable();
		DefaultTableModel model;
		TextField tf = new TextField();
		
		MyTable(Container container) {
			setLayout(new BorderLayout());
			model = (DefaultTableModel) table.getModel();
			
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			for (int i = 0; i < 5; i++) {
				model.addColumn(i);
			}
			for (int i = 0; i < 10; i++) {
				model.addRow(new Object[]{i});
			}
			sp = new JScrollPane(table);
			sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			
			JPanel jp = new JPanel();
			
			//sp.setSize(500, 500);
			tf.setSize(450, 7);
			//jp.add(tf, Gr.NORTH);
			//jp.add(sp, BorderLayout.CENTER);
			//sp.add(tf, BorderLayout.NORTH);
			//sp.setOpaque(true);
			//container.add(table, BorderLayout.CENTER);
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
		
		public String currSelected() {
			return "";
		}
	}
	
}
