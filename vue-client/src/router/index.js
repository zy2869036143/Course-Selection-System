import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/views/Login'
import home from  '@/views/Home.vue'
import Register from '@/components/Register.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: home
  },
  {
    path: '/login',
    name: 'login',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: Login
  },
  {
    path: '/register',
    name: 'register',
    component: Register
  },
  {
    path: '/userInfor',
    name: 'userInfor',
    component: () => import('@/components/UserInfor.vue')
  },
  {
    path: '/sidebar',
    name: 'sidebar',
    component: () => import('@/components/Sidebar.vue')
  },
  {
    path: '/mysidebar',
    name: 'mySidebar',
    component: () => import('@/components/MySideBar.vue')
  },{
    path: '/chosecourses',
    name: 'CourseChose',
    component: () =>import('@/components/CourseChose.vue')
  },
  {
    path: '/DashSample',
    name: 'DashSample',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '@/views/dashboard/DashSample.vue')
  },

  {
    path: '/ChangePassword',
    name: 'ChangePassword',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '@/views/dashboard/ChangePassword.vue')
  },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
