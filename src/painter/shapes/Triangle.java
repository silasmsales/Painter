package painter.shapes;

/**
 *
 * @author silasmsales
 */
public class Triangle extends GraphicObject{

    public Triangle(Point p1, Point p2, Point p3) {
        super.addPoint(p1);
        super.addPoint(p2);
        super.addPoint(p3);
    }

    public Triangle() {
    }
    
    
}
