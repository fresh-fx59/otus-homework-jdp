import ru.otus.lesson11_concurrency.pool.CustomPool;

import java.util.logging.Logger;

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
        private static final Logger logger = Logger.getLogger(Task.class.getName());

        public void run() {
            logger.info("Task running in thread: " + Thread.currentThread().getName());
        }
    }
}
