<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input style="width:240px; margin-right: 10px" v-model="data.name" placeholder="请输入物品名称查询" prefix-icon="Search"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary" @click="handleAdd">新增</el-button>
      <el-button type="warning" @click="delBatch">批量删除</el-button>

    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.tableData" stripe  @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column label="物品名称" prop="name"></el-table-column>
        <el-table-column label="图像">
          <template #default="scope">
            <img v-if="scope.row.picture" :src="scope.row.picture" alt="" style="display: block; width: 40px; height: 40px; border-radius: 50%"/>
          </template>
        </el-table-column>
        <el-table-column label="丢失地点" prop="place"></el-table-column>
        <el-table-column label="丢失时间" prop="time"></el-table-column>
        <el-table-column label="详细说明" prop="description" show-overflow-tooltip></el-table-column>
        <el-table-column label="联系方式" prop="phone" show-overflow-tooltip></el-table-column>
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

    <el-dialog title="拾物信息" v-model="data.formVisible"  width="500" destroy-on-close>
      <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="80px" style="padding-right: 40px; padding-top: 20px">
        <el-form-item label="物品名称" prop="name">
          <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入失物名称"></el-input>
        </el-form-item>
        <el-form-item label="图像">
          <el-upload
              limit= 1
              action= "http://localhost:9090/files/upload"
              list-type= "picture"
              :on-success= "handleAvatarSuccess">
            <el-button type="primary">上传图像</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="拾取时间" >
          <el-date-picker
              v-model="data.form.time"
              value-format="YYYY-MM-DD"
              format="YYYY-MM-DD"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="丢失地点" prop="place">
          <el-input v-model="data.form.place" autocomplete="off" placeholder="请输入名称"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
          <el-input v-model="data.form.phone" autocomplete="off" placeholder="请输入联系方式"></el-input>
        </el-form-item>
        <el-form-item label="详细说明" >
          <el-input :rows="3" type="textarea" v-model="data.form.description" autocomplete="off" placeholder="请输入个人介绍"></el-input>
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
  user: JSON.parse(localStorage.getItem('xm-pro-user')),
  rules:{
    name:[
      {required:true, message: '请输入物品名称', trigger: 'blur'}
    ],
    place:[
      {required:true, message: '请输入拾取地点', trigger: 'blur'}
    ],
    phone:[
      {required:true, message: '请输入联系方式', trigger: 'blur'}
    ]
  }
})

const formRef = ref()


const load = () =>{
  request.get('found/selectPage',{
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
  data.form.posterId = data.user.id
  request.post('found/add',data.form).then(res => {
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
  request.put('found/update',data.form).then(res => {
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
    request.delete(`found/delete?id=${id}`).then(res =>{
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
    request.delete('found/deleteBatch',{data: data.ids}).then(res =>{
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
  data.form.picture = res.data
}
</script>