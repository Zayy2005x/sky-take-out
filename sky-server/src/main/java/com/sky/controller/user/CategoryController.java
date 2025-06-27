package com.sky.controller.user;


import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController("userCategoryController")
@Api(tags = "菜品分类查询")
@RequestMapping("/user/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * 根据type查询，1-菜品分类 2-套餐分类
     * @param type
     * @return
     */
    @GetMapping("list")
    @ApiOperation("查询分类")
    public Result<List<Category>> categoryList(Integer type){
        log.info("查询分类：{}",type);
        List<Category> categoryList = categoryService.list(type);
        return Result.success(categoryList);
    }
}
