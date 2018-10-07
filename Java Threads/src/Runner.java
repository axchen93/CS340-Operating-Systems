import java.util.*;

class Runner implements Runnable{
    public int runnerID;
    private Random rand;
    private boolean ready;
    public static boolean interrupt;
    Runner(int x){
        runnerID = x;
        rand = new Random();
        ready = false;
        interrupt = false;
    }
    
    public void run(){
        try{
            rest();
            int increment = Thread.currentThread().getPriority() + rand.nextInt(5);
            if(increment > 10) increment  = 10;
            Thread.currentThread().setPriority(increment);
            forest();
            Thread.currentThread().setPriority(5);
            rest();
            mountain();
            longRest();
            river();
            Judge.totalTime[runnerID-1] = (int)(System.currentTimeMillis()-Main.time)/1000;
            goHome();
            Judge.finished.addElement(this);
            if(Judge.finished.size() == Main.numRunner) Judge.printStat();
        }catch(Exception e){}
    }

    private void forest(){
        toString("Has entered the forest.");
        String magicWord = "abcd";
        String wordFound = "";
        char randChar;
        for(int i = 0; i<4; i++){
            randChar = (char)(rand.nextInt(5) + 'a');
            wordFound+=randChar;
        }
        
        if(wordFound!=magicWord){
            toString("Did not find the key");
            yield();
        }
        else toString("HAS FOUND THE MAGIC WORD " + wordFound + "!!!!!");
        Judge.forestTime[runnerID-1] = (int)(System.currentTimeMillis() - Main.time)/1000;
        toString("Has exited the forest.");
    }

    private void mountain(){
        try{
            long time = System.currentTimeMillis();
            toString("Has entered the Mountain!");
            Main.mountainQueue.addElement(this);
            if(Main.mountainQueue.size()==Main.numRunner){
                Main.mountainQueue.firstElement().setReady();
            }
            while(!Main.mountainQueue.isEmpty()){
                if(ready) break;
            }
            Main.mountainQueue.remove(Main.mountainQueue.firstElement());
            Judge.mountainTime[runnerID-1] = (int)(System.currentTimeMillis() - time)/1000;
            toString("Has passed the Mountain!");
            Thread.sleep(rand.nextInt(4)*1000);
            Main.mountainQueue.firstElement().setReady();
        }catch(Exception e){}
    }

    private void river(){
        try{
            long time = System.currentTimeMillis();
            Thread.sleep(rand.nextInt(4)*1000);
            Judge.riverTime[runnerID-1] = (int)(System.currentTimeMillis() - time)/1000;
            toString("Has passed the River!");
        }catch(Exception e){}
    }

    private void goHome(){
        try{
            if(runnerID == Main.numRunner){
                toString("Has went home.");
            }else {
                if(Main.runners[runnerID].isAlive()) Main.runners[runnerID].join();
                toString("Has went home.");
            }
        }catch(Exception e){}
        
    }

    private void rest(){
        try{
            int restTime = rand.nextInt(4);
            toString("Has started resting...");
            Thread.sleep(restTime*1000);
            toString("Is done resting.");
        }catch(Exception e){}
    }

    public void longRest(){
        try{
            toString("Taking a long nap...");
            Judge.longsleep.addElement(this);
            Thread.sleep(rand.nextInt(4)*2*1000);
        }catch(InterruptedException e){
            toString("Judge interrupts runners sleep");
            interrupt = true;
        }

    }

    private void yield(){
        Thread.yield();
        Thread.yield();
    }

    public void setReady(){
        ready = true;
    }

    private void toString(String m){
        System.out.println("[" + Main.getTime() + "] Runner " + runnerID + ":" + m);
    }
}