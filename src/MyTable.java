import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.Console;

class MyTable extends JTable
{
	
	JTable table = new JTable();
	DefaultTableModel model;
	TextField tf = new TextField();
	private JSONArray functions = new JSONArray();
	
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
					evalFunction(currSelectedContent());
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
				if (e.getKeyCode() == 10)
				{
					try
					{
						double text = Double.parseDouble(tf.getText());
					} catch (NumberFormatException ex)
					{
						evalFunction(tf.getText());
					}
				}
			}
		});
	}
	
	public String currSelectedContent()
	{
		int row = table.getSelectedRow();
		int column = table.getSelectedColumn();
		String f = getFunction(row, column);
		if (f != null)
		{
			return f;
		}
		return (String) table.getValueAt(row, column);
	}
	
	private String getFunction(int row, int column)
	{
		for (Object function : functions)
		{
			JSONObject jObj = (JSONObject) function;
			Object r = jObj.get("row_num");
			Object c = jObj.get("col_num");
			int curr_r = Integer.parseInt(r.toString());
			int curr_c = Integer.parseInt(c.toString());
			if (curr_r == row && curr_c == column)
			{
				return (String) jObj.get("func_exp");
			}
		}
		return null;
	}
	
	public void evalFunction(String function)
	{
		saveFunction(function);
		
		int row = table.getSelectedRow();
		int column = table.getSelectedColumn();
		
		MParser mps = new MParser(currSelectedContent());
		double res = mps.CalculateExpression();
		if (Double.toString(res) =="NaN")
		{
			table.setValueAt(currSelectedContent(), row, column);
			
		}
		else {
			table.setValueAt(res, row, column);
			
		}
	}
	
	private void saveFunction(String function)
	{
		int row = table.getSelectedRow();
		int col = table.getSelectedColumn();
		
		boolean exists = false;
		
		for (int i = 0; i < functions.size(); i++)
		{
			JSONObject func = (JSONObject) functions.get(i);
			if (Integer.parseInt(func.get("row_num").toString()) == row &&
					Integer.parseInt(func.get("col_num").toString()) == col)
			{
				exists = true;
				func.put("func_exp", function);
			}
		}
		if (!exists)
		{
			JSONObject jObj = new JSONObject();
			jObj.put("row_num", row);
			jObj.put("col_num", col);
			jObj.put("func_exp", currSelectedContent());
			functions.add(jObj);
		}
	}
	
	public String tableToValues()
	{
		JSONArray values = new JSONArray();
		int rows = table.getRowCount();
		int columns = table.getColumnCount();
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				if (table.getValueAt(i, j) != null)
				{
					JSONObject jObj = new JSONObject();
					jObj.put("row_num", i);
					jObj.put("col_num", j);
					jObj.put("func_exp", table.getValueAt(i, j));
					values.add(jObj);
				}
			}
		}
		String res = values.toString();
		System.out.println(res);
		return res;
	}
	
	public void setValues(String s2)
	{
		try
		{
			JSONParser parser = new JSONParser();
			JSONArray array = (JSONArray) parser.parse(s2);
			
			for (int i = 0; i < array.size(); i++)
			{
				JSONObject func = (JSONObject) array.get(i);
				int row = Integer.parseInt(func.get("row_num").toString());
				int col = Integer.parseInt(func.get("col_num").toString());
				if (row >= table.getRowCount())
				{
					for (int j = table.getRowCount() + 1; j <= row+1; j++)
					{
						model.addRow(new Object[]{j+1});
					}
				}
				if (col >= table.getColumnCount())
				{
					for (int j = table.getColumnCount() + 1; j <= col+1; j++)
					{
						model.addColumn(j-1);
					}
				}
				table.setValueAt(func.get("func_exp").toString(), row, col);
			}
			
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
	}
}
