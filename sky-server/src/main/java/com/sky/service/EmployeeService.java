package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 添加员工
     *
     * @param employeeDTO 新员工信息
     */
    void add(EmployeeDTO employeeDTO);

    /**
     * 分页查询员工信息
     *
     * @param employeePageQueryDTO 分页查询条件实体
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用/禁用员工账号
     */
    void operateAccountStatus(Integer status, Long id);

    /**
     * 根据员工id查询员工信息
     *
     * @param id 员工id
     */
    Employee getEmployeeById(Long id);

    /**
     * 编辑员工信息
     *
     * @param employeeDTO 需要修改的目标信息
     */
    void updateEmployee(EmployeeDTO employeeDTO);
}
