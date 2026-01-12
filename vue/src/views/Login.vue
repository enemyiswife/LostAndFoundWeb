<template>
  <div class="login-container">
    <div class="login-box">
      <div style="padding:50px; background-color: white; margin-left: 100px; border-radius: 5px; box-shadow: 0 0 10px rgb(0,0,0,0.2)">
        <el-form ref="formRef" :rules="data.rules" :model="data.form" style="width: 350px">
          <div style="margin-bottom: 30px; font-size: 24px; text-align: center; color: dodgerblue; font-weight: bold">欢迎登录失物招领系统</div>
          <el-form-item prop="username">
            <el-input size="large" v-model="data.form.username" placeholder="请输入账号" prefix-icon="User"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input show-password size="large" v-model="data.form.password" placeholder="请输入密码" prefix-icon="Lock"></el-input>
          </el-form-item>
          <el-form-item prop="role">
            <el-select v-model="data.form.role" style="width: 100%" size="large" >
              <el-option value="EMP" label="普通用户"></el-option>
              <el-option value="ADMIN" label="管理员"></el-option>
            </el-select>
          </el-form-item>
          <div @click="login" style="margin-bottom: 20px">
            <el-button size="large" style="width:100%" type="primary">登 录</el-button>
          </div>
          <div style="text-align: right">
            还没注册账号？请<a style="color:blue" href="/register">注册</a>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import request from "@/utils/request.js";

const data = reactive({
  form: {role:"EMP"},
  rules:{
    username: [
      {required: true, message: '请输入账号', trigger: 'blur'}
    ],
    password:[
      {required: true, message:'请输入密码', trigger: 'blur'}
    ]
  }
})

const formRef = ref()

const login = () =>{
  formRef.value.validate((valid) =>{
    if (valid){
      request.post('/login',data.form).then(res =>{
        if(res.code === '200'){
          //存取后端传来的用户数据
          localStorage.setItem('xm-pro-user', JSON.stringify(res.data))
          ElMessage.success('登录成功')
          location.href = '/manager/lost'
        }else{
          ElMessage.error(res.msg)
        }
      })
    }
  })
}
</script>


<style scoped>
.login-container{
  height: 100vh;
  overflow: hidden;
  background-image: url("@/assets/sky.jpg");
  background-size: cover;
}
.login-box{
  width: 50%;
  height: 100%;
  display: flex;
  align-items: center;
  right: 0;
  position: absolute;
}
</style>