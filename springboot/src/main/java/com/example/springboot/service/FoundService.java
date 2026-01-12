package com.example.springboot.service;

import com.example.springboot.entity.Item;
import com.example.springboot.mapper.FoundMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FoundService {

    @Resource
    private FoundMapper foundMapper;
    public List<Item> selectAll(Item item) {
        return foundMapper.selectAll(item);
    }


    public Item selectById(Integer id) {
        return foundMapper.selectById(id);
    }

    public Item selectByPosterId(Integer posterId) {
        return foundMapper.selectByPosterId(posterId);
    }

    public List<Item> selectList(Item item) {
        System.out.println(item);
        return null;
    }

    public PageInfo<Item> selectPage(Item item, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Item> list = foundMapper.selectAll(item);
        return PageInfo.of(list);
    }

    public void add(Item item) {
        item.setPostTime(LocalDateTime.now());
        foundMapper.insert(item);
    }

    public void update(Item item) {
        foundMapper.updateById(item);
    }

    public void delete(Integer id) {
        foundMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            foundMapper.deleteById(id);
        }
    }

}
