//<editor-fold desc="IMPORTS">
/*************************/    
/* includes              */
/*************************/
package eleTest;
import java.util.ArrayList;
import java.util.Iterator;
//</editor-fold>

public class Elevator {
    //<editor-fold desc="INITIALIZATION">  
    public static int maxFloor = 0;
    private static int minFloor = 0;
    private int capacity = 1;
    private int floor = 0;
    private int closest = -1; 
    private int furthest = 0; 
    private int label;
    private ArrayList< Visitor > visitors;
    private ElevatorStates previousState;
    private ElevatorStates state;
    ArrayList<Integer> elevatorStops = new ArrayList<Integer>();
    //</editor-fold>
    
    //<editor-fold desc="CONSTRUCTORS">  
    public Elevator( int label, int floor, int capacity ) {
        this.label = label;
        this.floor = floor;
        this.capacity = capacity;
        this.visitors = new ArrayList<>();
        this.state = ElevatorStates.MOVING_UP;
        this.furthest = maxFloor;
    }//Elevator
    //</editor-fold>
    
    //<editor-fold desc="GETTERS AMD SETTERS">       
    public int getLabel() {
        return this.label;
    }//getLabel
    
    public int getFloor() {
        return this.floor;
    }//getFloor
    
    public int getCapacity() {
        return this.capacity;
    }//getCapacity
    
    public int getVisitorsSize() {
        return this.visitors.size();
    }//getVisitors
    
    public ElevatorStates getState() {
        return this.state;
    }//getState
    //</editor-fold>
    
    //<editor-fold desc="FUNCTIONS">   
    
    public void update() {
        makeSureEveryoneCanGetOff();
        checkElevatorStops();       //Handles pathfinding
        move();                     //Moves the Elevator in that direction          
    }//update
    
    public int checkCallDistance(Visitor visitor) {
        int distance = -1;
        if (this.visitors.size() < this.capacity){
            switch (this.state){
                case WAITING:
                    if (visitor.getFloor() >= this.floor) {
                        distance = visitor.getFloor() - this.floor;
                    }
                    else {
                        distance = this.floor - visitor.getFloor();
                    }
                    break;
                case MOVING_UP:
                    if (visitor.getFloor() >= this.floor) {
                        distance = visitor.getFloor() - this.floor;
                    }
                    else {
                        distance = (this.furthest - this.floor) 
                                 + (this.furthest - visitor.getFloor());
                    }
                    break;
                case MOVING_DOWN:
                    if (visitor.getFloor() <= this.floor) {
                        distance =  this.floor - visitor.getFloor();
                    }
                    else {
                        distance = (this.floor- this.furthest) 
                                 + (visitor.getFloor() - this.furthest);
                    }
                    break;
                case OPEN_DOORS:
                    break;
            } 
        }
        return distance;
    }//checkCallDistance
    
    public void makeSureEveryoneCanGetOff(){
        for ( Visitor visitor : visitors) {
            updateQueue(visitor.getDestination());
        }
    }
    
    public void updateQueue(int floor){
        if (!this.elevatorStops.contains(floor)) {
            this.elevatorStops.add(floor);
        }
    }//updateQueue
    
