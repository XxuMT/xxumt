package com.example.xxumt.finals;

/**
 * final引用对象成员域操作
 *
 * @author mengting.xu@ucarinc.com
 * @date 2023/1/4 11:37
 * @since 1.0
 */
public class FinalReferenceDemo {
    final int[] arrays;
    private FinalReferenceDemo demo;

    public FinalReferenceDemo() {
        arrays = new int[2];
        arrays[0] = 1;
    }

    public void writeOne() {
        demo = new FinalReferenceDemo();
    }

    public void writeTwo() {
        arrays[1] = 2;
    }

    public void read() {
        if (demo != null) {
            int temp = demo.arrays[1];
            System.out.println("temp:" + temp);
        }
    }


    public static void main(String[] args) {
        final FinalReferenceDemo demo = new FinalReferenceDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.writeOne();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.writeTwo();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.read();
            }
        }).start();
    }
}
