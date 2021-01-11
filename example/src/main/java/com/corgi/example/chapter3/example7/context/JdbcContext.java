package com.corgi.example.chapter3.example7.context;

import com.corgi.example.chapter3.example7.statement.StatementStrategy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component(value = "chapter3JdbcContext7")
@AllArgsConstructor
@Slf4j
public class JdbcContext {

    private DataSource dataSource;

    public void executeSql(final String sql) throws SQLException {

        workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                return c.prepareStatement(sql);
            }
        });
    }

    /**
     * todo - 개선 필요
     */
    public <T> void executeSql(final String sql, T ... args) throws SQLException {

        workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement(sql);

                for (int i=0; i<args.length; i++) {
                    ps.setString(i+1, (String) args[i]);
                }

                return ps;
            }
        });
    }

    public void workWithStatementStrategy(StatementStrategy st) throws SQLException {

        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();
            ps = st.makePreparedStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
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
}
