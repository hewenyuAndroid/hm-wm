package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.type.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 插入菜品数据
     *
     * @param dish 菜品信息
     */
    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 菜品分页查询
     *
     * @param pageQueryDTO 分页查询条件
     * @return 分页查询结果
     */
    Page<DishVO> pageQuery(DishPageQueryDTO pageQueryDTO);

    /**
     * 根据菜品id查询菜品信息
     *
     * @param dishId 菜品id
     * @return 菜品信息
     */
    @Select("select * from dish where id = #{dishId}")
    Dish getById(@Param("dishId") Long dishId);

    /**
     * 根据菜品id删除菜品
     *
     * @param dishId 菜品id
     */
    @Delete("delete from dish where id = #{dishId}")
    void deleteById(@Param("dishId") Long dishId);

    /**
     * 批量删除菜品
     *
     * @param ids 菜品id列表
     */
    void batchDeleteByIds(@Param("dishIds") List<Long> ids);

}
