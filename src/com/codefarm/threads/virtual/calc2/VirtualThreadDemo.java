package com.codefarm.threads.virtual.calc2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualThreadDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor(); // create a thread pool with 100 threads
        long start = System.currentTimeMillis(); // start timing

        for (int i = 0; i < 100; i++) {
            int index = i; // final variable for use in lambda expression
            executor.submit(() -> {
//                System.out.println("Thread " + index + " is running.");
                int result = 0;
                for (int j = 0; j < 1000000000; j++) {
                    result += j; // perform CPU-intensive task
                }
//                System.out.println("Thread " + index + " finished with result " + result + ".");
            });
        }

        executor.shutdown(); // shutdown the executor when finished

        while (!executor.isTerminated()) {
            // wait for all tasks to complete
        }

        long end = System.currentTimeMillis(); // end timing

        System.out.println("Execution time: " + (end - start) + " ms."); // output execution time
    }
}
