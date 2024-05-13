package com.codefarm.java8;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */

@FunctionalInterface
public interface SimpleInterface {

    void giveMeTask();
//    void giveMeTask1();
    //static
    //default
    //private
    static void task1(){
        System.out.println("static");
    }

    default void task2(){
        System.out.println("deafult");
        task3();
    }

    private void task3(){
        System.out.println("private");
    }

}
