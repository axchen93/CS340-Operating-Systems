import java.util.*;
class Judge implements Runnable{
    public static int forestTime[];
    public static int mountainTime[];
    public static int riverTime[];
    public static int totalTime[];
    public static Vector<Runner> finished;
    private Vector<Runner> atRiver;
    public static  Vector<Runner> longsleep;

    Judge(int numRunner){
        forestTime = new int[numRunner];
        mountainTime = new int[numRunner];
        riverTime = new int[numRunner];
        totalTime = new int[numRunner];
        finished = new Vector<>();
        longsleep = new Vector<>();
        atRiver = new Vector<>();
    }

    public void run(){
        while(atRiver.size()<Main.numRunner){
            if(!longsleep.isEmpty()){
                Main.runners[longsleep.firstElement().runnerID - 1 ].interrupt();
                atRiver.addElement(longsleep.firstElement());
                longsleep.remove(longsleep.firstElement());
            }
        }
        
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