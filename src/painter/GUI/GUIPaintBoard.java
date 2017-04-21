package painter.GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import painter.tools.Controler;
import painter.tools.Selection;
import painter.shapes.Circle;
import painter.shapes.Ellipse;
import painter.shapes.GraphicObject;
import painter.shapes.Point;
import painter.shapes.Rectangle;
import painter.shapes.Square;
import painter.shapes.VisiblePoint;
import painter.tools.ObjectsType;

public class GUIPaintBoard extends JPanel {

    private static final int CTRL = 17;
    private static final int ESC = 27;
    private static final int DEL = 127;
    private static final int LEFT = 37;
    private static final int UP = 38;
    private static final int RIGHT = 39;
    private static final int DOWN = 40;
    private static final int X_AXIS = 0;
    private static final int Y_AXIS = 1;
    private static final int XY_AXIS = 2;

    private static final String ESCALAR = "ESCALAR";
    private static final String ROTACIONAR = "ROTACIONAR";
    private static final String ESPELHAR = "ESPELHAR";
    private static final String CISALHAR = "CISALHAR";
    private static final String DELETAR = "DELETAR";

    private static GUIPaintBoard guiPaintBoard = null;
    private final Controler controler;

    private JPopupMenu popupMenu;
    private JMenu menuScale, menuRotate, menuShear, menuReflect, menuTranslate;
    private JMenu menuScalePositive, menuScaleNegative, menuRotatePositive, menuRotateNegative, menuShearPositive, menuShearNegative;
    private JMenuItem menuDelete;
    private JMenuItem menuItemScaleAt, menuItemRotateAt, menuItemShearAt;
    private JMenuItem[] menuItemsScalePositive, menuItemsScaleNegative;
    private JMenuItem[] menuItemsRotatePositive, menuItemsRotateNegative;
    private JMenuItem[] menuItemsShearPositive, menuItemsShearNegative;
    private JMenuItem[] menuItemsReflectPositive;
    private JMenuItem menuItemTranslate, menuItemTranslateSetting;

    ActionEventHandler actionEventHandler;

    private GUIPaintBoard() {
        MouseEventHandler mouseEventHandler = new MouseEventHandler();
        KeyboardEventHandler keyboardEventHandler = new KeyboardEventHandler();
        actionEventHandler = new ActionEventHandler();
        controler = new Controler();

        setPopupMenu();

        addMouseListener(mouseEventHandler);
        addMouseMotionListener(mouseEventHandler);
        addKeyListener(keyboardEventHandler);
    }

    public static synchronized GUIPaintBoard getInstance() {
        if (guiPaintBoard == null) {
            guiPaintBoard = new GUIPaintBoard();
        }
        return guiPaintBoard;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        g.setColor(controler.getDefaultColor());
        int selectedObject = controler.getSelectedObjectIndex();
        List<GraphicObject> listObjects = controler.getObjects();
        for (GraphicObject object : listObjects) {
            boolean selected = controler.getSelectObjects().contains(object);
            if (selected) {
                g.setColor(Color.RED);
            }
            if (object.getLength() > 0) {

                if (object.getType() == ObjectsType.POLYGON) {
                    for (Point point : object.getNonConvexPoint()) {
                        VisiblePoint vp = new VisiblePoint(point);
                        g.drawPolygon(vp.getXAxisConvex(), vp.getYAxisConvex(), vp.getLength());
                    }
                }

                if (object.getType() == ObjectsType.LINE) {
                    g.drawPolyline(object.getXAxisNonConvex(), object.getYAxisNonConvex(), object.getMatrix().length);
                    continue;
                }
                if (controler.getDrawMethod() == Controler.FILL) {
                    g.fillPolygon(object.getXAxisConvex(), object.getYAxisConvex(), object.getLength());
                } else {
                    g.drawPolygon(object.getXAxisConvex(), object.getYAxisConvex(), object.getLength());
                }
            }
            g.setColor(controler.getDefaultColor());
        }
    }

    public void reset() {
        controler.setOperation(ObjectsType.NONE);
        controler.setSelectedObjectIndex(Selection.NONE);
        controler.clearSelection();
        controler.reset();
        if (controler.isDrawing()) {
            controler.removeLastObject();
            controler.changeDrawingStatus();
        }
        repaint();
    }

    public Controler getListControler() {
        return controler;
    }

