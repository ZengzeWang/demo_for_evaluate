package com.zengze.demo_for_evaluate.controller;

import com.zengze.demo_for_evaluate.entity.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JdbcController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value="/jdbc", method=RequestMethod.GET)
    public String index() {

        String sql = "SELECT timestamp FROM detail_info WHERE id = ?";

        // 通过jdbcTemplate查询数据库
        String mobile = (String)jdbcTemplate.queryForObject(
                sql, new Object[] { 1 }, String.class);

        return "Hello " + mobile;
    }

    /**
     * 新增数据
     * @param items
     * @return
     */
    @RequestMapping("/add")
    public @ResponseBody String  addItems(Score items) {
        String sql = "insert into score (F_s,D_s,A_s,N_s,Final_s,calculate_time) value (?,?,?,?,?,?)";
        Object args[] = {items.getFs(),items.getDs(),items.getAs(),items.getNs(),items.getFinals(),items.getCtime()};
        int temp = jdbcTemplate.update(sql, args);
        if(temp > 0) {
            return "数据新增成功";
        }
        return "新增出现错误";
    }

}