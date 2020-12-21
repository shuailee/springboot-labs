package shuailee.springboot.lesson.entity;


import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @program: lesson
 * @description:
 * @create: 2019-10-12 12:03
 **/
public class FooRowMapper implements RowMapper {

    @Nullable
    @Override
    public Foo mapRow(ResultSet resultSet, int i) throws SQLException {
        Foo foo=new Foo();
        foo.setID(resultSet.getInt("ID"));
        foo.setBAR(resultSet.getString("BAR"));
        return foo;
    }
}
