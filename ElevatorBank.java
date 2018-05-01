//<editor-fold desc="IMPORTS">
package eleTest;
import java.util.ArrayList;
//</editor-fold>

public class ElevatorBank {
    //<editor-fold desc="INITIALIZATION">  
    private final ArrayList< Elevator > elevators;
    private final ArrayList< Floor > floors;
    private static final Mutex mMutex = new Mutex();    //Singleton
    private static ElevatorBank mInstance = null;       //Singleton
    //</editor-fold>
    
    //<editor-fold desc="CONSTRUCTORS">  
    public ElevatorBank() {
        elevators = new ArrayList<>();
        floors = new ArrayList<>();
    }//ElevatorBank
    //</editor-fold>
    
    //<editor-fold desc="GETTERS AMD SETTERS">  
    public static ElevatorBank getInstance() {
        Lock lock = new Lock(mMutex); // automatically locks
        if (mInstance == null) {
            mInstance = new ElevatorBank();
        }
        lock.release(); // don't forget to unlock
        return mInstance;
    }//getInstance
    
    public Floor getFloor(int floorSeqNumber) {
        return floors.get(floorSeqNumber);
    }//getFloor
    
    public Elevator getElevator(int elevatorSeqNumber) {
        return elevators.get(elevatorSeqNumber);
    }//getElevator
    
    public ArrayList<Elevator> getElevators() {
        return elevators;
    }//getElevators
    
    public ArrayList<Floor> getFloors() {
        return floors;
    }//getFloors
    //</editor-fold>
    
    //<editor-fold desc="FUNCTIONS"> 
    public void addElevator(Elevator elevator) {
        elevators.add(elevator);
    }//addElevator

    public void addFloor(Floor floor) {
        floors.add(floor);
    }//addFloor

    public void updateConfiguration(
        int numberOfFloors, int numberOfElevators, int capacity) {
        elevators.clear();
        floors.clear();
        
        // populate the floors
        for ( int idx = 0; idx < numberOfFloors; ++idx ) {
            Floor floor;
            switch (idx) {
                case 0:     //Parking Garage
                    floor = new Floor( "P", 0 );
                    break;
                case 1:     //Ground Floor
                    floor = new Floor( "G", 1 );
                    break;
                default:    //Floor Number
                    floor = new Floor( Integer.toString( idx ), idx );
                    break;
            }
            addFloor( floor );
        }
        
        // populate the elevators
        for ( int idx = 0; idx < numberOfElevators; ++idx ) {
            Elevator elevator;
            elevator = new Elevator(idx, idx, capacity);
            addElevator( elevator );
        }
    }//updateConfiguration
    
    public void callElevator(Visitor visitor) {
        int distance = -1;
        int elevatorCalled = 0;
        int leastDistance = floors.size() * 2;
        for (Elevator elevator : elevators) {
            distance = elevator.checkCallDistance(visitor);
            if (distance != -1 && distance < leastDistance
                && elevator.getVisitorsSize() < elevator.getCapacity()) {
                leastDistance = distance;
                elevatorCalled = elevator.getLabel();
            }
        }
        elevators.get(elevatorCalled).updateQueue(visitor.getFloor());
    }
    //</editor-fold>
}//class ElevatorBank
