package com.dmdev.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author E.Parominsky 17/07/2023 09:50
 */
class LocalDateFormatterTest {

    @Test
    void format() {
        // given
        String date = "2020-11-28";
        // when
        LocalDate actualResult = LocalDateFormatter.format(date);
        // then
        assertThat(actualResult).isEqualTo(LocalDate.of(2020, 11, 28));
    }

    @Test
    void shouldThrowExceptionIfDateInvalid() {
        String date = "2020-11-28 12:25";
        assertThrows(DateTimeParseException.class, () -> LocalDateFormatter.format(date));
    }

    static Stream<Arguments> getValidationArguments() {
        return Stream.of(
                Arguments.of("2020-11-28", true),
                Arguments.of("11-28-2001", false),
                Arguments.of("2020-11-28 12:25", false),
                Arguments.of(null, false)
        );
    }

    @ParameterizedTest
    @MethodSource("getValidationArguments")
    void isValid(String date, boolean expectedResult){
        boolean actualResult = LocalDateFormatter.isValid(date);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

}