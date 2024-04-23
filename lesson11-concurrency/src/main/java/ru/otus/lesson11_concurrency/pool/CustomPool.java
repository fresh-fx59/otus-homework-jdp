package ru.otus.lesson11_concurrency.pool;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CustomPool {
    private final WorkerThread[] workers;
    private final List<Runnable> taskQueue;
    private boolean isShutdown;

    public CustomPool(int numThreads) {
        taskQueue = new LinkedList<>();
        workers = new WorkerThread[numThreads];
        isShutdown = false;

        for (int i = 0; i < numThreads; i++) {
            workers[i] = new WorkerThread();
            workers[i].start();
        }
    }

    public synchronized void execute(Runnable task) {
        if (isShutdown) {
            throw new IllegalStateException("Cannot accept new tasks. Pool is shutdown.");
        }
        synchronized (taskQueue) {
            taskQueue.add(task);
            taskQueue.notify();
        }
    }

    public synchronized void shutdown() {
        isShutdown = true;
        Arrays.stream(workers).forEach(WorkerThread::interrupt);
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            Runnable task;

            while (true) {
                synchronized (taskQueue) {
                    while (taskQueue.isEmpty() && !isShutdown) {
                        try {
                            taskQueue.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    if (isShutdown && taskQueue.isEmpty()) {
                        return;
                    }
                    task = taskQueue.remove(0);
                }

                try {
                    task.run();
                } catch (RuntimeException e) {
                    throw new RuntimeException("Task run failed " + e.getMessage());
                }
            }
        }
    }
}
