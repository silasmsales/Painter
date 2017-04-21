package painter.shapes;

/**
 *
 * @author Tiago
 */

/*
- Get First Point (furthest) (A) OK
- Get Next Point at the right (or left) (B)
- Get Next (third) Point at the right (or left) (C)
- Check angle, if it is a Reflexive angle, remove B.
- Continue checking until returns to start
- New matrix is the ConvexHull.
 */
public class Convex extends GraphicObject {

    public Convex(float[][] matrixPoints) {
        float[][] sortedMatrix = (getSortedMatrix(matrixPoints));
    }

    private void getAngle() {
        Point p1 = new Point(200, 200);
        Point p2 = new Point(50, 50); // Test angle

        float difX = p2.getX() - p1.getX();
        double difY = p2.getY() - p1.getY();
        double rotAng = Math.toDegrees(Math.atan2(difX, -difY));

        System.out.println(rotAng);
    }

    private Point getLowestPoint(float[][] matrixPoints) {
        Point lowest = new Point(matrixPoints[0][0], matrixPoints[0][1]);

        for (int i = 0; i < matrixPoints.length; i++) {
            Point temp = new Point(matrixPoints[i][0], matrixPoints[i][1]);

            if (temp.getY() < lowest.getY() || temp.getY() == lowest.getY() && temp.getX() < lowest.getX()) {
                lowest = temp;
            }
        }
        return lowest;
    }

    // Test
    private float[][] getSortedMatrix(float[][] matrixPoints) {
        return matrixPoints;
    }
}
