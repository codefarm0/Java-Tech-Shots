package com.codefarm.general;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */
public class FunctionalProgramming {

    static int value;

    public static void main(String[] args) {
        //FAAFCC -- Lambda expression

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("running..");
            }
        };

        Runnable runnable1 = () -> System.out.println("running..");

        runnable1.run();

        sum(12,43);

        //higher order functions
        List<Integer> ints = Arrays.asList(1,2,3,4,5);

        Consumer<Integer> action = e -> System.out.println(e);

        ints.forEach(action);

        Predicate<Integer> predicate = (n) -> n>23;

        System.out.println(predicate.test(12));//false
        System.out.println(predicate.negate().test(12)); //true
    }

    //Pure functions
    static int sum(int n1, int n2){
//        value = value + n1;
        return n1+n2;
    }
}
