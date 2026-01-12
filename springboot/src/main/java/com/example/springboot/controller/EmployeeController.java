package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Employee;
import com.example.springboot.service.EmployeeService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    /**
     * 查询所有的数据
     */
    @GetMapping("/selectAll")
    public Result selectAll(Employee employee){
        List<Employee> list = employeeService.selectAll(employee);
        return Result.success(list);
    }

    /**
     *  通过@PathVariable查询单个数据
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Employee employee = employeeService.selectById(id);
        return Result.success(employee);
    }

    /**
     *  通过@RequestParam查询单个数据
     */
    @GetMapping("/selectOne")
    public Result selectOne(@RequestParam Integer id,@PathVariable(required = false) String no){
        Employee employee = employeeService.selectById(id);
        return Result.success(employee);
    }

    /**
     *  通过对象查询数据
     */
    @GetMapping("/selectList")
    public Result selectList(Employee employee){
        List<Employee> list = employeeService.selectList(employee);
        return Result.success(list);
    }

    /**
     *  分页查询数据
     */
    @GetMapping("/selectPage")
    public Result selectPage(Employee employee,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "2") Integer size){
        PageInfo<Employee> pageInfo = employeeService.selectPage(employee,page,size);
        return Result.success(pageInfo);
    }

    /**
     *  新增数据
     */
    @PostMapping("/add")
    public Result Add(@RequestBody Employee employee){
        employeeService.add(employee);
        return Result.success();
    }

    /**
     *  修改数据
     */
    @PutMapping("/update")
    public Result Update(@RequestBody Employee employee){
        employeeService.update(employee);
        return Result.success();
    }

    /**
     *  删除单个数据
     */
    @DeleteMapping("/delete")
    public Result Delete(@RequestParam Integer id){
        employeeService.delete(id);
        return Result.success();
    }

    /**
     *  批量删除数据
     */
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        employeeService.deleteBatch(ids);
        return Result.success();
    }
}
