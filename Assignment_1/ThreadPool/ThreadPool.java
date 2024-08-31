package ThreadPool;

// imports
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * ThreadPool implementation
 * Based on medium article by Vipul Pachauri
 * https://vipulpachauri12.medium.com/design-custom-thread-pool-java-617263ee57ec
 */
public class ThreadPool {

    /**
     * Task storage, blocking queue prevents additional tasks exceeding thread-task availability.
     */
    private BlockingQueue<Runnable> blockingQueue;

    /**
     * Instantiate thread pool.
     * @param nQueue - Size of task queue.
     * @param nThreads - Number of available threads.
     */
    public ThreadPool(int nQueue, int nThreads) {

        blockingQueue = new LinkedBlockingQueue<>(nQueue);
        Executor taskExecutor;

        for (int i = 0; i < nThreads; i++) {

            // run previous task in queue
            taskExecutor = new Executor(blockingQueue);

            // Establish thread for given task and start.
            Thread thread = new Thread(taskExecutor);
            thread.start();
        }
    }

    /**
     * Add task to blocking queue.
     * @param task - given task.
     */
    public void addTask(Runnable task) {

        blockingQueue.add(task);
    }
}
