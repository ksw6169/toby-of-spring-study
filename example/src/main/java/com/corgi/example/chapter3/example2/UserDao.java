package com.corgi.example.chapter3.example2;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor
public abstract class UserDao {

    private final DataSource dataSource;

    public void deleteAll() throws SQLException {

        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();
            ps = makeStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();     // 사용한 리소스 반환
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }

            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    protected abstract PreparedStatement makeStatement(Connection c) throws SQLException;
}
