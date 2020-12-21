package shuailee.springboot.lesson.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import shuailee.springboot.lesson.entity.Foo;
import shuailee.springboot.lesson.entity.FooRowMapper;

import java.util.List;
import java.util.Map;

/**
 * @program: lesson
 * @description:
 * @create: 2019-10-11 22:21
 **/
@Repository
public class FooRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Foo> findAll() {
        /**
         * JdbcTemplate
         * */
        List<Foo> list = jdbcTemplate.query("select * from FOO", new FooRowMapper());
        return list;
    }

    public void add(Foo foo) {

        jdbcTemplate.execute("insert into Foo values (" + foo.getID() + ",'" + foo.getBAR() + "')");

    }

}
