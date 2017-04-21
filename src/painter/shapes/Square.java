package painter.shapes;

/**
 *
 * @author silasmsales
 */
public class Square extends Rectangle{

    public Square(Point point, float size) {
        super(point, new Point(point.getX()+size, point.getY()+size));
    }

    public Square(float x, float y, float size) {
        this(new Point(x, y), size);
    }

    public Square() {
    }

}
