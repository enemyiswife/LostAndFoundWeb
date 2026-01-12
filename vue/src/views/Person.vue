<template>
  <div class="card" >
    <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px" style="padding-right: 40px; padding-top: 20px">
      <div style="width: 100%; display: flex; justify-content: center; margin-bottom: 20px">
        <el-upload
            class="avatar-uploader"
            action="http://localhost:9090/files/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess">
          <img v-if="data.form.avatar" :src="data.form.avatar" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </div>
      <el-form-item label="账号" prop="username">
        <el-input disabled v-model="data.form.username" autocomplete="off" placeholder="请输入账号"></el-input>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入名称"></el-input>
      </el-form-item>
      <div v-if="data.user.role === 'EMP'">
        <el-form-item label="性别" >
          <el-radio-group v-model="data.form.sex">
            <el-radio value="男" label="男"></el-radio>
            <el-radio value="女" label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="工号" prop="no">
          <el-input v-model="data.form.no" autocomplete="off" placeholder="请输入工号"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input-number style="width:180px" :min="1" v-model="data.form.age" autocomplete="off" placeholder="请输入年龄"></el-input-number>
        </el-form-item>
        <el-form-item label="个人介绍" >
          <el-input :rows="3" type="textarea" v-model="data.form.description" autocomplete="off" placeholder="请输入个人介绍"></el-input>
        </el-form-item>
      </div>
      <div style="text-align: center">
        <el-button @click="updateUser" type="primary" style="padding: 20px 30px">更新个人信息</el-button>
      </div>
    </el-form>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";

const formRef = ref()

const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-pro-user')),
  form: {},
  rules:{
    username:[
      {required:true, message: '账号为空', trigger: 'blur'}
    ],
    name:[
      {required:true, message: '名称为空', trigger: 'blur'}
    ],
    no:[
      {required:true, message: '工号为空', trigger: 'blur'}
    ]
  }
})

const emit = defineEmits(['updateUser'])

if(data.user.role === 'EMP'){
  request.get('/employee/selectById/' + data.user.id).then(res => {
    data.form = res.data;
  })
}else {
  data.form = data.user;
}

const updateUser = () =>{
  if(data.user.role === 'EMP'){
    request.put('/employee/update',data.form).then(res =>{
      if(res.code === '200'){
        ElMessage.success('更新成功')
        localStorage.setItem('xm-pro-user', JSON.stringify(data.form))
        emit('updateUser')
      }else{
        ElMessage.error(res.msg)
      }
    })
  }else{
    request.put('/admin/update',data.form).then(res =>{
      if(res.code === '200'){
        ElMessage.success('更新成功')
        localStorage.setItem('xm-pro-user', JSON.stringify(data.form))
        emit('updateUser')
      }else{
        ElMessage.error(res.msg)
      }
    })
  }
}

const handleAvatarSuccess = (res) =>{
  data.form.avatar = res.data
}
</script>

<!-- 上传窗口样式 -->
<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 50%;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>