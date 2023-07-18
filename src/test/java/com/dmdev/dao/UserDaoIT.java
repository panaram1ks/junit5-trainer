package com.dmdev.dao;

import com.dmdev.entity.Gender;
import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import com.dmdev.integration.IntegrationTestBase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author E.Parominsky 17/07/2023 17:33
 */
class UserDaoIT extends IntegrationTestBase {

    private final UserDao userDao = UserDao.getInstance();

    @Test
    void findAll() {
        //given
        User user1 = userDao.save(getUser("test1gmail.com"));
        User user2 = userDao.save(getUser("test2gmail.com"));
        User user3 = userDao.save(getUser("test3gmail.com"));

        //when
        List<User> actualResult = userDao.findAll();

        //then
        Assertions.assertThat(actualResult).hasSize(3);
        List<Integer> userIds = actualResult.stream().map(User::getId).collect(Collectors.toList());
        Assertions.assertThat(userIds).contains(user1.getId(), user2.getId(), user3.getId());
    }

    @Test
    void findById() {
        User user = userDao.save(getUser("test1gmail.com"));

        Optional<User> actualResult = userDao.findById(user.getId());

        Assertions.assertThat(actualResult).isPresent();
        Assertions.assertThat(actualResult.get()).isEqualTo(user);
    }

    @Test
    void save() {
        User user = getUser("test1gmail.com");

        User actualResult = userDao.save(user);

        org.junit.jupiter.api.Assertions.assertNotNull(actualResult.getId());
    }

    @Test
    void findByEmailAndPassword() {
        User user = userDao.save(getUser("test1gmail.com"));

        Optional<User> actualResult = userDao.findByEmailAndPassword(user.getEmail(), user.getPassword());

        Assertions.assertThat(actualResult).isPresent();
        Assertions.assertThat(actualResult.get()).isEqualTo(user);
    }

    @Test
    void shouldNotFindByEmailAndPasswordIfUserDoesNotExist() {
        User user = userDao.save(getUser("test1gmail.com"));

        Optional<User> actualResult = userDao.findByEmailAndPassword("dummy", "123");

        Assertions.assertThat(actualResult).isEmpty();

    }

    @Test
    void deleteExistingEntity() {
        User user = userDao.save(getUser("test1gmail.com"));

        boolean actualResult = userDao.delete(user.getId());

        org.junit.jupiter.api.Assertions.assertTrue(actualResult);
    }

    @Test
    void deleteNotExistingEntity() {
        userDao.save(getUser("test1gmail.com"));

        boolean actualResult = userDao.delete(Integer.MAX_VALUE);

        org.junit.jupiter.api.Assertions.assertFalse(actualResult);
    }

    @Test
    void update() {
        User user = getUser("test1gmail.com");
        userDao.save(user);
        user.setName("Ivan-updated");
        user.setPassword("new_password");

        userDao.update(user);

        User actualResult = userDao.findById(user.getId()).get();
        Assertions.assertThat(actualResult).isEqualTo(user);
    }

    private static User getUser(String email) {
        return User.builder()
                .name("Ivan")
                .email(email)
                .password("123")
                .birthday(LocalDate.of(2000, 01, 01))
                .role(Role.USER)
                .gender(Gender.MALE)
                .build();
    }
}