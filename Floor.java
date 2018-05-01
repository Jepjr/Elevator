//<editor-fold desc="IMPORTS">
package eleTest;
import java.util.ArrayList;
import java.util.Iterator;
//</editor-fold>

public class Floor {
    //<editor-fold desc="INITIALIZATION">  
    private String label;
    private ArrayList< Visitor > visitors;
    private int number;
    //</editor-fold>
    
    //<editor-fold desc="CONSTRUCTORS">     
    public Floor(String label, int number) {
        this.label = label;
        this.visitors = new ArrayList<>();
        this.number = number;
    }//Floor
    //</editor-fold>

    //<editor-fold desc="GETTERS AMD SETTERS">
    public String getLabel() {
        return this.label;
    }//getLabel
    
    public int getNumber() {
        return this.number;
    }//getNumber
    
    public ArrayList< Visitor > getVisitors(){
        return this.visitors;
    }//getVisitors
    
    public int getNonwaitingVisitors() {
        //gets total of all visitors who are content to be on this floor
        int total = 0;
        for (Visitor visitor: this.visitors) {
            if (visitor.getState() == VisitorStates.ON_FLOOR) {
                total++;
            }
        }
        return total;
    }//getNonwaitingVisitors
    
    public int getWaitingVisitors() {
        //gets total of all visitors waiting for elevator on this floor
        int total = 0;
        for (Visitor visitor: this.visitors) {
            if (visitor.getState() == VisitorStates.WAITING_FOR_ELEVATOR) {
                total++;
            }
        }
        return total;
    }//getWaitingVisitors
    //</editor-fold>

    //<editor-fold desc="FUNCTIONS"> 
    public void accept( Visitor visitor ) {
        this.visitors.add(visitor);
        visitor.setFloor(this.number);
        visitor.setState(VisitorStates.ON_FLOOR);
    }//accept
    
    public void remove (){
        for (Iterator<Visitor> iterator = visitors.iterator(); iterator.hasNext();) {
            Visitor visitor = iterator.next();
            if (visitor.getState() == VisitorStates.ON_ELEVATOR) {
                iterator.remove();
            }
//            else if (visitor.getFloor() != this.getNumber()) {
//                iterator.remove();
//            }
        }
    }//remove
    //</editor-fold>
}//class Floor
