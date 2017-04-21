package painter.shapes;

import painter.tools.ObjectsType;

/**
 *
 * @author silasmsales
 */
public class Square extends Rectangle {

    public Square(Point point, float size) {
        super(point, new Point(point.getX() + size, point.getY() + size));
        super.setType(ObjectsType.POINT);
    }

    public Square(float x, float y, float size) {
        this(new Point(x, y), size);
    }

    public Square() {
    }

}
