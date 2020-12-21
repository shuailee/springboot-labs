package shuailee.springboot.lesson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @program: lesson
 * @description:
 * @create: 2019-10-09 20:28
 **/
@RestController
public class JdbcController {

    @Autowired
    DataSource dataSource;

    @GetMapping("/conn")
    public void conn() {
        /**
         * JDBC 访问数据库
         * */
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            System.out.println(connection);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