    private void setPopupMenu() {
        popupMenu = new JPopupMenu("Transformações 2D");

        menuScale = new JMenu("Escalar");
        menuScalePositive = new JMenu("Expandir");
        menuScaleNegative = new JMenu("Reduzir");
        menuItemScaleAt = new JMenuItem("Escalar...");
        menuItemScaleAt.addActionListener(actionEventHandler);
        menuScale.add(menuScalePositive);
        menuScale.add(menuScaleNegative);
        menuScale.add(menuItemScaleAt);
        menuItemsScalePositive = new JMenuItem[10];
        menuItemsScaleNegative = new JMenuItem[10];
        for (int i = 0; i < menuItemsScalePositive.length; i++) {
            menuItemsScalePositive[i] = new JMenuItem("Escalar: " + String.valueOf(i + 1) + " vezes");
            menuItemsScalePositive[i].addActionListener(actionEventHandler);
            menuItemsScaleNegative[i] = new JMenuItem("Escalar: -" + String.valueOf(i + 1) + " vezes");
            menuItemsScaleNegative[i].addActionListener(actionEventHandler);
            menuScalePositive.add(menuItemsScalePositive[i]);
            menuScaleNegative.add(menuItemsScaleNegative[i]);
        }

        menuRotate = new JMenu("Rotacionar");
        menuRotatePositive = new JMenu("Sentido horário");
        menuRotateNegative = new JMenu("Sentido anti-horário");
        menuItemRotateAt = new JMenuItem("Rotacionar...");
        menuItemRotateAt.addActionListener(actionEventHandler);
        menuRotate.add(menuRotatePositive);
        menuRotate.add(menuRotateNegative);
        menuRotate.add(menuItemRotateAt);
        menuItemsRotatePositive = new JMenuItem[18];
        menuItemsRotateNegative = new JMenuItem[18];
        for (int i = 0; i < menuItemsRotatePositive.length; i++) {
            menuItemsRotatePositive[i] = new JMenuItem("Rotacionar: " + String.valueOf((i + 1) * 10) + " graus");
            menuItemsRotatePositive[i].addActionListener(actionEventHandler);
            menuItemsRotateNegative[i] = new JMenuItem("Rotacionar: " + String.valueOf(((i + 1) * -1) * 10) + " graus");
            menuItemsRotateNegative[i].addActionListener(actionEventHandler);
            menuRotatePositive.add(menuItemsRotatePositive[i]);
            menuRotateNegative.add(menuItemsRotateNegative[i]);
        }

        menuShear = new JMenu("Cisalhar");
        menuShearPositive = new JMenu("Na esquerda");
        menuShearNegative = new JMenu("Na direita");
        menuItemShearAt = new JMenuItem("Cisalhar...");
        menuItemShearAt.addActionListener(actionEventHandler);
        menuShear.add(menuShearPositive);
        menuShear.add(menuShearNegative);
        menuShear.add(menuItemShearAt);
        menuItemsShearPositive = new JMenuItem[10];
        menuItemsShearNegative = new JMenuItem[10];
        for (int i = 0; i < menuItemsShearPositive.length; i++) {
            menuItemsShearPositive[i] = new JMenuItem("Cisalhar: " + String.valueOf(((i + 1) / 1)) + " porcento");
            menuItemsShearPositive[i].addActionListener(actionEventHandler);
            menuItemsShearNegative[i] = new JMenuItem("Cisalhar: -" + String.valueOf(((i + 1) / 1)) + " porcento");
            menuItemsShearNegative[i].addActionListener(actionEventHandler);
            menuShearPositive.add(menuItemsShearPositive[i]);
            menuShearNegative.add(menuItemsShearNegative[i]);
        }

        menuReflect = new JMenu("Espelhar");
        menuItemsReflectPositive = new JMenuItem[3];
        menuItemsReflectPositive[X_AXIS] = new JMenuItem("Espelhar: em X");
        menuItemsReflectPositive[Y_AXIS] = new JMenuItem("Espelhar: em Y");
        menuItemsReflectPositive[XY_AXIS] = new JMenuItem("Espelhar: em X e Y");
        menuItemsReflectPositive[X_AXIS].addActionListener(actionEventHandler);
        menuItemsReflectPositive[Y_AXIS].addActionListener(actionEventHandler);
        menuItemsReflectPositive[XY_AXIS].addActionListener(actionEventHandler);
        menuReflect.add(menuItemsReflectPositive[X_AXIS]);
        menuReflect.add(menuItemsReflectPositive[Y_AXIS]);
        menuReflect.add(menuItemsReflectPositive[XY_AXIS]);

        menuTranslate = new JMenu("Transladar");
        menuItemTranslate = new JMenuItem("Transladar para...");
        menuItemTranslateSetting = new JMenuItem("Pulo do translado");
        menuItemTranslate.addActionListener(actionEventHandler);
        menuItemTranslateSetting.addActionListener(actionEventHandler);
        menuTranslate.add(menuItemTranslate);
        menuTranslate.add(menuItemTranslateSetting);

        menuDelete = new JMenuItem("Deletar");
        menuDelete.addActionListener(actionEventHandler);

        popupMenu.add(menuScale);
        popupMenu.add(menuRotate);
        popupMenu.add(menuShear);
        popupMenu.add(menuReflect);
        popupMenu.add(menuTranslate);
        popupMenu.addSeparator();
        popupMenu.add(menuDelete);

    }

