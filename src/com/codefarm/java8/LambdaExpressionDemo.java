package com.codefarm.java8;

import java.util.Arrays;
import java.util.List;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */
public class LambdaExpressionDemo {

    public static void main(String[] args) {

        Demo1 demo1 = () -> System.out.println("hello there..");//no args
        Demo1 demo11 = () -> {
            System.out.println("hello there..");
            System.out.println("hello there..");
        };//no args

        demo1.display();
        demo11.display();


        Demo11 demo111 = n -> n + 23;
        Demo11 demo1111 = (n) -> {
            n = n + 23;
            n = n + 12;
            return n;
        };

        System.out.println(demo111.sum(32));
        System.out.println(demo1111.sum(32));

        Demo2 demo2 = (n1, n2) -> n1 * n2;

        System.out.println(demo2.mul(12,12));

        List<Integer> ints = Arrays.asList(1,2,6,3,4,5);
       ints.forEach(e -> System.out.println(e));

    }


}

interface Demo1 {
    void display();

}

interface Demo11 {
    int sum(int n);

}

interface Demo2 {
    int mul(int n, int n2);

}

