package animations;


import java.awt.*;
import java.util.List;

public class ClockAnimation extends ThPanel
{
	private double[] endHPoint;
	private double[] endMPoint;
	private double[] centerPoint;
	
	public ClockAnimation(int updateRate)
	{
		super(updateRate, "Clock");
		centerPoint = new double[]{300, 300};
		endMPoint = new double[]{300, 180};
		endHPoint = new double[]{300, 200};
	}
	
	
	private void ClockMove()
	{
		double mturn = 0.2;
		double hturn = 0.02;
		double new_X;
		double new_Y;
		
		new_X = centerPoint[0] + (endMPoint[0] - centerPoint[0]) * Math.cos(mturn) - (endMPoint[1] - centerPoint[1]) * Math.sin(mturn);
		new_Y = centerPoint[1] + (endMPoint[0] - centerPoint[0]) * Math.sin(mturn) + (endMPoint[1] - centerPoint[1]) * Math.cos(mturn);
		
		endMPoint[0] = new_X;
		endMPoint[1] = new_Y;
		
		new_X = centerPoint[0] + (endHPoint[0] - centerPoint[0]) * Math.cos(hturn) - (endHPoint[1] - centerPoint[1]) * Math.sin(hturn);
		new_Y = centerPoint[1] + (endHPoint[0] - centerPoint[0]) * Math.sin(hturn) + (endHPoint[1] - centerPoint[1]) * Math.cos(hturn);
		
		endHPoint[0] = new_X;
		endHPoint[1] = new_Y;
	}
	
	@Override
	public void paint(Graphics2D g2d)
	{
		try
		{
			
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(5));
			g2d.drawOval((int) centerPoint[0]/2, (int) centerPoint[1]/2, 300, 300);
			g2d.drawLine((int) centerPoint[0], (int) centerPoint[1], (int) endMPoint[0], (int) endMPoint[1]);
			g2d.drawLine((int) centerPoint[0], (int) centerPoint[1], (int) endHPoint[0], (int) endHPoint[1]);
			
			
		} catch (IndexOutOfBoundsException ignored)
		{
		}
	}
	
	@Override
	public void loop() {
		
		while (true) {
			ClockMove();
			this.updateFrame();
		}
	}
	
	
}
