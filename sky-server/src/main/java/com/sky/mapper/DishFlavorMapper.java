package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜品口味表dao层
 */
@Mapper
public interface DishFlavorMapper {

    /**
     * 批量插入口味信息
     *
     * @param dishFlavors 口味信息
     */
    void batchInsert(@Param("dishFlavors") List<DishFlavor> dishFlavors);

    /**
     * 删除菜品关联的口味数据
     *
     * @param dishId 菜品id
     */
    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    void deleteByDishId(@Param("dishId") Long dishId);

    /**
     * 根据菜品id批量删除口味
     *
     * @param dishIds 菜品id列表
     */
    void batchDeleteByDishIds(@Param("dishIds") List<Long> dishIds);

}
