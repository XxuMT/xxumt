package com.example.xxumt.lambda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/8/16 17:07
 * @since 1.0
 */
public class LambdaTest {

  public void test() {
    List<Integer> list = Arrays.asList(new Integer[]{1,3,5,7,9});
    list.forEach(n -> System.out.println(n));
    list.forEach(System.out::println);
    List<Integer> collectList = list.parallelStream().filter(n -> n > 2).collect(Collectors.toList());
  }

  @Test
  public void testParallelStream() {
    long t0 = System.nanoTime();

    int a[] = IntStream.range(0, 1_000_000).filter(p -> p % 2 == 0).toArray();

    long t1 = System.nanoTime();

    int b[] = IntStream.range(0, 1_000_000).parallel().filter(p -> p % 2 == 0).toArray();

    long t2 = System.nanoTime();

    System.out.printf("serial: %.2fs, parallel: %2fs%n", (t1 - t0) * 1e-9, (t2 - t1) * 1e-9);
  }

  @Test
  public void testComparator() {
    List<Person> personList = new ArrayList<>();
    personList.add(new Person(1, 19));
    personList.add(new Person(2, 16));
    personList.add(new Person(3, 25));
    personList.add(new Person(4, 26));

    Person max = personList.stream().max(Comparator.comparing(t -> t.id)).get();
    System.out.println(max.id);

    Person min = personList.stream().min(Comparator.comparing(Person::getAge)).get();
    System.out.println(min.id);
  }

  @Test
  public void testGroupBy() {
    List<String> listStr = Lists.newArrayList("a", "b", "c", "b", "a", "a");
    Map<String, List<String>> mapGroup = listStr.stream().collect(Collectors.groupingBy(item -> item));
    System.out.println(mapGroup);
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Person {
    private Integer id;
    private Integer age;
  }

  public static void main(String[] args) {
  }
}
