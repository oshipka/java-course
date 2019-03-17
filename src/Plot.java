import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Plot extends JFrame implements ComponentListener {




    private double zoom = 20;
    Plot(double a, double minX, double maxX) {
        super("Строфоїда");

        Strofoida strofoida = new Strofoida(a, minX, maxX);
        int width =(int) (strofoida.deltaX()*zoom*5);
        int height =(int) (strofoida.deltaY()*zoom*5);

        getContentPane().addComponentListener(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container drawable = getContentPane();
        JPanel canvas = new GraphCanvas(strofoida, width, height);
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

    public class GraphCanvas extends JPanel {
        private Strofoida strofoida;

        GraphCanvas(Strofoida _strofoida, double _width, double _height) {
            super();
            strofoida = _strofoida;
            this.setSize((int)_width, (int)_height);
        }

        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            ArrayList<Strofoida.Point> points = this.strofoida.GetPoints();
            for (int i = 0; i < points.size() - 2; i++) {
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
            g2d.drawString("Шипка Олена. Варіант 9", 10, 20);
        }
    }
}
