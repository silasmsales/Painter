package painter.shapes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import painter.tools.GrahamScan;

/**
 *
 * @author silasmsales
 */
public class GraphicObject{

    private final static int ONE_NEGATIVE = -1;
    private final static int ZERO = 0;
    private final static int ONE = 1;
    private final static int TWO = 2;
    private final static int THREE = 3;

    private final static int X_AXIS = 0;
    private final static int Y_AXIS = 1;
    private final static int NULL_AXIS = 2;
    private final static int COLUMN_NUMBER = 3;
    private final static int NON_DETERMINANT = 1;

    private float[][] matrixPoints;
    private float[][] transformMatrix = new float[][]{{ZERO, ZERO, ZERO},
    {ZERO, ZERO, ZERO},
    {ZERO, ZERO, ZERO}};

    protected boolean isLine = false;
    
    public GraphicObject(float[] xAxis, float[] yAxis) {
        this.matrixPoints = new float[xAxis.length][COLUMN_NUMBER];

        for (int row = 0; row < matrixPoints.length; row++) {
            matrixPoints[row][X_AXIS] = xAxis[row];
            matrixPoints[row][Y_AXIS] = yAxis[row];
            matrixPoints[row][NULL_AXIS] = NON_DETERMINANT;
        }

    }

    public GraphicObject(Point[] points) {
        for (Point point : points) {
            addPoint(point);
        }
    }

    public GraphicObject() {
    }

    public void shear(float shearX, float shearY) {
        Point centroid;
        clearTransformMatrix();
        getTransformMatrix()[ZERO][ZERO] = ONE;
        getTransformMatrix()[ONE][ONE] = ONE;
        getTransformMatrix()[TWO][TWO] = ONE;
        getTransformMatrix()[ONE][ZERO] = shearX;
        getTransformMatrix()[ZERO][ONE] = shearY;
        centroid = this.getCentroid();
        this.moveCentroidToOrigin();
        matrixPoints = multiply(matrixPoints, getTransformMatrix());
        this.moveCentroidTo(centroid.getX(), centroid.getY());

    }

    public void shear(float shear) {
        shear(shear, shear);
    }

    public void reflect(boolean reflectAtX, boolean reflectAtY) {
        clearTransformMatrix();
        getTransformMatrix()[ZERO][ZERO] = ONE;
        getTransformMatrix()[ONE][ONE] = ONE;
        getTransformMatrix()[TWO][TWO] = ONE;

        if (reflectAtX) {
            getTransformMatrix()[ONE][ONE] = ONE_NEGATIVE;
        }
        if (reflectAtY) {
            getTransformMatrix()[ZERO][ZERO] = ONE_NEGATIVE;
        }

        matrixPoints = multiply(matrixPoints, getTransformMatrix());
    }

    public void reflect() {
        reflect(true, true);
    }

    public void rotate(double angle) {

        Point centroid;
        clearTransformMatrix();
        getTransformMatrix()[ZERO][ZERO] = (float) Math.cos(Math.toRadians(angle));
        getTransformMatrix()[ZERO][ONE] = (float) Math.sin(Math.toRadians(angle)) * -1;
        getTransformMatrix()[ONE][ZERO] = (float) Math.sin(Math.toRadians(angle));
        getTransformMatrix()[ONE][ONE] = (float) Math.cos(Math.toRadians(angle));
        getTransformMatrix()[TWO][TWO] = ONE;

        centroid = getCentroid();
        this.moveCentroidToOrigin();
        matrixPoints = multiply(matrixPoints, getTransformMatrix());
        this.moveCentroidTo(centroid.getX(), centroid.getY());
    }

    public void scale(float scaleX, float scaleY) {
        Point centroid;
        clearTransformMatrix();
        getTransformMatrix()[ZERO][ZERO] = scaleX;
        getTransformMatrix()[ONE][ONE] = scaleY;
        getTransformMatrix()[TWO][TWO] = ONE;

        centroid = getCentroid();
        this.moveCentroidToOrigin();
        matrixPoints = multiply(matrixPoints, getTransformMatrix());
        this.moveCentroidTo(centroid.getX(), centroid.getY());
    }

    public void scale(float scale) {
        scale(scale, scale);
    }

    public void translate(float translateX, float translateY) {
        clearTransformMatrix();
        getTransformMatrix()[ZERO][ZERO] = ONE;
        getTransformMatrix()[ONE][ONE] = ONE;
        getTransformMatrix()[TWO][TWO] = ONE;
        getTransformMatrix()[TWO][ZERO] = translateX;
        getTransformMatrix()[TWO][ONE] = translateY;

        matrixPoints = multiply(matrixPoints, getTransformMatrix());
    }