    private void selectObjects(Point point) {
        int tolerance = 2;
        controler.setSelectedObjectIndex(Selection.NONE);

        float x_1 = point.getX() - tolerance;
        float y_1 = point.getY() - tolerance;
        float x_2 = point.getX() + tolerance;
        float y_2 = point.getY() + tolerance;
        for (float x = x_1; x < x_2; x++) {
            for (float y = y_1; y < y_2; y++) {
                Point selectionPoint = new Point(x, y);
                int selected = Selection.getSelectedObject(controler.getObjects(), selectionPoint);
                if (selected != Selection.NONE) {
                    controler.addSelectedObject(controler.getObjects().get(selected));
                }
            }
        }
        repaint();
    }

    private void translateOverMouse(Point point) {
        ArrayList<GraphicObject> objects = controler.getSelectObjects();
        for (GraphicObject object : objects) {
            float x_distance = point.getX() - object.getCentroid().getX();
            float y_distance = point.getY() - object.getCentroid().getY();
            object.translate(x_distance, y_distance);
        }
        controler.changeTranslatingStatus();
    }

    private void translateObjects(float x, float y) {
        ArrayList<GraphicObject> objects = controler.getSelectObjects();
        for (GraphicObject object : objects) {
            object.translate(x, y);
        }

    }

    private void scaleObjects(float parameter) {
        if (parameter < 0) {
            parameter = 1 / (parameter * -1);
        }
        ArrayList<GraphicObject> objects = controler.getSelectObjects();
        for (GraphicObject object : objects) {
            object.scale(parameter);
        }
    }

    private void rotateObjects(float parameter) {
        if (parameter < 0) {
            parameter = 1 / (parameter * -1);
        }
        ArrayList<GraphicObject> objects = controler.getSelectObjects();
        for (GraphicObject object : objects) {
            object.rotate(parameter);
        }
    }

    private void reflectObjects(String parameter) {
        boolean reflect[] = new boolean[]{false, false};
        if (parameter.contains("X")) {
            reflect[X_AXIS] = true;
        }
        if (parameter.contains("Y")) {
            reflect[Y_AXIS] = true;
        }
        ArrayList<GraphicObject> objects = controler.getSelectObjects();
        for (GraphicObject object : objects) {
            object.reflect(reflect[X_AXIS], reflect[Y_AXIS]);
        }
    }

    private void shearObjects(float parameter) {
        ArrayList<GraphicObject> objects = controler.getSelectObjects();
        for (GraphicObject object : objects) {
            if (parameter > 0) {
                object.shear(parameter / 10, 0);
            } else {
                object.shear(parameter / 10, 0);
            }
        }
    }

    private void deleteObjects() {
        if (controler.getSelectObjects().size() > 0) {
            ArrayList<GraphicObject> objects = controler.getSelectObjects();
            for (Iterator<GraphicObject> iterator = objects.iterator(); iterator.hasNext();) {
                GraphicObject object = iterator.next();
                controler.removeObject(object);
                iterator.remove();
            }
        }
    }

