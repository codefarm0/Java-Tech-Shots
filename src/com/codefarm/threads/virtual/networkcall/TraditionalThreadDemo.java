package com.codefarm.threads.virtual.networkcall;

import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//13831ms, 13330ms - cached
//17800ms, 10135ms
public class TraditionalThreadDemo {
    
    public static void main(String[] args) throws Exception {
        
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    URL url = new URL("https://jsonplaceholder.typicode.com/posts/" + finalI % 100);
                    URLConnection connection = url.openConnection();
                    connection.getInputStream();
                    System.out.println("Task " + finalI + " completed by thread " + Thread.currentThread().getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.sleep(100);
        }
        
        long end = System.currentTimeMillis();
        System.out.println("Total time taken: " + (end - start) + "ms");
    }
}
