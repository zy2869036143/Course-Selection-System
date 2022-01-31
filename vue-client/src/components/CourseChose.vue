<template>
  <el-form :inline="true" :model="formInline" class="demo-form-inline">
    <el-form-item label="课程性质">
      <el-select v-model="formInline.property" placeholder="性质">
        <el-option label="全部" value=""></el-option>
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
    <el-form-item label="授课星期">
        <el-select v-model="formInline.weekday" placeholder="星期">
            <el-option label="取消" value="0"></el-option>
            <el-option label="星期一" value="1"></el-option>
            <el-option label="星期二" value="2"></el-option>
            <el-option label="星期三" value="3"></el-option>
            <el-option label="星期四" value="4"></el-option>
            <el-option label="星期五" value="5"></el-option>
            <el-option label="星期六" value="6"></el-option>
            <el-option label="星期日" value="7"></el-option>
        </el-select>
    </el-form-item>
    <el-form-item label="授课节次">
        <el-select v-model="formInline.section" placeholder="节次">
            <el-option label="取消" value="0"></el-option>
            <el-option label="第一大节" value="1"></el-option>
            <el-option label="第二大节" value="2"></el-option>
            <el-option label="第三大节" value="3"></el-option>
            <el-option label="第四大节" value="4"></el-option>
            <el-option label="第五大节" value="5"></el-option>
        </el-select>
    </el-form-item>
    <el-form-item label="课序号">
      <el-input v-model="formInline.id" placeholder="课序号"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">查询</el-button>
    </el-form-item>
  </el-form>


  <el-button type="success" style="margin-left: 10px; size: 20px" @click="drawer = true">
    已选课程
  </el-button>

  <el-drawer v-model="drawer" title="我的选课课程" :with-header="false">
    <el-table :data="myCourse" border style="width: 1620px">
    <el-table-column prop="id" label="课序号" width="100" />
    <el-table-column prop="name" label="课程名" width="100" />
    <el-table-column prop="teacher" label="教师" width="100" />
    <el-table-column prop="time" label="时间" width="100" />
    <el-table-column prop="place" label="地点" width="100" />
    <el-table-column prop="credit" label="学分" width="50" />
    <el-table-column prop="property" label="课程性质" width="100" />
    <el-table-column fixed="right" label="操作" width="100">
      <template #default="mine">
        <el-button type="text" size="big"  @click.prevent="erase(mine.$index, myCourse)">退课</el-button>
      </template>
    </el-table-column> 
  </el-table>
  </el-drawer>

  <el-table :data="tableData" border style="width: 1360px;">
    <el-table-column prop="sequence" label="标号" width="100"></el-table-column>
    <el-table-column prop="id" label="课序号" width="200" />
    <el-table-column prop="name" label="课程名" width="200" />
    <el-table-column prop="teacher" label="教师" width="100" />
    <el-table-column prop="time" label="时间" width="100" />
    <el-table-column prop="place" label="地点" width="200" />
    <el-table-column prop="credit" label="学分" width="100" />
    <el-table-column prop="property" label="课程性质" width="100" />
    <el-table-column prop="remain" label="剩余容量" width="130" />
    <el-table-column fixed="right" label="操作" width="120">
      <template #default="scope">
        <el-button type="text" size="big"
         @click.prevent="handleClick(scope.$index, tableData)">选课</el-button>
      </template>
    </el-table-column> 
      </el-table>   
   <el-button-group style="padding-left: 1196px">
    <el-button type="primary" @click="prePage">上一页</el-button>
    <el-button type="primary" @click="nextPage">下一页</el-button>
  </el-button-group>
</template>

<script lang="ts">
import {getAllCourses} from '../service/genServ.js'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import {postCourse} from '../service/genServ.js'
import {eraseCourse} from '../service/genServ.js'
import {getmine} from '../service/genServ.js'
import { mapState } from 'vuex'

