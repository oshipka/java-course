package animations;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WormAnimation extends ThPanel
{
	private List<Boolean> goingUps=new LinkedList<Boolean>();
	private List<List<double[]>> ptss = new LinkedList<List<double[]>>();
	int frames;
	public WormAnimation(int updateRate)
	{
		super(updateRate, "Worm");
		GetStartPoints(15, 250);
		frames = updateRate;
		addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
			GetStartPoints(e.getX(), e.getY());
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
			
			}
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
			
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
			
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
			
			}
		});
	}
	
	private void GetStartPoints(int xLoc, int yLoc)
	{
		List<double[]> pts = new LinkedList<double[]>();
		for (int i = 0; i < 7; i++)
		{
			pts.add(new double[]{xLoc, yLoc});
			xLoc += 30;
		}
		ptss.add(pts);
		goingUps.add(true);
	}
	
	/*public void run()
	{
		WormMove();
	}*/
	
	private void WormMove()
	{
		for (int j = 0; j < ptss.size(); j++)
		{
			
			List<double[]> pts = ptss.get(j);
			boolean goingUp = goingUps.get(j);
			
			int direction = 1;
			double framerate = 300/frames;
			if (!goingUp)
			{
				direction = -1;
			}
			
			if (goingUp)
			{
				pts.get(0)[0] -= 0 / framerate;
				pts.get(1)[0] -= 10 / framerate;
				pts.get(2)[0] -= 20 / framerate;
				pts.get(3)[0] -= 30 / framerate;
				pts.get(4)[0] -= 40 / framerate;
				pts.get(5)[0] -= 50 / framerate;
				pts.get(6)[0] -= 60 / framerate;
			}
			if (!goingUp)
			{
				pts.get(0)[0] -= 60 / framerate;
				pts.get(1)[0] -= 50 / framerate;
				pts.get(2)[0] -= 40 / framerate;
				pts.get(3)[0] -= 30 / framerate;
				pts.get(4)[0] -= 20 / framerate;
				pts.get(5)[0] -= 10 / framerate;
				pts.get(6)[0] -= 0 / framerate;
			}
			
			pts.get(1)[1] -= direction * 27 / framerate;
			pts.get(2)[1] -= direction * 54 / framerate;
			pts.get(3)[1] -= direction * 75 / framerate;
			pts.get(4)[1] -= direction * 54 / framerate;
			pts.get(5)[1] -= direction * 27 / framerate;
			
			if ((pts.get(3)[1] <= pts.get(0)[1] - (2.5 * 30 ) && goingUp )|| ((Math.abs(pts.get(3)[1] - pts.get(0)[1])) < 10 && !goingUp))
			{
				goingUp = !goingUp;
			}
			
			ptss.set(j, pts);
			goingUps.set(j, goingUp);
		}
		for (int i = ptss.size()-1; i>=0; i--)
		{
			if(ptss.get(i).get(ptss.get(i).size()-1)[0]<0)
			{
				ptss.remove(i);
			}
		}
	}
	

	
	
	@Override
	public void paint(Graphics2D g2d)
	{
		try
		{
			for (List<double[]> points : this.ptss)
			{
				for (int i = 0; i < points.size(); i++)
				{
					g2d.setColor(Color.getHSBColor((float)0.35, (float)0.6, (float)0.6));
					g2d.fillOval((int) points.get(i)[0], (int) points.get(i)[1], 30, 30);
					g2d.setStroke(new BasicStroke(5));
					g2d.setColor(Color.getHSBColor((float)0.35, (float)0.6, (float)0.3));
					g2d.drawOval((int) points.get(i)[0], (int) points.get(i)[1], 30, 30);
				}
			}
		} catch (IndexOutOfBoundsException ignored)
		{
		}
	}
	
	@Override
	public void loop() {
		
		while (true) {
			WormMove();
			this.updateFrame();
		}
	}
	
	
}
