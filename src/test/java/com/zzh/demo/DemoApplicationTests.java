package com.zzh.demo;

import com.zzh.demo.Entity.User;
import com.zzh.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    //@Autowired
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Test
    public void contextLoads() {
    }

    @Test
    public void mySqlTest() {
        List<Map<String, Object>> result= jdbcTemplate.queryForList("select * from user");
        System.out.println("query result is " + result.size());
        System.out.println("success!");
    }

    @Test
    public void mySqlTest2(){
        String sql = "select id,name,age from user";
        List<User> userList =
                (List<User>) jdbcTemplate.query(sql, new RowMapper<User>(){
                    @Override
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User user = new User();
                        user.setId(rs.getString("id"));
                        user.setName(rs.getString("name"));
                        user.setAge(rs.getString("age"));
                        return user;
                    }
                });
        System.out.println("查询成功：");
        for(User user:userList){
            System.out.println("【id】: " + user.getId() + "；【name】: " + user.getName() + "；【age】: " + user.getAge());
        }

    }

    @Resource
    private UserService userService;

    @Test
    public void testRepository(){
        List<User> userList1 = userService.findAll();
        System.out.println("findAll(): " + userList1.size());

        List<User> userList2 = userService.findByName("张三");
        System.out.println("findByName(): " + userList2.size() );
        Assert.isTrue(userList2.get(0).getAge().equals("17"), "数据错误");

        List<User> userList3 = userService.findByNameLike("%三%");
        System.out.println("findByNameLike(): " + userList3.size() );
        Assert.isTrue(userList3.get(0).getAge().equals("17"), "数据错误");

        List<String> ids = new ArrayList<String>();
        ids.add("1");
        ids.add("2");
        List<User> userList4 = userService.findByIdIn(ids);
        System.out.println("findByIdIn(): " + userList4.size() );

        PageRequest pageRequest = new PageRequest(0, 10);
        Page<User> userList5 = userService.findAll(pageRequest);
        System.out.println("page findAll(): " + userList5.getTotalPages() + "/" + userList5.getSize());

        User user = new User();
        user.setId("3");
        user.setName("王二");
        user.setAge("20");
        userService.save(user);

        userService.delete("3");
    }

    @Test
    public  void testTransaction(){
        User user = new User();
        user.setId("6");
        user.setName("孟十一");
        user.setAge("31");
        userService.save(user);
    }

}


