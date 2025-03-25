package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 添加菜品操作service层实现
 */
@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    /**
     * 批量插入菜品数据
     * 涉及多张表操作，开启事务
     *
     * @param dishDTO 菜品信息
     */
    @Transactional
    @Override
    public void addDishWithFlavor(DishDTO dishDTO) {
        // 转换bean
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        // 向菜品表中插入菜品数据
        dishMapper.insert(dish);

        // 获取insert语句生成的主键
        long dishId = dish.getId();

        // 获取口味信息
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            // 绑定口味信息中的菜品
            flavors.forEach(flavor -> flavor.setDishId(dishId));
            // 向口味表中批量插入数据
            dishFlavorMapper.batchInsert(flavors);
        }

    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO pageQueryDTO) {
        PageHelper.startPage(pageQueryDTO.getPage(), pageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(pageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
