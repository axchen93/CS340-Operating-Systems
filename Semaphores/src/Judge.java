import java.util.*;
import java.util.concurrent.Semaphore;

import javax.print.attribute.standard.NumberOfDocuments;
class Judge implements Runnable{
    public static int forestTime[];
    public static int mountainTime[];
    public static int riverTime[];
    public static int totalTime[];
    public static Semaphore finished;
    public static Semaphore atRiver;
    public static int numAtRiver;
    public static int numFinished;
    public static int numHome;

    Judge(int numRunner){
        forestTime = new int[numRunner];
        mountainTime = new int[numRunner];
        riverTime = new int[numRunner];
        totalTime = new int[numRunner];
        finished = new Semaphore(0);
        atRiver = new Semaphore(0);
        numAtRiver = 0;
        numFinished = 0;
        numHome = 0;
    }

    public void run(){
        try{
            Main.goHome.acquire();
            toString("Judge Has Went home");
            Main.goHome.release();
        }catch(Exception e){}
    }

    public static void printStat(){
        for(int i=0; i<Main.numRunner;i++){
            toString("Racer " + (i+1) + " finshed the race in " + totalTime[i] + " seconds");
        }
        for(int i=0; i<Main.numRunner;i++){
            toString("Racer " + (i+1) + " finshed the forest in " + forestTime[i] + " seconds");
        }
        for(int i=0; i<Main.numRunner;i++){
            toString("Racer " + (i+1) + " finshed the mountain in " + mountainTime[i] + " seconds");
        }
        for(int i=0; i<Main.numRunner;i++){
            toString("Racer " + (i+1) + " finshed the river in " + riverTime[i] + " seconds");
        }
    }

    private static void toString(String m){
        System.out.println("[" + Main.getTime() + "] Judge:" + m);
    }

}