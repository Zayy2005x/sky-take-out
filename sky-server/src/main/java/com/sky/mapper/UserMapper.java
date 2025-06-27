package com.sky.mapper;


import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 插入用户信息
     * @param user
     */
    @Insert("INSERT INTO sky_take_out.user(openid, name, phone, sex, id_number, avatar, create_time)" +
            "VALUES (#{openid},#{name},#{phone},#{sex},#{idNumber},#{avatar},#{createTime})")
    void insert(User user);


    @Select("SELECT * FROM sky_take_out.user WHERE openid=#{openid}")
    User getByOpenId(String openid);
}
