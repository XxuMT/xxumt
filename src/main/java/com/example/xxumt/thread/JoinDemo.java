package com.example.xxumt.thread;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/12/1 16:29
 * @since 1.0
 */
public class JoinDemo {

  private class A extends Thread {
    @Override
    public void run() {
      System.out.println("A");
    }
  }

  private class B extends Thread {

    private A a;

    B(A a) {
      this.a = a;
    }

    @Override
    public void run() {
      try {
        a.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("B");
    }
  }

  public void test() {
    A a = new A();
    B b = new B(a);
    b.start();
    a.start();
  }

  public static void main(String[] args) {
    JoinDemo join = new JoinDemo();
    join.test();
  }
}