    public void translate(float translate) {
        translate(translate, translate);
    }

    public void moveCentroidTo(float x, float y) {
        Point centroid = getCentroid();
        float centerX, centerY;
        centerX = centroid.getX() - x;
        centerY = centroid.getY() - y;
        for (float[] point : matrixPoints) {
            point[X_AXIS] = point[X_AXIS] - centerX;
            point[Y_AXIS] = point[Y_AXIS] - centerY;
        }
    }

    public void moveCentroidToOrigin() {
        moveCentroidTo(ZERO, ZERO);
    }

    public void addPoint(Point point) {
        if (matrixPoints != null) {
            this.matrixPoints = Arrays.copyOf(this.matrixPoints, matrixPoints.length + 1);
        } else {
            this.matrixPoints = new float[1][3];
        }

        this.matrixPoints[this.matrixPoints.length - 1] = new float[COLUMN_NUMBER];
        this.matrixPoints[this.matrixPoints.length - 1][X_AXIS] = point.getX();
        this.matrixPoints[this.matrixPoints.length - 1][Y_AXIS] = point.getY();
        this.matrixPoints[this.matrixPoints.length - 1][NULL_AXIS] = NON_DETERMINANT;
    }

    public void addPoint(float x, float y) {
        this.addPoint(new Point(x, y));
    }

    public void remove(Point point) {
        if (this.matrixPoints.length > 1) {
            for (int i = 0; i < this.matrixPoints.length; i++) {
                if (matrixPoints[i][X_AXIS] == point.getX() && matrixPoints[i][Y_AXIS] == point.getY()) {
                    List<float[]> points = new ArrayList(Arrays.asList(matrixPoints));
                    points.remove(i);
                    matrixPoints = new float[points.size()][3];
                    for (int j = 0; j < this.matrixPoints.length; j++) {
                        matrixPoints[j][X_AXIS] =  points.get(j)[X_AXIS];
                        matrixPoints[j][Y_AXIS] =  points.get(j)[Y_AXIS];
                        matrixPoints[j][NULL_AXIS] =  points.get(j)[NULL_AXIS];
                    }
                    return;
                }
            }
        }
    }

    public static float getDistanceOfTwoPoints(Point pointA, Point pointB) {
        double distance;
        distance = ((Math.pow(pointA.getX() - pointB.getX(), 2)) + (Math.pow(pointA.getY() - pointB.getY(), 2)));
        distance = (Math.sqrt(distance));
        return (float) distance;
    }

    public Point getCentroid() {
        float x = 0, y = 0;
        int [] xAxis;
        int [] yAxis;
        xAxis = this.getXAxisConvex();
        yAxis = this.getYAxisConvex();
        int length = this.getLength();
        
        for (int i = 0; i < length; i++) {
            x += xAxis[i];
            y += yAxis[i];
        }
        return new Point(x / length, y / length);
    }

    protected void sortClockwise() {
        Point centroid = getCentroid();
    }

    public float getArea() {
        if (matrixPoints == null) {
            return 0;
        }
        if (matrixPoints.length <= 2) {
            return 0;
        }
        float area = 0;
        float[][] copy = Arrays.copyOf(matrixPoints, matrixPoints.length + 1);
        copy[copy.length - ONE] = copy[0];

        for (int i = 0; i < copy.length - 1; i++) {
            area += (copy[i][X_AXIS] * copy[i + 1][Y_AXIS] - copy[i + 1][X_AXIS] * copy[i][Y_AXIS]);
        }
        return area / TWO;
    }

    public int [] getXAxisNotConvex() {
        int[] xAxis = new int[matrixPoints.length];
        for (int i = 0; i < matrixPoints.length; i++) {
            xAxis[i] = (int)matrixPoints[i][0];
        }
        return xAxis;
    }
    public int [] getYAxisNotConvex() {
        int[] yAxis = new int[matrixPoints.length];
        for (int i = 0; i < matrixPoints.length; i++) {
            yAxis[i] = (int)matrixPoints[i][1];
        }
        return yAxis;
    }
    
