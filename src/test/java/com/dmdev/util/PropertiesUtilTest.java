package com.dmdev.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author E.Parominsky 17/07/2023 11:22
 */
class PropertiesUtilTest {

    public static Stream<Arguments> getPropertyArgument() {
        return Stream.of(
                Arguments.of("db.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"),
                Arguments.of("db.user", "sa"),
                Arguments.of("db.password", "")
        );
    }

    @ParameterizedTest
    @MethodSource("getPropertyArgument")
    void checkGet(String key, String expectedValue) {
        String actualResult = PropertiesUtil.get(key);
        assertEquals(expectedValue, actualResult);
    }


}