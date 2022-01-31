<template>
  <div class="side">
    <el-menu
      default-active="2"
      class="el-menu-vertical-demo"
      :collapse="isCollapse"
      @open="handleOpen"
      @close="handleClose">
      <el-submenu index="1">
          <template #title class="top">
            <i class="el-icon-location"></i>
            <span>{{lable[0][0]}}</span>
          </template>
            <el-menu-item class="submenu" index="1-1" @click="toFather(component[0])">{{lable[0][1]}}</el-menu-item>
            <!-- 公告通知功能 <el-menu-item index="1-2" class="submenu" @click="toFather(component[1])" >{{lable[0][2]}}</el-menu-item> -->
      </el-submenu>
      
      <el-submenu index="2">
         <template #title>
            <i class="el-icon-menu"></i>           
            <span>{{lable[1][0]}}</span>
          </template>
          <el-menu-item index="2-1" class="submenu" @click="toFather(component[2])" >{{lable[1][1]}}</el-menu-item>
      </el-submenu>

      <el-submenu index="3">
         <template #title>
            <i class="el-icon-document"></i>
            <span>{{lable[2][0]}}</span>
          </template>
          <el-menu-item  class="submenu" index="3-1" @click="toFather(component[3])">{{lable[2][1]}}</el-menu-item>
          <el-menu-item class="submenu" index="3-2" @click="toFather(component[4])">{{lable[2][2]}}</el-menu-item>
      </el-submenu>

    </el-menu>

  </div>
</template>


<script lang="ts">
import {ref } from 'vue'
import 'element-plus'
import {getAllCourses} from '../service/genServ.js'
import { mapState } from 'vuex'

export default {
    name: 'sidebar',
    props: ['inPage'],
    computed: {
      ...mapState([
      'username', 'jwtToken', 'identity'
      ])
    },
    data() {
        return {
            clkpage : "userInfor",
            response: '没有',
            stuLable: [
              ['我的主页','个人信息', '公告通知'],
              ['学籍成绩', '我的成绩'],
              ['培养管理', '我的课表', '选课入口']
            ],
            teaLable:[
              ['我的主页', '个人信息', '公告发布'],
              ['教学日历', '我的课表'],
              ['课程管理', '课程开设', '已开课程'],
            ],
    //         OpenCourse,
    // AlreadyCourse,
    // ReleaseNotice,
            stuComponent: ['Edit', 'Notice' ,'Score', 'courseTable', 'Entrance'],
            teaComponent: ['Edit', 'ReleaseNotice', 'courseTable', 'OpenCourse', 'AlreadyCourse'],
            //teaComponent: ['Edit', 'Notice' ,]
            lable :  [
              // ['我的主页','个人信息', '公告通知'],
              // ['学籍成绩', '我的成绩'],
              // ['培养管理', '我的课表', '选课入口']
            ],
            component: [],
        }
    },
    created(){
      if(this.identity=='stu'){
        this.lable = this.stuLable;
        this.component = this.stuComponent;
      }else{
        this.lable = this.teaLable;
        this.component = this.teaComponent;
      }
    },
    methods: {
        al(num) {
            document.getElementById('sub-menu'+num).style.display='inline' ;
        },
        // 子组件给父组件传值
        toFather(page) {
            this.clkpage = page;
            this.$emit('changeCCP',this.clkpage);
        },
        getCourses(){
      //  samplePost()
      // .then(res => {
      //   this.response = res
      // })
        alert("获取课程");
        getAllCourses().then(res =>{
          this.response = res;
          alert(res);
        })
    }
    },
  setup() {
    const isCollapse = ref(false)
    const handleOpen = (key, keyPath) => {
      console.log(key, keyPath)
    }
    const handleClose = (key, keyPath) => {
      console.log(key, keyPath)
    }
    return {
      isCollapse,
      handleOpen,
      handleClose,
    }
  },
}
</script>


<style scoped>
span{
  color: white;
}
span:hover{
  color: black;
}
.el-icon-location, .el-icon-menu, .el-icon-document{
  color: white;
}
.el-icon-location:hover, .el-icon-menu:hover, .el-icon-document:hover{
  color: black;
}
.el-menu-vertical-demo{
  border-radius: 15px;
  background-image: linear-gradient(to bottom right , rgb(118, 76, 163), rgb(92, 103, 211));
  color: white;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 195px;
  border-radius: 20px;

  /* background-color: blue; */
  min-height: 100%;
}
.submenu{
  color: gray;
  width: 0px;
  /* background-color: rgb(6, 150, 150); */
}
/* .side {
  height: 100%;
} */
</style>

