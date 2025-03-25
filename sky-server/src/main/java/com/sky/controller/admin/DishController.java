package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 菜品接口控制层
 */
@RestController
@RequestMapping("/admin/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 新增菜品
     *
     * @param dishDTO 菜品信息
     * @return
     */
    @PostMapping
    public Result<Void> addDish(@RequestBody DishDTO dishDTO) {
        log.info("DishController: addDish(), dishDTO: {}", dishDTO);
        dishService.addDishWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * 菜品分页查询
     *
     * @param pageQueryDTO 分页查询条件
     * @return 分页查询结果
     */
    @GetMapping("/page")
    public Result<PageResult> pageQuery(DishPageQueryDTO pageQueryDTO) {
        log.info("DishController: pageQuery(), pageQueryDTO: {}", pageQueryDTO);
        PageResult pageResult = dishService.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }

}
