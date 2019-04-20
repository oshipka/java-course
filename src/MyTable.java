import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

class MyTable extends JTable
{
	
	JTable table = new JTable();
	DefaultTableModel model;
	TextField tf = new TextField();
	JSONArray functions = new JSONArray();
	
	MyTable(Container container)
	{
		
		model = (DefaultTableModel) table.getModel();
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for (int i = 0; i < 5; i++)
		{
			model.addColumn(table.getColumnCount());
		}
		for (int i = 0; i < 10; i++)
		{
			model.addRow(new Object[]{i});
		}
		JScrollPane sp = new JScrollPane(table);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		jp.add(tf, BorderLayout.NORTH);
		jp.add(sp, BorderLayout.CENTER);
		container.add(jp, BorderLayout.CENTER);
		new TableCellListener(table, new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("cell edited");
				try
				{
					Double.parseDouble(currSelectedContent());
				} catch (NumberFormatException ex)
				{
					// Use whatever default you like
					JSONObject jObj = new JSONObject();
					jObj.put("row_num", table.getSelectedRow());
					jObj.put("col_num", table.getSelectedColumn());
					jObj.put("func_exp", currSelectedContent());
					functions.add(jObj);
					
					int row = table.getSelectedRow();
					int column = table.getSelectedColumn();
					
					MParser mps = new MParser(currSelectedContent());
					
					table.setValueAt(mps.CalculateExpression(), row, column);
					
				}
			}
		});
		addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				tf.setText(currSelectedContent());
			}
			
			@Override
			public void focusLost(FocusEvent e)
			{
			
			}
		});
		tf.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
			}
			
			@Override
			public void keyPressed(KeyEvent e)
			{
			
			}
			
			@Override
			public void keyReleased(KeyEvent e)
			{
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();
				table.setValueAt(tf.getText(), row, column);
			}
		});
	}
	
	public String currSelectedContent()
	{
		int row = table.getSelectedRow();
		int column = table.getSelectedColumn();
		String f = getFunction(row, column);
		if (f!=null)
		{
			return f;
		}
		return (String) table.getValueAt(row, column);
	}
	
	private String getFunction(int row, int column)
	{
		for (int i = 0; i < functions.size(); i++)
		{
			JSONObject jObj = (JSONObject) functions.get(i);
			Object r =  jObj.get("row_num");
			Object c =  jObj.get("col_num");
			int curr_r = Integer.parseInt( r.toString());
			int curr_c = Integer.parseInt( c.toString());
			if ( curr_r == row
						&&  curr_c== column)
			{
				return (String) jObj.get("func_exp");
			}
		}
		return null;
	}
}

