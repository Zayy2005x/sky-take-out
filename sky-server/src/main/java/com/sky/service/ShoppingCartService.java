package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    /**
     * 添加购物车记录
     * @param shoppingCartDTO
     */
    void add(ShoppingCartDTO shoppingCartDTO);


    /**
     * 查看购物车
     * @return
     */
    List<ShoppingCart> list();


    /**
     * 清空购物车
     */
    void clean();


    /**
     * 删除购物车的一个商品
     * @param shoppingCartDTO
     */
    void deleteAShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