    public void checkElevatorStops() {
        int done = 0;
        if (this.elevatorStops.isEmpty()){
            this.state = ElevatorStates.WAITING;
        } else {
            switch (this.state) {
                case WAITING: {
                    for (int idx = 0; idx < this.elevatorStops.size(); idx++) {
                        if (Math.abs(this.elevatorStops.get(idx) - this.floor) < this.closest) {
                            this.closest = this.elevatorStops.get(idx);
                        }
                        if ((this.elevatorStops.get(idx) - this.floor) > this.furthest) {
                            this.furthest = this.elevatorStops.get(idx);
                        }
                    }
                    if (this.closest > this.floor) {
                        this.state = ElevatorStates.MOVING_UP;
                    } else if (this.closest < this.floor) {
                        this.state = ElevatorStates.MOVING_DOWN;
                    } else if (this.closest == this.floor) {
                        this.state = ElevatorStates.OPEN_DOORS;
                    }
                }
                break;
                case MOVING_UP:
                    //IS ANYONE ON THIS FLOOR
                    this.previousState = this.state;
                    if (done == 0) {
                        for (int idx = 0; idx < this.elevatorStops.size(); idx++) {
                            if (elevatorStops.get(idx) == this.floor) {
                                this.state = ElevatorStates.OPEN_DOORS;
                                done = 1;
                            }
                        }
                    }
                    //IS ANYONE ABOVE
                    if (done == 0) {
                        for (int idx = 0; idx < this.elevatorStops.size(); idx++) {
                            if (elevatorStops.get(idx) > this.floor) {
                                this.state = ElevatorStates.MOVING_UP;
                                done = 1;
                            }
                        }
                    }
                    //IS ANYONE BELOW
                    if (done == 0) {
                        for (int idx = 0; idx < this.elevatorStops.size(); idx++) {
                            if (elevatorStops.get(idx) < this.floor) {
                                this.state = ElevatorStates.MOVING_DOWN;
                                done = 1;
                            }
                        }
                    }
                    break;
                case MOVING_DOWN:
                    //IS ANYONE ON THIS FLOOR
                    this.previousState = this.state;
                    if (done == 0) {
                        for (int idx = 0; idx < this.elevatorStops.size(); idx++) {
                            if (elevatorStops.get(idx) == this.floor) {
                                this.state = ElevatorStates.OPEN_DOORS;
                                done = 1;
                            }
                        }
                    }
                    //IS ANYONE BELOW
                    if (done == 0) {
                        for (int idx = 0; idx < this.elevatorStops.size(); idx++) {
                            if (elevatorStops.get(idx) < this.floor) {
                                this.state = ElevatorStates.MOVING_DOWN;
                                done = 1;
                            }
                        }
                    }
                    //IS ANYONE ABOVE
                    if (done == 0) {
                        for (int idx = 0; idx < this.elevatorStops.size(); idx++) {
                            if (elevatorStops.get(idx) > this.floor) {
                                this.state = ElevatorStates.MOVING_UP;
                                done = 1;
                            }
                        }
                    }
                    break;
                case OPEN_DOORS:
                    this.state = this.previousState;
                    done = 1;
            }
        }
    }//checkElevatorStops
 
    public void move() {
        switch (this.state) {
            case WAITING:
                break;
            case MOVING_UP:
                int newFloor = this.floor + 1;
                if (newFloor >= maxFloor - 1) {
                    this.floor = maxFloor - 1;
                    this.state = ElevatorStates.MOVING_DOWN;
                } else {
                    this.floor = newFloor;
                }
                break;
            case MOVING_DOWN:
                newFloor = this.floor - 1;
                if (newFloor <= minFloor) {
                    this.floor = minFloor;
                    this.state = ElevatorStates.MOVING_UP;
                } else {
                    this.floor = newFloor;
                }
                break;
            case OPEN_DOORS:
                
                remove();
                break;
        }
        for (Visitor visitor : visitors) {
            visitor.setFloor(this.floor);
        }
        for (Iterator<Integer> iterator = this.elevatorStops.iterator(); iterator.hasNext();) {
            Integer stop = iterator.next();
            if (stop == this.floor) {
                iterator.remove();
            }
        }
    }//move
    
    public void accept(Visitor visitor) {
        this.visitors.add(visitor);
        visitor.setState(VisitorStates.ON_ELEVATOR);
    }//accept
        
    void remove() {
        for (Iterator<Visitor> iterator = this.visitors.iterator(); iterator.hasNext();) {
            Visitor visitor = iterator.next();
            if (visitor.getDestination() == this.floor) {
                ElevatorBank.getInstance().getFloor(this.floor).accept(visitor);
                iterator.remove();
            }
        }
    }//remove
    //</editor-fold>
}    
//<editor-fold desc="ENUMERATIONS">
enum ElevatorStates{
    WAITING, MOVING_UP, MOVING_DOWN, OPEN_DOORS
}//enum ElevatorStates
//</editor-fold>
