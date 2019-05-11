import animations.ClockAnimation;
import animations.FloatingRectangleAnimation;
import animations.IAnimation;
import animations.WormAnimation;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Frame extends JFrame {
private Color color;
private Stroke str;
private Random rd = new Random();

	private static final int WIDTH = 1000;
	private static final int HEIGHT = 800;
	private static final int DEFAULT_FRAME_RATE = 30;
	
	Frame() {
		super("Task 4 - OShipka");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(new GridLayout(1, 3, 5, 5));
		
		IAnimation[] animations = new IAnimation[] {
				new WormAnimation(DEFAULT_FRAME_RATE),
				new ClockAnimation(DEFAULT_FRAME_RATE),
				new FloatingRectangleAnimation(DEFAULT_FRAME_RATE)
		};
		
		this.setBackground(Color.lightGray);
		str = new BasicStroke(2f);
		
		for (int i = 0; i < animations.length; i++) {
			this.add(this.getThreadView(i, animations[i], WIDTH / animations.length + 1, HEIGHT, i % 2 != 0));
		}
		
		setSize(WIDTH, HEIGHT);
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
	}
	
	private JPanel getThreadView(final int pos, final IAnimation animation, int width, int height, boolean setBorder) {
		JPanel view = new JPanel();
		view.setLayout(new BorderLayout());
		view.setSize(width, height);
		if (setBorder) {
			view.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		}
		((JPanel)animation).setSize(width, height - 100);
		view.add((JPanel)animation, BorderLayout.CENTER);
		
		final JButton resume = new JButton("Resume");
		resume.setEnabled(false);
		final JButton pause = new JButton("Pause");
		resume.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				animation.resume();
				resume.setEnabled(false);
				pause.setEnabled(true);
			}
		});
		pause.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				animation.pause();
				pause.setEnabled(false);
				resume.setEnabled(true);
			}
		});
		
		final JTextField frameRate = new JTextField();
		frameRate.setColumns(10);
		frameRate.setText(Integer.toString(DEFAULT_FRAME_RATE));
		frameRate.getDocument().addDocumentListener(new DocumentListener() {
			
			private final Border _emptyBorder = BorderFactory.createLineBorder(Color.WHITE);
			private final Border _errorBorder = BorderFactory.createLineBorder(Color.RED);
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				this.readRate();
			}
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				this.readRate();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				this.readRate();
			}
			
			private void readRate() {
				String text = frameRate.getText().trim().replaceFirst("^0+(?!$)", "");
				if (text.length() == 0) {
					text = "0";
				}
				if (text.matches("[0-9]+")) {
					int rate = Integer.parseInt(text);
					animation.setFrameRate(rate);
					frameRate.setBorder(this._emptyBorder);
				} else {
					frameRate.setBorder(this._errorBorder);
				}
			}
		});
		JLabel frameLabel = new JLabel("Frame rate:");
		
		JPanel framePanel = new JPanel();
		framePanel.add(frameLabel, BorderLayout.NORTH);
		framePanel.add(frameRate, BorderLayout.CENTER);
		
		JPanel inputs = new JPanel();
		inputs.setLayout(new GridLayout(3, 1));
		inputs.add(framePanel);
		inputs.add(resume);
		inputs.add(pause);
		
		view.add(inputs, BorderLayout.NORTH);
		
		animation.start();
		return view;
	}
}
