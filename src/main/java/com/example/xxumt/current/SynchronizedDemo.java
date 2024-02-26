package com.example.xxumt.current;

/**
 * <类说明>
 *
 * @author mengting.xu@ucarinc.com
 * @date 2022/11/29 16:58
 * @since 1.0
 */
public class SynchronizedDemo {
    Object object = new Object();

    public void method1() {
        synchronized (object) {

        }
        method2();
    }

    public void method2() {

    }
}
