package painter.tools;

import java.util.Arrays;
import java.util.List;
import painter.shapes.GraphicObject;
import painter.shapes.Point;

public class Selection {

    public static final int NONE = -1;
    
    public static int getSelectedObject(List<GraphicObject> list, Point point){
        for (int i = list.size()-1; i >= 0; i--) {
            if (isSelected(list.get(i), point)) {
                return i;
            }
        }
        return NONE;
    }

    private static boolean isSelected(GraphicObject object, Point point) {
        if (object.getLength() == 2) {
            Point point1 = new Point(object.getMatrix()[0][0], object.getMatrix()[0][1]);
            Point point2 = new Point(object.getMatrix()[1][0], object.getMatrix()[1][1]);
            float distance1 = GraphicObject.getDistanceOfTwoPoints(point1, point);
            float distance2 = GraphicObject.getDistanceOfTwoPoints(point, point2);
            float distance3 = GraphicObject.getDistanceOfTwoPoints(point1, point2);
            float distance4 = distance1+distance2;
            
            if (distance4 < distance3 + 0.1 && distance4 > distance3 - 0.1) {
                return true;
            }
        }
        int i, j;
        int[][] pointArray = new int[object.getLength()][2];
        int [] Xs = object.getXAxisConvex();
        int [] Ys = object.getYAxisConvex();
        int vertNumber = pointArray.length;
        for (int k = 0; k < vertNumber; k++) {
            pointArray[k][0] = Xs[k];
            pointArray[k][1] = Ys[k];
        }

        boolean c = false;

        for (i = 0, j = vertNumber - 1; i < vertNumber; j = i++) {
            if (((pointArray[i][1] >= point.getY()) != (pointArray[j][1] >= point.getY()))
                    && (point.getX() <= (pointArray[j][0] - pointArray[i][0]) * (point.getY() - pointArray[i][1]) / (pointArray[j][1] - pointArray[i][1]) + pointArray[i][0])) {
                c = !c;
            }
        }
        return c;
    }

}
