import java.util.concurrent.Semaphore;


public class Car implements Runnable {
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    private  Semaphore semaphore;
    public Car(Race race, Semaphore semaphore, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.semaphore=semaphore;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
             Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            MainClass.start.add(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

          if (MainClass.start.size()==MainClass.CARS_COUNT){
            semaphore.release(CARS_COUNT);}
           else{
           try {
               semaphore.acquire();

           } catch (InterruptedException e) {
               e.printStackTrace();
           }}

            for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
            if (i==(race.getStages().size()-1)){
                if (MainClass.finish==false){
                    MainClass.finish=true;
                    System.out.println(this.name + " Win ");

                }
            }
        }
        MainClass.start.remove(0);
            if( MainClass.start.size()==0){
            semaphore.release(CARS_COUNT);}

   }
}
