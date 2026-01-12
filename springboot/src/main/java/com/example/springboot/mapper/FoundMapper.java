package com.example.springboot.mapper;

import com.example.springboot.entity.Item;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FoundMapper {

    List<Item> selectAll(Item item);

    @Select("select * from `found` where id = #{id}")
    Item selectById(Integer id);

    void insert(Item item);

    void updateById(Item item);

    @Delete("delete from `found` where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from `found` where name = #{name}")
    Item selectByName(String name);

    @Select("select * from `found` where place = #{place}")
    Item selectByPlace(String place);

    @Select("select * from `found` where posterId = #{posterId}")
    Item selectByPosterId(Integer posterId);
}
