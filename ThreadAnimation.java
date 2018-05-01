package eleTest;

/*************************/    
/* includes              */
/*************************/


/*************************/    
/* class start           */
/*************************/
public class ThreadAnimation implements Runnable {
    
    /*************************/    
    /* initlialization       */
    /*************************/
    private static final long ANIMATION_TIMEOUT_MS = 500; // half second
    private final Controller controller;
    
    /*************************/    
    /* constructors          */
    /*************************/
    public ThreadAnimation( Controller controller ) {
        this.controller = controller;
    }//ThreadAnimation
    
    /*************************/    
    /* functions             */
    /*************************/
    @Override
    public void run(){
        for (;;) {
            try {
                Thread.sleep( ANIMATION_TIMEOUT_MS );
            } catch ( InterruptedException ex ) {
                System.out.println( ex.getMessage() );
                ex.printStackTrace();
                return;
            }
            controller.animate();
        }
    }//run
}//class ThreadAnimation
