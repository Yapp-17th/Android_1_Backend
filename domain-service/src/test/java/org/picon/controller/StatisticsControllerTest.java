package org.picon.controller;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsControllerTest {

    @Test
    void test() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String monthOfCurrentYear = LocalDate.of(LocalDate.now().getYear(), 3, 1).format(dateTimeFormatter);
        System.out.println(monthOfCurrentYear);
    }
}