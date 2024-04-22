import pool.CustomPool;

public class Main {
    public static void main(String[] args) {
        CustomPool customThreadPool = new CustomPool(50);

        for (int i = 0; i < 10; i++) {
            Runnable task = new Task();
            customThreadPool.execute(task);
        }

        customThreadPool.shutdown();
    }

    static class Task implements Runnable {
        public void run() {
            System.out.println("Task running in thread: " + Thread.currentThread().getName());
        }
    }
}