    public int[] getXAxisConvex() {
        int[][] matrix = GrahamScan.getConvexHull(this.getXAxisInFloat(), this.getYAxisInFloat());
        int[] xAxis = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            xAxis[i] = matrix[i][0];
        }
        return xAxis;
    }

    public float[] getXAxisInFloat() {
        float[] xAxis = new float[this.matrixPoints.length];
        for (int row = 0; row < xAxis.length; row++) {
            xAxis[row] = matrixPoints[row][X_AXIS];
        }
        return xAxis;
    }

    public int[] getYAxisConvex() {
        int[][] matrix = GrahamScan.getConvexHull(this.getXAxisInFloat(), this.getYAxisInFloat());
        int[] yAxis = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            yAxis[i] = matrix[i][1];
        }
        return yAxis;
    }

    public float[] getYAxisInFloat() {
        float[] yAxis = new float[this.matrixPoints.length];
        for (int row = 0; row < yAxis.length; row++) {
            yAxis[row] = matrixPoints[row][Y_AXIS];
        }
        return yAxis;
    }

    public int getLength() {
        if (matrixPoints == null) {
            return 0;
        }
        int lenght = GrahamScan.getConvexHull(this.getXAxisInFloat(), this.getYAxisInFloat()).length;
        if(lenght > 2)
            lenght--; 
        return lenght;
    }

    public float[][] getMatrix() {
        return this.matrixPoints;
    }

    public void setMatrix(float matrix[][]) {
        this.matrixPoints = matrix;
    }

    public void showTranformMatrix() {
        for (float[] matrixPoint : getTransformMatrix()) {
            for (int column = 0; column < getTransformMatrix()[0].length; column++) {
                System.out.print(matrixPoint[column] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void showMatrix() {
        if (matrixPoints == null) {
            System.err.println("Matrix empty");
            return;
        }
        for (float[] matrixPoint : matrixPoints) {
            for (int column = 0; column < matrixPoints[0].length; column++) {
                System.out.print(matrixPoint[column] + " ");
            }
            System.out.println();
        }
    }

    private void clearTransformMatrix() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                getTransformMatrix()[i][j] = ZERO;
            }
        }
    }

    private float[][] multiply(float[][] matrixA, float[][] matrixB) {

        int matrixARowNumber = matrixA.length;
        int matrixAColumnNumber = matrixA[0].length;
        int matrixBRowNumber = matrixB.length;
        int matrixBColumnNumber = matrixB[0].length;
        if (matrixAColumnNumber != matrixBRowNumber) {
            System.err.println("Illegal matrix dimensions.");
        }
        float[][] product = new float[matrixARowNumber][matrixBColumnNumber];
        for (int i = 0; i < matrixARowNumber; i++) {
            for (int j = 0; j < matrixBColumnNumber; j++) {
                for (int k = 0; k < matrixAColumnNumber; k++) {
                    product[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        return product;
    }

    public float[][] getTransformMatrix() {
        return transformMatrix;
    }

    public void setTransformMatrix(float[][] transformMatrix) {
        this.transformMatrix = transformMatrix;
    }

    public void reflectAt(GraphicObject lineObject) {
        float[][] line = lineObject.getMatrix();
        float lineFunctionSlope_m, pointFunctionSlope_m;
        float pointFunctionConstant_b;
        float pointFunctionX;
        float pointFunctionY;
        float vectorPerp[];
        float vectorPerpMod;
        float distance[] = new float[2];

        float denominator;
        float divider;
        float distanceToThePoint;

        float lineXA = line[0][X_AXIS];
        float lineXB = line[1][X_AXIS];
        float lineYA = line[0][Y_AXIS];
        float lineYB = line[1][Y_AXIS];

        float pointReflectX;
        float pointreflectY;

        float firstTerm;
        float secondTerm;

        lineFunctionSlope_m = (lineXA - lineXB / lineYA - lineYB);
        pointFunctionSlope_m = -1 / lineFunctionSlope_m;
        pointFunctionConstant_b = matrixPoints[0][Y_AXIS] - (pointFunctionSlope_m * matrixPoints[0][X_AXIS]);
        vectorPerp = new float[]{lineYA - lineYB, lineXB - lineXA};

        distance[0] = matrixPoints[0][X_AXIS] - lineXB;
        distance[1] = matrixPoints[0][Y_AXIS] - lineYB;
        denominator = distance[0] * vectorPerp[0] + distance[1] * vectorPerp[1];

        vectorPerpMod = (float) Math.sqrt(Math.pow(vectorPerp[0], 2) + Math.pow(vectorPerp[1], 2));
        distanceToThePoint = denominator / vectorPerpMod;

        firstTerm = lineXB * -1;
        secondTerm = (pointFunctionConstant_b - lineYB) / pointFunctionSlope_m;

        System.out.println(firstTerm);
        System.out.println(secondTerm);

    }

    public boolean isLine() {
        return this.isLine;
    }

}
