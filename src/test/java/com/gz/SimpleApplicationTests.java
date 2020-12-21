package com.gz;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.JdbcUtils;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@SpringBootTest
class SimpleApplicationTests {
    //导入JDBCTemplate模板
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Test
    void contextLoads() {
        String sql="select 1";
        Object args=null;
        System.out.println(jdbcTemplate.queryForMap(sql));
                System.out.println(13);
        System.out.println(13);
        System.out.println(13);
        System.out.println(13);

    }

}
