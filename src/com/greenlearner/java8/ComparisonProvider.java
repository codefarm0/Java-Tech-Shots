package com.greenlearner.java8;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */
public class ComparisonProvider {

    public int compareByName(Person a, Person b) {
        return a.getName().compareTo(b.getName());
    }

    public int compareByAge(Person a, Person b) {
        return a.getBirthday().compareTo(b.getBirthday());
    }
}
