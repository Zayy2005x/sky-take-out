package com.sky.controller.user;


import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Slf4j
@Api(tags = "C端-地址簿相关接口")
public class AddressBookController {


    @Autowired
    private AddressBookService addressBookService;


    /**
     * 新增地址
     * @param addressBook
     * @return
     */
    @PostMapping()
    @ApiOperation("新增地址")
    public Result<String> save(@RequestBody AddressBook addressBook){
        log.info("新增地址:{}",addressBook);
        addressBookService.save(addressBook);
        return Result.success();
    }

    /**
     * 查询当前登录用户的所有地址信息
     * @return
     */
    @GetMapping("list")
    @ApiOperation("查询用户地址信息")
    public Result<List<AddressBook>> list(){
        Long userId = BaseContext.getCurrentId();
        log.info("查询用户所有地址,用户ID:{}",userId);
        List<AddressBook> addressBooks = addressBookService.list(userId);
        return Result.success(addressBooks);
    }


    /**
     * 获得用户默认地址
     * @param addressBook
     * @return
     */
    @GetMapping("/default")
    @ApiOperation("查询默认地址")
    public Result<AddressBook> getDefault(){
        Long userId = BaseContext.getCurrentId();
        log.info("查询默认地址,用户ID:{}",userId);
        AddressBook addressBook = addressBookService.getDefault(userId);
        return Result.success(addressBook);
    }


    /**
     * 设置默认地址
     * @param id
     * @return
     */
    @PutMapping("/default")
    @ApiOperation("设置默认地址")
    public Result<String> setDefault(@RequestBody AddressBook addressBook){
        Long id = addressBook.getId();
        log.info("设置默认地址,住址id:{}",id);
        addressBookService.setDefault(id);
        return Result.success();
    }


    @GetMapping("/{id}")
    @ApiOperation("根据id查询地址")
    public Result<AddressBook> getAddress(@PathVariable Long id){
        log.info("查询地址,id:{}",id);
        AddressBook addressBook = addressBookService.getAddress(id);
        return Result.success(addressBook);
    }


    @DeleteMapping()
    @ApiOperation("根据id删除地址")
    public Result<String> deleteAddress(Long id){
        log.info("删除地址,{}",id);
        addressBookService.deleteAddress(id);
        return Result.success();
    }
}
