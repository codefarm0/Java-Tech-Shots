package com.codefarm.threads.virtual.bigfile;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualThreadExampleBigFileProcessing {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor(); // use virtual threads

        long start = System.currentTimeMillis(); // start timing

        for (int i = 0; i < 10; i++) {
            executor.submit(new MyRunnable(i));
        }

        executor.shutdown(); // shutdown the executor when finished

        while (!executor.isTerminated()) {
            // wait for all tasks to complete
        }

        long end = System.currentTimeMillis(); // end timing

        System.out.println("Execution time: " + (end - start) + " ms."); // output execution time
    }

    private static class MyRunnable implements Runnable {
        private final int index;

        public MyRunnable(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new FileReader("hugefile.txt"));
                 BufferedWriter writer = new BufferedWriter(new FileWriter("output-" + index + ".txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
