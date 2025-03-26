package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜品和套餐关联的表
 */
@Mapper
public interface SetMealDishMapper {

    /**
     * 根据菜品id列表查询关联的菜单id列表
     *
     * @param dishIds 菜品id列表
     * @return 关联的菜单id列表
     */
    List<Long> getSetMealIdsByDishIds(@Param("dishIds") List<Long> dishIds);

}
