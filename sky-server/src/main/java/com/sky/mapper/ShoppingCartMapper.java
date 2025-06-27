package com.sky.mapper;


import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * 查询购物车记录
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 根据购物车id更新购物车数量
     * @param shoppingCart
     */
    @Update("UPDATE sky_take_out.shopping_cart set number=#{number} WHERE id=#{id} ")
    void updateNumberById(ShoppingCart shoppingCart);

    /**
     * 添加购物车信息
     * @param shoppingCart
     */
    @Insert("INSERT INTO sky_take_out.shopping_cart(name, image, user_id, dish_id, setmeal_id, dish_flavor, amount, create_time) VALUES " +
            "(#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{amount},#{createTime})")
    void insert(ShoppingCart shoppingCart);


    /**
     * 根据用户id清楚购物车信息表
     * @param userId
     */
    @Delete("DELETE FROM sky_take_out.shopping_cart WHERE user_id=#{userId}")
    void deleteByUserId(Long userId);
}
