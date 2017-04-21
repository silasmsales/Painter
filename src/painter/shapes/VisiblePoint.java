package painter.shapes;

import painter.tools.ObjectsType;

public class VisiblePoint extends GraphicObject{

    
    public VisiblePoint(Point point) {
        super.addPoint(new Point(point.getX(), point.getY()));
        super.addPoint(new Point(point.getX()+1, point.getY()));
        super.addPoint(new Point(point.getX(), point.getY()+1));
        super.addPoint(new Point(point.getX()+1, point.getY()+1));
        super.setType(ObjectsType.POINT);
    }
    
}