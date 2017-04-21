package painter.shapes;

import painter.tools.ObjectsType;

public class Circle extends Ellipse {

    private Point corner;

    public Circle(Point point, float size) {
        super(point, new Point(point.getX() + size, point.getY() + size));
        super.setType(ObjectsType.CIRCLE);

    }

    public Circle() {
    }

}
