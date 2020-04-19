package com.zengze.demo_for_evaluate.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.zengze.demo_for_evaluate.entity.Detail;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/test", method= RequestMethod.GET)
public class TestController {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value="/list", method= RequestMethod.GET)
    public String index() {

        String sql = "select * from detail_info";
        List<Detail> userList = (List<Detail>) jdbcTemplate.query(sql, new RowMapper<Detail>() {

            @Override
            public Detail mapRow(ResultSet rs, int rowNum) throws SQLException {
                Detail user = new Detail();
                user.setId(rs.getString("id"));
                //user.setTimestamp(rs.getString("timestamp"));
                return user;
            }
        });

        for (Detail user : userList) {
            System.out.println("id = " + user.getId()); //+ " , timestamp = " + user.getTimestamp());
        }
        return "查询成功";
    }
}
