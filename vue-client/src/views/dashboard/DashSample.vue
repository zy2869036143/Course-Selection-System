<template>
<Navi/>
  <el-form
    ref="ruleForm"
    :model="osys"
    :rules="rules"
    label-width="120px"
    class="form"
  >
  <el-form-item prop="term" class="item">
    <div v-if="exist">
        <span>当前正在开放{{currentTerm}}学期的选课系统</span>
        <el-button @click="end">停止开放选课系统</el-button>  
    </div>


    <!-- <div>
      <el-input-number  v-model="term" :min="2021"/>——————{{term+1}}——————<el-input-number  v-model='sequence' :min="1" :max="4"/>
            <el-button @click="submit">开放</el-button>

    </div> -->
    <div class = "remind" v-if="!exist">
      <h2>{{"开放"+term+"到"+(term+1)+"学年, 第"+sequence+"学期选课系统"}}</h2>
      <el-input-number  v-model="term" :min="2021"/>——————{{term+1}}——————<el-input-number  v-model='sequence' :min="1" :max="4"/>
      <el-button type="primier" @click="submit">开放</el-button>
    </div>
  </el-form-item>
  </el-form>
  
</template>

<script>
import Navi from '@/components/Navi'
import {openCourseSYS,endCourseSYS,getTerm} from '@/service/genServ.js'
export default {
  name: 'ChangePassword',
  components: {
    Navi
  },
  data() {
    return {
      currentTerm:'',
      exist: false,
      term : 2020,
      sequence: 0, 
      passw: 'password',
      oldPassword: '',
      newPassword: '',
      newPassword1: '',
    }
  },
  created(){
    this.getCurrentTerm();
  },
  methods: {
    getCurrentTerm(){
      getTerm().then(res =>{
        this.currentTerm = res[0]['term'];
        this.exist = true;
      })
    },
    submit() {
        if(this.exist){
          alert("当前正在开放"+this.currentTerm+"学期的选课系统。\n请先关闭后才能再开启新一学期的选课系统。")
          return false;
        }
        if (this.sequence!=0){
          openCourseSYS(this.term, this.sequence).then(res =>{
            if (res=="success"){
              alert("选课系统开放成功。")
            }
          })
        }
    },
    end(){
      endCourseSYS().then(res=>{
        if (res=="success"){
          alert("选课系统已停止开放。")
          this.exist = false;
        }
      })
    }
  }
}
</script>
<style scoped>
.form{
  padding-left: 200px;
  padding-top: 100px;
}
.remind{
   padding-left: 100px;
  padding-top: 50px;
}
span{
  font-size: 20px;
}
</style>