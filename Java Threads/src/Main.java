import java.util.*;

class Main{
    public static long time = System.currentTimeMillis();
    public static Vector<Runner> mountainQueue;
    public static int numRunner;
    public static Thread runners[];
    
    public static void main(String args[]){
        numRunner = 10;
        if(args.length!=0) numRunner = Integer.parseInt(args[0]);

        mountainQueue = new Vector<Runner>(numRunner);
        Thread judge = new Thread(new Judge(numRunner));
        
        runners = new Thread[numRunner];

        for(int i =0; i<numRunner; i++){
            runners[i] = new Thread(new Runner(i+1));
            runners[i].start();
        }
        judge.run();
    }

    public static long getTime(){
        return System.currentTimeMillis() - time;
    }
}