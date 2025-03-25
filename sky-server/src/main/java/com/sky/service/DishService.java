package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

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

    /**
     * 菜品分页查询
     *
     * @param pageQueryDTO 查询条件
     * @return 分页结果
     */
    PageResult pageQuery(DishPageQueryDTO pageQueryDTO);

}
