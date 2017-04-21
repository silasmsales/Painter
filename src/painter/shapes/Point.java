package painter.shapes;

import painter.tools.ObjectsType;

public class Point extends GraphicObject {

    public Point(float x, float y) {
        super(new float[]{x}, new float[]{y});
        super.setType(ObjectsType.POINT);

    }

    public Point() {
    }

    public Point getPoint() {
        return this;
    }

    public float getX() {
        return getXAxisInFloat()[0];
    }

    public float getY() {
        return getYAxisInFloat()[0];
    }

    public void setX(float x) {
        if (this.getMatrix() == null) {
            this.addPoint(0, 0);
        }
        getMatrix()[0][0] = x;
    }

    public void setY(float y) {
        if (this.getMatrix() == null) {
            this.addPoint(0, 0);
        }
        getMatrix()[0][1] = y;
    }
}
