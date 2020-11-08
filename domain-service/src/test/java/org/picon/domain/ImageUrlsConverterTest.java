package org.picon.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImageUrlsConverterTest {

    @Test
    void test() {
        List<String> a = Arrays.asList("1", "2");
        System.out.println(String.join(",", a));
    }
}