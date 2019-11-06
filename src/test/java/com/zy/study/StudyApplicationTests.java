package com.zy.study;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.EnumSet;

@SpringBootTest
class StudyApplicationTests {

    @Test
    void contextLoads() {
        int a = tableSizeFor(7);
        System.out.println(a);
    }

    static final int MAXIMUM_CAPACITY = 1 << 30;

    static final int tableSizeFor(int cap) {
        int n = cap - 1;

        System.out.println("1." + n);

        n |= n >>> 1;
        System.out.println("2." + n);

        n |= n >>> 2;
        System.out.println("3." + n);

        n |= n >>> 4;

        System.out.println("4." + n);
        n |= n >>> 8;
        System.out.println("5." + n);

        n |= n >>> 16;

        System.out.println("6." + n);



        int c = 6;
        System.out.println("1." + (c >>> 1));
        c |= c >>> 1;
        System.out.println("1." + c);



        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

}
