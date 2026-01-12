<template>
  <div>
    <!-- 头部开始 -->
    <div style="height:60px;background-color:rgba(6,83,195,0.94); display:flex; align-items:center">
      <div style="width: 200px; display: flex;align-items: center;margin-left:20px">
        <span style="font-size:20px;color:white">失物招领系统</span>
      </div>
      <div style="flex: 1"></div>
      <div style="width: fit-content;display:flex;align-items: center; padding-right: 10px">
        <el-avatar :size="35" :src="data.user.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" />
        <span style="color:white; margin-left:5px">{{data.user.name}}</span>
      </div>
    </div>
    <!-- 头部结束 -->

    <!-- 下面部分开始 -->
    <div style="display: flex">
      <!-- 左侧导航开始 -->
      <div style="width: 200px; border-right: 1px solid #ddd; min-height: calc(100vh - 60px)">
        <el-menu router :default-active="currentPath" :default-openeds="['1']" style="border:0">
          <el-menu-item index="/Manager/Lost">
            <el-icon ><House /></el-icon>
            失物浏览
          </el-menu-item>
          <el-menu-item index="/Manager/Found">
            <el-icon><House /></el-icon>
            拾物浏览
          </el-menu-item>
          <el-sub-menu index="1" v-if="data.manager">
            <template #title>
              <el-icon><User /></el-icon>
              <span>用户管理</span>
            </template>
            <el-menu-item index="/Manager/Admin">管理员信息</el-menu-item>
            <el-menu-item index="/Manager/Employee">普通用户信息</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="1" v-if="!data.manager">
            <template #title>
              <el-icon><User /></el-icon>
              <span>我的发布</span>
            </template>
            <el-menu-item index="/Manager/MyLost">失物管理</el-menu-item>
            <el-menu-item index="/Manager/MyFound">拾物管理</el-menu-item>
          </el-sub-menu>
          <el-menu-item index="/Manager/Person">
            <el-icon><UserFilled /></el-icon>
            个人信息
          </el-menu-item>
          <el-menu-item index="/Manager/Password">
            <el-icon><Lock /></el-icon>
            修改密码
          </el-menu-item>
          <el-menu-item @click="logout">
            <el-icon><SwitchButton /></el-icon>
            退出登录
          </el-menu-item>
        </el-menu>
      </div>
      <!-- 左侧导航结束 -->
      <!-- 右侧导航开始 -->
      <div style="flex:1; width: 0; background-color: #f5f7ff; padding: 20px">
        <RouterView @updateUser="updateUser"/>
      </div>
      <!-- 右侧导航结束 -->
    </div>
    <!-- 下面部分结束 -->
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import {reactive} from "vue";

const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-pro-user')),
  manager: false
})

if(data.user.role === 'ADMIN'){
  data.manager = true
}

const currentPath = useRoute().path

const logout = () =>{
  localStorage.removeItem('xm-pro-user')
  location.href = '/login'
}

const updateUser = () =>{
  data.user = JSON.parse(localStorage.getItem('xm-pro-user'))
}
</script>