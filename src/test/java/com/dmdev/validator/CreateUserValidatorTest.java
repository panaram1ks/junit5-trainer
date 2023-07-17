package com.dmdev.validator;

import com.dmdev.dto.CreateUserDto;
import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;


/**
 * @author E.Parominsky 17/07/2023 11:38
 */
class CreateUserValidatorTest {

    private final CreateUserValidator validator = CreateUserValidator.getInstance();

    @Test
    void shouldPassValidation() {
        CreateUserDto dto = CreateUserDto.builder()
                .name("Ivan")
                .email("test@gmail.com")
                .password("123")
                .birthday("2000-01-01")
                .role(Role.USER.name())
                .gender(Gender.MALE.name())
                .build();

        ValidationResult actualResult = validator.validate(dto);

        assertFalse(actualResult.hasErrors());
    }

    @Test
    void invalidBirthday() {
        CreateUserDto dto = CreateUserDto.builder()
                .name("Ivan")
                .email("test@gmail.com")
                .password("123")
                .birthday("01-01-2000")
                .role(Role.USER.name())
                .gender(Gender.MALE.name())
                .build();

        ValidationResult actualResult = validator.validate(dto);

        assertThat(actualResult.getErrors()).hasSize(1);
        assertThat(actualResult.getErrors().get(0).getCode()).isEqualTo("invalid.birthday");
    }

    @Test
    void invalidGender() {
        CreateUserDto dto = CreateUserDto.builder()
                .name("Ivan")
                .email("test@gmail.com")
                .password("123")
                .birthday("2000-01-01")
                .role(Role.USER.name())
                .gender("fake")
                .build();

        ValidationResult actualResult = validator.validate(dto);

        assertThat(actualResult.getErrors()).hasSize(1);
        assertThat(actualResult.getErrors().get(0).getCode()).isEqualTo("invalid.gender");
    }

    @Test
    void invalidRole() {
        CreateUserDto dto = CreateUserDto.builder()
                .name("Ivan")
                .email("test@gmail.com")
                .password("123")
                .birthday("2000-01-01")
                .role("fake")
                .gender(Gender.MALE.name())
                .build();

        ValidationResult actualResult = validator.validate(dto);

        assertThat(actualResult.getErrors()).hasSize(1);
        assertThat(actualResult.getErrors().get(0).getCode()).isEqualTo("invalid.role");
    }

    @Test
    void invalidRoleGenderBirthday() {
        CreateUserDto dto = CreateUserDto.builder()
                .name("Ivan")
                .email("test@gmail.com")
                .password("123")
                .birthday("fake")
                .role("fake_role")
                .gender("fake_gender")
                .build();

        ValidationResult actualResult = validator.validate(dto);

        assertThat(actualResult.getErrors()).hasSize(3);
        List<String> errorCodes = actualResult.getErrors().stream().map(Error::getCode).collect(Collectors.toList());
        assertThat(errorCodes).contains("invalid.role", "invalid.gender", "invalid.birthday");
    }

}