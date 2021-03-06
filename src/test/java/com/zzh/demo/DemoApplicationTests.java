package com.zzh.demo;

import com.zzh.demo.entity.Mood;
import com.zzh.demo.entity.User;
import com.zzh.demo.impl.MoodProducer;
import com.zzh.demo.service.MoodService;
import com.zzh.demo.service.UserService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    //@Autowired
    @Resource
    private JdbcTemplate jdbcTemplate;

    Logger logger = LogManager.getLogger(this.getClass());

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
        user.setId("7");
        user.setName("钱十");
        user.setAge("31");
        user.setPassword("147258");
        userService.save(user);
    }

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedis(){
        //增
        redisTemplate.opsForValue().set("name","zzh");
        String name = (String)redisTemplate.opsForValue().get("name");
        System.out.println(name);
        //删
        redisTemplate.delete("name");
        //改
        stringRedisTemplate.opsForValue().set("name","allen");
        //查
        name = stringRedisTemplate.opsForValue().get("name");
        System.out.println(name);
        stringRedisTemplate.delete("name");
    }

    @Test
    public void testFindById(){
        Long redisUserSize = 0L;
        //查询id = 1 的数据，该数据存在于redis缓存中
        User user = userService.findById("1");
        redisUserSize = redisTemplate.opsForList().size("ALL_USER_LIST");
        System.out.println("目前缓存中的用户数量为：" + redisUserSize);
        System.out.println("--->>> id: " + user.getId() + " name:" + user.getName());
        //查询id = 2 的数据，该数据存在于redis缓存中
        User user1 = userService.findById("2");
        redisUserSize = redisTemplate.opsForList().size("ALL_USER_LIST");
        System.out.println("目前缓存中的用户数量为：" + redisUserSize);
        System.out.println("--->>> id: " + user1.getId() + " name:" + user1.getName());
        //查询id = 4 的数据，不存在于redis缓存中，存在于数据库中，所以会把数据库查询的数据更新到缓存中
        User user3 = userService.findById("7");
        System.out.println("--->>> id: " + user3.getId() + " name:" + user3.getName());
        redisUserSize = redisTemplate.opsForList().size("ALL_USER_LIST");
        System.out.println("目前缓存中的用户数量为：" + redisUserSize);
    }

    @Test
    public void testLog4j(){
        userService.delete("7");
        logger.info("delete success!");
    }

    @Test
    public void testMybatis(){
        User user = userService.findByNameAndAge("李四", "22");
        logger.info(user.getId() + user.getName());
    }

    @Resource
    private MoodService moodService;

    @Test
    public void testMood(){
        Mood mood = new Mood();
        mood.setId("1");
        mood.setUserId("1");
        mood.setPraiseNum(0);
        mood.setContent("This is the first message!");
        mood.setPublishTime(new Date());
        Mood moodSave = moodService.save(mood);
    }

    @Resource
    private MoodProducer moodProducer;

    @Test
    public void testActiveMQ(){
        Destination destination = new ActiveMQQueue("test.q");
        moodProducer.sendMessage(destination, "Hello, MQ!");
    }

    @Test
    public void testActiveMQAsynSave(){
        Mood mood = new Mood();
        mood.setId("2");
        mood.setUserId("2");
        mood.setPraiseNum(0);
        mood.setContent("This is the second message!");
        mood.setPublishTime(new Date());
        String msg = moodService.asynSave(mood);
        System.out.println("异步发布信息： " + msg);
    }

    @Test
    public void testAsync(){
        long startTime = System.currentTimeMillis();
        System.out.println("第一次查询所有用户！");
        List<User> userList1 = userService.findAll();
        System.out.println("第二次查询所有用户！");
        List<User> userList2 = userService.findAll();
        System.out.println("第三次查询所有用户！");
        List<User> userList3 = userService.findAll();
        long endTime = System.currentTimeMillis();
        System.out.println("总共耗时： " + (endTime - startTime) + " 毫秒！");
    }

    @Test
    public void testAsync2() throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("第一次查询所有用户！");
        Future<List<User>> userList1 = userService.findAsynAll();
        System.out.println("第二次查询所有用户！");
        Future<List<User>> userList2 = userService.findAsynAll();
        System.out.println("第三次查询所有用户！");
        Future<List<User>> userList3 = userService.findAsynAll();
        while (true){
            if (userList1.isDone() && userList2.isDone() && userList3.isDone()){
                break;
            }else {
                Thread.sleep(10);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("总共耗时： " + (endTime - startTime) + " 毫秒！");
    }
}


