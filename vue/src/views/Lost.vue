<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input style="width:240px; margin-right: 10px" v-model="data.name" placeholder="请输入物品名称查询" prefix-icon="Search"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
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
  ids: []
})

const formRef = ref()


const load = () =>{
  request.get('lost/selectPage',{
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
  request.post('employee/add',data.form).then(res => {
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
  request.put('employee/update',data.form).then(res => {
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
    request.delete(`employee/delete?id=${id}`).then(res =>{
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
    request.delete('employee/deleteBatch',{data: data.ids}).then(res =>{
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