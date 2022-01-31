<template>
  <div class="side">
   
    <el-menu
      default-active="2"
      class="el-menu-vertical-demo"
      :collapse="isCollapse"
      @open="handleOpen"
      @close="handleClose"
      style="{background: red}"
    >

      <el-menu-item index="1">
          <i class="el-icon-s-custom"></i>
        <template #title>个人信息</template>
      </el-menu-item>
       <el-menu-item index="2">
        <i class="el-icon-setting"></i>
        <template #title>修改信息</template>
      </el-menu-item>
      <el-menu-item index="3">
        <i class="el-icon-menu"></i>
        <template #title>选课入口</template>
      </el-menu-item>
      <el-menu-item index="4">
        <i class="el-icon-document"></i>
        <template #title>我的课表</template>
      </el-menu-item>
     
    </el-menu>
  </div>
</template>


<script lang="ts">
import {ref } from 'vue'

export default {
    name: 'sidebar',
    props: ['inPage'],
    data() {
        return {
            clkpage : "userInfor",
        }
    },
    methods: {
        al(num) {
            document.getElementById('sub-menu'+num).style.display='inline' ;
        },
        // 子组件给父组件传值
        toFather() {
            this.$emit('changeCCP',this.clkpage);
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

<style>
.el-menu-vertical-demo{
    color: red;
}


.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}

.side {
  height: 100%;
  color: rebeccapurple;
}
</style>
