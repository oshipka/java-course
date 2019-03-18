import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import static java.awt.BasicStroke.*;

public class Plot extends JFrame implements MouseListener, ComponentListener {
Color color;
Stroke str;
Random rd = new Random();
	JPanel canvas;

	private double zoom = 20;
	Plot(double a, double minX, double maxX) {
		super("Строфоїда");

		Strofoida strofoida = new Strofoida(a, minX, maxX);
		int width =(int) (strofoida.deltaX()*zoom*2);
		int height =(int) (strofoida.deltaY()*zoom*4);

		getContentPane().addComponentListener(this);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container drawable = getContentPane();
		str = new BasicStroke(2f);
		canvas = new GraphCanvas(strofoida, width, height, color, str, this);
		drawable.add(canvas);
		setSize(width, height);
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
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		//canvas.invalidate();
		//this.repaint();
		//GraphCanvas.draw();
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
	
	public class GraphCanvas extends JPanel {
		private Strofoida strofoida;
		Stroke stroke;
		Color color;
        
        int prevWidth;
        int prevHeight;
		
		GraphCanvas(Strofoida _strofoida, int _width, int _height, Color _c, Stroke _s, final JFrame parent) {
			super();
			strofoida = _strofoida;
			this.setSize(_width, _height);
			stroke = _s;
			color = _c;
			prevHeight = _height;
			prevWidth = _width;
			this.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e)
				{
					SwingUtilities.updateComponentTreeUI(parent);
				}
				
				@Override
				public void mousePressed(MouseEvent e)
				{
					color = swapColor();
				}
				
				@Override
				public void mouseReleased(MouseEvent e)
				{
					stroke = swapStroke();
				}
				
				private Stroke swapStroke()
				{
					int[] caps = new int[]{CAP_BUTT, CAP_ROUND,	CAP_SQUARE};
					int[] joins = new int[]{CAP_BUTT, CAP_ROUND, CAP_SQUARE};
					
					int size = 1+ rd.nextInt(5);
					float[] dash = new float[size];
					for (int i = 0; i< size; i++)
					{
						dash[i] = 1 + rd.nextFloat();
					}
					
					return new BasicStroke(
							rd.nextInt(10)+rd.nextFloat(),
							caps[rd.nextInt(caps.length)],
							joins[rd.nextInt(joins.length)],
							1 + rd.nextFloat() * 4,
							dash,
							1+rd.nextFloat());
				}
				
				private Color swapColor()
				{
					
					Color[] colors = new Color[]{
							Color.BLACK,
							Color.blue,
							Color.lightGray,
							Color.cyan,
							Color.GRAY,
							Color.green,
							Color.MAGENTA,
							Color.ORANGE,
							Color.PINK,
							Color.RED,
							Color.YELLOW
					};
					return colors[rd.nextInt(colors.length)];
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

		
		public void paint(Graphics g)
		{
			Graphics2D g2d = (Graphics2D) g;
			ArrayList<Strofoida.Point> points = this.strofoida.GetPoints();
	
			g2d.setColor(Color.lightGray);
			Stroke s = new BasicStroke(0.5f);
			g2d.setStroke(s);
			
			//draw axis
			Point2D x1 = new Point2D.Double(
					0,
					this.getHeight() / 2.0
			);
			Point2D x2 = new Point2D.Double(
					this.getWidth(),
					this.getHeight()/2.0
			);
			g2d.draw(new Line2D.Double(x1, x2));
	
			Point2D y1 = new Point2D.Double(
					this.getWidth() / 2.0,
					0
			);
			Point2D y2 = new Point2D.Double(
					this.getWidth() / 2.0,
					this.getHeight()
			);
			g2d.draw(new Line2D.Double(y1, y2));
	
			g2d.setColor(this.color);
			g2d.setStroke(this.stroke);
			
			zoom = getZoom();
			
			//draw plot
			for (int i = 0; i < points.size() - 2; i++)
			{
				Point2D p1 = new Point2D.Double(
						points.get(i).x * zoom + this.getWidth() / 2.0,
						points.get(i).y * zoom + this.getHeight() / 2.0
				);
				Point2D p2 = new Point2D.Double(
						points.get(i + 2).x * zoom + this.getWidth() / 2.0,
						points.get(i + 2).y * zoom + this.getHeight() / 2.0
				);
				g2d.draw(new Line2D.Double(p1, p2));
			}
			g2d.setColor(Color.BLACK);
			g2d.drawString("Шипка Олена. Варіант 9", 10, 20);
		}
        
        private double getZoom()
        {
            double res = zoom;
            
            double longerDelta = getDelta(this.getWidth(), (prevWidth*1.0), this.getHeight(), (prevHeight*1.0));

            res *= longerDelta;
            System.out.println("Zoom: " + zoom);
            
            prevHeight = this.getHeight();
            prevWidth = this.getWidth();
            
            return res;
        }
        
        private double getDelta(int width, double v, int height, double v1)
        {
            double longer = Math.max(Math.abs(width-v), Math.abs(height-v1));
            if (longer == Math.abs(width-v))
            {
                return width/v;
            }
            else
                return height/v1;
        }
    }
}
