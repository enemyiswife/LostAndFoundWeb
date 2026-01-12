<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input style="width:240px; margin-right: 10px" v-model="data.name" placeholder="请输入名称查询" prefix-icon="Search"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary" @click="handleAdd">新增</el-button>
      <el-button type="warning" @click="delBatch">批量删除</el-button>
<!--      <el-button type="info">导入</el-button>-->
<!--      <el-button type="success">导出</el-button>-->
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.tableData" stripe  @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column label="账号" prop="username"></el-table-column>
        <el-table-column label="头像">
          <template #default="scope">
            <img v-if="scope.row.avatar" :src="scope.row.avatar" alt="" style="display: block; width: 40px; height: 40px; border-radius: 50%"/>
          </template>
        </el-table-column>
        <el-table-column label="名称" prop="name"></el-table-column>
        <el-table-column label="操作" width="160px">
          <template #default="scope">
            <el-button @click="handleUpdate(scope.row)" type="primary">编辑</el-button>
            <el-button @click="del(scope.row.id)" type="danger">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin-top: 15px">
        <el-pagination
            @current-change="load"
            @size-change="load"
            v-model:current-page="data.pageNum"
            v-model:page-size="data.pageSize"
            :page-sizes="[5, 10, 15, 20]"
            background
            layout="total,sizes,prev,pager,next,jumper"
            :total="data.total"
        />
      </div>
    </div>

    <el-dialog title="管理员信息" v-model="data.formVisible"  width="500" destroy-on-close>
      <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px" style="padding-right: 40px; padding-top: 20px">
        <el-form-item :disabled="data.form.id" label="账号" prop="username">
          <el-input v-model="data.form.username" autocomplete="off" placeholder="请输入账号"></el-input>
        </el-form-item>
        <el-form-item label="头像">
          <el-upload
              limit= 1
              action= "http://localhost:9090/files/upload"
              list-type= "picture"
              :on-success= "handleAvatarSuccess">
            <el-button type="primary">上传头像</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入名称"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="data.formVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import {reactive,ref} from "vue";
import request from "@/utils/request.js";
import {ElMessage, ElMessageBox} from "element-plus";

const data= reactive({
  name:null,
  tableData:[],
  pageNum: 1,
  pageSize:5,
  total:0,
  formVisible: false,
  form: {},
  ids: [],
  rules:{
    username:[
      {required:true, message: '账号为空', trigger: 'blur'}
    ],
    name:[
      {required:true, message: '名称为空', trigger: 'blur'}
    ]
  }
})

const formRef = ref()

const load = () =>{
  request.get('admin/selectPage',{
    params: {
      page: data.pageNum,
      size: data.pageSize,
      name: data.name
    }
  }).then(res =>{
    data.tableData = res.data.list
    data.total = res.data.total
  })
}

const reset =() =>{
  data.name = null
  load()
}

const handleAdd =() =>{
  data.formVisible = true
  data.form = {}
}

const save = () => {
  formRef.value.validate((valid) => {
    if(valid){
      data.form.id ? update() : add()
    }
  })

}

const add = () => {
  request.post('admin/add',data.form).then(res => {
    if (res.code === '200'){
      ElMessage.success('操作成功')
      load()
    }else{
      ElMessage.error(res.msg)
    }
  })
  data.formVisible = false
}

const update = () => {
  request.put('admin/update',data.form).then(res => {
    if (res.code === '200'){
      ElMessage.success('操作成功')
      load()
    }else{
      ElMessage.error(res.msg)
    }
  })
  data.formVisible = false
}

const handleUpdate = (row) =>{
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

const del = (id) =>{
  ElMessageBox.confirm('删除数据后无法恢复，是否确认删除？','删除确认',{type:'warning'}).then(() =>{
    request.delete(`admin/delete?id=${id}`).then(res =>{
      if (res.code === '200'){
        ElMessage.success('操作成功')
        load()
      }else{
        ElMessage.error(res.msg)
      }
    })
  }).catch()
}

const handleSelectionChange = (rows) =>{
  data.ids = rows.map(row => row.id)
  console.log(data.ids)
}

const delBatch = () =>{
  if(data.ids.length === 0){
    ElMessage.warning('请选择数据')
    return
  }
  ElMessageBox.confirm('删除数据后无法恢复，是否确认删除？','删除确认',{type:'warning'}).then(() => {
    request.delete('admin/deleteBatch',{data: data.ids}).then(res =>{
      if (res.code === '200'){
        ElMessage.success('操作成功')
        load()
      }else{
        ElMessage.error(res.msg)
      }
    })
  }).catch()
}
load()

const handleAvatarSuccess = (res) =>{
  data.form.avatar = res.data
}
</script>