import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static boolean finish=false;
    public static volatile ArrayList<Integer> start;


    public static void main(String[] args) {
         Semaphore semaphore = new Semaphore(CARS_COUNT);
        start=new ArrayList<>();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, semaphore, 20 + (int) (Math.random() * 10));
        }

        for (int i = 0; i < cars.length; i++) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(cars[i]).start();
        }

       try {

            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

        try {
            Thread.sleep(1000);
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
             System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}

