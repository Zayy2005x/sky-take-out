package com.sky.controller.user;


import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/user/dish")
@RestController("userDishController")
@Api(tags = "C端菜品相关接口")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据分类查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<DishVO>> list(Long categoryId){
        log.info("根据分类id查询菜品:{}",categoryId);
        String key = "dish_"+categoryId;    //redis的key属性
        //查询Redis是否存在对应菜品信息
        List<DishVO> dishVOS = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if(dishVOS != null && !dishVOS.isEmpty()){
            //如果非空,则直接返回数据,无需查询数据库
            return Result.success(dishVOS);
        }

        //为空,查询数据库信息,并将查询结果保存到Redis中
        Dish dish = Dish.builder()
                .status(StatusConstant.ENABLE)  //起售的菜品
                .categoryId(categoryId)
                .build();
        dishVOS = dishService.listWithFlavors(dish);
        redisTemplate.opsForValue().set(key,dishVOS);
        return Result.success(dishVOS);
    }
}
