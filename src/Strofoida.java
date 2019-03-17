import java.awt.*;
import java.util.ArrayList;

public class Strofoida {
    public double a;
    public double minX;
    public double maxX;
    double y;
    ArrayList<Point> points;

    Strofoida()
    {
        a=0;
        minX = 0;
        maxX = 0;
        y = 0;
        points = new ArrayList<Point>();
    }

    Strofoida(double _a, double _minX, double _maxX)
    {
        a = _a;
        minX = _minX;
        maxX = _maxX;
        points = new ArrayList<Point>();
        y = 0;
        for ( double x = minX; x<maxX; x+= 0.05)
        {
            double _y;
            _y = Math.sqrt(Math.pow(x, 2)*((a+x)/(a-x)));
            points.add(new Point(x, _y));
            points.add(new Point(x, -1*_y));
            y = Math.max(y, Math.max(_y, -1*_y));
        }
    }

    public ArrayList<Point> GetPoints(){
        return points;
    }

    public double deltaY() {
       return 2*y;
    }

    public double deltaX(){
        return maxX-minX;
    }

    public class Point{
        double x;
        double y;
        Point(double _x, double _y)
        {
            x = _x;
            y = _y;
        }
    }
}
