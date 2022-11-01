import java.awt.event.ActionListener;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@FunctionalInterface
interface MyPredicate<T>{
    double divide(T t1, T t2);
}

public class Main {
    public static void main(String[] args) {
        // у функциональных интерфейсов Runnable, ActionListener есть всего один абстрактный метод,
        // и здесь мы его переопределяем (@override) через лямбда-выражение
        // В лямбда используются только не изменяемые в коде переменные (но модификатор final у них писать необязательно)
        Runnable runnable = () -> System.out.println("runs");
        ActionListener actionListener = event -> System.out.println(event.paramString());

        Predicate<Integer> predicate = x -> x>5; // Boolean
        Consumer<String> consumer = x -> System.out.println(x); // void
        Function<Integer, String> function = x -> "x"; // 1 параметр - входной, 2 - выходной
        Supplier<Integer> supplier = () -> 5;
        UnaryOperator<Integer> unaryOperator = x -> x*x;
        BinaryOperator<Integer> binaryOperator = (x,y) -> x*y;

        MyPredicate<Double> myPredicate = (x,y)-> x/y;
        double d = myPredicate.divide(10.0,3.0);
        System.out.println(d);

        List<String> list = List.of("C", "E", "G");
        list.forEach(n -> System.out.print(n));
        System.out.println();
        list.forEach(System.out::print); // оператор :: конвертирует метод в лямбда-выражение

        // Для анонимных классов ключевое слово ‘this’ обозначает объект анонимного класса,
        //  в то время как в lambda-выражении ‘this’ обозначает объект класса, в котором lambda-выражение используется.

            // STREAMS
        // Конвеерные (intermediate) методы выполняются только тогда, когда после них следует терминальный
        Stream<String> stream = list.stream();
        stream.forEach(x-> System.out.print(x));
        stream = list.stream();
        stream.forEach(x-> System.out.print(x));
        System.out.println();

        list.stream().filter(x -> x.equals("E")).filter((s) -> s.startsWith("E")) .forEach(x-> System.out.print(x)); // E
        System.out.println();

        List<String> list2 = Stream.of("one","two", "three").collect(Collectors.toList());
        list2.stream().map(String::toUpperCase).forEach(System.out::println);
        //list2.stream().map(x-> x.toUpperCase()).forEach(System.out::println);
        Stream.of(List.of("A", "B", "C"), List.of("D", "E", "F")).flatMap(Collection::stream).forEach(System.out::print);

        list2.stream().sorted().forEach(System.out::println);

        long n = list2.stream().count();
        System.out.println(n);
        Optional<String> str = list2.stream().reduce((s1, s2) -> s1 + "#" + s2);
        str.ifPresent(System.out::println);
        int i = Stream.of(1,2,3,4,5).reduce(0, (sum, s) -> sum + s);
        System.out.println(i);


        int min = Stream.of(1,2,3,4,5).min(Comparator.comparing(x -> x)).get();
        String max = Stream.of("abc", "cba", "bac").max(Comparator.comparing(String::valueOf)).get();
        System.out.println(min + " " + max);

        IntStream intStream = Stream.of(1,1,2,3,3).mapToInt(x->x);
        double average = intStream.summaryStatistics().getAverage();
        IntStream intStream2 = Stream.of(1,1,2,3,3).mapToInt(x->x);
        long sum = intStream2.summaryStatistics().getSum();
        System.out.println(average + " " + sum);
        Stream.of(1,1,2,3,3).distinct().forEach(System.out::print);
        Stream.of(1,1,2,3,3).limit(4).skip(1).forEach(System.out::print);
    }
}
