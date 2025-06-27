package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {


    /**
     * 新增地址
     * @param addressBook
     */
    void save(AddressBook addressBook);

    /**
     * 查询当前用户所有的地址信息
     * @param userId
     * @return
     */
    List<AddressBook> list(Long userId);

    /**
     * 获得用户默认地址
     * @param userId
     * @return
     */
    AddressBook getDefault(Long userId);

    /**
     * 设置默认地址
     * @param id
     */
    void setDefault(Long id);

    /**
     * 查询地址
     * @param id
     * @return
     */
    AddressBook getAddress(Long id);

    /**
     * 删除地址
     * @param id
     */
    void deleteAddress(Long id);
}
