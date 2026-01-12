import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {path: '/', redirect:"/Login"},
    {path: '/Manager', name: 'Manager', component: () => import('../views/Manager.vue'),children:[
        {path:'Data', name:'Data',meta:{title:'数据页面'}, component: ()=>import('../views/Data.vue')},
        {path:'Home', name:'Home',component: ()=>import('../views/Home.vue')},
        {path:'Employee', name:'Employee',meta:{title:'员工信息'}, component: ()=>import('../views/Employee.vue')},
        {path:'Admin', name:'Admin',meta:{title:'管理员信息'}, component: ()=>import('../views/Admin.vue')},
        {path:'Person', name:'Person',meta:{title:'个人信息'}, component: ()=>import('../views/Person.vue')},
        {path:'Password', name:'Password',meta:{title:'修改密码'}, component: ()=>import('../views/Password.vue')},
        {path:"Found",name:"Found",meta:{title:"找回物品页面"}, component: ()=>import('../views/Found.vue')},
        {path:"Lost",name:"Lost",meta:{title:"丢失物品页面"}, component: ()=>import('../views/Lost.vue')},
        {path:"MyFound",name:"MyFound",meta:{title:"我的找回物品页面"}, component: ()=>import('../views/MyFound.vue')},
        {path:"MyLost",name:"MyLost",meta:{title:"我的丢失物品页面"}, component: ()=>import('../views/MyLost.vue')}
    ]},
    {path: '/Login', name: 'Login', meta:{title:'登录系统'}, component:()=> import('../views/Login.vue')},
    {path: '/Register', name: 'Register', meta:{title:'注册账号'}, component:()=> import('../views/Register.vue')}
  ],
})

export default router
