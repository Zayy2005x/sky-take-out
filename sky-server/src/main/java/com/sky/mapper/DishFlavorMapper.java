package com.sky.mapper;


import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 批量插入口味
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 根据菜品id删除关联口味
     * @param id
     */
    @Delete("DELETE FROM sky_take_out.dish_flavor WHERE dish_id = #{dishId}")
    void deleteById(Long dishId);

    /**
     * 批量删除菜品id关联的口味
     * @param ids
     */
    void deleteByIds(List<Long> dishIds);

    /**
     * 根据菜品id查询口味信息
     * @param id
     * @return
     */
    @Select("SELECT * FROM sky_take_out.dish_flavor WHERE dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
}
