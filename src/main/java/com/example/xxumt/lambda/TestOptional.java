package com.example.xxumt.lambda;

import org.junit.Test;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/8/18 16:28
 * @since 1.0
 */
public class TestOptional {

  @Test
  public void testOrElse() {
    Optional<String> strOptional = Optional.of("String Optional");
    System.out.println(strOptional.orElse("no value optional"));
    Optional<String> strUpper = strOptional.map(value -> value.toUpperCase());
    System.out.println(strUpper.orElse("no value optional"));
    strUpper = strOptional.flatMap(value -> Optional.of(value.toLowerCase()));
    System.out.println(strUpper.orElse("no value optional"));

    // 判断对象是否为空
    Optional.of(new Outer())
        .map(Outer::getNested)
        .map(Nested::getInner)
        .map(Inner::getFoo)
        .ifPresent(System.out::println);

    Outer outer = new Outer();
    resolve(() -> outer.getNested().getInner().getFoo()).ifPresent(System.out::println);
  }

  public static <T> Optional<T> resolve(Supplier<T> reslover) {
    try {
      T result = reslover.get();
      return Optional.ofNullable(result);
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  class Outer {
    Nested nested;

    public Nested getNested() {
      return nested;
    }
  }

  class Nested {
    Inner inner;

    public Inner getInner() {
      return inner;
    }
  }

  class Inner {
    String foo;

    public String getFoo() {
      return foo;
    }
  }

  public interface A {
    default void aa() {
      System.out.println("A`s aa");
    }
  }

  public interface B {
    default void aa() {
      System.out.println("B`s aa");
    }
  }

  public interface C extends A, B {
    @Override
    default void aa() {
      System.out.println("C`s aa");
    }
  }

  public static class D implements C {

  }
}
