import java.util.*;
import java.util.concurrent.Semaphore;

class Main{
    public static long time = System.currentTimeMillis();
    public static Semaphore mountainQueue;
    public static Semaphore riverMutex;
    public static Semaphore finshedMutex;
    public static Semaphore goHome;
    public static int numRunner;
    public static Thread runners[];
    public static int numLines;
    
    public static void main(String args[]){
        try{
            numRunner = 10;
            numLines = 3;
            if(args.length!=0) {
                numRunner = Integer.parseInt(args[0]);
                numLines = Integer.parseInt(args[1]);
            }

            mountainQueue = new Semaphore(1);
            riverMutex = new Semaphore(1);
            finshedMutex = new Semaphore(1);
            goHome = new Semaphore(1);
            goHome.acquire();
            Thread judge = new Thread(new Judge(numRunner));
            
            runners = new Thread[numRunner];

            for(int i =0; i<numRunner; i++){
                runners[i] = new Thread(new Runner(i+1));
                runners[i].start();
            }
            judge.start();
        }catch(Exception e){}
    }

    public static long getTime(){
        return System.currentTimeMillis() - time;
    }
}