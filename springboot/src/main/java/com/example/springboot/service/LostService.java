package com.example.springboot.service;

import com.example.springboot.entity.Item;
import com.example.springboot.mapper.LostMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LostService {

    @Resource
    private LostMapper lostMapper;
    public List<Item> selectAll(Item item) {
        return lostMapper.selectAll(item);
    }


    public Item selectById(Integer id) {
        return lostMapper.selectById(id);
    }

    public Item selectByPosterId(Integer posterId) {
        return lostMapper.selectByPosterId(posterId);
    }

    public List<Item> selectList(Item item) {
        System.out.println(item);
        return null;
    }

    public PageInfo<Item> selectPage(Item item, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Item> list = lostMapper.selectAll(item);
        return PageInfo.of(list);
    }

    public void add(Item item) {
        item.setPostTime(LocalDateTime.now());
        lostMapper.insert(item);
    }

    public void update(Item item) {
        lostMapper.updateById(item);
    }

    public void delete(Integer id) {
        lostMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            lostMapper.deleteById(id);
        }
    }

}
