package collections.aggregate_operations;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamDemo {
    @Test
    void testObtainStream() {
        Stream<String> s1 = Stream.of("a", "b", "c");
        s1.forEach(e -> System.out.print(e + " "));
        System.out.println();

        String[] strArray = new String[]{"x", "y", "z"};
        Stream<String> s2 = Stream.of(strArray);
        s2.forEach(e -> System.out.print(e + " "));
        System.out.println();

        Stream<String> s3 = Arrays.stream(strArray);
        s3.forEach(e -> System.out.print(e + " "));
        System.out.println();

        List<String> list = Arrays.asList(strArray);
        Stream<String> s4 = list.stream();
        s4.forEach(e -> System.out.print(e + " "));
        System.out.println();

        IntStream.of(1, 2, 3).forEach(System.out::println);
        IntStream.range(1, 5).forEach(System.out::println);
        IntStream.rangeClosed(1, 5).forEach(System.out::println);
    }

    @Test
    void testFilter() {
        IntStream.rangeClosed(1, 10)
                .filter(e -> e % 2 == 0)
                .forEach(System.out::println);
    }

    @Test
    void testPeek() {
        Stream.of("one", "two", "three", "four", "five")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());
    }

    @Test
    void testMap() {
        int[] ints = Arrays.stream(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
                .map(e -> e * e)
                .toArray();
        for (int i : ints)
            System.out.print(i + " ");
    }

    @Test
    void testFlatMap() {
        Stream<? extends List<? extends Number>> listStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3, 4),
                Arrays.asList(5, 6, 7, 8, 9)
        );

        listStream.flatMap(childList -> childList.stream())
                .forEach(e -> System.out.print(e + " "));
    }

    @Test
    void testFindFirst() {
        Optional<String> first = Arrays.asList("a", "b", "c")
                .stream()
                .findFirst();
        if (first.isPresent()) {
            System.out.println(first.get());
        } else {
            System.out.println("No element.");
        }
    }

    /*
     * reduce 这个方法的主要作用是把 Stream 元素组合起来。
     * 它提供一个起始值（种子），然后依照运算规则（BinaryOperator），
     * 和前面 Stream 的第一个、第二个、第 n 个元素组合。从这个意义上说，字符串拼接、
     * 数值的 sum、min、max、average 都是特殊的 reduce。例如 Stream 的 sum 就相当于
     *     Integer sum = integers.reduce(0, (a, b) -> a+b); 或
     *     Integer sum = integers.reduce(0, Integer::sum);
     * 也有没有起始值的情况，这时会把 Stream 的前面两个元素组合起来，返回的是 Optional。
     */
    @Test
    void testReduce() {
        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("MALE", "FEMALE", "C", "D").reduce("", String::concat);
        System.out.println(concat);

        // 求最小值，minValue = -3.0
        Double minValue = Stream.of(-1.5, 1.0, 3.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        System.out.println(minValue);

        // 求和，sumValue = 10, 有起始值
        Integer sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        System.out.println(sumValue);

        // 求和，sumValue2 = 10, 无起始值
        Integer sumValue2 = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
        System.out.println(sumValue2);

        // 过滤，字符串连接，concat = "ace"
        String concat2 = Stream.of("a", "FEMALE", "c", "D", "e", "F")
                .filter(x -> x.compareTo("Z") > 0)
                .reduce("", String::concat);
        System.out.println(concat2);


        /*
        上面代码例如第一个示例的 reduce()，第一个参数（空白字符）即为起始值， 第二个参数（String::concat）为 BinaryOperator。
        这类有起始值的 reduce() 都返回具体的对象。而对于第四个示例没有起始值的 reduce()，
        由于可能没有足够的元素，返回的是 Optional，请留意这个区别。
         */
    }

    @Test
    void testLimitAndSkip() {
        List<Person> persons = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            persons.add(new Person(i, "name" + i));
        }

        /*
        这是一个有 10，000 个元素的 Stream，但在 short-circuiting 操作 limit 和 skip 的作用下，
        管道中 map 操作指定的 getName() 方法的执行次数为 limit 所限定的 10 次，
        而最终返回结果在跳过前 3 个元素后只有后面 7 个返回。
         */
        List<String> collectNames = persons.stream()
                .map(Person::getName)
                .limit(10)
                .skip(3)
                .collect(Collectors.toList());

        System.out.println(collectNames);
        System.out.println();

        /*
        有一种情况是 limit/skip 无法达到 short-circuiting 目的的，就是把它们放在 Stream 的排序操作后，
        原因跟 sorted 这个 intermediate 操作有关：此时系统并不知道 Stream 排序后的次序如何，
        所以 sorted 中的操作看上去就像完全没有被 limit 或者 skip 一样。

        最后有一点需要注意的是，对一个 parallel 的 Steam 管道来说，如果其元素是有序的，
        那么 limit 操作的成本会比较大，因为它的返回对象必须是前 n 个也有一样次序的元素。
        取而代之的策略是取消元素间的次序，或者不要用 parallel Stream。
         */
        List<Person> collectPersons = persons.stream()
                .sorted(Comparator.comparing(Person::getName))
                .limit(2)
                .collect(Collectors.toList());

        System.out.println(collectPersons.size());
    }

    /*
    对 Stream 的排序通过 sorted 进行，它比数组的排序更强之处在于你可以首先对 Stream 进行各类 map、filter、limit、skip
    甚至 distinct 来减少元素数量后，再排序，这能帮助程序明显缩短执行时间。我们对清单 14 进行优化
     */
    @Test
    void testSorted() {
        List<Person> persons = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            persons.add(new Person(i, "name" + i));
        }

        Collections.shuffle(persons);

        System.out.println(persons);

        List<Person> collectPersons = persons.stream()
                .limit(3)
                .sorted(Comparator.comparing(Person::getName))
                .collect(Collectors.toList());

        System.out.println(collectPersons);
    }

    /*
    min 和 max 的功能也可以通过对 Stream 元素先排序，再 findFirst 来实现，但前者的性能会更好，为 O(n)，
    而 sorted 的成本是 O(n log n)。同时它们作为特殊的 reduce 方法被独立出来也是因为求最大最小值是很常见的操作。
     */
    @Test
    void testMax() {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("test/input/dictionary.txt"))) {
            int longest = reader.lines()
                    .mapToInt(String::length)
                    .max()
                    .getAsInt();
            System.out.println(longest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDistinct() {
        try (BufferedReader br = new BufferedReader(new FileReader("test/input/months.txt"))) {
            List<String> words = br.lines()
                    .flatMap(line -> Stream.of(line.split(" ")))
                    .filter(word -> word.length() > 5)
                    .map(String::toLowerCase)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
            System.out.println(words);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testMatch() {
        List<Person> persons = new ArrayList<>();
        Random r = new Random();
        for (int i = 1; i <= 10; i++) {
            int age = r.nextInt(100);
            persons.add(new Person(i, "name" + i, age));
        }
        System.out.println(persons);

        boolean isAllAdult = persons.stream()
                .allMatch(p -> p.getAge() >= 18);

        System.out.println("All adult? " + isAllAdult);

        boolean isThereAnyChild = persons.stream()
                .anyMatch(p -> p.getAge() < 12);

        System.out.println("Any Child? " + isThereAnyChild);
    }

    /*
    通过实现 Supplier 接口，你可以自己来控制流的生成。这种情形通常用于随机数、常量的 Stream，
    或者需要前后元素间维持着某种状态信息的 Stream。把 Supplier 实例传递给 Stream.generate() 生成的 Stream，
    默认是串行（相对 parallel 而言）但无序的（相对 ordered 而言）。
    由于它是无限的，在管道中，必须利用 limit 之类的操作限制 Stream 大小。
     */
    @Test
    void testGenerate() {
        Random seed = new Random();
        Supplier random = seed::nextInt;
        Stream.generate(random)
                .limit(10)
                .forEach(System.out::println);

        System.out.println();

        //
        IntStream.generate(() -> (int) (System.nanoTime() % 100))
                .limit(10)
                .forEach(System.out::println);
    }

    /*
    Stream.generate() 还接受自己实现的 Supplier。例如在构造海量测试数据的时候，用某种自动的规则给每一个变量赋值；
    或者依据公式计算 Stream 的每个元素值。这些都是维持状态信息的情形。
     */
    @Test
    void testGenerate2() {
        Stream.generate(new PersonSupplier())
                .limit(10)
                .forEach(System.out::println);
    }

    /*
    iterate 跟 generate 操作很像，接受一个种子值，和一个 UnaryOperator（例如 f）。
    然后种子值成为 Stream 的第一个元素，f(seed) 为第二个，f(f(seed)) 第三个，以此类推。
    iterate生成无限、串行、有序流。
    generate生成无限、串行、无序流。
     */
    @Test
    void testIterate() {
        Stream.iterate(1, e -> e * 2)
                .limit(10)
                .forEach(System.out::println);
    }

    /*
    java.util.streams.Collectors 类的主要作用是辅助各种reduction操作，例如转变为Collection，把Stream元素进行分组等。
     */
    @Test
    void testCollectorsGroupingBy() {
        Map<Integer, List<Person>> personByAge = Stream.generate(new PersonSupplier())
                .limit(100)
                .collect(Collectors.groupingBy(Person::getAge));

        for (Iterator<Map.Entry<Integer, List<Person>>> it = personByAge.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Integer, List<Person>> personGroup = it.next();
            System.out.println("Age " + personGroup.getKey() + ": " + personGroup.getValue().size());
        }
    }

    /*
    在使用条件“年龄小于 18”进行分组后可以看到，不到 18 岁的未成年人是一组，成年人是另外一组。
    partitioningBy 其实是一种特殊的 groupingBy，它依照条件测试的是否两种结果来构造返回的数据结构，
    get(true) 和 get(false) 能即为全部的元素对象。
     */
    @Test
    void testCollectorsPartitioningBy() {
        Map<Boolean, List<Person>> minors = Stream.generate(new PersonSupplier())
                .limit(10)
                .peek(System.out::println)
                .collect(Collectors.partitioningBy(p -> p.getAge() < 18));

        System.out.println("Minors number: " + minors.get(Boolean.TRUE).size());
        System.out.println("Adults number: " + minors.get(Boolean.FALSE).size());
    }

    private static class Person {
        private int id;
        private String name;
        private int age;

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public Person(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            System.out.println(name);
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "{" + id + ",'" + name + "'," + age + "}";
        }
    }

    private static class PersonSupplier implements Supplier<Person> {
        private int index = 1;
        private Random random = new Random();

        @Override
        public Person get() {
            return new Person(index++, "name" + index, random.nextInt(100));
        }
    }

}
