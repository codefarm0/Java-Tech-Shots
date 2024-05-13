package com.codefarm.threads.virtual.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualThreadsExample {
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int numTasks = 10000000;
        int numThreads = 4;
        
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        
        long startTime = System.nanoTime();
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(new CalculationTask(numTasks / numThreads), executor);
            futures.add(future);
        }
        
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();
        long endTime = System.nanoTime();
        
        System.out.println("Time taken: " + (endTime - startTime) / 1000000 + " ms");
        
        executor.shutdown();
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
