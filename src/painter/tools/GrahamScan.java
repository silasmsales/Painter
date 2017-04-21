package painter.tools;

import painter.shapes.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

/**
 *
 * @author Tiago
 */
public final class GrahamScan {

    private static enum Turn {
        CLOCKWISE, COUNTER_CLOCKWISE, COLLINEAR
    }

    private static boolean areAllCollinear(List<Point> points) {
        if (points.size() < 2) {
            return true;
        }
        final Point a = points.get(0);
        final Point b = points.get(1);

        for (int i = 2; i < points.size(); i++) {
            Point c = points.get(i);

            if (getTurn(a, b, c) != Turn.COLLINEAR) {
                return false;
            }
        }

        return true;
    }

    private static Turn getTurn(Point a, Point b, Point c) {
        float crossProduct = ((b.getX() - a.getX()) * (c.getY() - a.getY()))
                - ((b.getY() - a.getY()) * (c.getX() - a.getX()));

        if (crossProduct > 0) {
            return Turn.COUNTER_CLOCKWISE;
        } else if (crossProduct < 0) {
            return Turn.CLOCKWISE;
        } else {
            return Turn.COLLINEAR;
        }
    }

    private static Point getLowestPoint(List<Point> points) {
        Point lowest = points.get(0);

        for (int i = 1; i < points.size(); i++) {
            Point temp = points.get(i);

            if (temp.getY() < lowest.getY() || (temp.getY() == lowest.getY() && temp.getX() < lowest.getX())) {
                lowest = temp;
            }
        }

        return lowest;
    }

    private static Set<Point> getSortedPointSet(List<Point> points) {

        final Point lowest = getLowestPoint(points);

        TreeSet<Point> set = new TreeSet<Point>(new Comparator<Point>() {
            @Override
            public int compare(Point a, Point b) {
                if (a == b || a.equals(b)) {
                    return 0;
                }

                double thetaA = Math.atan2(a.getY() - lowest.getY(), a.getX() - lowest.getX());
                double thetaB = Math.atan2(b.getY() - lowest.getY(), b.getX() - lowest.getX());

                if (thetaA < thetaB) {
                    return -1;
                } else if (thetaA > thetaB) {
                    return 1;
                } else {
                    double distanceA = Math.sqrt(((lowest.getX() - a.getX()) * (lowest.getX() - a.getX()))
                            + ((lowest.getY() - a.getY()) * (lowest.getY() - a.getY())));
                    double distanceB = Math.sqrt(((lowest.getX() - b.getX()) * (lowest.getX() - b.getX()))
                            + ((lowest.getY() - b.getY()) * (lowest.getY() - b.getY())));
                    if (distanceA < distanceB) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        });
        set.addAll(points);
        return set;
    }

    public static int [][] getConvexHull(float[] xs, float[] ys) {

        int [][] matrix;
        if (xs.length < 3) {
            matrix = new int[xs.length][2];
            for (int i = 0; i < xs.length; i++) {
                matrix[i][0] = (int)xs[i];
                matrix[i][1] = (int)ys[i];
            }
            return matrix;
        }
        List<Point> points = new ArrayList<Point>();

        for (int i = 0; i < xs.length; i++) {
            points.add(new Point(xs[i], ys[i]));
        }
        List <Point> sortPoints;
        sortPoints = getConvexHull(points);
        matrix = new int[sortPoints.size()][2];
        for (int i = 0; i < sortPoints.size(); i++) {
            matrix[i][0] = (int)sortPoints.get(i).getX();
            matrix[i][1] = (int)sortPoints.get(i).getY();
        }
        
        return matrix;
    }

    public static List<Point> getConvexHull(List<Point> points) {
        List<Point> sorted = new ArrayList<Point>(getSortedPointSet(points));

        Stack<Point> stack = new Stack<Point>();
        stack.push(sorted.get(0));
        stack.push(sorted.get(1));

        for (int i = 2; i < sorted.size(); i++) {
            Point head = sorted.get(i);
            Point middle = stack.pop();
            Point tail = stack.peek();

            Turn turn = getTurn(tail, middle, head);

            switch (turn) {
                case COUNTER_CLOCKWISE:
                    stack.push(middle);
                    stack.push(head);
                    break;
                case CLOCKWISE:
                    i--;
                    break;
                case COLLINEAR:
                    stack.push(head);
                    break;
            }
        }
        stack.push(sorted.get(0));
        return new ArrayList<Point>(stack);
    }
}
