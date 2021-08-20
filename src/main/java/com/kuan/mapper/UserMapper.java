package com.kuan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kuan.pojo.User;
import org.springframework.stereotype.Repository;

//  在对应的Mapper上面继承基本的类BaseMapper
@Repository
public interface UserMapper extends BaseMapper<User> {

}