    private class ActionEventHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) throws NumberFormatException {
            List<GraphicObject> selectedObjects = controler.getSelectObjects();
            GraphicObject object = selectedObjects.get(0);
            Scanner scanner = new Scanner(e.getActionCommand());
            String command = scanner.next();
            if (e.getSource() == menuItemTranslateSetting) {
                try {
                    String DefaultGap = JOptionPane.showInputDialog("Pulo para operação de translado");
                    controler.setDefaultTranslationJump(Float.parseFloat(DefaultGap));
                } catch (NumberFormatException numberFormatException) {
                    System.err.println("Número Inválido");
                }

            } else if (e.getSource() == menuItemScaleAt) {
                String reply = JOptionPane.showInputDialog(null, "Fator de escala", "Escalar", JOptionPane.QUESTION_MESSAGE);
                float scaleAt = Float.parseFloat(reply);
                scaleObjects(scaleAt);
            } else if (e.getSource() == menuItemRotateAt) {
                String reply = JOptionPane.showInputDialog(null, "Ângulo de rotação", "Rotacionar", JOptionPane.QUESTION_MESSAGE);
                float rotateAt = Float.parseFloat(reply);
                rotateObjects(rotateAt);
            } else if (e.getSource() == menuItemShearAt) {
                String reply = JOptionPane.showInputDialog(null, "Fator de cisalhamento", "Cisalhar", JOptionPane.QUESTION_MESSAGE);
                float cisalharAt = Float.parseFloat(reply);
                shearObjects(cisalharAt);
            } else if (e.getSource() == menuItemTranslate) {
                controler.changeTranslatingStatus();
            } else if (command.toUpperCase().contains(ESCALAR)) {
                float parameter = scanner.nextInt();
                scaleObjects(parameter);
            } else if (command.toUpperCase().contains(ROTACIONAR)) {
                float parameter = scanner.nextInt();
                rotateObjects(parameter);
            } else if (command.toUpperCase().contains(ESPELHAR)) {
                scanner.next();
                String parameter = scanner.nextLine();
                reflectObjects(parameter);
            } else if (command.toUpperCase().contains(CISALHAR)) {
                float parameter = scanner.nextInt();
                shearObjects(parameter);
            } else if (command.toUpperCase().contains(DELETAR)) {
                deleteObjects();
            }

            repaint();
        }

    }

    private class MouseEventHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            Point point = new Point(e.getX(), e.getY());
            if (!controler.isDrawing()) {
                if (controler.getLength() < 1) {
                    return;
                }
                if (controler.isTranslating()) {
                    translateOverMouse(point);
                }
                if (controler.isSelecting()) {
                    selectObjects(point);
                }
                if (!controler.isSelecting() && e.getButton() != MouseEvent.BUTTON3) {
                    controler.clearSelection();
                }
                repaint();
                if ((e.getButton() == MouseEvent.BUTTON3) && (controler.getSelectObjects().size() > 0)) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
                
            } else {
                controler.clearSelection();
                GraphicObject object = controler.getLast();
                switch (controler.getOperation()) {
                    case POLYGON:
                        object.addPoint(point);
                        object.addPoint(point);
                        controler.setShadow(point);
                        break;
                    case POINT:
                        controler.removeLastObject();
                        controler.addObject(new VisiblePoint(point));
                        controler.changeDrawingStatus();
                        break;
                    case LINE:
                        object.addPoint(point);
                        object.addPoint(point);
                        controler.setShadow(point);
                        break;
                    case TRIANGLE:
                        if (object.getLength() < 3) {
                            object.addPoint(point);
                            object.addPoint(point);
                            controler.setShadow(point);
                        } else {
                            object.remove(controler.getShadow());
                            object.addPoint(point);
                            controler.changeDrawingStatus();
                        }
                        break;
                    case QUADRANGLE:
                        if (object.getLength() == 0) {
                            controler.removeLastObject();
                            controler.addObject(new Rectangle(point, point));
                            controler.setShadow(point);
                        } else if (object.getLength() == 4) {
                            Point corner = new Point(object.getXAxisInFloat()[0], object.getYAxisInFloat()[0]);
                            controler.removeLastObject();
                            controler.addObject(new Rectangle(point, corner));
                            controler.changeDrawingStatus();
                        }
                        break;
                    case SQUARE:
                        if (object.getLength() == 0) {
                            controler.removeLastObject();
                            controler.addObject(new Square(point, 0));
                            controler.setShadow(point);
                        } else if (object.getLength() == 4) {
                            Point corner = new Point(object.getXAxisInFloat()[0], object.getYAxisInFloat()[0]);
                            controler.removeLastObject();
                            controler.addObject(new Square(corner, (corner.getX() - point.getX()) * -1));
                            controler.changeDrawingStatus();
                        }
                        break;
                    case ELLIPSE:
                        if (object.getLength() == 0) {
                            controler.removeLastObject();
                            controler.addObject(new Ellipse(point, point));
                        } else {
                            Ellipse ellipse = (Ellipse) controler.getLast();
                            Point corner = ellipse.getFirstCorner();
                            controler.removeLastObject();
                            controler.addObject(new Ellipse(corner, point));
                            controler.changeDrawingStatus();
                        }
                        break;
                    case CIRCLE:
                        if (object.getLength() == 0) {
                            controler.removeLastObject();
                            controler.addObject(new Circle(point, 5));
                        } else {
                            Circle circle = (Circle) controler.getLast();
                            Point corner = circle.getFirstCorner();
                            float weight = point.getX() - corner.getX();
                            controler.removeLastObject();
                            controler.addObject(new Circle(corner, weight));
                            controler.changeDrawingStatus();
                        }
                        break;
                }
                repaint();
            }
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            Point point = new Point(e.getX(), e.getY());
            ObjectsType menu = controler.getOperation();

            if (null != menu) {
                if (!controler.isDrawing() || menu == ObjectsType.POINT) {
                    return;
                }
                GraphicObject object = controler.getLast();
                if (object.getLength() < 1) {
                    return;
                }
                switch (menu) {
                    case POLYGON:
                    case LINE:
                    case TRIANGLE:
                        object.remove(controler.getShadow());
                        object.addPoint(point);
                        controler.setShadow(point);
                        break;
                    case QUADRANGLE: {
                        Point corner = new Point(object.getXAxisInFloat()[0], object.getYAxisInFloat()[0]);
                        controler.removeLastObject();
                        controler.addObject(new Rectangle(corner, point));
                        controler.setShadow(point);
                        break;
                    }
                    case SQUARE: {
                        Point corner = new Point(object.getXAxisInFloat()[0], object.getYAxisInFloat()[0]);
                        controler.removeLastObject();
                        controler.addObject(new Square(corner, (corner.getX() - point.getX()) * -1));
                        controler.setShadow(point);
                        break;
                    }
                    case ELLIPSE: {
                        Ellipse ellipse = (Ellipse) controler.getLast();
                        Point corner = ellipse.getFirstCorner();
                        controler.removeLastObject();
                        controler.addObject(new Ellipse(corner, point));
                        break;
                    }
                    case CIRCLE:
                        Circle circle = (Circle) controler.getLast();
                        Point corner = circle.getFirstCorner();
                        float weight = point.getX() - corner.getX();
                        controler.removeLastObject();
                        controler.addObject(new Circle(corner, weight));

                        break;
                    default:
                        break;
                }
            }
            repaint();
        }

    }

    private class KeyboardEventHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int keyCode = e.getKeyCode();

            if (controler.getSelectObjects().size() > 0) {
                GraphicObject object = controler.getSelectObjects().get(0);

                float defaultGap = controler.getDefaultTranslationJump();
                switch (keyCode) {
                    case DEL:
                        deleteObjects();
                        break;
                    case LEFT:
                        translateObjects(-defaultGap, 0);
                        break;
                    case UP:
                        translateObjects(0, -defaultGap);
                        break;
                    case RIGHT:
                        translateObjects(defaultGap, 0);
                        break;
                    case DOWN:
                        translateObjects(0, defaultGap);
                        break;
                }
            } else {
                switch (keyCode) {
                    case CTRL:
                        if (!controler.isDrawing() && (controler.getOperation() == ObjectsType.POLYGON)) {
                            controler.changeDrawingStatus();
                            controler.addObject();
                        } else if (controler.isDrawing() && (controler.getOperation() == ObjectsType.LINE && (controler.getLast().getMatrix().length > 1))) {
                            try {
                                controler.getLast().remove(controler.getShadow());
                                controler.changeDrawingStatus();
                            } catch (Exception ex) {
                                System.err.println("Sorry bro! Something got wrong");
                            }
                        } else if (!controler.isSelecting()) {
                            controler.changeSelectingStatus();
                        }

                        break;
                }

            }
            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case CTRL:
                    if (controler.isDrawing() && (controler.getOperation() == ObjectsType.POLYGON)) {
                        controler.setOperation(ObjectsType.NONE);
                        controler.changeDrawingStatus();
                        if (controler.getLast().getLength() == 0) {
                            controler.removeLastObject();
                        }
                        controler.getLast().remove(controler.getShadow());
                    } else if (controler.isSelecting()) {
                        controler.changeSelectingStatus();
                    }
                    break;
                case ESC:
                    if (controler.isDrawing()) {
                        controler.changeDrawingStatus();
                        controler.removeLastObject();
                    } else if (controler.getSelectObjects().size() > 0) {
                        controler.clearSelection();
                    }
                    controler.setSelectedObjectIndex(Selection.NONE);
                    break;
            }
            repaint();
        }

    }

}
