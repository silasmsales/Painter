package painter.shapes;

public class Rectangle extends GraphicObject{

    private Point pointA;
    private Point pointB;
    
    
    public Rectangle(Point pointA, Point pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
        super.addPoint(this.pointA);
        super.addPoint(new Point(this.pointB.getX(), this.pointA.getY()));
        super.addPoint(pointB);
        super.addPoint(new Point(this.pointA.getX(), this.pointB.getY()));
    }

    public Rectangle(float x1, float y1, float x2, float y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }
    public Rectangle(){
        
    }

    public Point getPointA() {
        return new Point(getXAxisInFloat()[0], getYAxisInFloat()[0]);
    }

    public Point getPointB() {
        return new Point(getXAxisInFloat()[2], getYAxisInFloat()[2]);
    }
    
    
}
