package painter.tools;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import painter.shapes.GraphicObject;
import painter.shapes.Point;

public class Controler {

    public static final int DRAW = 0;
    public static final int FILL = 1;

    ObjectsType selectedItem = ObjectsType.NONE;
    private int selectedObjectIndex = Selection.NONE;
    private final ArrayList<GraphicObject> listOfObjects;
    private final ArrayList <GraphicObject>  selectObjectsList;
    private float defaultTranslationJump;
    private Color defaultColor;
    private int drawMethod;
    private boolean drawingStatus;
    private boolean translationStatus;
    private boolean selectingStatus;
    private Point shadow;

    public Controler() {
        this.listOfObjects = new ArrayList<>();
        this.selectObjectsList = new ArrayList<>();
        defaultColor = Color.GRAY;
        defaultTranslationJump = 10;
        drawingStatus = false;
        translationStatus = false;
        selectingStatus = false;
        drawMethod = DRAW;
    }

    public Point getShadow() {
        return this.shadow;
    }

    public void setShadow(Point point) {
        this.shadow = point;
    }

    public String[] getObjectsNames() {
        String names[] = new String[this.getLength()];
        for (int i = 0; i < this.getLength(); i++) {
            names[i] = "Object #" + String.valueOf(i);
        }
        return names;
    }

    public List<GraphicObject> getObjects() {
        List<GraphicObject> objects = new ArrayList<>();
        for (GraphicObject objectFromList : listOfObjects) {
            if (objectFromList.getLength() > 0) {
                objects.add(objectFromList);
            }
        }
        return objects;
    }

    public void addObject() {
        GraphicObject object = new GraphicObject();
        listOfObjects.add(object);
    }

    public void addObject(GraphicObject object) {
        listOfObjects.add(object);
    }

    public void removeLastObject() {
        listOfObjects.remove(listOfObjects.size() - 1);
    }

    public GraphicObject getLast() {
        if (listOfObjects.isEmpty()) {
            return null;
        }
        return listOfObjects.get(listOfObjects.size() - 1);
    }

    public int getLength() {
        return listOfObjects.size();
    }

    public void reset() {
        listOfObjects.clear();
    }

    public void setOperation(ObjectsType opcoesMenu) {
        this.selectedItem = opcoesMenu;
    }

    public ObjectsType getOperation() {
        return selectedItem;
    }

    public void setSelectedObjectIndex(int index) {
        this.selectedObjectIndex = index;
    }

    public int getSelectedObjectIndex() {
        return this.selectedObjectIndex;
    }

    public GraphicObject getSelectedObject() {
        return listOfObjects.get(this.getSelectedObjectIndex());
    }

    public void removeObject(GraphicObject object) {
        if (listOfObjects.contains(object)) {
            listOfObjects.remove(object);
        }
    }

    public boolean isDrawing() {
        return drawingStatus;
    }

    public void changeDrawingStatus() {
        this.drawingStatus = !drawingStatus;
    }

    public boolean isSelecting(){
        return this.selectingStatus;
    }
    
    public void changeSelectingStatus(){
        this.selectingStatus = !this.selectingStatus;
    }
    
    public boolean isTranslating() {
        return translationStatus;
    }

    public void changeTranslatingStatus() {
        this.translationStatus = !translationStatus;
    }

    public void setDefaultTranslationJump(float defaultTranslationJump) {
        this.defaultTranslationJump = defaultTranslationJump;
    }

    public float getDefaultTranslationJump() {
        return this.defaultTranslationJump;
    }

    public void changeDrawMethod() {
        if (this.drawMethod == DRAW) {
            this.drawMethod = FILL;
        } else {
            this.drawMethod = DRAW;
        }
    }

    public void setDefaultColor(Color color) {
        this.defaultColor = color;
    }

    public Color getDefaultColor() {
        return this.defaultColor;
    }

    public int getDrawMethod() {
        return this.drawMethod;
    }

    public void addSelectedObject(GraphicObject object) {
        if ((selectObjectsList.contains(object))) {
            return;
        }
        this.selectObjectsList.add(object);
    }

    public ArrayList<GraphicObject> getSelectObjects(){
        return this.selectObjectsList;
    }
    
    public void clearSelection(){
        this.selectObjectsList.clear();
    }
}
