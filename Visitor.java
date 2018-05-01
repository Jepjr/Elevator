//<editor-fold desc="IMPORTS">
package eleTest;
import java.util.Random;
//</editor-fold>

public class Visitor {
    //<editor-fold desc="INITIALIZATION">
    Random rand = new Random();
    private int floor = 0;
    private VisitorStates state;
    private int destination = 0;
    //</editor-fold>
    
    //<editor-fold desc="CONSTRUCTORS"> 
    public Visitor() {
        this.floor = rand.nextInt(ElevatorBank.getInstance().getFloors().size()-1)+1;
        this.state = VisitorStates.ON_FLOOR;
        this.destination = rand.nextInt(ElevatorBank.getInstance().getFloors().size()-1)+1;
        visit(ElevatorBank.getInstance().getFloor(floor));
    }//Elevator
    //</editor-fold>
    
    //<editor-fold desc="GETTERS AMD SETTERS"> 
    public int getFloor() {
        return floor;
    }//getFloor
    
    public void setFloor(int floor) {
        this.floor = floor;
    }//setFloor
    
    public VisitorStates getState() {
        return state;
    }//getState
    
    public void setState(VisitorStates state){
        this.state = state;
    }//setState
    
    public int getDestination() {
        return destination;
    }//getDestination
    //</editor-fold>
    
    //<editor-fold desc="FUNCTIONS">
    public void visit( Floor floor ) {
        floor.accept(this);
    }//visit floor
    
     public void visit( Elevator elevator ) {
        elevator.accept(this);
    }//visit elevator
    //</editor-fold>
}//class Visitor

//<editor-fold desc="ENUMERATIONS">
enum VisitorStates {
    ON_FLOOR, WAITING_FOR_ELEVATOR, ON_ELEVATOR
}//enum states
//</editor-fold>
