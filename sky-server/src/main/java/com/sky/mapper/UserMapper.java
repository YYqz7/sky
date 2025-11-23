package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("SELECT * FROM user where openid = #{openID}")
    User selectByOpenID(String openID);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO user(openid, name, create_time) values (#{openid}, #{name}, #{createTime})")
    void insert(User user);
}
