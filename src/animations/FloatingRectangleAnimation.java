package animations;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class FloatingRectangleAnimation extends ThPanel
{
	int[] leftTomCorner;
	boolean moveRight;
	boolean moveDown;
	int length, width;
	
	Color[] colors = new Color[]{Color.BLACK,
			Color.darkGray,
			Color.lightGray,
			Color.RED,
			Color.blue,
			Color.DARK_GRAY,
			Color.GRAY,
			Color.green,
			Color.MAGENTA,
			Color.ORANGE,
			Color.PINK};
	Color currC = Color.green;
	Random r = new Random();
	
	public FloatingRectangleAnimation(int updateRate)
	{
		super(updateRate, "Floater");
		moveRight = true;
		moveDown = true;
		leftTomCorner = new int[]{0, 0};
		length = 50;
		width = 30;
	}
	
	private void MoveRectangle()
	{
		if (moveDown)
		{
			leftTomCorner[1] += 1;
		} else
		{
			leftTomCorner[1] -= 1;
		}
		if (moveRight)
		{
			leftTomCorner[0] += 1;
		} else
		{
			leftTomCorner[0] -= 1;
		}
		
		if (leftTomCorner[0] + width == this.getWidth() || leftTomCorner[0] == 0)
		{
			moveRight = !moveRight;
			currC = colors[r.nextInt(colors.length)];
		}
		if (leftTomCorner[1] + length == this.getHeight() || leftTomCorner[1] == 0)
		{
			moveDown = !moveDown;
			currC = colors[r.nextInt(colors.length)];
		}
	}
	
	@Override
	public void paint(Graphics2D g2d)
	{
		try
		{
			g2d.setColor(currC);
			g2d.fillRect(leftTomCorner[0], leftTomCorner[1], width, length);
			
		} catch (IndexOutOfBoundsException ignored)
		{
		}
	}
	
	@Override
	public void loop()
	{
		
		while (true)
		{
			MoveRectangle();
			this.updateFrame();
		}
	}
}
