
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/** @noinspection deprecation*/
public class Frame extends JFrame implements ComponentListener
{
	
	private MyTable table;
	
	private Container contentPane = getContentPane();
	
	//private double zoom = 20;
	
	Frame()
	{
		super("Exel 2.0");
		
		contentPane.addComponentListener(this);
		
		createMenu();
		table = new MyTable(contentPane);
		createToolbar();
		createStatusBar();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(500, 600);
	}
	
	
	private void createMenu()
	{
		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem i1 = new JMenuItem(new AbstractAction("New")
		{
			public void actionPerformed(ActionEvent ae)
			{
				table.model.setRowCount(1);
				table.model.setColumnCount(1);
				
				for (int i = 0; i < 5; i++)
				{
					table.model.addColumn(table.table.getColumnCount());
				}
				for (int i = 0; i < 10; i++)
				{
					table.model.addRow(new Object[]{i});
				}
			}
		});
		i1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		JMenuItem i2 = new JMenuItem(new AbstractAction("Save")
		{
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
					
					LocalDateTime now = LocalDateTime.now();
					String currentTime = dtf.format(now);
					currentTime = currentTime.replace('/', '_');
					currentTime = currentTime.replace(':', '_');
					currentTime = currentTime.replace(' ', '_');
					
					String filename = "D:/" + currentTime +".json";
					String values = table.tableToValues();
					BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
					
					writer.write(values);
					
					writer.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});
		i2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		JMenuItem i3 = new JMenuItem(new AbstractAction("Open")
		{
			public void actionPerformed(ActionEvent ae)
			{
				JFileChooser fc = new JFileChooser();
				int i = fc.showOpenDialog(contentPane);
				if (i == JFileChooser.APPROVE_OPTION)
				{
					File f = fc.getSelectedFile();
					String filepath = f.getPath();
					try
					{
						BufferedReader br = new BufferedReader(new FileReader(filepath));
						String s1, s2 = "";
						while ((s1 = br.readLine()) != null)
						{
							s2 += s1 + "\n";
						}
						
						//output saved to string s2
						System.out.println(s2);
						table.setValues(s2);
						br.close();
					} catch (Exception ex)
					{
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
	
	private void createToolbar()
	{
		JToolBar tb = new JToolBar();
		tb.setRollover(true);
		JButton b1 = new JButton("copy");
		b1.addActionListener(new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
				StringSelection selection = new StringSelection(table.currSelectedContent());
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(selection, selection);
				System.out.println("copied" + selection);
			}
		});
		tb.add(b1);
		JButton b2 = new JButton("paste");
		b2.addActionListener(new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
				Transferable t = c.getContents(this);
				if (t == null) return;
				try
				{
					int row = table.table.getSelectedRow();
					int column = table.table.getSelectedColumn();
					table.table.setValueAt((String) t.getTransferData(DataFlavor.stringFlavor), row, column);
					System.out.println("pasted");
				} catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		tb.add(b2);
		JButton b3 = new JButton("cut");
		b3.addActionListener(new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
				System.out.println("cut");
				
				StringSelection selection = new StringSelection(table.currSelectedContent());
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(selection, selection);
				
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();
				table.evalFunction("0");
			}
		});
		tb.add(b3);
		tb.addSeparator();
		
		//Adding buttons to edit amount of rows
		
		JButton b7 = new JButton("R-");
		b7.addActionListener(new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
				System.out.println("subtract row");
				int last_row = table.table.getRowCount() - 1;
				if (last_row != 0)
				{
					table.model.removeRow(last_row);
				}
			}
		});
		tb.add(b7);
		JButton b8 = new JButton("R+");
		b8.addActionListener(new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
				System.out.println("add row");
				table.model.addRow(new Object[]{table.table.getRowCount()});
			}
		});
		tb.add(b8);
		JButton b9 = new JButton("C-");
		b9.addActionListener(new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
				System.out.println("subtract column");
				
				int last_col = table.table.getColumnCount() - 1;
				if (last_col != 0)
				{
					table.model.setColumnCount(last_col);
				}
			}
		});
		tb.add(b9);
		JButton b10 = new JButton("C+");
		b10.addActionListener(new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
				System.out.println("add column");
				int last_col = table.table.getColumnCount();
				table.model.addColumn(last_col);
			}
		});
		tb.add(b10);
		this.contentPane.add(tb, BorderLayout.NORTH);
	}
	
	private void createStatusBar()
	{
		JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		statusBar.setBorder(new CompoundBorder(new LineBorder(Color.DARK_GRAY),
				new EmptyBorder(4, 4, 4, 4)
		));
		final JLabel status = new JLabel();
		statusBar.add(status);
		
		add(statusBar, BorderLayout.SOUTH);
		table.table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				status.setText("row:" + table.table.getSelectedRow() + "column: " + table.table.getSelectedColumn());
				table.tf.setText(table.currSelectedContent());
			}
		});
	}
	
	@Override
	public void componentResized(ComponentEvent e)
	{
	}
	
	@Override
	public void componentMoved(ComponentEvent e)
	{
	}
	
	@Override
	public void componentShown(ComponentEvent e)
	{
	}
	
	@Override
	public void componentHidden(ComponentEvent e)
	{
	}
}
