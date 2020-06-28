package com.greenlearner.java8;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 *
 * 1. create functional interface
 * 2. add static and default methods in it
 * 3. add another abstract method to demo
 * 4. add @functionalInterface
 * 5. Example with Runnable
 * 6. Anonymous class implementation
 * 7. some example of FI -java.util.function> Consumer, Supplier, Function and Predicate
 */
public class FunctionalInterfaceDemo1 {
    public static void main(String[] args) {

        SimpleInterface si = new SimpleInterface() {
            @Override
            public void giveMeTask() {
                System.out.println("tesk running");
            }

//            @Override
//            public void giveMeTask1() {
//
//            }
        };
        si.giveMeTask();
        SimpleInterface si1 = () -> {
            System.out.println("simple and easy");
        };
        si1.giveMeTask();

        Runnable runnable = () -> {
            for(int i=0;i<10; i++){
                System.out.println(i);
            }
        };

        Thread t = new Thread(runnable);

        t.start();
    }
}
