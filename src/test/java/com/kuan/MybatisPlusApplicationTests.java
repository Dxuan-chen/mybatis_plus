package com.kuan;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kuan.mapper.UserMapper;
import com.kuan.pojo.User;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class MybatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
        //查询全部用户
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void testInsert(){
        User user = new User();
        user.setName("狂神说Java");
        user.setAge(3);
        user.setEmail("1187900000@qq.com");

        int result = userMapper.insert(user);
        System.out.println(result);//受影响的行数
        System.out.println(user);
    }

    //测试更新
    @Test
    public void testUpdate(){
        User user = new User();
        //通过条件自动拼接动态sql
        user.setId(1427247820062588931L);
        user.setName("测试更新。。。");
        user.setAge(18);

        //注意：updateById 的参数是一个对象
        int i = userMapper.updateById(user);
        System.out.println(i);
    }

    //测试乐观锁成功
    @Test
    public void testOptimisticLocker(){
        //1.查询用户信息
        User user = userMapper.selectById(1L);
        System.out.println(user);
        //2.修改用户信息
        user.setName("kuangkuang");
        //3.执行更新操作
        userMapper.updateById(user);
    }

    //测试乐观锁失败，多线程下
    @Test
    public void testOptimisticLocker2(){
        //线程1
        User user = userMapper.selectById(1L);
        System.out.println(user);
        user.setName("kuangkuang111");
        user.setEmail("1187900000@qq.com");

        //模拟另外一个线程执行了插队操作
        User user2 = userMapper.selectById(1L);
        user2.setName("kuangkuang222");
        user2.setEmail("1187900000@qq.com");
        userMapper.updateById(user2);

        //可以使用自旋锁来多次尝试提交
        userMapper.updateById(user);//如果没有乐观锁就会覆盖插队线程的值
    }

    //测试查询
    @Test
    public void testSelectById(){
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    //测试批量查询
    @Test
    public void testSelectByBatchId(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1,2,3));
        users.forEach(System.out::println);
    }

    //按条件查询之一使用map操作
    @Test
    public void testSelectByBatchIds(){
        HashMap<String,Object> map = new HashMap<>();
        //自定义要查询的条件
        map.put("name","狂神说Java");
        map.put("age",3);

        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    //测试分页查询
    @Test
    public void testPage(){
        //参数一：当前页
        //参数二：页面大小
        Page<User> page = new Page<>(2,5);
        userMapper.selectPage(page,null);
        page.getRecords().forEach(System.out::println);
        System.out.println(page.getTotal());
    }

    //测试删除
    @Test
    public void testDeleteById(){
        userMapper.deleteById(6L);
    }

    //通过id批量删除
    @Test
    public void testDeleteBatchId(){
        userMapper.deleteBatchIds(Arrays.asList(1427247820062588929L,1427247820062588930L));
    }

    //通过Map删除
    @Test
    public void testDeleteMap(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("name","狂神说Java");
        userMapper.deleteByMap(map);
    }
}
