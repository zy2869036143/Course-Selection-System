<template>
    
    <!-- {{myCourse}} -->
  <h3>我开设的课程</h3>
  <el-table title="我开设的课程" :data="myCourse" border style="width: 1350px">
    <el-table-column prop="id" label="课序号" width="150" />
    <el-table-column prop="name" label="课程名" width="150" />
    <el-table-column prop="teacher" label="教师" width="150" />
    <el-table-column prop="time" label="时间" width="150" />
    <el-table-column prop="place" label="地点" width="150" />
    <el-table-column prop="credit" label="学分" width="100" />
    <el-table-column prop="capacity" label="课容量(人)" width="100" />

     <el-table-column prop="currentnumber" label="当前选课人数" width="100" />
    <el-table-column prop="property" label="课程性质" width="100" />
    <el-table-column fixed="right" label="操作1" width="100">
      <template #default="mine">
        <el-button type="text" size="big"  @click.prevent="score(mine.$index, myCourse)">录入成绩</el-button>
      </template>
    </el-table-column> 
    <el-table-column fixed="right" label="操作2" width="100">
      <template #default="mine">
        <el-button type="text" size="big"  @click.prevent="erase(mine.$index, myCourse)">停开</el-button>
      </template>
    </el-table-column> 
    <!-- <el-table-column fixed="right" label="操作3" width="100"> 
      <template #default="mine">
        <el-button type="text" size="big"  @click.prevent="erase(mine.$index, myCourse)">修改信息</el-button>
      </template>
    </el-table-column>  -->
  </el-table>

  <h4>{{this.remindInfor}}</h4>
  <div class="toexcel">
    <el-button  @click="exportExcel" :loading="downloadLoading" type="primary" class="button">导出</el-button>
  </div>
  <el-table :data="students" border id="studentTable" style="width:1201px">
    <el-table-column prop="userName" label="学号" width="150" />
    <el-table-column prop="realName" label="真实姓名" width="150"/>
    <el-table-column prop="gender" label="性别" width="150" />
    <el-table-column prop="university" label="学校" width="150" />
    <el-table-column prop="major" label="专业" width="150" />
    <el-table-column prop="score" label="得分" width="150" />
    <el-table-column fixed="right" label="查看学生"   width="300">
      <template #default="score" >
        <el-input-number v-model="students[score.$index]['score']" :min="0" :max="100" />
      </template>
    </el-table-column> 
  </el-table>
  <el-button @click="setScore">保存成绩</el-button>
  <div id="chart-container"></div>
  
</template>
<script>
import * as echarts from 'echarts';
import {getmine, setScore} from '@/service/genServ.js'
import { mapState } from 'vuex'
import {eraseCourse} from '@/service/genServ.js'
import {score} from '../service/genServ.js'
import { ElMessage } from 'element-plus'
import FileSaver from "file-saver"
import XLSX from "xlsx";
// import { VisualMapComponent } from 'echarts/components';
// import { PieChart } from 'echarts/charts';
// import { LabelLayout } from 'echarts/features';
// import { CanvasRenderer } from 'echarts/renderers';
//echarts.use([VisualMapComponent, PieChart, CanvasRenderer, LabelLayout]);

