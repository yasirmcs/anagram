package de.noori.software;

import org.junit.jupiter.api.Assertions;

class AnagramUtilTest {

    @org.junit.jupiter.api.Test
    void isAnagram() {
        AnagramUtil toTest = new AnagramUtil();
        Assertions.assertTrue(toTest.isAnagram("help", "lehp"));
        Assertions.assertFalse(toTest.isAnagram("help", "pole"));
    }
}