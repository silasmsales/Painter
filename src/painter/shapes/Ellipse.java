package painter.shapes;

import java.util.Arrays;

/**
 *
 * @author silasmsales
 */
public class Ellipse extends GraphicObject {

    private Point firstCorner;
    private Point secondCorner;

    public Ellipse(Point firstCorner, Point secondCorner) {

        this.firstCorner = firstCorner;
        this.secondCorner = secondCorner;
        
        if ((secondCorner.getX() < firstCorner.getX()) && (secondCorner.getY() < firstCorner.getY())) {
            Point temp = firstCorner;
            firstCorner = secondCorner;
            secondCorner = temp;
        } else if ((secondCorner.getX() > firstCorner.getX()) && (secondCorner.getY() < firstCorner.getY())) {
            float first_Y = firstCorner.getY();
            float second_Y = secondCorner.getY();
            firstCorner.setY(second_Y);
            secondCorner.setY(first_Y);
        }else if((secondCorner.getX() < firstCorner.getX())&&(secondCorner.getY() > firstCorner.getY())){
            float first_X = firstCorner.getX();
            float second_X = secondCorner.getX();
            firstCorner.setX(second_X);
            secondCorner.setX(first_X);
        }
        
        Rectangle quadrangle = new Rectangle(firstCorner, secondCorner);
        Point centerPoint = new Point(quadrangle.getCentroid().getX(), quadrangle.getCentroid().getY());
        float a = (secondCorner.getX() - firstCorner.getX()) / 2;
        float b = (secondCorner.getY() - firstCorner.getY()) / 2;
        double tmp = Math.pow(a, 2) - Math.pow(b, 2);
        if (tmp < 0) {
            tmp = tmp * -1;
        }
        float c = (float) Math.sqrt(tmp);
        Point knownPoint;
        Point focal_point1;
        Point focal_point2;
        if (a >= b) {
            knownPoint = new Point(firstCorner.getX() + a, firstCorner.getY());
            focal_point1 = new Point(centerPoint.getX() - c, centerPoint.getY());
            focal_point2 = new Point(centerPoint.getX() + c, centerPoint.getY());
        } else {
            knownPoint = new Point(firstCorner.getX(), firstCorner.getY() + b);
            focal_point1 = new Point(centerPoint.getX(), centerPoint.getY() - c);
            focal_point2 = new Point(centerPoint.getX(), centerPoint.getY() + c);
        }
        float constant_distance;
        constant_distance = super.getDistanceOfTwoPoints(focal_point1, knownPoint);
        constant_distance += super.getDistanceOfTwoPoints(knownPoint, focal_point2);
        for (int y = (int) firstCorner.getY(); y <= secondCorner.getY(); y++) {
            for (int x = (int) firstCorner.getX(); x <= secondCorner.getX(); x++) {
                Point point = new Point(x, y);
                float distance;
                distance = super.getDistanceOfTwoPoints(focal_point1, point);
                distance += super.getDistanceOfTwoPoints(point, focal_point2);
                if ((distance < constant_distance + 0.5) && (distance > constant_distance - 0.5)) {
                    super.addPoint(point);
                }
            }
        }

    }

    public Ellipse() {
    }

    private float getFocalDistance(Point pointA, Point pointB) {
        float distance;
        distance = (float) (Math.pow((pointA.getX() - pointB.getX()) / 2, 2) - Math.pow((pointA.getY() - pointB.getY()) / 2, 2));
        distance = (float) (Math.sqrt(distance));
        return distance;
    }

    public Point getFirstCorner() {
        return firstCorner;
    }

    public Point getSecondCorner() {
        return secondCorner;
    }

}
