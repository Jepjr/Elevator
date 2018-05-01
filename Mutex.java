package eleTest;

/*************************/    
/* includes              */
/*************************/


/*************************/    
/* class start           */
/*************************/
public class Mutex {
    
    /*************************/    
    /* initlialization       */
    /*************************/
    private static final java.util.concurrent.locks.Lock mLock =
        new java.util.concurrent.locks.ReentrantLock();

    /*************************/    
    /* functions             */
    /*************************/
    public void acquire(){
        mLock.lock();
    }//acquire
    
    public void release(){
        mLock.unlock();
    }//release
}//class Mutex
