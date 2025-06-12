package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

public interface DishService {
    /**
     * 保存菜品及口味
     * @param dishDTO
     */
    void saveWithFlavors(DishDTO dishDTO);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult list(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 批量删除菜品
     * @param list
     */
    void deleteBatch(List<Long> ids);
}
