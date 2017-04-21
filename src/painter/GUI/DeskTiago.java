package painter.GUI;

import painter.tools.GrahamScan;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import painter.shapes.GraphicObject;
import painter.shapes.Ellipse;
import painter.shapes.Point;
import painter.shapes.Rectangle;
import painter.shapes.Square;
import painter.GUI.GUIPaintBoard;

/**
 *
 * @author silasmsales
 */
public class DeskTiago extends JPanel {

    private int[][] matrixA;
    private int[][] matrixB;
    private int[] xs;
    private int[] ys;

    private List<GraphicObject> listObjects;

    public DeskTiago() {

        listObjects = new ArrayList<>();

        Point p1 = new Point(000, 000);
        Point p2 = new Point(200, 000);
        Point p3 = new Point(200, 200);
        Point p4 = new Point(000, 200);

        Ellipse ellipse = new Ellipse(new Point(-50, -50), new Point(50, 50));
        ellipse.getCentroid().showMatrix();
        
        listObjects.add(ellipse);

        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        for (GraphicObject object : listObjects) {
            g.drawPolygon(object.getXAxisConvex(), object.getYAxisConvex(), object.getLength());
        }

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Teste");
        frame.setSize(new Dimension(800, 800));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new DeskTiago());
    }
}
