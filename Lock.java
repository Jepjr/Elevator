package eleTest;

/*************************/    
/* includes              */
/*************************/


/*************************/    
/* class start           */
/*************************/
public class Lock {

    /*************************/    
    /* initlialization       */
    /*************************/
    private final Mutex mutex;
    
    /*************************/    
    /* getters and setters   */
    /*************************/
    public Lock( Mutex m ) {
        /* placeholder code to acquire the mutex */
        mutex = m;
        mutex.acquire();
    }//Lock
    
    /*************************/    
    /* functions             */
    /*************************/
    public void release() {
        /* placeholder code to release the mutex */
        mutex.release();
    }//release
}//class Lock
