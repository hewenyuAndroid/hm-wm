package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     *
     * @param employeeDTO 新增员工的信息
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result<Void> add(@RequestBody EmployeeDTO employeeDTO) {
        log.info("EmployeeController: add(), employeeDTO: {}, thread={}", employeeDTO, Thread.currentThread().getName());
        employeeService.add(employeeDTO);
        return Result.success();
    }


    /**
     * 分页查询员工信息
     *
     * @param employeePageQueryDTO 封装 queryParam 信息的实体
     * @return 查询结果
     */
    @GetMapping("/page")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("EmployeeController: page(), employeePageQueryDTO: {}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用/禁用员工账号
     *
     * @param status 状态 1:启用 0:禁用
     * @param id     员工id
     * @return 操作结果
     */
    @PostMapping("/status/{status}")
    public Result<Void> operateAccountStatus(@PathVariable("status") Integer status, Long id) {
        log.info("EmployeeController: status(), status: {}， id={}", status, id);
        employeeService.operateAccountStatus(status, id);
        return Result.success();
    }

    /**
     * 根据员工id查询员工信息
     *
     * @param id 需要查询的员工id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public Result<Employee> queryEmployeeById(@PathVariable("id") Long id) {
        log.info("EmployeeController: queryEmployeeById(), id: {}", id);
        Employee employee = employeeService.getEmployeeById(id);
        return Result.success(employee);
    }

    /**
     * 编辑员工信息
     *
     * @param employeeDTO 目标员工信息
     * @return 操作结果
     */
    @PostMapping("/update")
    public Result<Void> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("EmployeeController: updateEmployee(), employeeDTO: {}", employeeDTO);
        employeeService.updateEmployee(employeeDTO);
        return Result.success();
    }

}
