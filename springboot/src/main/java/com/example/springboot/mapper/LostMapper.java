package com.example.springboot.mapper;

import java.util.List;

import com.example.springboot.entity.Item;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

public interface LostMapper {

    List<Item> selectAll(Item item);

    @Select("select * from `lost` where id = #{id}")
    Item selectById(Integer id);

    void insert(Item item);

    void updateById(Item item);

    @Delete("delete from `lost` where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from `lost` where name = #{name}")
    Item selectByName(String name);

    @Select("select * from `lost` where place = #{place}")
    Item selectByPlace(String place);

    @Select("select * from `lost` where posterId = #{posterId}")
    Item selectByPosterId(Integer posterId);
}
