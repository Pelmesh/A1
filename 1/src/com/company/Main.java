package com.company;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class Main {

    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("Input \"1\" or \"2\"\n1 - int to IpV4\n2 - IpV4 to int");
                int num = in.nextInt();
                if (num == 1) {
                    System.out.println("int to IpV4");
                    toIpV4();
                }
                if (num == 2) {
                    System.out.println("IpV4 to int");
                    toInt();
                }
            } catch (Throwable t) {
                System.out.println("---Check input---");
                in.nextLine();
            }
        }
    }

    private static void toInt() throws UnknownHostException {
        System.out.println("Input Ipv4");
        in.nextLine();
        String ip = in.nextLine();
        InetAddress bar = InetAddress.getByName(ip);
        int value = ByteBuffer.wrap(bar.getAddress()).getInt();
        System.out.println(value);
    }

    private static void toIpV4() throws UnknownHostException {
        System.out.println("Input number");
        int number = in.nextInt();
        InetAddress foo = InetAddress.getByName(Integer.toString(number));
        String address = foo.getHostAddress();
        System.out.println(address);
    }
}

