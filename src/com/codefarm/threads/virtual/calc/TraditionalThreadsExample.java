package com.codefarm.threads.virtual.calc;

import java.util.ArrayList;
import java.util.List;

public class TraditionalThreadsExample {
    
    public static void main(String[] args) {
        int numTasks = 10000000;
        int numThreads = 4;
        
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            threads.add(new Thread(new CalculationTask(numTasks / numThreads)));
        }
        
        long startTime = System.nanoTime();
        for (Thread thread : threads) {
            thread.start();
        }
        
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        long endTime = System.nanoTime();
        System.out.println("Time taken: " + (endTime - startTime) / 1000000 + " ms");
    }
    
    static class CalculationTask implements Runnable {
        
        private int numCalculations;
        
        public CalculationTask(int numCalculations) {
            this.numCalculations = numCalculations;
        }
        
        @Override
        public void run() {
            for (int i = 0; i < numCalculations; i++) {
                double result = Math.sqrt(i * Math.PI);
            }
        }
    }
}
