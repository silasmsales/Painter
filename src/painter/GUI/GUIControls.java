package painter.GUI;

import painter.tools.ListSingleton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import painter.tools.MenuOptions;
import painter.tools.Selection;
import painter.shapes.Circle;
import painter.shapes.Ellipse;
import painter.shapes.Line;
import painter.shapes.Rect;
import painter.shapes.Rectangle;
import painter.shapes.Square;
import painter.shapes.Triangle;

public final class GUIControls extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu menuFile, menuEdit, menuAbout;
    private JMenuItem menuItemClose;
    private JMenuItem menuItemClear, menuItemTranslationPreference;
    private JMenuItem menuItemCredits, menuItemAbout;

    private ListSingleton listSingleton;

    private JToolBar toolBar;
    private JButton buttonClear, buttonDrawPolygon, buttonDrawPoint, buttonDrawLine;
    private JButton buttonDrawTriangle, buttonDrawQuadrangle, buttonDrawSquare, buttonDrawEllipse, buttonDrawCircle;
    private JComboBox comboBoxDrawOptions;
    private ImageIcon iconDrawPolygon, iconClear, iconDrawPoint, iconDrawLine, iconDrawTriangle;
    private ImageIcon iconDrawQuadrangle, iconDrawSquare, iconDrawEllipse, iconDrawCircle;
    
    GUIPaintBoard paintBoard;

    public GUIControls() {
        setMenu();
        setDesk();
        setToolBar();
        requestFocusInWindow();
    }

    private void setMenu() {
        menuBar = new JMenuBar();

        menuFile = new JMenu("File");
        menuEdit = new JMenu("Edit");
        menuAbout = new JMenu("About");

        menuItemClose = new JMenuItem("Close");
        menuItemClear = new JMenuItem("Clear");
        menuItemTranslationPreference = new JMenuItem("Pulo do Translado");
        menuItemCredits = new JMenuItem("Credits");
        menuItemAbout = new JMenuItem("About");

        menuItemClear.addActionListener(this);
        menuItemTranslationPreference.addActionListener(this);
        menuItemClose.addActionListener(this);

        menuFile.add(menuItemClose);
        menuEdit.add(menuItemClear);
        menuEdit.add(menuItemTranslationPreference);
        menuAbout.add(menuItemCredits);
        menuAbout.add(menuItemAbout);
        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuAbout);

        setJMenuBar(menuBar);

    }

    private void setToolBar() {
        String location = "icons/";
        String[] iconsName = new String[]{"clear.png", "drawPolygon.png", "drawPoint.png", "drawLine.png",
            "drawTriangle.png", "drawRectangle.png", "drawSquare.png", "drawEllipse.png", "drawCircle.png", "Color.png"};

        toolBar = new JToolBar();

        iconClear = new ImageIcon(location + iconsName[0]);
        iconDrawPolygon = new ImageIcon(location + iconsName[1]);
        iconDrawPoint = new ImageIcon(location + iconsName[2]);
        iconDrawLine = new ImageIcon(location + iconsName[3]);
        iconDrawTriangle = new ImageIcon(location + iconsName[4]);
        iconDrawQuadrangle = new ImageIcon(location + iconsName[5]);
        iconDrawSquare = new ImageIcon(location + iconsName[6]);
        iconDrawEllipse = new ImageIcon(location + iconsName[7]);
        iconDrawCircle = new ImageIcon(location + iconsName[8]);
        
        buttonClear = new JButton(iconClear);
        buttonDrawPolygon = new JButton(iconDrawPolygon);
        buttonDrawPoint = new JButton(iconDrawPoint);
        buttonDrawLine = new JButton(iconDrawLine);
        buttonDrawTriangle = new JButton(iconDrawTriangle);
        buttonDrawQuadrangle = new JButton(iconDrawQuadrangle);
        buttonDrawSquare = new JButton(iconDrawSquare);
        buttonDrawEllipse = new JButton(iconDrawEllipse);
        buttonDrawCircle = new JButton(iconDrawCircle);
        
        buttonClear.setToolTipText("Limpar painel");
        buttonDrawPolygon.setToolTipText("Desenhar poligno");
        buttonDrawPoint.setToolTipText("Desenhar ponto");
        buttonDrawLine.setToolTipText("Desenhar linha");
        buttonDrawTriangle.setToolTipText("Desenhar triângulo");
        buttonDrawQuadrangle.setToolTipText("Desenhar retângulo");
        buttonDrawSquare.setToolTipText("Desenhar quadrado");
        buttonDrawEllipse.setToolTipText("Desenhar Elipse");
        buttonDrawCircle.setToolTipText("Desenhar Círculo");
        
        buttonClear.addActionListener(this);
        buttonDrawPolygon.addActionListener(this);
        buttonDrawPoint.addActionListener(this);
        buttonDrawLine.addActionListener(this);
        buttonDrawTriangle.addActionListener(this);
        buttonDrawQuadrangle.addActionListener(this);
        buttonDrawSquare.addActionListener(this);
        buttonDrawEllipse.addActionListener(this);
        buttonDrawCircle.addActionListener(this);

        String [] drawOptions = new String[]{"Desenhar bordas ", "Preencher objeto"};
        comboBoxDrawOptions = new JComboBox(drawOptions);
        comboBoxDrawOptions.addActionListener(this);
        
        toolBar.add(buttonClear);
        toolBar.addSeparator();
        toolBar.add(buttonDrawPolygon);
        toolBar.add(buttonDrawPoint);
        toolBar.add(buttonDrawLine);
        toolBar.add(buttonDrawTriangle);
        toolBar.add(buttonDrawQuadrangle);
        toolBar.add(buttonDrawSquare);
        toolBar.add(buttonDrawEllipse);
        toolBar.add(buttonDrawCircle);
        toolBar.addSeparator();
        toolBar.add(comboBoxDrawOptions);
        
        add(toolBar, BorderLayout.NORTH);
    }

    private void setDesk() {
        paintBoard = GUIPaintBoard.getInstance();
        paintBoard.setFocusable(true);
        paintBoard.requestFocusInWindow();

        listSingleton = ListSingleton.getInstance();

        listSingleton.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSingleton.setVisibleRowCount(25);
        listSingleton.setFixedCellWidth(200);
        listSingleton.setBackground(new Color(239, 242, 247));

        add(paintBoard);
        add(listSingleton, BorderLayout.EAST);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        paintBoard.requestFocus();
        paintBoard.getListControler().setSelectedObjectIndex(Selection.NONE);
        paintBoard.repaint();
        if (paintBoard.getListControler().isDrawing()) {
            paintBoard.getListControler().changeDrawingStatus();
            paintBoard.getListControler().removeLastObject();
        }
        if (e.getSource().equals(buttonClear) || e.getSource().equals(menuItemClear)) {
            paintBoard.reset();
        } else if (e.getSource().equals(menuItemTranslationPreference)) {
            try {
            String DefaultGap = JOptionPane.showInputDialog("Pulo para operação de translado");
            paintBoard.getListControler().setDefaultTranslationJump(Float.parseFloat(DefaultGap));
            } catch (NumberFormatException nullPointerException) {
                System.err.println("Número Inválido");
            }
        } else if (e.getSource().equals(menuItemClose)) {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        } else if (e.getSource().equals(buttonDrawPolygon)) {
            paintBoard.getListControler().setOperation(MenuOptions.POLYGON);
        } else if (e.getSource().equals(buttonDrawPoint)) {
            paintBoard.getListControler().setOperation(MenuOptions.POINT);
            paintBoard.getListControler().addObject(new Circle());
            paintBoard.getListControler().changeDrawingStatus();
        } else if (e.getSource().equals(buttonDrawLine)) {
            paintBoard.getListControler().setOperation(MenuOptions.LINE);
            paintBoard.getListControler().addObject(new Line());
            paintBoard.getListControler().changeDrawingStatus();
        } else if (e.getSource().equals(buttonDrawTriangle)) {
            paintBoard.getListControler().setOperation(MenuOptions.TRIANGLE);
            paintBoard.getListControler().addObject(new Triangle());
            paintBoard.getListControler().changeDrawingStatus();
        } else if (e.getSource().equals(buttonDrawQuadrangle)) {
            paintBoard.getListControler().setOperation(MenuOptions.QUADRANGLE);
            paintBoard.getListControler().addObject(new Rectangle());
            paintBoard.getListControler().changeDrawingStatus();
        } else if (e.getSource().equals(buttonDrawSquare)) {
            paintBoard.getListControler().setOperation(MenuOptions.SQUARE);
            paintBoard.getListControler().addObject(new Square());
            paintBoard.getListControler().changeDrawingStatus();
        } else if (e.getSource().equals(buttonDrawEllipse)) {
            paintBoard.getListControler().setOperation(MenuOptions.ELLIPSE);
            paintBoard.getListControler().addObject(new Ellipse());
            paintBoard.getListControler().changeDrawingStatus();
        } else if (e.getSource().equals(buttonDrawCircle)) {
            paintBoard.getListControler().setOperation(MenuOptions.CIRCLE);
            paintBoard.getListControler().addObject(new Circle());
            paintBoard.getListControler().changeDrawingStatus();
        }else if(e.getSource() == comboBoxDrawOptions){
            paintBoard.getListControler().changeDrawMethod();
        }
    }

}
