//<editor-fold desc="IMPORTS">
/*************************/    
/* includes              */
/*************************/
package eleTest;
import java.util.ArrayList;
//</editor-fold>

public class Controller {
    //<editor-fold desc="INITIALIZATION">    
    private WindowMainApp window = null;
    private boolean isSimulationRunning = false;
    private static final Mutex mMutex = new Mutex();    //Singleton
    private static Controller mInstance = null;         //Singleton
    
    private int numberOfFloors = 0;
    private int numberOfElevators = 0;
    private int numberOfGuests = 0;
    private int numberOfEmployees = 0;
    private int capacity = 0;
    //</editor-fold>
    
    //<editor-fold desc="GETTERS AMD SETTERS">    
    public static Controller getInstance() {
        Lock lock = new Lock(mMutex); // automatically locks
        if (mInstance == null) {
            mInstance = new Controller();
        }
        lock.release(); // don't forget to unlock
        return mInstance;
    }//getInstance

    public WindowMainApp getWindow() {
        return window;
    }//getWindow

    public void setWindow(WindowMainApp window) {
        this.window = window;
    }//setWindow

    public void runSimulation() {
        isSimulationRunning = true;
    }//runSimulation                                                

    public void stopSimulation() {
        isSimulationRunning = false;
    }//stopSimulation
    
    public int getNumberOfFloors() {
        return numberOfFloors;
    }//getNumberOfFloors
    
    public int getNumberOfElevators() {
        return numberOfElevators;
    }//getNumberOfElevators
    
    public int getNumberOfGuests() {
        return numberOfGuests;
    }//getgetNumberOfGuests
    
    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }//getnumberOfEmployees
    
    public int getElevatorCapacity() {
        return capacity;
    }//getElevatorCapacity
    
    public void setScenario( int numberOfFloors, int numberOfElevators, 
                int numberOfGuests, int numberOfEmployees, int capacity ) {
        // is called on "Save" button to save user data
        this.numberOfElevators = numberOfElevators;
        this.numberOfEmployees = numberOfEmployees;
        this.capacity = capacity;
        this.numberOfGuests = numberOfGuests;
        this.numberOfFloors = numberOfFloors;
        Elevator.maxFloor = numberOfFloors;
        
        // update the elevator bank with current user data
        ElevatorBank.getInstance().updateConfiguration(
            numberOfFloors, numberOfElevators, capacity
        );
        
        //create guests on the first floor
        for (int idx = 0; idx < numberOfGuests; idx++){
            new Visitor();
        }
        
        //create employees in the parking garage
        for (int idx = 0; idx < numberOfEmployees; idx++){
            new Visitor();
        }
    }//setScenario
    //</editor-fold>
    
    //<editor-fold desc="FUNCTIONS"> 
    public void animate(){
        if ( isSimulationRunning == false ) {
            return;
        }
        
        // runs each elevator through an update loop
        ArrayList<Elevator> elevators = ElevatorBank.getInstance().getElevators();
        for ( Elevator elevator : elevators) {
            elevator.update();
        }
        
        // update the table with the elevators new positions
        updateWindow(); 
    }//animate
    
    void updateWindow() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                window.update();    // this must be done on EDT thread
            }
        });
    }//updateWindow
    //</editor-fold>
}//class ControllerDemo
