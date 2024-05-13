package com.codefarm.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */
public class SecureRandomNumberGeneratorDemo {

    public static void main(String[] args) throws NoSuchAlgorithmException {
//        SecureRandom sc = new SecureRandom();//SHA1PRNG

        SecureRandom sc = SecureRandom.getInstance("Windows-PRNG");

        System.out.println(sc.nextInt(100));

        System.out.println(sc.getAlgorithm());
        System.out.println(sc.getProvider());
        System.out.println(sc.nextDouble());
        System.out.println(sc.nextInt());

        IntStream stream = sc.ints(100); //bounded
//        sc.ints();//unlimited streams
        LongStream longStream = sc.longs(100);
        DoubleStream doubleStream = sc.doubles(100);

        System.out.println(sc.nextInt(100));

        byte[] nextBytes = new byte[100];
        sc.nextBytes(nextBytes);

        sc.setSeed(nextBytes);
        sc.nextInt();

    }
}
