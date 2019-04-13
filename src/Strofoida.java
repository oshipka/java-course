import java.util.ArrayList;

public class Strofoida {
    public double a;
    public double minX;
    public double maxX;
    double y;
    ArrayList<Point> points;
    
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
            double toSqrt = Math.pow(x, 2)*((a+x)/(a-x));
            if (toSqrt<0)
            {
                continue;
            }
            
            else {
            _y = Math.sqrt(toSqrt);
            points.add(new Point(x, _y));
            points.add(new Point(x, -1*_y));
            y = Math.max(y, Math.max(_y, -1*_y));
        }}
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
