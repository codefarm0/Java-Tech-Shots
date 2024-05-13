package com.codefarm.java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 * <p>
 * Reference to a static method	>> ContainingClass::staticMethodName
 * Reference to an instance method of a particular object >>	containingObject::instanceMethodName
 * Reference to an instance method of an arbitrary object of a particular type	>> ContainingType::methodName
 * Reference to a constructor	>> ClassName::new
 */
public class MethodReferenceDemo {
    public static void main(String[] args) {

        ComparisonProvider provider = new ComparisonProvider();

        List<Person> roster = Person.createRoster();

        roster.forEach(person -> System.out.println(person));

//        class PersonAgeComparator implements Comparator<Person> {
//            public int compare(Person a, Person b) {
//                return a.getBirthday().compareTo(b.getBirthday());
//            }
//        }

        System.out.println("After sorting");

//        Collections.sort(roster, new PersonAgeComparator());

//        Collections.sort(roster, (Person p1, Person p2) -> Person.compareByAge(p1,p2));
//
//        Collections.sort(roster, (p1, p2) -> Person.compareByAge(p1,p2));

//        Collections.sort(roster, Person ::compareByAge);

        Collections.sort(roster, provider :: compareByName);

//        roster.forEach(person -> System.out.println(person));
        roster.forEach(System.out :: println);


//        InterfaceAgain interfaceAgain = (str) -> new  GreenLearner(str);
        InterfaceAgain interfaceAgain = GreenLearner::new;

        interfaceAgain.display("hello");

        //method reference in type
        String[] stringArray = { "Barbara", "James", "Mary", "John",
                "Patricia", "Robert", "Michael", "Linda" };
        Arrays.sort(stringArray, String::compareToIgnoreCase);

        Arrays.asList(stringArray).forEach(System.out ::println);
    }

    interface InterfaceAgain{
        GreenLearner display(String say);
    }
    static class GreenLearner{
        public GreenLearner(String say){
            System.out.print(say);
        }
    }
}
