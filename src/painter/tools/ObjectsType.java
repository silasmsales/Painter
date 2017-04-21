package painter.tools;

public enum ObjectsType{
    POLYGON(0), POINT(1), LINE(2), TRIANGLE(3), QUADRANGLE(4), SQUARE(5), ELLIPSE(6), CIRCLE(7), CONVEX(8), NONE(9);
    
    private final int operation;

    ObjectsType(int valor) {
        operation = valor;
    }
    
}
