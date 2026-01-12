package com.example.springboot.service;

import cn.hutool.core.util.StrUtil;
import com.example.springboot.entity.Account;
import com.example.springboot.entity.Employee;
import com.example.springboot.exception.CustomException;
import com.example.springboot.mapper.EmployeeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;
    public List<Employee> selectAll(Employee employee) {
        return employeeMapper.selectAll(employee);
    }


    public Employee selectById(Integer id) {
        return employeeMapper.selectById(id);
    }

    public List<Employee> selectList(Employee employee) {
        System.out.println(employee);
        return null;
    }

    public PageInfo<Employee> selectPage(Employee employee, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Employee> list = employeeMapper.selectAll(employee);
        return PageInfo.of(list);
    }

    public void add(Employee employee) {
        String username = employee.getUsername();
        Employee dbEmployee = employeeMapper.selectByUsername(username);
        if(dbEmployee != null) {
            throw new CustomException("500", "账号已存在");
        }
        dbEmployee = employeeMapper.selectByNo(employee.getNo());
        if(dbEmployee != null) {
            throw new CustomException("500", "工号重复");
        }
        if(StrUtil.isBlank(employee.getPassword())) {
            employee.setPassword("123456");
        }
        if(StrUtil.isBlank(employee.getName())) {
            employee.setName(employee.getUsername());
        }
        employee.setRole("EMP");
        employeeMapper.insert(employee);
    }

    public void update(Employee employee) {
        employeeMapper.updateById(employee);
    }

    public void delete(Integer id) {
        employeeMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            employeeMapper.deleteById(id);
        }
    }

    public Employee login(Account account) {
        String username = account.getUsername();
        Employee dbEmployee = employeeMapper.selectByUsername(username);
        if(dbEmployee == null) {
            throw new CustomException("500", "账号不存在");
        }
        String password = account.getPassword();
        if(!password.equals(dbEmployee.getPassword())) {
            throw new CustomException("500", "账号或密码错误");
        }
        return dbEmployee;
    }

    public void register(Employee employee) {
        this.add(employee);
    }

    public void updatePassword(Account account) {
        Integer id = account.getId();
        Employee employee = this.selectById(id);
        if(!employee.getPassword().equals(account.getPassword())) {
            throw new CustomException("500", "原密码错误");
        }
        employee.setPassword(account.getNewPassword());
        this.update(employee);
    }
}
