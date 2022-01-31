<template>

  <!--查询功能 <el-form :inline="true" :model="formInline" class="demo-form-inline">
    <el-form-item label="课程性质">
      <el-select v-model="formInline.property" placeholder="性质">
        <el-option label="全部" value="0"></el-option>
        <el-option label="必修" value="必修"></el-option>
        <el-option label="限选" value="限选"></el-option>
        <el-option label="选修" value="任选"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="课程名">
      <el-input v-model="formInline.name" placeholder="课序号"></el-input>
    </el-form-item>
    <el-form-item label="授课教师">
        <el-input v-model="formInline.teacher" placeholder="教师"></el-input>
    </el-form-item>
    <el-form-item label="课序号">
      <el-input v-model="formInline.id" placeholder="课序号"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">查询</el-button>
    </el-form-item>
  </el-form> -->

  <el-table :data="tableData" border style="width: 1401px;">
    <el-table-column prop="id" label="课序号" width="150" />
    <el-table-column prop="term" label="学期" width="150" />
    <el-table-column prop="name" label="课程名" width="150" />
    <el-table-column prop="teacher" label="教师" width="150" />
    <el-table-column prop="credit" label="学分" width="150" />
    <el-table-column prop="property" label="课程性质" width="150" />
    <el-table-column prop="score" label="分数" width="100" />
    <el-table-column prop="rankk" label="排名" width="100" />
    <el-table-column prop="jidian" label="绩点" width="100" />
    <el-table-column prop="maxScore" label="最高分" width="100" />
    <el-table-column prop="minScore" label="最低分" width="100" />    
    {{tableData}} 
    {{aaa}}
  </el-table>   
</template>

<script>
import {stuScore} from '@/service/genServ.js'
import { mapState } from 'vuex'


export default {
    name:"Score",
    computed: {
      ...mapState([
      'username', 'jwtToken', 'identity',
      ])
    },
    
    data(){
        return{
          aaa: '',
             tableData: [
          // {
          //   sequence: '1',
          //   id: '2',
          //   name: '3',
          //   teacher: '4',
          //   place: 's周三第四大节: 4去；周五',
          //   credit: '6',
          //   property: '7',
          //   capacity: '8',
          //   op: "选课"
          // },
      ],
        formInline: {
         id: '',
         name: '',
         property: '',
          teacher: '',
          weekday: '',
          section: '',
        },
      }
    },
    created() {
      this.getScore();
    },
    methods:{
      getScore(){
        stuScore(this.username).then(res=>{
          this.aaa = res;
          // (判断字典下一项是否为空千万不能用!==,只能用!=)
          for(var i=0; res[i+'']!=null;++i){
            res[i]["jidian"] = (res[i]["score"]/10)-5;
            this.tableData.push(res[i+'']);
          }
        })
      }
    }
}
</script>

<style scoped>

</style>