package ThreadPool;

import java.util.concurrent.BlockingQueue;

public class Executor implements Runnable {

    /**
     * Thread pool blocking queue.
     */
    private BlockingQueue<Runnable> blockingQueue;


    /**
     * Instantiate task executor.
     * @param blockingQueue - task queue.
     */
    public Executor(BlockingQueue<Runnable> blockingQueue) {

        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {

        while (true) {          /* Loop through available tasks. */

            try {

                // execute task from queue
                Runnable runnable = blockingQueue.take();
                runnable.run();
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }

}
