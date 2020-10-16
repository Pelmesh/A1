package com.company;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("Input n:");
                int n = in.nextInt();
                BigDecimal answer = BigDecimal.ONE.divide(factorial(n), 20, BigDecimal.ROUND_HALF_UP);
                BigDecimal bigDecimal = BigDecimal.ZERO;
                for (int i = 1; i <= n; i++) {
                    bigDecimal = bigDecimal.add(factorial(i));
                }
                answer = bigDecimal.multiply(answer);
                System.out.println(answer.setScale(10, BigDecimal.ROUND_DOWN) + "\n");
            } catch (Throwable t) {
                System.out.println("Check input");
                in.nextLine();
            }
        }
    }

    public static BigDecimal factorial(int n) {
        BigDecimal ret = BigDecimal.ONE;
        for (int i = 1; i <= n; ++i) {
            ret = ret.multiply(BigDecimal.valueOf(i));
        }
        return ret;
    }
}
