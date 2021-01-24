package com.fundzforus.server;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy HH:mm");
        System.out.println(LocalDateTime.parse("01-Dec-20 01:00", formatter));
    }
}
