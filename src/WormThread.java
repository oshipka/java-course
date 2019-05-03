import java.awt.*;

public class WormThread implements Runnable
{
	private Point[] wormSegmentsCenterPoints = new Point[7];
	Graphics2D g2d;
	private boolean goingUp=true;
	WormThread(Graphics2D g)
	{
		int xLoc = 15;
		int yLoc = 250;
		g2d = g;
		
		for (int i = 0; i < 7; i++)
		{
			//ellipses[i].Fill = Brushes.LimeGreen;
			//ellipses[i].Stroke = Brushes.DarkGreen;
			wormSegmentsCenterPoints[i].x = xLoc;
			wormSegmentsCenterPoints[i].y = yLoc;
			drawCenteredCircle(g, xLoc, yLoc, 30);
			xLoc += 30;
		}
	}
	
	public void run()
	{
		WormMove();
	}
	
	private void WormMove()
	{
		int direction = 1;
		int framerate = 150 / 60;
		if (!this.goingUp)
		{
			direction = -1;
		}
		
		//1 segment
		wormSegmentsCenterPoints[0].x += direction * 1.89 / framerate;
		drawCenteredCircle(g2d, wormSegmentsCenterPoints[0].x, wormSegmentsCenterPoints[0].y, 30);
		
		
		//2 segment
		wormSegmentsCenterPoints[1].x += direction * 1.32 / framerate;
		wormSegmentsCenterPoints[1].y -= direction * 0.9 / framerate;
		drawCenteredCircle(g2d, wormSegmentsCenterPoints[1].x, wormSegmentsCenterPoints[1].y, 30);
		
		//3 segment
		wormSegmentsCenterPoints[2].x += direction * 0.45 / framerate;
		wormSegmentsCenterPoints[2].y -= direction * 1.8 / framerate;
		drawCenteredCircle(g2d, wormSegmentsCenterPoints[2].x, wormSegmentsCenterPoints[2].y, 30);
		
		//4 segment
		wormSegmentsCenterPoints[3].y -= direction * 2.5 / framerate;
		drawCenteredCircle(g2d, wormSegmentsCenterPoints[3].x, wormSegmentsCenterPoints[3].y, 30);
		
		
		//5 segment - mirrored 3
		wormSegmentsCenterPoints[4].x -= direction * 0.45 / framerate;
		wormSegmentsCenterPoints[4].y -= direction * 1.8 / framerate;
		drawCenteredCircle(g2d, wormSegmentsCenterPoints[4].x, wormSegmentsCenterPoints[4].y, 30);
		
		//6 segment - mirrored 2
		wormSegmentsCenterPoints[5].x -= direction * 1.32 / framerate;
		wormSegmentsCenterPoints[5].y -= direction * 0.9 / framerate;
		drawCenteredCircle(g2d, wormSegmentsCenterPoints[1].x, wormSegmentsCenterPoints[1].y, 30);
		
		//7 segment - mirrored 1
		wormSegmentsCenterPoints[6].x -= direction * 1.89 / framerate;
		drawCenteredCircle(g2d, wormSegmentsCenterPoints[6].x, wormSegmentsCenterPoints[6].y, 30);
		
		if (wormSegmentsCenterPoints[3].y <= wormSegmentsCenterPoints[0].y - (2.5 * 30) || (Math.abs(wormSegmentsCenterPoints[3].y - wormSegmentsCenterPoints[0].y) < 0.005))
		{
			this.goingUp = !this.goingUp;
		}
	}
	
	public void drawCenteredCircle(Graphics2D g, int x, int y, int r)
	{
		x = x - (r / 2);
		y = y - (r / 2);
		g.setColor(Color.getHSBColor(122, 61, 46));
		g.fillOval(x, y, r, r);
	}
	
}
