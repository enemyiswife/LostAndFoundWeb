<template>
  <el-table :data="data.tableData" style="width: 100%" stripe border>
    <el-table-column prop="content" label="内容">
      <template #default="scope">
        <div v-html="scope.row.content" ></div>
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button type="primary"  @click="editContent(scope.row)">
          编辑富文本
        </el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-dialog v-model="data.formContentVisible" title="编辑内容" width="500">
    <div style="padding: 20px">
      <div style="border: 1px solid #ccc; width: 100%">
        <Toolbar
            style="border-bottom: 1px solid #ccc"
            :editor="editorRef"
            :mode="mode"
        />

        <Editor
            style="height: 500px; overflow-y: hidden;"
            v-model="data.form.content"
            :mode="mode"
            :defaultConfig="editorConfig"
            @onCreated="handleCreated"
        />
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="data.formVisible = false">取 消</el-button>
      <el-button type="primary" @click="saveContent">保 存</el-button>
    </div>
  </el-dialog>
</template>

<script setup>
import {reactive} from "vue";
import '@wangeditor/editor/dist/css/style.css'
import {onBeforeUnmount, ref, shallowRef} from "vue"
import {Editor, Toolbar} from '@wangeditor/editor-for-vue'

const data = reactive({
  tableData:[
    {content: '<h1>哈哈哈</h1>'},
    {content:'<h1 style="color: red">哈哈哈</h1>'}
  ],
  formContenVisible: false
})

const editContent = (row) =>{

}
</script>