package painter.GUI;

import javax.swing.JFrame;

public class GUI {

    public static void main(String[] args) {
        GUIControls gui = new GUIControls();
        gui.setTitle("Painter");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gui.setSize(1024, 720);
        gui.setVisible(true);
    }
}
