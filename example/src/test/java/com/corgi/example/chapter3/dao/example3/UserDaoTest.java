package com.corgi.example.chapter3.dao.example3;

import com.corgi.example.chapter3.dao.example3.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@DisplayName(value = "example-3")
@Transactional
@SpringBootTest
class UserDaoTest {

    @Autowired
    @Qualifier(value = "exampleUserDao3")
    private UserDao userDao;

    @Test
    void deleteAll() throws SQLException {
        userDao.deleteAll();
    }

    @Test
    void add() throws SQLException {
        User user = User.builder()
                .id("testIdentity")
                .name("testName")
                .password("testPassword")
                .build();

        userDao.add(user);
    }
}