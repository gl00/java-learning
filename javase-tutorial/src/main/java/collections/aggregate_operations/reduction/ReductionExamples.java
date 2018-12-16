package collections.aggregate_operations.reduction;

import collections.aggregate_operations.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;

public class ReductionExamples {
    public static void main(String[] args) {

        // Create sample data

        List<Person> roster = Person.createRoster();

        System.out.println("Contents of roster:");

        roster
            .stream()
            .forEach(p -> p.printPerson());

        System.out.println();

        // 1. Average age of male members, average operation

        double average = roster
            .stream()
            .filter(p -> p.getGender() == Person.Sex.MALE)
            .mapToInt(Person::getAge)
            .average()
            .getAsDouble();

        System.out.println("Average age (bulk data operations): " + average);
        System.out.println();

        // 2. sum of ages with sum operation

        int totalAge = roster
            .stream()
            .mapToInt(Person::getAge)
            .sum();

        System.out.println("Sum of ages (sum operation): " + totalAge);
        System.out.println();

        // 3. Sum of ages with reduce(identity, accumulator)

        int totalAgeReduce = roster
            .stream()
            .mapToInt(Person::getAge)
            .reduce(0, Integer::sum);

        System.out.println("Sum of ages with reduce(identity, accumulator): " + totalAgeReduce);
        System.out.println();

        // 4. Average age of male members with collect operation

        Average averageCollect = roster
            .stream()
            .filter(p -> p.getGender() == Person.Sex.MALE)
            .map(Person::getAge)
            .collect(Average::new, Average::accept, Average::combine);

        System.out.println("Average age of male members: " + averageCollect.average());
        System.out.println();

        // 5. Names of male members with collect operation

        System.out.println("Names of male members with collect operation:");
        List<String> namesOfMaleMembersCollect = roster
            .stream()
            .filter(p -> p.getGender() == Person.Sex.MALE)
            .map(Person::getName)
            .collect(Collectors.toList());

        namesOfMaleMembersCollect
            .stream()
            .forEach(System.out::println);
        System.out.println();

        // 6. Group members by gender

        System.out.println("Members by gender:");
        Map<Person.Sex, List<Person>> byGender = roster
            .stream()
            .collect(Collectors.groupingBy(Person::getGender));

        List<Map.Entry<Person.Sex, List<Person>>> byGenderList =
            new ArrayList<>(byGender.entrySet());

        byGenderList
            .stream()
            .forEach(e -> {
                System.out.println("Gender: " + e.getKey());
                e.getValue()
                    .stream()
                    .map(Person::getName)
                    .forEach(System.out::println);
            });
        System.out.println();

        // 7. Group names by gender

        System.out.println("Names by gender:");
        Map<Person.Sex, List<String>> namesByGender = roster
            .stream()
            .collect(
                Collectors.groupingBy(
                    Person::getGender,
                    Collectors.mapping(
                        Person::getName,
                        Collectors.toList())));

        ArrayList<Map.Entry<Person.Sex, List<String>>> namesByGenderList =
            new ArrayList<>(namesByGender.entrySet());

        namesByGenderList
            .stream()
            .forEach(e -> {
                System.out.println("Gender: " + e.getKey());
                e.getValue()
                    .stream()
                    .forEach(System.out::println);
            });
        System.out.println();

        // 8. Total age by gender

        System.out.println("Total age by gender:");
        Map<Person.Sex, Integer> totalAgeByGender = roster
            .stream()
            .collect(
                Collectors.groupingBy(
                    Person::getGender,
                    Collectors.reducing(
                        0,
                        Person::getAge,
                        Integer::sum)
                ));

        ArrayList<Map.Entry<Person.Sex, Integer>> totalAgeByGenderList =
            new ArrayList<>(totalAgeByGender.entrySet());

        totalAgeByGenderList
            .stream()
            .forEachOrdered(e -> {
                System.out.println("Gender: " + e.getKey() + ", Total age: " + e.getValue());
            });
        System.out.println();

        // 9. Average age by gender

        System.out.println("Average age by gender:");
        Map<Person.Sex, Double> averageAgeByGender = roster
            .stream()
            .collect(
                Collectors.groupingBy(
                    Person::getGender,
                    Collectors.averagingInt(Person::getAge)
                ));

        for (Map.Entry<Person.Sex, Double> e : averageAgeByGender.entrySet()) {
            System.out.println("Gender: " + e.getKey() + ", Average age: " + e.getValue());
        }
    }

    static class Average implements IntConsumer {
        private int total = 0;
        private int count = 0;

        public double average() {
            return count > 0 ? ((double) total) / count : 0;
        }

        @Override
        public void accept(int value) {
            total += value;
            count ++;
        }

        public void combine(Average other) {
            total += other.total;
            count += other.count;
        }
    }
}
