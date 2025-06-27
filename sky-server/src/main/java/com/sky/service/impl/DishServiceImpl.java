package com.sky.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.aspectj.bridge.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class DishServiceImpl implements DishService {


    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 保存菜品及口味
     *
     * @param dishDTO
     */

    @Transactional
    public void saveWithFlavors(DishDTO dishDTO) {
        Dish dish = new Dish();
        //拷贝dishDto => dish
        BeanUtils.copyProperties(dishDTO, dish);

        //向菜品表中添加数据
        dishMapper.insert(dish);

        //获得主键回显,菜品的id
        Long dishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            //批量插入口味
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    public PageResult list(DishPageQueryDTO dishPageQueryDTO) {
        //开启分页查询
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.page(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }


    /**
     * 批量删除菜品
     *
     * @param ids
     */
    public void deleteBatch(List<Long> ids) {
        //判断当前菜品是否能够删除---是否存在起售中的菜品
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                //当前菜品处于起售状态，不能被删除
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断当前菜品是否能够删除--是否被套餐关联
        List<Long> setmealIdsByDishIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (!setmealIdsByDishIds.isEmpty()) {
            //当前菜品被套餐关联无法删除
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
//        for (Long id : ids) {
//            //删除菜品表中的菜品数据
//            dishMapper.deleteById(id);
//            //删除菜品关联的口味数据
//            dishFlavorMapper.deleteById(id);
//        }
        //批量删除菜品表的菜品数据
        dishMapper.deleteByIds(ids);
        //批量删除菜品关联的口味数据
        dishFlavorMapper.deleteByIds(ids);
    }

    /**
     * 根据id查询菜品及其口味
     *
     * @param id
     * @return
     */
    public DishVO getByIdWithFlavor(Long id) {
        //根据菜品id查询菜品数据
        Dish dish = dishMapper.getById(id);
        //根据菜品id查询口味信息
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);
        //将查询数据封装到VO中
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    /**
     * 根据菜品id修改菜品基本信息和口味信息
     *
     * @param dishDTO
     */
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        //修改菜品表基本信息
        dishMapper.update(dish);
        //删除原有的口味信息
        Long id = dishDTO.getId();
        dishFlavorMapper.deleteById(id);
        //重新插入新的口味信息
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(id);
            });
            //批量插入口味
            dishFlavorMapper.insertBatch(flavors);
        }

    }

    /**
     * 启售 停售 菜品
     * @param status
     * @param id
     */
    public void startOrStop(Integer status, Long id) {
        Dish dish = Dish.builder()
                .status(status)
                .id(id)
                .build();
        dishMapper.update(dish);
    }

    /**
     * 根据分类id查询
     * @param categoryId
     * @return
     */
    @Override
    public List<Dish> getByCategoryId(Long categoryId) {
        return dishMapper.getByCategoryId(categoryId);
    }

    /**
     * 根据菜品查询菜品信息及其口味
     * @param dish
     * @return
     */
    @Override
    public List<DishVO> listWithFlavors(Dish dish) {
        List<Dish> dishes = dishMapper.list(dish);
        List<DishVO> dishVOS = new ArrayList<>();
        for(Dish d:dishes){
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(d.getId());
            dishVO.setFlavors(dishFlavors);
            dishVOS.add(dishVO);
        }

        return dishVOS;
    }

}
