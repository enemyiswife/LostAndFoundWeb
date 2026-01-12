package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Item;
import com.example.springboot.service.LostService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lost")
public class LostController {

    @Resource
    private LostService lostService;

    /**
     * 查询所有的数据
     */
    @GetMapping("/selectAll")
    public Result selectAll(Item item){
        List<Item> list = lostService.selectAll(item);
        return Result.success(list);
    }

    /**
     *  通过@PathVariable查询单个数据
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Item item = lostService.selectById(id);
        return Result.success(item);
    }

    /**
     *  通过@RequestParam查询单个数据
     */
    @GetMapping("/selectOne")
    public Result selectOne(@RequestParam Integer id,@PathVariable(required = false) String no){
        Item item = lostService.selectById(id);
        return Result.success(item);
    }

    /**
     *  通过对象查询数据
     */
    @GetMapping("/selectList")
    public Result selectList(Item item){
        List<Item> list = lostService.selectList(item);
        return Result.success(list);
    }

    /**
     *  分页查询数据
     */
    @GetMapping("/selectPage")
    public Result selectPage(Item item,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "2") Integer size){
        PageInfo<Item> pageInfo = lostService.selectPage(item,page,size);
        return Result.success(pageInfo);
    }

    /**
     *  新增数据
     */
    @PostMapping("/add")
    public Result Add(@RequestBody Item item){
        lostService.add(item);
        return Result.success();
    }

    /**
     *  修改数据
     */
    @PutMapping("/update")
    public Result Update(@RequestBody Item item){
        lostService.update(item);
        return Result.success();
    }

    /**
     *  删除单个数据
     */
    @DeleteMapping("/delete")
    public Result Delete(@RequestParam Integer id){
        lostService.delete(id);
        return Result.success();
    }

    /**
     *  批量删除数据
     */
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        lostService.deleteBatch(ids);
        return Result.success();
    }
}
