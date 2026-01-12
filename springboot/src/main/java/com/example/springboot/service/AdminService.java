package com.example.springboot.service;

import cn.hutool.core.util.StrUtil;
import com.example.springboot.entity.Account;
import com.example.springboot.entity.Admin;
import com.example.springboot.exception.CustomException;
import com.example.springboot.mapper.AdminMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Resource
    private AdminMapper adminMapper;
    public List<Admin> selectAll(Admin admin) {
        return adminMapper.selectAll(admin);
    }


    public Admin selectById(Integer id) {
        return adminMapper.selectById(id);
    }

    public List<Admin> selectList(Admin admin) {
        System.out.println(admin);
        return null;
    }

    public PageInfo<Admin> selectPage(Admin admin, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> list = adminMapper.selectAll(admin);
        return PageInfo.of(list);
    }

    public void add(Admin admin) {
        String username = admin.getUsername();
        Admin dbAdmin = adminMapper.selectByUsername(username);
        if(dbAdmin != null) {
            throw new CustomException("500", "账号已存在");
        }
        if(StrUtil.isBlank(admin.getPassword())) {
            admin.setPassword("admin");
        }
        if(StrUtil.isBlank(admin.getName())) {
            admin.setName(admin.getUsername());
        }
        admin.setRole("ADMIN");
        adminMapper.insert(admin);
    }

    public void update(Admin admin) {
        adminMapper.updateById(admin);
    }

    public void delete(Integer id) {
        adminMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            adminMapper.deleteById(id);
        }
    }

    public Admin login(Account account) {
        String username = account.getUsername();
        Admin dbAdmin = adminMapper.selectByUsername(username);
        if(dbAdmin == null) {
            throw new CustomException("500", "账号不存在");
        }
        String password = account.getPassword();
        if(!password.equals(dbAdmin.getPassword())) {
            throw new CustomException("500", "账号或密码错误");
        }
        return dbAdmin;
    }

    public void updatePassword(Account account) {
        Integer id = account.getId();
        Admin dbAdmin = this.selectById(id);
        if(!dbAdmin.getPassword().equals(account.getPassword())) {
            throw new CustomException("500", "原密码错误");
        }
        dbAdmin.setPassword(account.getNewPassword());
        this.update(dbAdmin);
    }
}
