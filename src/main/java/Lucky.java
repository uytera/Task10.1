import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Lucky {
    static volatile int x = 0;
    static int count = 0;
    static Lock lockV2 = new ReentrantLock();

    static class LuckyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                lockV2.lock();
                int y;
                if(x < 999999) {
                    x++;
                    y=x;
                }
                else {
                    lockV2.unlock();
                    break;
                }
                lockV2.unlock();

                if ((y % 10) + (y / 10) % 10 + (y / 100) % 10 == (y / 1000) % 10 + (y / 10000) % 10 + (y / 100000) % 10) {
                    System.out.println(y);
                    count++;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        long start = System.nanoTime();

        Thread t1 = new LuckyThread();
        Thread t2 = new LuckyThread();
        Thread t3 = new LuckyThread();
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();

        long end = System.nanoTime();
        long traceTime = end-start;

        System.out.println(traceTime);
        System.out.println("Total: " + count);
    }
}