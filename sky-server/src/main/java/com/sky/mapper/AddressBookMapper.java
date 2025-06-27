package com.sky.mapper;


import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface AddressBookMapper {
    /**
     * 插入地址信息
     * @param addressBook
     */
    void insert(AddressBook addressBook);


    /**
     * 查询当前登录用户的所有地址信息
     * @param userId
     * @return
     */
    @Select("SELECT * FROM sky_take_out.address_book WHERE user_id=#{userId}")
    List<AddressBook> getByUserId(Long userId);

    /**
     * 查询默认地址
     * @param userId
     * @return
     */
    @Select("SELECT * FROM sky_take_out.address_book WHERE user_id=#{userId} and is_default=1")
    AddressBook getDefaultByUserId(Long userId);

    /**
     * 根据住址id,设置为默认地址
     * @param id
     */
    @Update("UPDATE sky_take_out.address_book SET is_default=#{isDefault} WHERE id=#{id}")
    void setDefaultById(AddressBook addressBook);


    /**
     * 根据id获得地址
     * @param id
     * @return
     */
    @Select("SELECT * FROM sky_take_out.address_book WHERE id=#{id}")
    AddressBook getById(Long id);

    /**
     * 根据id删除地址
     * @param id
     */
    @Delete("DELETE FROM sky_take_out.address_book WHERE id=#{id}")
    void deleteById(Long id);
}
