package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AddressBookImpl implements AddressBookService {




    @Autowired
    private AddressBookMapper addressBookMapper;
    /**
     * 新增地址
     * @param addressBook
     */
    public void save(AddressBook addressBook) {
        Long userId = BaseContext.getCurrentId();
        addressBook.setUserId(userId);
        addressBookMapper.insert(addressBook);
    }

    /**
     * 查询当前登录用户的所有的地址信息
     * @param userId
     * @return
     */
    @Override
    public List<AddressBook> list(Long userId) {
        return addressBookMapper.getByUserId(userId);
    }

    /**
     * 获得用户默认地址
     * @param userId
     * @return
     */
    @Override
    public AddressBook getDefault(Long userId) {
        return addressBookMapper.getDefaultByUserId(userId);
    }


    /**
     * 设置默认地址
     * @param id
     */
    @Override
    public void setDefault(Long id) {
        AddressBook addressBook = addressBookMapper.getById(id);
        if(addressBook.getIsDefault() == 1){
            addressBook.setIsDefault(0);
        }else {
            addressBook.setIsDefault(1);
        }
        addressBookMapper.setDefaultById(addressBook);
    }

    /**
     * 查询地址
     * @param id
     * @return
     */
    @Override
    public AddressBook getAddress(Long id) {
        return addressBookMapper.getById(id);
    }

    /**
     * 删除地址
     * @param id
     */
    @Override
    public void deleteAddress(Long id) {
        addressBookMapper.deleteById(id);
    }
}