export default {
    name : 'AlreadyCourse',
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
      return{
          success,
          warn,
      }
    },

    computed: {
        ...mapState([
        'username', 'jwtToken', 'identity',
        ])
    },

    data(){
        return {
            downloadLoading: false,
            myCourse : [],
            ta: '',
            students: [],
            remindInfor: '当前尚未查看任何课程选课学生',
            currentCourseId: null,  
            currentCourseName: 'null',
            stu2score: ""
        }
    },

    created() {
      this.getOpenCourse();
    },
    mounted() {
      this.getPie();
    },

    methods:{
      //获取饼图
      getPie(){
        var chartDom = document.getElementById('chart-container');
        var myChart = echarts.init(chartDom);
        var option;
        var scr1=0;
        var scr2=0;
        var scr3=0;
        var scr4=0;
        var scr5=0;
        if (this.students!=null){
          for (var i=0; this.students[i]!=null;++i){
            if (this.students[i]['score']>=90) scr1++;
            else if (this.students[i]['score']>=80) scr2++;
            else if (this.students[i]['score']>=70) scr3++;
            else if (this.students[i]['score']>=60) scr4++;
            else scr5++;
          }
        }
       option = {
          tooltip: {
            trigger: 'item'
          },
          legend: {
            top: '5%',
            left: 'center'
          },
          series: [
            {
              name: 'Access From',
              type: 'pie',
              radius: ['40%', '70%'],
              avoidLabelOverlap: false,
              label: {
                show: false,
                position: 'center'
              },
              emphasis: {
                label: {
                  show: true,
                  fontSize: '40',
                  fontWeight: 'bold'
                }
              },
              labelLine: {
                show: false
              },
              data: [
                { value: scr1, name: '[90,100]' },
                { value: scr2, name: '[80,90)' },
                { value: scr3, name: '[70,80)' },
                { value: scr4, name: '[60,70)' },
                { value: scr5, name: '[0,60)' }
              ]
            }
          ]
        };
        myChart.setOption(option);
        // if (option && typeof option === 'object') {
        // myChart.setOption(option);
        // }
       // option && myChart.setOption(option);
      },
      // 记录学生成绩
      setScore(){
        this.stu2score="";
        if(this.currentCourseId!=null){
          for(var i=0; this.students[i]!=null; ++i){
            this.stu2score+=this.students[i]["userName"]+":"+this.students[i]["score"]+(this.students[i+1]==null?"":";");
          }
          setScore(this.currentCourseId, this.stu2score).then(res=>{
            if (res=="success"){
              this.getPie();
              alert("学生成绩保存成功。");
            }else if (res=="fail"){
              alert("学生选课正在进行，尚不能录入成绩。等待系统管理员关闭选课后，才可以录入成绩。")
            }
          })
        }
      },
      // 导出选课学生表格
      exportExcel() {
        this.downloadLoading = true
        var xlsxParam = { raw: true }
        // console.log(name)
        /* generate workbook object from table */
        //  .table要导出的是哪一个表格
        var wb = XLSX.utils.table_to_book(document.querySelector("#studentTable"), xlsxParam);
        this.ta = wb;
        /* get binary string as output */
        var wbout = XLSX.write(wb, {
          bookType: "xlsx",
          bookSST: true,
          type: "array"
        });
        try {
          //  name+'.xlsx'表示导出的excel表格名字
          FileSaver.saveAs(
            new Blob([wbout], { type: "application/octet-stream" }),
            this.currentCourseName + ".xlsx"
          );
        } catch (e) {
          if (typeof console !== "undefined") console.log(e, wbout);
        }
        this.downloadLoading = false;
        return wbout;
      },
    // 停开课程
    erase(index, row){
      eraseCourse(row[index]['id'], this.username, 'stopopen').then(res =>{
        if(res=='success'){
          this.success("停开课程成功。课序号:"+row[index]['id']+" 课程名:"+row[index]['name']);
           this.sleep(100);
            this.getOpenCourse();
          if(row[index]['id']==this.currentCourseId){
             this.currentCourseName = '';
             this.currentCourseId ='';
             this.students = [];
          }
        }
      })
    },
    // 查看某一课程的选课学生
    score(index, row){
      this.students=[];
      score(row[index]['id'], this.username).then(res =>{
        for(var i=0; res[i]!=null;++i){
          this.students.push(res[i]);
        }
        this.remindInfor = "正在查看 课序号:"+ row[index]['id']+" "+  "课程名:"+ row[index]['name'] +" 的选课学生"
        this.currentCourseName = row[index]['name'];
        this.currentCourseId =  row[index]['id'];
        this.getPie();
      })
    },
    // 获取我已开设的课程
    getOpenCourse(){
        // 清空
        this.myCourse=[];
        getmine(this.username).then(res => {
        // (判断字典下一项是否为空千万不能用!==,只能用!=)
        for(var i=0; res[i+'']!=null;++i){
            this.myCourse.push(res[i+'']);
        }
        })
    },  
  }

}
</script>

<style scoped>
#chart-container{
  height: 600px;
}
</style>