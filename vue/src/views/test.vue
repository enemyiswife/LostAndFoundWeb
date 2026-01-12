
<template>
  <div style="background-color: #ff0000">
    Homeview
  </div>

  <div style="font-size : 30px">
    number
  </div>

  <div>
    <input v-model = "number"/>
  </div>
  <div v-if = "name === '小猪佩奇'">小猪佩奇</div>
  <div v-if = "name === '小猪乔治'">小猪乔治</div>
  <div @click = "click"> <el-button>Default</el-button> </div>>
  <div style = "margin : 20px"> <el-icon><Plus /></el-icon> </div>
  <el-button type="danger" :icon="Delete" circle />
</template>


<script setup>
import {ref, reactive, onMounted} from "vue"
import {Delete} from "@element-plus/icons-vue"
const number = ref(1)
const name = ref("小猪乔治")

const click = () =>{
  alert("组件被点击")
}

onMounted(() => {
  console.log("页面加载完成")
})
</script>