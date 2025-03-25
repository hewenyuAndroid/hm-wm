package com.sky.service;

import com.sky.dto.DishDTO;

/**
 * 菜品操作service层
 */
public interface DishService {

    /**
     * 添加菜品和口味
     *
     * @param dishDTO 菜品信息
     */
    void addDishWithFlavor(DishDTO dishDTO);

}
