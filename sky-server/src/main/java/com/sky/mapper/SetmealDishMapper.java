package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {


    /**
     * 根据菜品id查询对应的套餐Id
     * @param dishIds
     * @return
     */

    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    /**
     * 批量插入菜品和套餐之间的关系
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);


    /**
     * 更新套餐信息
     * @param setmealDish
     */
    @AutoFill(value = OperationType.INSERT)
    void update(SetmealDish setmealDish);

    /**
     * 根据套餐id,删除关联信息ibao
     * @param setmealId
     */
    @Delete("DELETE FROM sky_take_out.setmeal_dish WHERE setmeal_id=#{setmealId}")
    void deleteBySetmealId(Long setmealId);


    /**
     * 根据套餐id查询关联信息
     * @param setmealId
     * @return
     */
    @Select("SELECT * FROM sky_take_out.setmeal_dish where setmeal_id=#{setmealId}")
    List<SetmealDish> getBySetmealId(Long setmealId);
}
