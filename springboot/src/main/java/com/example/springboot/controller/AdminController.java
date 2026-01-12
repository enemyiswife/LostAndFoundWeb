package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Admin;
import com.example.springboot.service.AdminService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    /**
     * 查询所有的数据
     */
    @GetMapping("/selectAll")
    public Result selectAll(Admin admin){
        List<Admin> list = adminService.selectAll(admin);
        return Result.success(list);
    }

    /**
     *  通过@PathVariable查询单个数据
     */
    @GetMapping("/selectById/{id}/{no}")
    public Result selectById(@PathVariable Integer id , String no){
        Admin admin = adminService.selectById(id);
        return Result.success(admin);
    }

    /**
     *  通过@RequestParam查询单个数据
     */
    @GetMapping("/selectOne")
    public Result selectOne(@RequestParam Integer id,@PathVariable(required = false) String no){
        Admin admin = adminService.selectById(id);
        return Result.success(admin);
    }

    /**
     *  通过对象查询数据
     */
    @GetMapping("/selectList")
    public Result selectList(Admin admin){
        List<Admin> list = adminService.selectList(admin);
        return Result.success(list);
    }

    /**
     *  分页查询数据
     */
    @GetMapping("/selectPage")
    public Result selectPage(Admin admin,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "2") Integer size){
        PageInfo<Admin> pageInfo = adminService.selectPage(admin,page,size);
        return Result.success(pageInfo);
    }

    /**
     *  新增数据
     */
    @PostMapping("/add")
    public Result Add(@RequestBody Admin admin){
        adminService.add(admin);
        return Result.success();
    }

    /**
     *  修改数据
     */
    @PutMapping("/update")
    public Result Update(@RequestBody Admin admin){
        adminService.update(admin);
        return Result.success();
    }

    /**
     *  删除单个数据
     */
    @DeleteMapping("/delete")
    public Result Delete(@RequestParam Integer id){
        adminService.delete(id);
        return Result.success();
    }

    /**
     *  批量删除数据
     */
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        adminService.deleteBatch(ids);
        return Result.success();
    }
}
