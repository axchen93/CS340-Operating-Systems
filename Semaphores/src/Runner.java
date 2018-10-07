import java.util.*;

class Runner implements Runnable{
    public int runnerID;
    private Random rand;
    public static boolean interrupt;
    Runner(int x){
        runnerID = x;
        rand = new Random();
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
            Main.finshedMutex.acquire();
            Judge.numFinished++;
            if(Judge.numFinished!= Main.numRunner){
                Main.finshedMutex.release();
                Judge.finished.acquire();
            }else{
                Main.finshedMutex.release();
                Judge.printStat();
                for(int i = 0; i<Main.numRunner-1; i++)
                    Judge.finished.release();
            }
            goHome();
            Judge.numHome++;
            if(Judge.numHome==Main.numRunner)
                Main.goHome.release();

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
            Main.mountainQueue.acquire();
            toString("Has entered the Mountain!");
            Thread.sleep(rand.nextInt(4)*1000);
            toString("Has passed the Mountain!");
            Judge.mountainTime[runnerID-1] = (int)(System.currentTimeMillis() - time)/1000;
            Main.mountainQueue.release();
            Thread.sleep(rand.nextInt(4)*1000);
        }catch(Exception e){}
    }

    private void river(){
        try{
            Main.riverMutex.acquire();
            long time = System.currentTimeMillis();
            toString("Entered the River!");
            Judge.numAtRiver++;
            if((Judge.numAtRiver%Main.numLines!=0) && (Judge.numAtRiver!=Main.numRunner)){
                Main.riverMutex.release();
                toString("Is waiting at the River!");
                Judge.atRiver.acquire();
            }else{
                Main.riverMutex.release();
                for(int i = 0; i< Main.numLines-1; i++)
                Judge.atRiver.release();
            }   
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
            Thread.sleep(rand.nextInt(4)*2*1000);
        }catch(InterruptedException e){}

    }

    private void yield(){
        Thread.yield();
        Thread.yield();
    }


    private void toString(String m){
        System.out.println("[" + Main.getTime() + "] Runner " + runnerID + ":" + m);
    }
}