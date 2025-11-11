package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     */
    void saveEmployee(EmployeeDTO employee);

    /**
     * 员工分页查询
     * @param dto
     * @return
     */
    PageResult queryEmployeePage(EmployeePageQueryDTO dto);

    /**
     * 更新员工（更新状态）
     * @param status
     * @param id
     */
    void updateEmployeeStatus(Integer status, Long id);


    /**
     * 修改员工
     * @param employee
     */
    void updateEmployee(EmployeeDTO employee);

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    Employee getEmployeeById(Long id);
}
