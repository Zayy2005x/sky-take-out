package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SetmealMapper {
    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);


    /**
     * 分页查询套餐
     * @param setmealPageQueryDTO
     * @return
     */
    Page<Setmeal> page(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 向套餐表中插入套餐数据
     * @param setmeal
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Setmeal setmeal);

    /**
     * 设置套餐状态
     * @param status
     * @param id
     */
    @AutoFill(value = OperationType.UPDATE)
    void setStatus(Integer status, Long id);

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @Select("SELECT * FROM sky_take_out.setmeal WHERE id=#{id}")
    Setmeal getById(Long id);


    /**
     * 根据id删除套餐
     * @param id
     */
    @Delete("DELETE FROM sky_take_out.setmeal WHERE id=#{id}")
    void deleteById(Long id);

    /**
     * 更新套餐信息
     * @param setmeal
     */
    void update(Setmeal setmeal);

    /**
     * 查询套餐基本信息
     * @param setmeal
     * @return
     */
    @Select("SELECT * FROM sky_take_out.setmeal WHERE status=#{status} and category_id=#{categoryId}")
    List<Setmeal> list(Setmeal setmeal);


    @Select("SELECT sd.name,sd.copies,d.image,d.description FROM sky_take_out.setmeal_dish sd LEFT JOIN sky_take_out.dish d ON sd.dish_id = d.id " +
            " WHERE sd.setmeal_id =#{setmealID}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);
}
