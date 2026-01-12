package com.example.springboot.controller;

import com.example.springboot.entity.Account;
import com.example.springboot.entity.Employee;
import com.example.springboot.exception.CustomException;
import com.example.springboot.service.AdminService;
import com.example.springboot.service.EmployeeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.springboot.common.Result;

import java.util.HashMap;

@RestController
public class WebController {

    @Resource
    private EmployeeService employeeService;
    @Autowired
    private AdminService adminService;

    @GetMapping("/hello")
    public Result hello(){
        HashMap map = new HashMap();
        map.put("键1","键值1");
        map.put("键2",123);
        return Result.success(map);
    }

    @PostMapping("/login")
    public Result login(@RequestBody Account account){
        Account result = null;
        if("ADMIN".equals(account.getRole())){
            result = adminService.login(account);
        } else if ("EMP".equals(account.getRole())) {
            result = employeeService.login(account);
        }else{
            throw new CustomException("500", "非法输入");
        }
        return Result.success(result);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Employee employee) {
        employeeService.register(employee);
        return Result.success();
    }

    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account) {
        if("ADMIN".equals(account.getRole())){
            adminService.updatePassword(account);
        }else if("EMP".equals(account.getRole())){
            employeeService.updatePassword(account);
        }else{
            throw new CustomException("500", "非法输入");
        }
        return Result.success();
    }
}