export default {
  name: 'CourseChose',
  setup() {
   // 当用户没有同时查询星期和节次时
    const warn = () => {
      ElMessage({
        showClose: true,
        message: '上课星期和节次必须同时选择',
        type: 'warning',
      })
    }
    // 用户选课成功，消息提醒
    const success = (cid) => {
      ElMessage({
        message: cid,
        type: 'success',
      })
    }
    // 用户选课失败，消息提醒
    const fail = (cid) => {
      ElMessage.error('选课失败。\n'+cid)
    }
    // 当用户点击下一页按钮，而下一页已经没有内容时
    const nextNoContent = () => {
      ElMessage('下一页已无内容')
    }
    return { 
      nextNoContent,
      warn,
      success,
      fail,
      drawer: ref(false),
    }
  },
  // 计算属性
  computed: {
    ...mapState([
      'username', 'jwtToken'
    ])
  },
  // 页面初加载调用方法
  created() {
      this.getCourses();
      this.getMyCourse();
  },
  data() {
    return {
      // 所有课程的信息, 这是一个字典形式的，因为服务器返回的是一个字典，虽然此处定义为数组，后续会被覆盖
      response: [],
      // 我已选课的信息，是一个数组
      myCourse: [],
      begin: 0,
      end : 0,
      a: '',
      count : 10,    // 一页最多展示多少条课程信息
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
  methods: {
    sleep(numberMillis) { 
      var now = new Date(); 
      var exitTime = now.getTime() + numberMillis; 
      while (now.getTime() < exitTime) { 
          now = new Date();
      } 
    },
    // 获取学生已经选择的课程
    getMyCourse(){
        // 清空
        this.myCourse=[];
        getmine(this.username).then(res => {
          // (判断字典下一项是否为空千万不能用!==,只能用!=)
          for(var i=0; res[i+'']!=null;++i){
            this.myCourse.push(res[i+'']);
          }
        })
    },
    // 退课操作
    erase(index, row){
      //alert(row[index]['id']);
      eraseCourse(row[index]['id'], this.username, 'erase').then(res =>{
        if(res=='success'){
          this.success("退课成功。课序号:"+row[index]['id']+" 课程名:"+row[index]['name']);
        }
      })
      this.sleep(100);
      getAllCourses().then(res => {
        this.response = res;
        this.tableData=[];
        for (var i=this.begin; i<this.end ;++i){
          this.tableData.push(this.response[i+'']);
        }
      })
      this.getMyCourse();
    },
    // 选课操作
    handleClick(index, row){
      this.a= row;
      // 判断是否是同一门课程
      var courseid = row[index]['id'];
      for (var p1=0; p1< this.myCourse.length;++p1){
        if(courseid==this.myCourse[p1]['id']){
          this.fail("此课已选");
          return;
        }
      }
      // 判断时间是否冲突
      // 当前点击的课程的时间信息
      var timelist = row[index]['time'].split(";");
      for (var i=1;timelist[i]!=null;++i){
        for (var p=0; p< this.myCourse.length;++p){
          var tls = this.myCourse[p]['time'].split(";");
          for(var s=1;tls[s]!=null;++s ){
            if(tls[s]==timelist[i]){  
              this.fail("与已选课程“"+this.myCourse[p]['name']+"”时间冲突")
              return;
            }
          }
        }
      }
      postCourse(row[index]['id'], this.username).then(res =>{
          if(res=='success'){
            this.success("选课成功。课序号:"+row[index]['id']+" 课程名:"+row[index]['name']);
          }else{
            this.fail("课余量为0。课序号:"+row[index]['id']+" 课程名:"+row[index]['name']);
          }
      })
      // 用户点击选课后，更新本地课程信息以和服务器端同步
      // 但是需要稍等一下再执行，因为更新数据操作比较耗时。
      this.sleep(100);
      getAllCourses().then(res => {
        this.response = res;
        this.tableData=[];
        for (var i=this.begin; i<this.end ;++i){
          this.tableData.push(this.response[i+'']);
        }
      })
      // 更新用户已经选择的课程
      this.getMyCourse();
    },
    // 下一页方法
    nextPage(){
      if (this.response[this.end+'']==null) {this.nextNoContent(); return;}
      this.begin+=this.count;
      this.tableData=[];
      for (var i=this.begin; this.response[i+'']!=null && i<this.begin+this.count ;++i){
        this.tableData.push(this.response[i+'']);
        this.end = i+1;
      }
    },
    // 上一页方法
    prePage(){
      if(this.begin-this.count<0)  {this.begin=this.count;}
      this.begin -= this.count;
      this.end -= this.count;
      this.tableData=[];
      for (var i=this.begin; this.response[i+'']!=null && i<this.begin+this.count;++i){
        this.tableData.push(this.response[i+'']);
        this.end=i+1;
      }
    },
    // 根据行內表单查询课程
    onSubmit(){     
      if ((this.formInline.weekday=="0" || this.formInline.weekday=="" ) != (this.formInline.section=="0" || this.formInline.section=="")){
        this.warn();
        return;
      }
      var infor = [];
      var hastime = false;
      if (this.formInline.teacher!='' ){
        infor.push('teacher');
      } 
      if (this.formInline.name!='' ){
        infor.push('name');
      } 
      if (this.formInline.id!=''){
        infor.push('id');
        alert("1id");
      }
      if ((this.formInline.weekday!="0" && this.formInline.weekday!=""  ) && 
          (this.formInline.section!="0" && this.formInline.section!="")){
        infor.push('time');
        hastime = true;
      }
      if(this.formInline.property!='' && this.formInline.property!='0'){
        infor.push('property');
      }
      if(infor.length!=0){
        this.tableData=[];
        // 取出一节课，他不为空
        for (var i=0; this.response[i+'']!=null ;++i){
          // 判断是否查询老师，否的话进入下一层判断，是的话判断。
          if(this.formInline.teacher=='' || this.response[i+""]['teacher']==this.formInline.teacher){
            // 判断是否查询id,
            if(this.formInline.id=='' || this.response[i+""]['id']==this.formInline.id){
              if(this.formInline.property=='' || this.formInline.property=='0'  || this.response[i+""]['property']==this.formInline.property){
                if(this.formInline.name=='' || this.response[i+""]['name'].includes(this.formInline.name)){
                  if (!hastime || this.response[i+""]['time'].includes(this.formInline.section+this.formInline.weekday)){
                    this.tableData.push(this.response[i+'']);  
                    this.end=i+1;
                  }
                }
              }
            }
          }
        }
      }else{
        this.tableData = this.response;
      }
    },
    // 页面初次加载时访问数据库
    getCourses(){
      getAllCourses().then(res =>{
        this.response = res;
        for (var i=this.begin; i<this.count && this.response[i+'']!=null;++i){
          this.tableData.push(this.response[i+'']);
          this.end=i+1;
        }
      })
    }
  },
}
</script>