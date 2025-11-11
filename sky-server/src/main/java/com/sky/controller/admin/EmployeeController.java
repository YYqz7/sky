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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@Api(tags = "员工相关接口")
@Slf4j
@RestController
@RequestMapping("/admin/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     */
    @ApiOperation("员工登录")
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
     */
    @ApiOperation("员工退出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }


    /**
     * 新增员工
     */
    @ApiOperation("添加员工")
    @PostMapping
    public Result<String> addEmployee(@RequestBody EmployeeDTO dto) {
        log.info("新增员工: {}", dto);
        employeeService.saveEmployee(dto);
        return Result.success();
    }


    /**
     * 员工分页查询
     *
     * @param dto
     * @return
     */
    @ApiOperation("分页查询")
    @GetMapping("/page")
    public Result getEmployeePage(EmployeePageQueryDTO dto) {
        log.info("员工分页查询: {}", dto);
        PageResult pg = employeeService.queryEmployeePage(dto);
        return Result.success(pg);
    }

    /**
     * 修改员工状态
     *
     * @param status
     * @param id
     * @return
     */
    @ApiOperation("修改员工状态")
    @PostMapping("/status/{status}")
    public Result<String> updateEmployeeStatus(@PathVariable Integer status, Long id) {
        log.info("员工状态修改: {}, id = {}", status, id);
        employeeService.updateEmployeeStatus(status, id);
        return Result.success();
    }


    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @ApiOperation("根据id查询员工")
    @GetMapping("/{id}")
    public Result<Employee> getEmployeeById(@PathVariable Long id) {
        log.info("根据id = {} 查询员工", id);
        Employee e =  employeeService.getEmployeeById(id);
        return Result.success(e);
    }

    /**
     * 修改员工
     */
    @ApiOperation("编辑员工信息")
    @PutMapping
    public Result<String> updateEmployee(@RequestBody EmployeeDTO dto) {
        log.info("修改员工: {}", dto);
        employeeService.updateEmployee(dto);
        return Result.success();
    }
}
