package com.codefarm.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */
public class PeopleDriverClass {
    private static final String SPORTS = "Sports";
    private static final String ACTOR = "Actor";
    private static final String SINGER = "Singer";
    public static void main(String[] args) {
        List<People> peoples = new ArrayList<>();
        peoples = addPlayers(peoples, SPORTS);
        peoples = addActors(peoples, ACTOR);
        peoples = addSingers(peoples, SINGER);

        int players = peoples.stream().
                filter(e -> e.getCategory().equals(SPORTS))
                .collect(Collectors.toList())
                .size();

        System.out.println("players ->" + players);

        int actors = peoples.stream().
                filter(e -> e.getCategory().equals(ACTOR))
                .collect(Collectors.toList())
                .size();

        System.out.println("actors ->" + actors);

        int singers = peoples.stream().
                filter(e -> e.getCategory().equals(SINGER))
                .collect(Collectors.toList())
                .size();

        System.out.println("singers ->" + singers);

        int ageGreaterThan40 = peoples.stream()
                .filter(e -> e.getAge() > 40)
                .collect(Collectors.toList())
                .size();
        System.out.println("ageGreaterThan40 -> " +ageGreaterThan40);
    }

    private static List<People> addPlayers(List<People> peoples, String category) {
        peoples.add(new People("Sachin", category, 43));
        peoples.add(new People("Rohit Sharma", category, 32));
        peoples.add(new People("Virat", category, 33));
        peoples.add(new People("Jahir", category, 34));
        peoples.add(new People("Jayavardhane", category, 45));

        return peoples;
    }
    private static List<People> addActors(List<People> peoples, String category) {
        peoples.add(new People("Akshay Kumar", category, 43));
        peoples.add(new People("Akshay Khanna", category, 32));
        peoples.add(new People("Jackey", category, 33));
        peoples.add(new People("Tiger", category, 54));
        peoples.add(new People("Ram Kapoor", category, 54));
        peoples.add(new People("Ranveer", category, 34));
        peoples.add(new People("Ranbeer", category, 45));

        return peoples;
    }
    private static List<People> addSingers(List<People> peoples, String category) {
        peoples.add(new People("Arijit", category, 43));
        peoples.add(new People("Lata Di", category, 32));
        peoples.add(new People("Alka", category, 33));
        peoples.add(new People("Neha Kakar", category, 54));
        peoples.add(new People("Tony", category, 34));
        peoples.add(new People("Sonu", category, 45));

        return peoples;
    }
}
