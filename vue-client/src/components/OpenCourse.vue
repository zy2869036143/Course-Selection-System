<template>
  <el-form
    ref="dynamicValidateForm"
    :model="dynamicValidateForm"
    label-width="120px"
    class="demo-dynamic">

    <el-form-item
      prop="id"
      label="课序号"
      :rules="[
        {
          required: true,
          message: '课序号不能为空',
          trigger: 'blur',
        },
      ]">
      <el-input v-model="dynamicValidateForm.id"></el-input>
    </el-form-item>

    <el-form-item
      prop="name"
      label="课程名"
      :rules="[
        {
          required: true,
          message: '课程名不能为空',
          trigger: 'blur',
        },
      ]">
      <el-input v-model="dynamicValidateForm.name"></el-input>
    </el-form-item>

    <el-form-item
      prop="teacher"
      label="教师姓名"
      :rules="[
        {
          required: true,
          message: '授课教师不能为空',
          trigger: 'blur',
        },
      ]">
      <el-input v-model="dynamicValidateForm.teacher"></el-input>
    </el-form-item>

    <el-form-item
      prop="term"
      label="开课学期"
      :rules="[
        {
          required: true,
          message: '开课学期不能为空',
          trigger: 'blur',
        },
      ]">
       <el-select v-model="dynamicValidateForm.term" placeholder="学期">
          <el-option :label="currentTerm"  :value="currentTerm"></el-option>
       </el-select>
    </el-form-item>

    
    <el-form-item
      prop="credit"
      label="课程学分"
      :rules="[
        {
          required: true,
          message: '开课学期不能为空',
          trigger: 'blur',
        },
      ]">
    <el-input-number  v-model="dynamicValidateForm.credit" :min="1" :max="30" />
    </el-form-item>


    <el-form-item
      prop="property"
      label="课程性质"
      :rules="[
        {
          required: true,
          message: '课程性质不能为空',
          trigger: 'blur',
        },
      ]">
      <el-select  v-model="dynamicValidateForm.property" placeholder="属性">
                <el-option label="必修" value="必修"></el-option>
                <el-option label="限选" value="限选"></el-option>
                <el-option label="任选" value="任选"></el-option>
                <el-option label="公选" value="公选"></el-option>
                <el-option label="选修" value="选修"></el-option>
                <el-option label="交流" value="交流"></el-option>
                <el-option label="其他" value="其他"></el-option>
        </el-select>
    </el-form-item>

    <el-form-item
      prop="capacity"
      label="最大选课人数"
      :rules="[
        {
          required: true,
          message: '最大选课人数不能为空',
          trigger: 'blur',
        },
      ]">
        <el-input-number v-model="dynamicValidateForm.capacity" :min="1" :max="1000" />

    </el-form-item>



    <el-form-item
      prop="beginweek"
      label="起止周次"
      :rules="[
        {
          required: true,
          message: '最大选课人数不能为空',
          trigger: 'blur',
        },
      ]">
        <div> <span>第</span> <el-input-number v-model="dynamicValidateForm.beginweek" :min="1" :max="20" /> <span>周开始</span></div>
        <span>第</span> <el-input-number v-model="dynamicValidateForm.endweek" :min="1" :max="20" /> <span>周结束</span>

    </el-form-item>

    <el-form-item
      v-for="(domain, index) in dynamicValidateForm.timeAndPlace"
      :key="domain.key"
      :label="'时间地点' + index"
      :prop="'timeAndPlace.' + index + '.week'"
      :rules="{
        required: true,
        message: '时间地点不能为空',
        trigger: 'blur',
      }">
       <el-form-item label="授课星期和节次">    
            <el-select  v-model="domain.week" placeholder="星期">
                <el-option label="星期一" value="1"></el-option>
                <el-option label="星期二" value="2"></el-option>
                <el-option label="星期三" value="3"></el-option>
                <el-option label="星期四" value="4"></el-option>
                <el-option label="星期五" value="5"></el-option>
                <el-option label="星期六" value="6"></el-option>
                <el-option label="星期日" value="7"></el-option>
            </el-select>
            <el-select  v-model="domain.time" placeholder="节次">
                <el-option label="第一大节" value="1"></el-option>
                <el-option label="第二大节" value="2"></el-option>
                <el-option label="第三大节" value="3"></el-option>
                <el-option label="第四大节" value="4"></el-option>
                <el-option label="第五大节" value="5"></el-option>
            </el-select>
            <el-input placeholder="对应地点"  v-model="domain.place"></el-input>

        </el-form-item>
      <el-button @click.prevent="removeDomain(domain)">删除</el-button>
    </el-form-item>


    <el-form-item>
      <el-button type="primary" @click="submitForm('dynamicValidateForm')">提交</el-button>
      <el-button @click="addDomain">新建时间地点</el-button>
      <el-button @click="resetForm('dynamicValidateForm')">重置</el-button>
    </el-form-item>


  </el-form>
</template>

<script lang="ts">
import {setUpCourse, getTerm} from '../service/genServ.js'
import { mapState } from 'vuex'
import { ElMessage } from 'element-plus'

export default {
    setup(){
        const success = (cid) => {
        ElMessage({
            message: cid,
            type: 'success',
            })
        }
        const fail = (cid) => {
        ElMessage.error('选课失败。\n'+cid)
    }
    return {
        success,
        fail,
    }
    },
    created(){
      this.getCurrentTerm();
    },
    computed: {
        ...mapState([
        'username', 'jwtToken', 'identity'
        ])
    },
//     setup() {
//         const handleChange = (value: string) => {
//         console.log(value)
//     }
//     return {
//       handleChange,
//     }
//   },

  data() {
    return {
      currentTerm:'',
        form:[],
      dynamicValidateForm: {
        timeAndPlace: [
          {
            key: 1,
            week: '',  // 周次
            time: '',  // 节次
            place: '', // 地点
          },
        ],
        name: '',
        id: '',        
        teacher: '',
        term: '',
        credit: '',
        property: '',
        capacity: '',
        beginweek: '',
        endweek : '',

      },
    }
  },
  methods: {
    getCurrentTerm(){
        getTerm().then(res =>{
            this.currentTerm = res[0]['term'];
        })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
        // alert(this.dynamicValidateForm['id']);
        var place = '';
        var time  = this.dynamicValidateForm['beginweek']+"-"+ this.dynamicValidateForm['endweek']+";";
        for (var i=0;this.dynamicValidateForm['timeAndPlace'][i]!=null;++i ){
            place += this.dynamicValidateForm['timeAndPlace'][i]['place'];
            time += this.dynamicValidateForm['timeAndPlace'][i]['time']+this.dynamicValidateForm['timeAndPlace'][i]['week'];
            if(this.dynamicValidateForm['timeAndPlace'][i+1]!=null){
                time += ";";
                place+=";";
            }
        }
        this.dynamicValidateForm['place'] = place;
        this.dynamicValidateForm['time'] = time;
        this.form = this.dynamicValidateForm;
        delete this.form['timeAndPlace'];
        this.form['userid'] = this.username;
        setUpCourse(this.form).then(res =>{
            if(res=='success'){
                this.success('课程开课成功');
                this.resetForm('dynamicValidateForm');
            }else{
                this.fail('发生了一些错误');
            }
        }
        )

        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    removeDomain(item) {
      const index = this.dynamicValidateForm.timeAndPlace.indexOf(item)
      if (index !== -1) {
        this.dynamicValidateForm.timeAndPlace.splice(index, 1)
      }
    },
    addDomain() {
      this.dynamicValidateForm.timeAndPlace.push({
        key: Date.now(),
        value: '',
      })
    },
  },
}
</script>
