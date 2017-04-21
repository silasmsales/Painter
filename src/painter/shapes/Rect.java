package painter.shapes;

import painter.tools.ObjectsType;

public class Rect extends GraphicObject {

    public Rect(Point p1, Point p2) {
        super.addPoint(p1);
        super.addPoint(p2);
        super.setType(ObjectsType.QUADRANGLE);

    }

    public Rect() {
    }

    public Rect(float x1, float y1, float x2, float y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

}
