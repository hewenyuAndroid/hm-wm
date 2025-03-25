package com.sky.mapper;

import com.sky.entity.DishFlavor;
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

}
