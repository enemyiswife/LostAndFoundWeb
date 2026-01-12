<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input style="width:240px; margin-right: 10px" v-model="data.name" placeholder="请输入名称查询" prefix-icon="Search"></el-input>
      <el-button type="primary">查询</el-button>
      <el-button type="warning">重置</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary">新增</el-button>
      <el-button type="warning">批量删除</el-button>
      <el-button type="info">导入</el-button>
      <el-button type="success">导出</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.tableData" stripe>
        <el-table-column label="日期" prop="date"></el-table-column>
        <el-table-column label="名字" prop="name"></el-table-column>
        <el-table-column label="地址" prop="address"></el-table-column>
      </el-table>
      <div style="margin-top: 15px">
        <el-pagination
            v-model:current-page="data.pageNum"
            v-model:page-size="data.pageSize"
            :pag-sizes="[5,10,15,20]"
            background
            layout="total,sizes,prev,pager,next,jumper"
            :total="data.total"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import {reactive} from "vue";
import request from "@/utils/request.js";
const data= reactive({
  name:null,
  tableData:[
    {id:1, date: '2024-11-11', name: '名字一', address:'地址一'},
    {id:2, date: '2024-11-11', name: '名字二', address:'地址二'},
    {id:3, date: '2024-11-11', name: '名字三', address:'地址三'}
  ],
  pageNum: 1,
  pageSize:10,
  total:47,
  employeeList: []
})

request.get('employee/selectAll').then(res =>{
  console.log(res)
  data.employeeList = res.data
  console.log(data.employeeList)
})
</script>