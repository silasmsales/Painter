package painter.tools;

import javax.swing.JList;

public class ListSingleton extends JList{

    private static ListSingleton list;
    
    private ListSingleton() {
    }
    
    public static synchronized ListSingleton getInstance(){
        if (list == null) {
            list = new ListSingleton();
        }
        return list;
    }

    
}
