package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface DishMapper {
    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 添加菜品
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);


    Page<DishVO> page(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据菜品id查询
     * @param id
     * @return
     */
    @Select("SELECT * FROM sky_take_out.dish WHERE id= #{id}")
    Dish getById(Long id);

    /**
     * 根据菜品id删除菜品
     * @param id
     */
    @Delete("DELETE FROM sky_take_out.dish WHERE id = #{id}")
    void deleteById(Long id);

    /**
     * 批量删除菜品
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 更新菜品信息
     * @param dish
     */
    void update(Dish dish);

    /**
     * 根据分类id查询菜品信息
     * @param categoryId
     * @return
     */
    @Select("SELECT * FROM sky_take_out.dish WHERE category_id=#{categoryId}")
    List<Dish> getByCategoryId(Long categoryId);


    /**
     * 根据菜品信息查询菜品
     * @param dish
     * @return
     */
    @Select("SELECT * FROM sky_take_out.dish WHERE status=#{status} and category_id=#{categoryId}")
    List<Dish> list(Dish dish);
}
