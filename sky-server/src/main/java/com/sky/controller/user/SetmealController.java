package com.sky.controller.user;


import com.sky.constant.StatusConstant;
import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@Api(tags = "C端套餐浏览相关接口")
@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
public class SetmealController {


    @Autowired
    private SetmealService setmealService;
    @Autowired
    private DishService dishService;

    @GetMapping("/list")
    @ApiOperation("根据分类id查询套餐")
    @Cacheable(cacheNames = "setmealCache",key = "#categoryId")
    public Result<List<Setmeal>> list(Long categoryId){
        log.info("查询套餐:{}",categoryId);
        Setmeal setmeal = Setmeal.builder()
                .status(StatusConstant.ENABLE)
                .categoryId(categoryId)
                .build();
        List<Setmeal> setmealList = setmealService.list(setmeal);
        return Result.success(setmealList);
    }

    @GetMapping("/dish/{id}")
    @ApiOperation("根据套餐id查询包含的菜品")
    public Result<List<DishItemVO>> getDishBySetmealId(@PathVariable Long id){
        log.info("查询套餐菜品:{}",id);
        List<DishItemVO> dishItemVOS = setmealService.getDishItemById(id);
        return Result.success(dishItemVOS);
    }
}
